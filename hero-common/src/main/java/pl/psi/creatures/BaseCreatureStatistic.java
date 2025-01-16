package pl.psi.creatures;

import com.google.common.collect.Range;

public class BaseCreatureStatistic implements CreatureStatisticIf {
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

    public BaseCreatureStatistic() {
        this.name = "Base creature";
        this.attack = 5;
        this.armor = 5;
        this.maxHp = 5;
        this.moveRange = 4;
        this.damage = Range.closed(1, 3);
        this.tier = 1;
        this.description = "Base creature, nothing special";
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
