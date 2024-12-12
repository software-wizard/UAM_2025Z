package pl.psi.building.factory;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.psi.building.CreatureDwellingsBuilding;
import pl.psi.building.EconomyBuildingStatistic;
import pl.psi.resource.Resource;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

class CreatureDwellingsNecropolisFactory implements EconomyBuildingFactory<CreatureDwellingsBuilding> {

    @Override
    public CreatureDwellingsBuilding createBuilding(String aBuildingName) {
        return buildDwellingsFromName(aBuildingName);
    }

    @Override
    public Optional<EconomyBuildingStatistic> getStatisticByName(String name) {
        return EnumSet.allOf(NecropolisDwellingsStatistic.class)
                .stream()
                .map(NecropolisDwellingsStatistic::getBuildingStatistic)
                .filter(necropolisBuildingStatistic -> necropolisBuildingStatistic.name().equals(name))
                .findFirst();
    }

    @Override
    public Set<EconomyBuildingStatistic> getAllAvailableBuildingsToBuild() {
        return EnumSet.allOf(NecropolisDwellingsStatistic.class)
                .stream()
                .map(NecropolisStatistic::getBuildingStatistic)
                .collect(Collectors.toSet());
    }

    private CreatureDwellingsBuilding buildDwellingsFromName(String name) {
        var building = NecropolisDwellingsStatistic.valueOf(name.toUpperCase());
        var economyBuildingStatistic = EconomyBuildingStatistic.builder()
                .type(EconomyBuildingStatistic.EconomyBuildingType.DWELLINGS)
                .cost(building.buildingStatistic.cost())
                .prerequisites(building.buildingStatistic.prerequisites())
                .name(name)
                .build();
        return new CreatureDwellingsBuilding(
                economyBuildingStatistic, building.creatureNames, building.upgradeCost, building.costPerWeek
        );
    }

    @Getter
    @RequiredArgsConstructor
    private enum NecropolisDwellingsStatistic implements NecropolisStatistic {
        CURSED_TEMPLE(new EconomyBuildingStatistic(
                "Cursed Temple",
                EconomyBuildingStatistic.EconomyBuildingType.DWELLINGS,
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
