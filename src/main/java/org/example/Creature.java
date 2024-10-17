package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Creature {
    private int attack;
    private int defence;
    private int damage;
    private int maxHp;
    private int currentHp;
    private int amount;

    private DefaultDamageCalculator dmgCalc;

    public Creature(DefaultDamageCalculator dmgCalc) {
        this.dmgCalc = dmgCalc;
    }

    public void attack(Creature defender) {
        defender.applyDamage(dmgCalc.calculateDamage(this, defender));
        counterAttack(this, dmgCalc.calculateDamage(defender, this));
    }

    private void counterAttack(Creature creature, int damage) {
        creature.applyDamage(damage);
    }

    private void applyDamage(int aDamageToApply) {
        int stackToKill = aDamageToApply / maxHp;
        amount = amount - stackToKill;
        int restOfDamage = aDamageToApply - (stackToKill * maxHp);
        currentHp = currentHp - restOfDamage;
    }
}
