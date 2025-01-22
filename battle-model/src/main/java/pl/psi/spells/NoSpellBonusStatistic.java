package pl.psi.spells;

import com.google.common.collect.Range;
import pl.psi.creatures.CreatureStatisticIf;

public class NoSpellBonusStatistic implements CreatureStatisticIf {
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

    public NoSpellBonusStatistic() {
        this.name = "No spell bonus statistic";
        this.attack = 0;
        this.armor = 0;
        this.maxHp = 0;
        this.moveRange = 0;
        this.damage = Range.closed(0, 0);
        this.tier = 1;
        this.description = "No spell bonus, nothing special";
        this.isUpgraded = false;
        this.isUndead = false;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getAttack() {
        return attack;
    }

    @Override
    public int getArmor() {
        return armor;
    }

    @Override
    public int getMaxHp() {
        return maxHp;
    }

    @Override
    public int getMoveRange() {
        return moveRange;
    }

    @Override
    public Range<Integer> getDamage() {
        return damage;
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isUpgraded() {
        return isUpgraded;
    }

    @Override
    public boolean isUndead() {
        return isUndead;
    }

    @Override
    public void changeAttack(int boost) {
        attack += boost;
    }
}
