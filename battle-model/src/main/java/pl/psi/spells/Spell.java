package pl.psi.spells;

import lombok.Getter;
import pl.psi.GameEngine;
import pl.psi.Point;
import pl.psi.creatures.Creature;

@Getter
public class Spell {
    private final SpellName name;
    private final int damage;
    private final int level;
    private final int manaCost;
    private final int spellBonusRoundsDuration;
    private final int radius;
    private final SpellBonus spellBonus;

    public Spell(final SpellName aName, final int aDamage, final int aLevel, final int aManaCost, final SpellBonus aSpellBonus, final int aSpellBonusRoundsDuration, final int aRadius) {
        name = aName;
        damage = aDamage;
        level = aLevel;
        manaCost = aManaCost;
        spellBonusRoundsDuration = aSpellBonusRoundsDuration;
        spellBonus = aSpellBonus;
        radius = aRadius;
    }

    public static class Builder {
        private SpellName name;
        private int damage = 0;
        private int level = 1;
        private int manaCost = 0;
        private int spellBonusRoundsDuration = 3;
        private int radius = 0;
        private SpellBonus spellBonus = SpellBonusFactory.createSpellBonus(SpellBonusName.NONE);
        public Builder name(SpellName aName) {
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

        public Builder spellBonusRoundsDuration(int aSpellBonusRoundsDuration){
            spellBonusRoundsDuration = aSpellBonusRoundsDuration;
            return this;
        }

        public Builder spellBonus(SpellBonusName aSpellBonusName){
            spellBonus = SpellBonusFactory.createSpellBonus(aSpellBonusName);
            return this;
        }

        public Builder radius(int aRadius) {
            radius = aRadius;
            return this;
        }
        public Spell build() {
            return new Spell(name, damage, level, manaCost, spellBonus, spellBonusRoundsDuration, radius);
        }
    }

    public void castSpell(Creature aDefender) {
        System.out.printf("Casting spell '%s' to a %s\n", getName(), aDefender.getName());
        aDefender.applyMagicDamage(damage);
        aDefender.getAppliedSpells().add(
                new AppliedSpell(this, spellBonusRoundsDuration)
        );
    }

    public void castSplashSpell(Point aTargetPoint, GameEngine aGameEngine) { // lista terget pointów i polimorfizm lub przekazanie jednej metody
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                Point targetPoint = new Point(aTargetPoint.getX() + dx, aTargetPoint.getY() + dy);
                if (aGameEngine.getCreature(targetPoint).isPresent()) {
                    Creature target = aGameEngine.getCreature(targetPoint).get();
                    target.applyMagicDamage(damage);
                }
            }
        }
    }

    @Override
    public String toString() {
        return name.getDisplayName() + " (Level: " + level + ", Mana Cost: " + manaCost + ")";
    }
}
