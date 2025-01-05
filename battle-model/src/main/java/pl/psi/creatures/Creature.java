package pl.psi.creatures;//  ******************************************************************

//
//  Copyright 2022 PSI Software AG. All rights reserved.
//  PSI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms
//
//  ******************************************************************

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import lombok.Setter;
import pl.psi.AppliedSpell;
import pl.psi.StatsBonusType;
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
    private final int MINIMAL_STAT_VALUE = 0;
    private ArrayList<AppliedSpell> appliedSpells;
    private DamageCalculatorIf calculator;
    private List<Buff> buffs = new ArrayList<>();

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
            final int damageWithBonus = getAttackWithBonus();
            System.out.println("Damage: " + damage + "\nDamage with attack bonus: " + damageWithBonus);
            aDefender.applyDamage(damageWithBonus);
            if (canCounterAttack(aDefender)) {
                System.out.println("Counter attack");
                counterAttack(aDefender);
            }
        }
    }

    public int getBonus(StatsBonusType statsBonusType){
        int bonus = 0;
        switch(statsBonusType){
            case ARMOR:
                for(AppliedSpell appliedSpell : appliedSpells){
                    bonus += appliedSpell.getSpell().getSpellBonus().getArmor();
                }
                break;
            case ATTACK:
                for(AppliedSpell appliedSpell : appliedSpells){
                    bonus += appliedSpell.getSpell().getSpellBonus().getAttack();
                }
                break;
            case MOVE_RANGE:
                for(AppliedSpell appliedSpell : appliedSpells){
                    bonus += appliedSpell.getSpell().getSpellBonus().getMoveRange();
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown statsBonusType");
        }
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

    // undead is a type of creature:
    public boolean isUndead()
    {
        return stats.isUndead();
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

    public int getAttack() {
        return stats.getAttack();
    }

    public int getAttackWithBonus(){
        return Math.max(stats.getAttack() + getBonus(StatsBonusType.ATTACK), MINIMAL_STAT_VALUE);
    }

    int getArmor() {
        return stats.getArmor();
    }

    public int getArmorWithBonus(){
        return Math.max(stats.getArmor() + getBonus(StatsBonusType.ARMOR), MINIMAL_STAT_VALUE);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (TurnQueue.END_OF_TURN.equals(evt.getPropertyName())) {
            counterAttackCounter = 1;
            // tutaj te wszystkie buffy powinny się robić
            // jakieś np. filtry ktowe filtrują które buffy dodaja ozdrowia itd i to
            // edytuja w creature statistics
            processBuffsAtTurnStart();
            buffs.forEach(buff -> buff.apply(this));

        }
        if (TurnQueue.NEXT_CREATURE.equals(evt.getPropertyName())) {
            buffs.forEach(buff -> buff.apply(this));
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

    public int getMoveRangeWithBonus(){
        return Math.max(stats.getMoveRange() + getBonus(StatsBonusType.MOVE_RANGE), MINIMAL_STAT_VALUE);
    }

    public void takeDamage(int damage) {
        int remainingHp = currentHp - damage;
        if (remainingHp <= 0) {
            int remainingDamage = Math.abs(remainingHp);
            int unitsLost = (int) Math.ceil((double) remainingDamage / getMaxHp());
            setAmount(Math.max(0, amount - unitsLost));
            currentHp = (amount > 0) ? getMaxHp() - (remainingDamage % getMaxHp()) : 0;
        } else {
            currentHp = remainingHp;
        }

        if (amount <= 0) {
            System.out.println(getName() + " has been defeated.");
        }
    }

    public void processBuffsAtTurnStart() {
        Iterator<Buff> iterator = buffs.iterator();
        while (iterator.hasNext()) {
            Buff buff = iterator.next();
            buff.decrementTurn();
            if (buff.isExpired()) {
                buff.onExpire(this);
                iterator.remove();
            }
        }
    }

    public void addBuff(Buff aBuff){
        buffs.add(aBuff);
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
