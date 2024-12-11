package pl.psi.building.factory;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.psi.building.CreatureDwellingsBuilding;
import pl.psi.building.EconomyBuilding;
import pl.psi.building.EconomyBuildingStatistic;
import pl.psi.resource.Resource;

import java.util.List;
import java.util.Set;

class EconomyBuildingNecropolisFactory implements EconomyBuildingFactory {

    @Override
    public EconomyBuilding createBuilding(String aBuildingName) {
        return resolveBuildingFromName(aBuildingName);
    }

    @Override
    public CreatureDwellingsBuilding createDwellings(String name) {
        return resolveDwellingsFromName(name);
    }

    private EconomyBuilding resolveBuildingFromName(String name) {
        var building = NecropolisBuildingStatistic.valueOf(name);
        var economyBuildingStatistic = EconomyBuildingStatistic.builder()
                .cost(building.buildingStatistic.cost())
                .prerequisites(building.buildingStatistic.prerequisites())
                .name(name)
                .build();
        return new EconomyBuilding(economyBuildingStatistic);
    }

    private CreatureDwellingsBuilding resolveDwellingsFromName(String name) {
        var building = NecropolisDwellingsStatistic.valueOf(name);
        var economyBuildingStatistic = EconomyBuildingStatistic.builder()
                .cost(building.buildingStatistic.cost())
                .prerequisites(building.buildingStatistic.prerequisites())
                .name(name)
                .build();
        return new CreatureDwellingsBuilding(
            economyBuildingStatistic, building.creatureNames, building.upgradeCost, building.costPerWeek
        );
    }

    @RequiredArgsConstructor
    private enum NecropolisBuildingStatistic {
        TAVERN(new EconomyBuildingStatistic(
                "Tavern",
                Set.of(new Resource(Resource.ResourceType.GOLD, 100)),
                List.of()
        ));
    
        private final EconomyBuildingStatistic buildingStatistic;
    }

    @Getter
    @RequiredArgsConstructor
    private enum NecropolisDwellingsStatistic {
        CURSED_TEMPLE(new EconomyBuildingStatistic(
                "Cursed Temple",
                Set.of(new Resource(Resource.ResourceType.GOLD, 1000)),
                List.of()
        ), Set.of("SKELETON", "SKELETON_WARRIOR"),
                Set.of(new Resource(Resource.ResourceType.GOLD, 70)),
                Set.of(new Resource(Resource.ResourceType.GOLD, 2100))
        );

        private final EconomyBuildingStatistic buildingStatistic;
        private final Set<String> creatureNames;
        private final Set<Resource> upgradeCost;
        private final Set<Resource> costPerWeek;
    }
}
