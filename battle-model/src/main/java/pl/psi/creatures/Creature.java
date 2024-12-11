package pl.psi.creatures;//  ******************************************************************

//
//  Copyright 2022 PSI Software AG. All rights reserved.
//  PSI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms
//
//  ******************************************************************

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Random;

import lombok.Setter;
import pl.psi.AppliedSpell;
import pl.psi.TurnQueue;

import com.google.common.collect.Range;

import lombok.Getter;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
@Getter
public class Creature implements PropertyChangeListener {
    private CreatureStatisticIf stats;
    @Setter
    private int amount;
    @Setter
    private int currentHp;
    private int counterAttackCounter = 1;
    private final int MINIMAL_DAMAGE = 0;
    private ArrayList<AppliedSpell> appliedSpells;
    private DamageCalculatorIf calculator;

    Creature() {
    }

    private Creature(final CreatureStatisticIf aStats, final DamageCalculatorIf aCalculator,
                     final int aAmount) {
        stats = aStats;
        amount = aAmount;
        currentHp = stats.getMaxHp();
        calculator = aCalculator;
        appliedSpells = new ArrayList<AppliedSpell>();
    }

    public void attack(final Creature aDefender) {
        if (isAlive()) {
            final int damage = getCalculator().calculateDamage(this, aDefender);
            final int damageWithRoundBonus = Math.max(damage + getRoundDamageBonus(), MINIMAL_DAMAGE);
            System.out.println("Damage: " + damage + "\nDamage with round bonus: " + damageWithRoundBonus);
            aDefender.applyDamage(damageWithRoundBonus);
            if (canCounterAttack(aDefender)) {
                System.out.println("Counter attack");
                counterAttack(aDefender);
            }
        }
    }

    public int getRoundDamageBonus(){
        int bonus = 0;

        System.out.println("Checking applied spells");
        for(AppliedSpell spell : appliedSpells){
            bonus += spell.getSpell().getDamageBonus();
            System.out.println("Spell: " + spell.getSpell().getName() + " bonus: " + spell.getSpell().getDamageBonus());
        }
        System.out.println("Bonus: " + bonus);

        return bonus;
    }

    public void decreaseAppliedSpellsRound(){
        for(AppliedSpell spell : appliedSpells){
            spell.decreaseRoundsLeft();
        }
    }

    public void clearNotActiveSpells(){
        if(appliedSpells.isEmpty()) return;
        appliedSpells.removeIf(spell -> !spell.isActive());
    }

    public boolean isAlive() {
        return getAmount() > 0;
    }

    public void applyMagicDamage(int damage) {
        applyDamage(damage); // todo fix not always reducing
    }

    private void applyDamage(final int aDamage) {
        int hpToSubstract = aDamage % getMaxHp();
        int amountToSubstract = Math.round(aDamage / getMaxHp());

        int hp = getCurrentHp() - hpToSubstract;
        if (hp <= 0) {
            setCurrentHp(getMaxHp() - hp);
            setAmount(getAmount() - 1);
        }
        else{
            setCurrentHp(hp);
        }
        setAmount(getAmount() - amountToSubstract);
    }

    private int getMaxHp() {
        return stats.getMaxHp();
    }

    protected void setCurrentHp(final int aCurrentHp) {
        currentHp = aCurrentHp;
    }

    private boolean canCounterAttack(final Creature aDefender) {
        return aDefender.getCounterAttackCounter() > 0 && aDefender.getCurrentHp() > 0;
    }

    private void counterAttack(final Creature aAttacker) {
        final int damage = aAttacker.getCalculator()
                .calculateDamage(aAttacker, this);
        applyDamage(damage);
        aAttacker.counterAttackCounter--;
    }

    Range<Integer> getDamage() {
        return stats.getDamage();
    }

    int getAttack() {
        return stats.getAttack();
    }

    int getArmor() {
        return stats.getArmor();
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (TurnQueue.END_OF_TURN.equals(evt.getPropertyName())) {
            counterAttackCounter = 1;
        }
    }

    protected void restoreCurrentHpToMax() {
        currentHp = stats.getMaxHp();
    }

    public String getName() {
        return stats.getName();
    }

    public int getMoveRange() {
        return stats.getMoveRange();
    }

    public static class Builder {
        private int amount = 1;
        private DamageCalculatorIf calculator = new DefaultDamageCalculator(new Random());
        private CreatureStatisticIf statistic;

        public Builder statistic(final CreatureStatisticIf aStatistic) {
            statistic = aStatistic;
            return this;
        }

        public Builder amount(final int aAmount) {
            amount = aAmount;
            return this;
        }

        Builder calculator(final DamageCalculatorIf aCalc) {
            calculator = aCalc;
            return this;
        }

        public Creature build() {
            return new Creature(statistic, calculator, amount);
        }
    }

    @Override
    public String toString() {
        return getName() + System.lineSeparator() + getAmount();
    }
}
