package pl.psi.spells;

public class SpellFactory {

    public static Spell createSpell(SpellName aSpellName) {
        int DEFAULT_SPELL_LEVEL = 1;
        return createSpell(aSpellName, DEFAULT_SPELL_LEVEL);
    }

    public static Spell createSpell(SpellName aSpellName, int aSpellLevel) {
        return switch (aSpellName) {
            case MAGIC_ARROW -> new Spell.Builder().name(SpellName.MAGIC_ARROW).damage(5).level(1).manaCost(5).build();
            case SPLASH_ATTACK -> new Spell.Builder().name(SpellName.SPLASH_ATTACK).damage(5).level(1).radius(3).manaCost(5).build();
            case EXTRA_MOVE_RANGE ->
                    new Spell.Builder().name(SpellName.EXTRA_MOVE_RANGE).manaCost(5).spellBonus(SpellName.EXTRA_MOVE_RANGE).spellBonusRoundsDuration(5).build();
            case WEAKEN_ATTACK ->
                    new Spell.Builder().name(SpellName.WEAKEN_ATTACK).damage(0).manaCost(5).spellBonus(SpellName.WEAKEN_ATTACK).spellBonusRoundsDuration(2).build();
            case STRONGER_ATTACK ->
                    new Spell.Builder().name(SpellName.STRONGER_ATTACK).damage(0).manaCost(5).spellBonus(SpellName.STRONGER_ATTACK).spellBonusRoundsDuration(2).build();
        };
    }
}
