package pl.psi.creatures;

import com.google.common.base.Preconditions;
import lombok.Getter;
import pl.psi.resource.Resources;

import java.util.Objects;

public class EconomyCreature
{
    public static EconomyCreature merge(EconomyCreature e1, EconomyCreature e2) {
        Preconditions.checkArgument(e1.stats.equals(e2.stats));
        Preconditions.checkArgument(e1.cost.equals(e2.cost));
        return new EconomyCreature(e1.stats, e1.amount + e2.amount, e1.cost);
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EconomyCreature creature = (EconomyCreature) o;
        return stats == creature.stats && Objects.equals(cost, creature.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stats, cost);
    }
}
