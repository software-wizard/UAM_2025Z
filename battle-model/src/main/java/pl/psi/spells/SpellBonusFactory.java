package pl.psi.spells;

import com.google.common.collect.Range;

public class SpellBonusFactory {
    public static SpellBonus createSpellBonus(SpellName aSpellName, SpellMasterityLevel aSpellMasterityLevel){

        NoSpellBonusStatistic baseStats = new NoSpellBonusStatistic();

        int masterityLevelToCreatureTier = aSpellMasterityLevel.getLevel();
        int baseStatsAttack  = baseStats.getAttack();
        int baseStatsArmor   = baseStats.getArmor();
        int baseStatsMaxHp   = baseStats.getMaxHp();
        int baseStatsMoveRange   = baseStats.getMoveRange();
        Range<Integer> baseStatsDamage   = baseStats.getDamage();
        boolean baseStatsUpgraded  = baseStats.isUpgraded();
        boolean baseStatsUndead   = baseStats.isUndead();
        String spellBonusNameStringified = aSpellName.toString();
        String spellBonusDescription = spellBonusNameStringified + " spell bonus";

        switch(aSpellName){
            case STRONGER_ATTACK -> {
                baseStatsAttack = switch (aSpellMasterityLevel) {
                    case EXPERT -> 10;
                    case ADVANCED -> 7;
                    default -> 5;
                };
            }
            case WEAKEN_ATTACK -> {
                baseStatsAttack = switch (aSpellMasterityLevel) {
                    case EXPERT -> -10;
                    case ADVANCED -> -7;
                    default -> -5;
                };
            }
            case EXTRA_MOVE_RANGE -> {
                baseStatsMoveRange = switch (aSpellMasterityLevel) {
                    case EXPERT -> 3;
                    case ADVANCED -> 2;
                    default -> 1;
                };
            }
            default -> {}
        }

        return new SpellBonus(
                spellBonusNameStringified,
                baseStatsAttack,
                baseStatsArmor,
                baseStatsMaxHp,
                baseStatsMoveRange,
                baseStatsDamage,
                masterityLevelToCreatureTier,
                spellBonusDescription,
                baseStatsUpgraded,
                baseStatsUndead
        );

    }

    public static SpellBonus createSpellBonus(SpellName aSpellName){
        return createSpellBonus(aSpellName, SpellMasterityLevel.BASIC);
    }
}
