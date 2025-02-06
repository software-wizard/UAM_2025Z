package pl.psi.building.factory;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.psi.building.model.DefaultEconomyBuilding;
import pl.psi.building.model.EconomyBuildingStatistic;
import pl.psi.resource.Resources;

import java.util.*;
import java.util.stream.Collectors;

import static pl.psi.resource.Resources.Type.*;

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
                .type(EconomyBuildingStatistic.Type.BUILDING)
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
                EconomyBuildingStatistic.Type.BUILDING,
                Resources.builder()
                        .resource(GOLD, 500)
                        .resource(WOOD, 5)
                        .build(),
                List.of()
        )),
        MARKETPLACE(new EconomyBuildingStatistic(
                "Marketplace",
                EconomyBuildingStatistic.Type.BUILDING,
                Resources.builder()
                        .resource(GOLD, 500)
                        .resource(WOOD, 5)
                        .build(),
                List.of()
        )),
        RESOURCE_SILO(new EconomyBuildingStatistic(
                "Resource_Silo",
                EconomyBuildingStatistic.Type.BUILDING,
                Resources.builder()
                        .resource(ORE, 5)
                        .resource(GOLD, 5000)
                        .build(),
                List.of(MARKETPLACE.buildingStatistic)
        ));
    
        private final EconomyBuildingStatistic buildingStatistic;
    }
}
