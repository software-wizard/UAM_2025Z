package pl.psi.spells;

import com.google.common.collect.Range;
import pl.psi.creatures.BaseCreatureStatistic;

public class SpellBonusFactory {
    public static SpellBonus createSpellBonus(SpellBonusName spellBonusName){

        BaseCreatureStatistic baseStats = new BaseCreatureStatistic();

        int baseStatsAttack  = baseStats.getAttack();
        int baseStatsArmor   = baseStats.getArmor();
        int baseStatsMaxHp   = baseStats.getMaxHp();
        int baseStatsMoveRange   = baseStats.getMoveRange();
        Range<Integer> baseStatsDamage   = baseStats.getDamage();
        int baseStatsTier   = baseStats.getTier();
        boolean baseStatsUpgraded  = baseStats.isUpgraded();
        boolean baseStatsUndead   = baseStats.isUndead();
        String spellBonusNameStringified = spellBonusName.toString();
        String spellBonusDescription = spellBonusNameStringified + " spell bonus";

        // TODO: Damage is not working correctly with damage calculator, fix
        switch(spellBonusName){
            case EXTRA_ATTACK:
                baseStatsDamage = Range.closed(baseStatsDamage.lowerEndpoint() + 3, baseStatsDamage.upperEndpoint() + 3);
                break;
            case WEAKEN_ATTACK:
                baseStatsDamage = Range.closed(baseStatsDamage.lowerEndpoint() - 3, baseStatsDamage.upperEndpoint() - 3);

            case EXTRA_MOVE_RANGE:
                baseStatsMoveRange += 5;
            case NONE:
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
