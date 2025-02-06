package pl.psi.spells;

import com.google.common.collect.Range;
import lombok.Getter;
import pl.psi.creatures.CreatureStatisticIf;

@Getter
public class SpellBonus implements CreatureStatisticIf {
    private final String name;
    private int attack;
    private final int armor;
    private final int maxHp;
    private final int moveRange;
    private final Range<Integer> damage;
    private final int tier;
    private final String description;
    private final boolean isUpgraded;
    private final boolean isUndead;

    public SpellBonus(String aName, int aAttack, int aArmor, int aMaxHp, int aMoveRange,
                      Range<Integer> aDamage, int aTier, String aDescription,
                      boolean aIsUpgraded, boolean aIsUndead) {
        name = aName;
        attack = aAttack;
        armor = aArmor;
        maxHp = aMaxHp;
        moveRange = aMoveRange;
        damage = aDamage;
        tier = aTier;
        description = aDescription;
        isUpgraded = aIsUpgraded;
        isUndead = aIsUndead;
    }

    public void changeAttack(int change) {
        attack += change;
    }

}
