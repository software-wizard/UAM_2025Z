package pl.psi.spells;

public class SpellFactory {

    public static Spell createSpell(SpellName aSpellName, SpellMasterityLevel aSpellMasterityLevel) {
        return switch (aSpellName) {
            case MAGIC_ARROW -> {
                int damage = switch (aSpellMasterityLevel) {
                    case EXPERT -> 40;
                    case ADVANCED -> 20;
                    default -> 5;
                };
                yield new Spell.Builder().name(SpellName.MAGIC_ARROW).masterityLevel(aSpellMasterityLevel).damage(damage).manaCost(5).build();
            }
            case SPLASH_ATTACK -> {
                int damage = switch (aSpellMasterityLevel) {
                    case EXPERT -> 15;
                    case ADVANCED -> 10;
                    default -> 5;
                };
                int radius = switch (aSpellMasterityLevel) {
                    case EXPERT -> 5;
                    case ADVANCED -> 4;
                    default -> 3;
                };
                yield new Spell.Builder().name(SpellName.SPLASH_ATTACK).masterityLevel(aSpellMasterityLevel).damage(damage).radius(radius).manaCost(5).build();
            }
            case EXTRA_MOVE_RANGE -> {
                int duration = switch (aSpellMasterityLevel) {
                    case EXPERT -> 7;
                    case ADVANCED -> 6;
                    default -> 5;
                };
                yield new Spell.Builder().name(SpellName.EXTRA_MOVE_RANGE).masterityLevel(aSpellMasterityLevel).manaCost(5).spellBonus(SpellName.EXTRA_MOVE_RANGE).spellBonusRoundsDuration(duration).build();
            }
            case WEAKEN_ATTACK -> {
                int duration = switch (aSpellMasterityLevel) {
                    case EXPERT -> 4;
                    case ADVANCED -> 3;
                    default -> 2;
                };
                int damageReduction = switch (aSpellMasterityLevel) {
                    case EXPERT -> 10;
                    case ADVANCED -> 5;
                    default -> 0;
                };
                yield new Spell.Builder().name(SpellName.WEAKEN_ATTACK).masterityLevel(aSpellMasterityLevel).damage(damageReduction).manaCost(5).spellBonus(SpellName.WEAKEN_ATTACK).spellBonusRoundsDuration(duration).build();
            }
            case STRONGER_ATTACK -> {
                int duration = switch (aSpellMasterityLevel) {
                    case EXPERT -> 4;
                    case ADVANCED -> 3;
                    default -> 2;
                };
                int attackBonus = switch (aSpellMasterityLevel) {
                    case EXPERT -> 10;
                    case ADVANCED -> 5;
                    default -> 2;
                };
                yield new Spell.Builder().name(SpellName.STRONGER_ATTACK).masterityLevel(aSpellMasterityLevel).damage(attackBonus).manaCost(5).spellBonus(SpellName.STRONGER_ATTACK).spellBonusRoundsDuration(duration).build();
            }
            case NONE -> new Spell.Builder().name(SpellName.NONE).damage(0).manaCost(0).build();
        };
    }

    public static Spell createSpell(SpellName aSpellName){
        return createSpell(aSpellName, SpellMasterityLevel.BASIC);
    }
}
