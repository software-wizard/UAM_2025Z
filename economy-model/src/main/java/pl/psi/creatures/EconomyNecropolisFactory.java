package pl.psi.creatures;

import pl.psi.resource.Resources;

import java.util.Map;

public class EconomyNecropolisFactory
{

    private static final String EXCEPTION_MESSAGE = "We support tiers from 1 to 7";

    public EconomyCreature create( final boolean aIsUpgraded, final int aTier, final int aAmount )
    {
        if( !aIsUpgraded )
        {
            switch( aTier )
            {
                case 1:
                    return new EconomyCreature( CreatureStatistic.SKELETON, aAmount, new Resources(Map.of(Resources.ResourceType.GOLD, 50)));
                case 2:
                    return new EconomyCreature( CreatureStatistic.WALKING_DEAD, aAmount, new Resources(Map.of(Resources.ResourceType.GOLD, 100)) );
                case 3:
                    return new EconomyCreature( CreatureStatistic.WIGHT, aAmount, new Resources(Map.of(Resources.ResourceType.GOLD, 200)) );
                case 4:
                    return new EconomyCreature( CreatureStatistic.VAMPIRE, aAmount, new Resources(Map.of(Resources.ResourceType.GOLD, 360)) );
                case 5:
                    return new EconomyCreature( CreatureStatistic.LICH, aAmount, new Resources(Map.of(Resources.ResourceType.GOLD, 550)) );
                case 6:
                    return new EconomyCreature( CreatureStatistic.BLACK_KNIGHT, aAmount, new Resources(Map.of(Resources.ResourceType.GOLD, 1200)) );
                case 7:
                    return new EconomyCreature( CreatureStatistic.BONE_DRAGON, aAmount, new Resources(Map.of(Resources.ResourceType.GOLD, 1800)) );
                default:
                    throw new IllegalArgumentException( EXCEPTION_MESSAGE );
            }
        }
        else
        {
            switch( aTier )
            {
                case 1:
                    return new EconomyCreature( CreatureStatistic.SKELETON_WARRIOR, aAmount, new Resources(Map.of(Resources.ResourceType.GOLD, 70)) );
                case 2:
                    return new EconomyCreature( CreatureStatistic.ZOMBIE, aAmount, new Resources(Map.of(Resources.ResourceType.GOLD, 125)) );
                case 3:
                    return new EconomyCreature( CreatureStatistic.WRAITH, aAmount, new Resources(Map.of(Resources.ResourceType.GOLD, 230)) );
                case 4:
                    return new EconomyCreature( CreatureStatistic.VAMPIRE_LORD, aAmount, new Resources(Map.of(Resources.ResourceType.GOLD, 500)) );
                case 5:
                    return new EconomyCreature( CreatureStatistic.POWER_LICH, aAmount, new Resources(Map.of(Resources.ResourceType.GOLD, 600)) );
                case 6:
                    return new EconomyCreature( CreatureStatistic.DREAD_KNIGHT, aAmount, new Resources(Map.of(Resources.ResourceType.GOLD, 1500)) );
                case 7:
                    return new EconomyCreature( CreatureStatistic.GHOST_DRAGON, aAmount, new Resources(Map.of(Resources.ResourceType.GOLD, 3000)) );
                default:
                    throw new IllegalArgumentException( EXCEPTION_MESSAGE );
            }
        }
    }
}
