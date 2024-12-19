package pl.psi.building.model;

import lombok.Builder;
import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resources;

import java.util.List;

@Builder
public record EconomyBuildingStatistic(
        String name,
        EconomyBuildingType type,
        Resources cost,
        List<EconomyBuildingStatistic> prerequisites
) {

    public enum EconomyBuildingType {
        BUILDING, DWELLINGS
    }

    public boolean hasEnoughResourcesToBuild(EconomyHero aBuyer) {
        return aBuyer.canAfford(cost);
    }
}
