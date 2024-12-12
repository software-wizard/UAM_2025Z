package pl.psi.building;

import lombok.Builder;
import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resource;

import java.util.List;
import java.util.Set;

@Builder
public record EconomyBuildingStatistic(
        String name,
        EconomyBuildingType type,
        Set<Resource> cost,
        List<EconomyBuildingStatistic> prerequisites
) {

    public enum EconomyBuildingType {
        BUILDING, DWELLINGS
    }

    public boolean hasEnoughResourcesToBuild(EconomyHero aBuyer) {
        return aBuyer.hasEnoughResources(cost);
    }
}
