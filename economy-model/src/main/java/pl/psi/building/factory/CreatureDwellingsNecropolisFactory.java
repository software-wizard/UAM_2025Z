package pl.psi.building.factory;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.psi.building.model.CreatureDwellingsBuilding;
import pl.psi.building.model.EconomyBuildingStatistic;
import pl.psi.resource.Resources;

import java.util.*;
import java.util.stream.Collectors;

class CreatureDwellingsNecropolisFactory implements EconomyBuildingFactory {

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
                "Cursed_Temple",
                EconomyBuildingStatistic.EconomyBuildingType.DWELLINGS,
                new Resources(Map.of(
                        Resources.ResourceType.GOLD, 400,
                        Resources.ResourceType.WOOD, 5,
                        Resources.ResourceType.ORE, 5
                )),
                List.of()
        ), Set.of("SKELETON", "SKELETON_WARRIOR"),
                new Resources(Map.of(
                        Resources.ResourceType.GOLD, 1000,
                        Resources.ResourceType.WOOD, 5,
                        Resources.ResourceType.ORE, 5
                )),
                new Resources(Map.of(Resources.ResourceType.GOLD, 2100))
        ),
        GRAVEYARD(new EconomyBuildingStatistic(
                "Graveyard",
                EconomyBuildingStatistic.EconomyBuildingType.DWELLINGS,
                new Resources(Map.of(
                        Resources.ResourceType.ORE, 5,
                        Resources.ResourceType.GOLD, 1000
                )),
                List.of()
        ), Set.of("WALKING_DEAD", "ZOMBIE"),
                new Resources(Map.of(
                        Resources.ResourceType.WOOD, 5,
                        Resources.ResourceType.ORE, 5,
                        Resources.ResourceType.GOLD, 1000
                )),
                new Resources(Map.of(Resources.ResourceType.GOLD, 2000))
        ),
        TOMB_OF_SOULS(new EconomyBuildingStatistic(
                "Tomb_of_Souls",
                EconomyBuildingStatistic.EconomyBuildingType.DWELLINGS,
                new Resources(Map.of(
                        Resources.ResourceType.ORE, 5,
                        Resources.ResourceType.WOOD, 5,
                        Resources.ResourceType.GOLD, 1500
                )),
                List.of()
        ), Set.of("WIGHT", "WRAITH"),
                new Resources(Map.of(
                        Resources.ResourceType.MERCURY, 1,
                        Resources.ResourceType.GOLD, 1500
                )),
                new Resources(Map.of(Resources.ResourceType.GOLD, 3220))
        ),
        ESTATE(new EconomyBuildingStatistic(
                "Estate",
                EconomyBuildingStatistic.EconomyBuildingType.DWELLINGS,
                new Resources(Map.of(
                        Resources.ResourceType.ORE, 5,
                        Resources.ResourceType.WOOD, 5,
                        Resources.ResourceType.GOLD, 2000
                )),
                List.of()
        ), Set.of("VAMPIRE", "VAMPIRE_LORD"),
                new Resources(Map.of(
                        Resources.ResourceType.WOOD, 10,
                        Resources.ResourceType.CRYSTAL, 10,
                        Resources.ResourceType.GEM, 10,
                        Resources.ResourceType.GOLD, 2000
                )),
                new Resources(Map.of(Resources.ResourceType.GOLD, 4000))
        ),
        MAUSOLEUM(new EconomyBuildingStatistic(
                "Mausoleum",
                EconomyBuildingStatistic.EconomyBuildingType.DWELLINGS,
                new Resources(Map.of(
                        Resources.ResourceType.ORE, 1,
                        Resources.ResourceType.SULFUR, 1,
                        Resources.ResourceType.GOLD, 2000
                )),
                List.of()
        ), Set.of("LICH", "POWER_LICH"),
                new Resources(Map.of(
                        Resources.ResourceType.ORE, 1,
                        Resources.ResourceType.SULFUR, 1,
                        Resources.ResourceType.GOLD, 2000
                )),
                new Resources(Map.of(Resources.ResourceType.GOLD, 3600))
        ),
        HALL_OF_DARKNESS(new EconomyBuildingStatistic(
                "Hall_of_Darkness",
                EconomyBuildingStatistic.EconomyBuildingType.DWELLINGS,
                new Resources(Map.of(
                        Resources.ResourceType.ORE, 10,
                        Resources.ResourceType.WOOD, 10,
                        Resources.ResourceType.GOLD, 6000
                )),
                List.of()
        ), Set.of("BLACK_KNIGHT", "DREAD_KNIGHT"),
                new Resources(Map.of(
                        Resources.ResourceType.ORE, 5,
                        Resources.ResourceType.WOOD, 5,
                        Resources.ResourceType.MERCURY, 2,
                        Resources.ResourceType.SULFUR, 2,
                        Resources.ResourceType.CRYSTAL, 2,
                        Resources.ResourceType.GEM, 2,
                        Resources.ResourceType.GOLD, 3000
                )),
                new Resources(Map.of(Resources.ResourceType.GOLD, 6000))
        ),
        DRAGON_VAULT(new EconomyBuildingStatistic(
                "Dragon_Vault",
                EconomyBuildingStatistic.EconomyBuildingType.DWELLINGS,
                new Resources(Map.of(
                        Resources.ResourceType.WOOD, 5,
                        Resources.ResourceType.MERCURY, 5,
                        Resources.ResourceType.ORE, 5,
                        Resources.ResourceType.SULFUR, 5,
                        Resources.ResourceType.GEM, 5,
                        Resources.ResourceType.CRYSTAL, 5,
                        Resources.ResourceType.GOLD, 10000
                )),
                List.of()
        ), Set.of("BLACK_KNIGHT", "DREAD_KNIGHT"),
                new Resources(Map.of(
                        Resources.ResourceType.WOOD, 5,
                        Resources.ResourceType.ORE, 5,
                        Resources.ResourceType.MERCURY, 20,
                        Resources.ResourceType.GOLD, 15000
                )),
                new Resources(Map.of(
                        Resources.ResourceType.GOLD, 6000,
                        Resources.ResourceType.MERCURY, 2
                ))
        );

        private final EconomyBuildingStatistic buildingStatistic;
        private final Set<String> creatureNames;
        private final Resources upgradeCost;
        private final Resources costPerWeek;
    }
}
