package pl.psi.building.factory;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.psi.building.model.DefaultEconomyBuilding;
import pl.psi.building.model.EconomyBuildingStatistic;
import pl.psi.resource.Resources;

import java.util.*;
import java.util.stream.Collectors;

class EconomyBuildingNecropolisFactory implements EconomyBuildingFactory {

    @Override
    public DefaultEconomyBuilding createBuilding(String aBuildingName) {
        return buildBuildingFromName(aBuildingName);
    }

    @Override
    public Optional<EconomyBuildingStatistic> getStatisticByName(String name) {
        return EnumSet.allOf(NecropolisBuildingStatistic.class)
                .stream()
                .map(NecropolisStatistic::getBuildingStatistic)
                .filter(necropolisBuildingStatistic -> necropolisBuildingStatistic.name().equals(name))
                .findFirst();
    }

    @Override
    public Set<EconomyBuildingStatistic> getAllAvailableBuildingsToBuild() {
        return EnumSet.allOf(NecropolisBuildingStatistic.class)
                .stream()
                .map(NecropolisStatistic::getBuildingStatistic)
                .collect(Collectors.toSet());
    }

    private DefaultEconomyBuilding buildBuildingFromName(String name) {
        var building = NecropolisBuildingStatistic.valueOf(name.toUpperCase());
        var economyBuildingStatistic = EconomyBuildingStatistic.builder()
                .type(EconomyBuildingStatistic.EconomyBuildingType.BUILDING)
                .cost(building.buildingStatistic.cost())
                .prerequisites(building.buildingStatistic.prerequisites())
                .name(name)
                .build();
        return new DefaultEconomyBuilding(economyBuildingStatistic);
    }

    @RequiredArgsConstructor
    @Getter
    private enum NecropolisBuildingStatistic implements NecropolisStatistic {
        TAVERN(new EconomyBuildingStatistic(
                "Tavern",
                EconomyBuildingStatistic.EconomyBuildingType.BUILDING,
                new Resources(Map.of(Resources.ResourceType.GOLD, 100)),
                List.of()
        ));
    
        private final EconomyBuildingStatistic buildingStatistic;
    }
}
