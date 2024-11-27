package pl.psi;

import lombok.Getter;

public class Spell {

    @Getter
    private final String name;

    @Getter
    private final int damage;

    @Getter
    private final int level;

    @Getter
    private final int manaCost;

    public Spell(final String aName, final int aDamage, final int aLevel, final int aManaCost) {
        name = aName;
        damage = aDamage;
        level = aLevel;
        manaCost = aManaCost;
    }


}
