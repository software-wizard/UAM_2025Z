package pl.psi.creatures;

import com.google.common.collect.Range;

import lombok.Getter;

@Getter
public enum SpellBonusStatistic implements CreatureStatisticIf
{
    WEAKEN_ATTACK(toSpellNameFormat("Weaken Attack"), -5, 0, 0, 0, Range.closed(0, 0), 1, "Weaken attack spell bonus", false, false, false),
    EXTRA_ATTACK(toSpellNameFormat("Extra Attack"), 5, 0, 0, 0, Range.closed(0, 0), 1, "Extra Damage spell bonus", false, false, false),
    EXTRA_MOVE_RANGE(toSpellNameFormat("Extra Move Range"), 0, 0, 0, 10, Range.closed(0, 0), 1, "Extra Move Range spell bonus", false, false, false),
    NO_BONUS(toSpellNameFormat("Empty"), 0, 0, 0, 0, Range.closed(0,0), 0, "No bonus to the spell", false, false, false);

    private final String name;
    private int attack;
    private final int armor;
    private final int maxHp;
    private final int moveRange;
    private final Range< Integer > damage;
    private final int tier;
    private final String description;
    private final boolean isUpgraded;
    private final boolean isUndead;
    private final boolean isRanged;

    SpellBonusStatistic( final String aName, final int aAttack, final int aArmor, final int aMaxHp,
                       final int aMoveRange, final Range< Integer > aDamage, final int aTier, final String aDescription,
                       final boolean aIsUpgraded, final boolean aIsUndead, final boolean aIsRanged )
    {
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
        isRanged = aIsRanged;
    }

    public void changeAttack(int change){
        attack = attack + change;
    }

    private static String toSpellNameFormat(String spellName){
        return String.format("Spell: %s bonus", spellName);
    }
    String getTranslatedName()
    {
        return name;
    }
}
