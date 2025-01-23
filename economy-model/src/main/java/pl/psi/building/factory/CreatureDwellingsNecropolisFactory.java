package pl.psi.building.factory;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.psi.building.model.CreatureDwellingsBuilding;
import pl.psi.building.model.EconomyBuildingStatistic;
import pl.psi.resource.Resources;

import java.util.*;
import java.util.stream.Collectors;

import static pl.psi.resource.Resources.Type.*;

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
                .type(EconomyBuildingStatistic.Type.DWELLINGS)
                .cost(building.buildingStatistic.cost())
                .prerequisites(building.buildingStatistic.prerequisites())
                .name(name)
                .build();
        return new CreatureDwellingsBuilding(
                economyBuildingStatistic,
                building.creatureTier,
                building.upgradeCost,
                building.costPerWeek
        );
    }

    @Getter
    @RequiredArgsConstructor
    protected enum NecropolisDwellingsStatistic implements NecropolisStatistic {
        CURSED_TEMPLE(new EconomyBuildingStatistic(
                "Cursed_Temple",
                EconomyBuildingStatistic.Type.DWELLINGS,
                Resources.builder()
                        .resource(GOLD, 400)
                        .resource(WOOD, 5)
                        .resource(ORE, 5)
                        .build(),
                List.of()
        ), 1,
                Resources.builder()
                        .resource(GOLD, 1000)
                        .resource(WOOD, 5)
                        .resource(ORE, 5)
                        .build(),
                Resources.builder()
                        .resource(GOLD, 2100)
                        .build()
        ),
        GRAVEYARD(new EconomyBuildingStatistic(
                "Graveyard",
                EconomyBuildingStatistic.Type.DWELLINGS,
                Resources.builder()
                        .resource(ORE, 5)
                        .resource(GOLD, 1000)
                        .build(),
                List.of(CURSED_TEMPLE.buildingStatistic)
        ), 2,
                Resources.builder()
                        .resource(WOOD, 5)
                        .resource(ORE, 5)
                        .resource(GOLD, 1000)
                        .build(),
                Resources.builder()
                        .resource(GOLD, 2000)
                        .build()
        ),
        TOMB_OF_SOULS(new EconomyBuildingStatistic(
                "Tomb_of_Souls",
                EconomyBuildingStatistic.Type.DWELLINGS,
                Resources.builder()
                        .resource(ORE, 5)
                        .resource(WOOD, 5)
                        .resource(GOLD, 1500)
                        .build(),
                List.of(GRAVEYARD.buildingStatistic)
        ), 3,
                Resources.builder()
                        .resource(MERCURY, 1)
                        .resource(GOLD, 1500)
                        .build(),
                Resources.builder()
                        .resource(GOLD, 3220)
                        .build()
        ),
        ESTATE(new EconomyBuildingStatistic(
                "Estate",
                EconomyBuildingStatistic.Type.DWELLINGS,
                Resources.builder()
                        .resource(ORE, 5)
                        .resource(WOOD, 5)
                        .resource(GOLD, 2000)
                        .build(),
                List.of(GRAVEYARD.buildingStatistic)
        ), 4,
                Resources.builder()
                        .resource(WOOD, 10)
                        .resource(CRYSTAL, 10)
                        .resource(GEMS, 10)
                        .resource(GOLD, 2000)
                        .build(),
                Resources.builder()
                        .resource(GOLD, 4000)
                        .build()
        ),
        MAUSOLEUM(new EconomyBuildingStatistic(
                "Mausoleum",
                EconomyBuildingStatistic.Type.DWELLINGS,
                Resources.builder()
                        .resource(ORE, 1)
                        .resource(SULFUR, 1)
                        .resource(GOLD, 2000)
                        .build(),
                List.of(GRAVEYARD.buildingStatistic)
        ), 5,
                Resources.builder()
                        .resource(ORE, 1)
                        .resource(SULFUR, 1)
                        .resource(GOLD, 2000)
                        .build(),
                Resources.builder()
                        .resource(GOLD, 3600)
                        .build()
        ),
        HALL_OF_DARKNESS(new EconomyBuildingStatistic(
                "Hall_of_Darkness",
                EconomyBuildingStatistic.Type.DWELLINGS,
                Resources.builder()
                        .resource(Resources.Type.ORE, 10)
                        .resource(WOOD, 10)
                        .resource(Resources.Type.GOLD, 6000)
                        .build(),
                List.of(ESTATE.buildingStatistic, MAUSOLEUM.buildingStatistic)
        ), 6,
                Resources.builder()
                        .resource(Resources.Type.ORE, 5)
                        .resource(WOOD, 5)
                        .resource(Resources.Type.MERCURY, 2)
                        .resource(Resources.Type.SULFUR, 2)
                        .resource(Resources.Type.CRYSTAL, 2)
                        .resource(Resources.Type.GEMS, 2)
                        .resource(Resources.Type.GOLD, 3000)
                        .build(),
                Resources.builder()
                        .resource(Resources.Type.GOLD, 6000)
                        .build()
        ),
        DRAGON_VAULT(new EconomyBuildingStatistic(
                "Dragon_Vault",
                EconomyBuildingStatistic.Type.DWELLINGS,
                Resources.builder()
                        .resource(WOOD, 5)
                        .resource(Resources.Type.MERCURY, 5)
                        .resource(Resources.Type.ORE, 5)
                        .resource(Resources.Type.SULFUR, 5)
                        .resource(Resources.Type.GEMS, 5)
                        .resource(Resources.Type.CRYSTAL, 5)
                        .resource(Resources.Type.GOLD, 10000)
                        .build(),
                List.of(HALL_OF_DARKNESS.buildingStatistic)
        ), 7,
                Resources.builder()
                        .resource(WOOD, 5)
                        .resource(Resources.Type.ORE, 5)
                        .resource(Resources.Type.MERCURY, 20)
                        .resource(Resources.Type.GOLD, 15000)
                        .build(),
                Resources.builder()
                        .resource(Resources.Type.GOLD, 6000)
                        .resource(Resources.Type.MERCURY, 2)
                        .build()
        );

        private final EconomyBuildingStatistic buildingStatistic;
        private final int creatureTier;
        private final Resources upgradeCost;
        private final Resources costPerWeek;
    }
}
