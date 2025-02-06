package pl.psi.spells;

public class SpellFactory {

    public static Spell createSpell(SpellName aSpellName, SpellMasterityLevel aSpellMasterityLevel) {
        int damage = 0;
        int radius = 0;
        int duration = 0;
        int manaCost = 5;
        switch (aSpellName) {
            case MAGIC_ARROW -> {
                damage = switch (aSpellMasterityLevel) {
                    case EXPERT -> 20;
                    case ADVANCED -> 10;
                    default -> 5;
                };
            }
            case SPLASH_ATTACK -> {
                damage = switch (aSpellMasterityLevel) {
                    case EXPERT -> 18;
                    case ADVANCED -> 12;
                    default -> 7;
                };
                radius = switch (aSpellMasterityLevel) {
                    case EXPERT -> 4;
                    case ADVANCED -> 3;
                    default -> 2;
                };
            }
            case EXTRA_MOVE_RANGE -> {
                duration = switch (aSpellMasterityLevel) {
                    case EXPERT -> 7;
                    case ADVANCED -> 6;
                    default -> 5;
                };
            }
            case WEAKEN_ATTACK -> {
                duration = switch (aSpellMasterityLevel) {
                    case EXPERT -> 4;
                    case ADVANCED -> 3;
                    default -> 2;
                };
            }
            case STRONGER_ATTACK -> {
                duration = switch (aSpellMasterityLevel) {
                    case EXPERT -> 4;
                    case ADVANCED -> 3;
                    default -> 2;
                };
                damage = switch (aSpellMasterityLevel) {
                    case EXPERT -> 10;
                    case ADVANCED -> 5;
                    default -> 2;
                };
            }

        };

        return new Spell.Builder().name(aSpellName).masterityLevel(aSpellMasterityLevel).manaCost(manaCost).damage(damage).radius(radius).spellBonus(aSpellName).spellBonusRoundsDuration(duration).build();
    }

    public static Spell createSpell(SpellName aSpellName){
        return createSpell(aSpellName, SpellMasterityLevel.BASIC);
    }
}
