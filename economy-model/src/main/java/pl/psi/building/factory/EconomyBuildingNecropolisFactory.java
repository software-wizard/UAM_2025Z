package pl.psi.building.factory;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.psi.building.CreatureDwellingsBuilding;
import pl.psi.building.EconomyBuilding;
import pl.psi.building.EconomyBuildingStatistic;
import pl.psi.resource.Resource;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

class EconomyBuildingNecropolisFactory implements EconomyBuildingFactory<EconomyBuilding> {

    @Override
    public EconomyBuilding createBuilding(String aBuildingName) {
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

    private EconomyBuilding buildBuildingFromName(String name) {
        var building = NecropolisBuildingStatistic.valueOf(name.toUpperCase());
        var economyBuildingStatistic = EconomyBuildingStatistic.builder()
                .type(EconomyBuildingStatistic.EconomyBuildingType.BUILDING)
                .cost(building.buildingStatistic.cost())
                .prerequisites(building.buildingStatistic.prerequisites())
                .name(name)
                .build();
        return new EconomyBuilding(economyBuildingStatistic);
    }

    @RequiredArgsConstructor
    @Getter
    private enum NecropolisBuildingStatistic implements NecropolisStatistic {
        TAVERN(new EconomyBuildingStatistic(
                "Tavern",
                EconomyBuildingStatistic.EconomyBuildingType.BUILDING,
                Set.of(new Resource(Resource.ResourceType.GOLD, 100)),
                List.of()
        ));
    
        private final EconomyBuildingStatistic buildingStatistic;
    }
}
