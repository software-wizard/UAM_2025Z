package pl.psi;

import lombok.Getter;
import pl.psi.creatures.Creature;

@Getter
public class Spell {

    private final String name;

    private final int damage;

    private final int level;

    private final int manaCost;

    public Spell(final String aName, final int aDamage, final int aLevel, final int aManaCost) {
        name = aName;
        damage = aDamage;
        level = aLevel;
        manaCost = aManaCost;
    }

    public void castSpell(Creature aDefender) {
        aDefender.applyMagicDamage(damage);
    }
}
