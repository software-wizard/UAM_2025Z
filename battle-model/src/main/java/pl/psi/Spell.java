package pl.psi;

import lombok.Getter;
import pl.psi.creatures.Creature;

@Getter
public class Spell {

    private final String name;

    private final int damage;

    private final int level;

    private final int manaCost;

    @Getter
    private final int damageBonus;

    public Spell(final String aName, final int aDamage, final int aLevel, final int aManaCost, final int aDamageBonus) {
        name = aName;
        damage = aDamage;
        level = aLevel;
        manaCost = aManaCost;
        damageBonus = aDamageBonus;
    }

    public static class Builder {
        private String name;
        private int damage = 0;
        private int level = 1;
        private int manaCost = 0;
        private int damageBonus = 0;
        public Builder name(String aName) {
            name = aName;
            return this;
        }

        public Builder damage(int aDamage) {
            damage = aDamage;
            return this;
        }

        public Builder level(int aLevel) {
            level = aLevel;
            return this;
        }

        public Builder manaCost(int aManaCost) {
            manaCost = aManaCost;
            return this;
        }

        public Builder damageBonus(int aDamageBonus) {
            damageBonus = aDamageBonus;
            return this;
        }

        public Spell build() {
            return new Spell(name, damage, level, manaCost, damageBonus);
        }

    }

    public void castSpell(Creature aDefender) {
        aDefender.applyMagicDamage(damage);
        aDefender.getAppliedSpells().add(
                new AppliedSpell(this, 3)
        );
    }
}
