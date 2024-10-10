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

    public void attack(Creature defender) {
        defender.applyDamage(damage);
    }

    private void applyDamage(int aDamageToApply) {
        int stackToKill = aDamageToApply / maxHp;
        amount = amount - stackToKill;
        int restOfDamage = aDamageToApply - (stackToKill * maxHp);
        currentHp = currentHp - restOfDamage;
    }
}
