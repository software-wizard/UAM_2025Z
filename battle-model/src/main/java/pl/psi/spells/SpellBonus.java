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

    public SpellBonus(String name, int attack, int armor, int maxHp, int moveRange,
                      Range<Integer> damage, int tier, String description,
                      boolean isUpgraded, boolean isUndead) {
        this.name = name;
        this.attack = attack;
        this.armor = armor;
        this.maxHp = maxHp;
        this.moveRange = moveRange;
        this.damage = damage;
        this.tier = tier;
        this.description = description;
        this.isUpgraded = isUpgraded;
        this.isUndead = isUndead;
    }

    public void changeAttack(int change) {
        attack += change;
    }

}
