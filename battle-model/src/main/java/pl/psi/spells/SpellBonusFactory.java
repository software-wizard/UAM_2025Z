package pl.psi.spells;

import com.google.common.collect.Range;

public class SpellBonusFactory {
    public static SpellBonus createSpellBonus(SpellName spellName){

        NoSpellBonusStatistic baseStats = new NoSpellBonusStatistic();

        int baseStatsAttack  = baseStats.getAttack();
        int baseStatsArmor   = baseStats.getArmor();
        int baseStatsMaxHp   = baseStats.getMaxHp();
        int baseStatsMoveRange   = baseStats.getMoveRange();
        Range<Integer> baseStatsDamage   = baseStats.getDamage();
        int baseStatsTier   = baseStats.getTier();
        boolean baseStatsUpgraded  = baseStats.isUpgraded();
        boolean baseStatsUndead   = baseStats.isUndead();
        String spellBonusNameStringified = spellName.toString();
        String spellBonusDescription = spellBonusNameStringified + " spell bonus";

        switch(spellName){
            case STRONGER_ATTACK:
                baseStatsAttack = 5;
                break;
            case WEAKEN_ATTACK:
                baseStatsAttack = -5;
                break;
            case EXTRA_MOVE_RANGE:
                baseStatsMoveRange = 1;
                break;
            case SPLASH_ATTACK:
            case MAGIC_ARROW:
            default:
                break;
        }

        return new SpellBonus(
                spellBonusNameStringified,
                baseStatsAttack,
                baseStatsArmor,
                baseStatsMaxHp,
                baseStatsMoveRange,
                baseStatsDamage,
                baseStatsTier,
                spellBonusDescription,
                baseStatsUpgraded,
                baseStatsUndead
        );

    }
}
