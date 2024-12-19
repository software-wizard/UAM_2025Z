package pl.psi.creatures;

import lombok.Getter;
import pl.psi.resource.Resources;

public class EconomyCreature
{

    private final CreatureStatistic stats;
    @Getter
    private final int amount;
    @Getter
    private final Resources cost;

    EconomyCreature( final CreatureStatistic aStats, final int aAmount, final Resources aCost )
    {
        stats = aStats;
        amount = aAmount;
        cost = aCost;
    }

    public String getName()
    {
        return stats.getTranslatedName();
    }

    public boolean isUpgraded()
    {
        return stats.isUpgraded();
    }

    public int getTier()
    {
        return stats.getTier();
    }
}
