package pl.psi.creatures;

import pl.psi.resource.Resources;

public class EconomyNecropolisFactory
{

    private static final String EXCEPTION_MESSAGE = "We support tiers from 1 to 7";

    public EconomyCreature create(final boolean aIsUpgraded, final int aTier, final int aAmount)
    {
        if (!aIsUpgraded)
        {
            switch (aTier)
            {
                case 1:
                    return new EconomyCreature(
                            CreatureStatistic.SKELETON,
                            aAmount,
                            Resources.builder().resource(Resources.Type.GOLD, 50).build()
                    );
                case 2:
                    return new EconomyCreature(
                            CreatureStatistic.WALKING_DEAD,
                            aAmount,
                            Resources.builder().resource(Resources.Type.GOLD, 100).build()
                    );
                case 3:
                    return new EconomyCreature(
                            CreatureStatistic.WIGHT,
                            aAmount,
                            Resources.builder().resource(Resources.Type.GOLD, 200).build()
                    );
                case 4:
                    return new EconomyCreature(
                            CreatureStatistic.VAMPIRE,
                            aAmount,
                            Resources.builder().resource(Resources.Type.GOLD, 360).build()
                    );
                case 5:
                    return new EconomyCreature(
                            CreatureStatistic.LICH,
                            aAmount,
                            Resources.builder().resource(Resources.Type.GOLD, 550).build()
                    );
                case 6:
                    return new EconomyCreature(
                            CreatureStatistic.BLACK_KNIGHT,
                            aAmount,
                            Resources.builder().resource(Resources.Type.GOLD, 1200).build()
                    );
                case 7:
                    return new EconomyCreature(
                            CreatureStatistic.BONE_DRAGON,
                            aAmount,
                            Resources.builder().resource(Resources.Type.GOLD, 1800).build()
                    );
                default:
                    throw new IllegalArgumentException(EXCEPTION_MESSAGE);
            }
        }
        else
        {
            switch (aTier)
            {
                case 1:
                    return new EconomyCreature(
                            CreatureStatistic.SKELETON_WARRIOR,
                            aAmount,
                            Resources.builder().resource(Resources.Type.GOLD, 70).build()
                    );
                case 2:
                    return new EconomyCreature(
                            CreatureStatistic.ZOMBIE,
                            aAmount,
                            Resources.builder().resource(Resources.Type.GOLD, 125).build()
                    );
                case 3:
                    return new EconomyCreature(
                            CreatureStatistic.WRAITH,
                            aAmount,
                            Resources.builder().resource(Resources.Type.GOLD, 230).build()
                    );
                case 4:
                    return new EconomyCreature(
                            CreatureStatistic.VAMPIRE_LORD,
                            aAmount,
                            Resources.builder().resource(Resources.Type.GOLD, 500).build()
                    );
                case 5:
                    return new EconomyCreature(
                            CreatureStatistic.POWER_LICH,
                            aAmount,
                            Resources.builder().resource(Resources.Type.GOLD, 600).build()
                    );
                case 6:
                    return new EconomyCreature(
                            CreatureStatistic.DREAD_KNIGHT,
                            aAmount,
                            Resources.builder().resource(Resources.Type.GOLD, 1500).build()
                    );
                case 7:
                    return new EconomyCreature(
                            CreatureStatistic.GHOST_DRAGON,
                            aAmount,
                            Resources.builder().resource(Resources.Type.GOLD, 3000).build()
                    );
                default:
                    throw new IllegalArgumentException(EXCEPTION_MESSAGE);
            }
        }
    }
}
