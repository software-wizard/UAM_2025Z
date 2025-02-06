package pl.psi.building.factory;

import org.junit.jupiter.api.Test;
import pl.psi.building.model.CreatureDwellingsBuilding;
import pl.psi.building.model.EconomyBuildingStatistic;
import pl.psi.resource.Resources;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class CreatureDwellingsNecropolisFactoryTest {

    @Test
    void should_create_cursed_temple_building() {
        // GIVEN
        var factory = new CreatureDwellingsNecropolisFactory();
        var name = "Cursed_Temple";
        var buildingStatistic = CreatureDwellingsNecropolisFactory.NecropolisDwellingsStatistic
                .CURSED_TEMPLE.getBuildingStatistic();
        var tier = 1;
        var upgradeCost = Resources.builder()
                .resource(Resources.Type.GOLD, 1000)
                .resource(Resources.Type.WOOD, 5)
                .resource(Resources.Type.ORE, 5)
                .build();
        var costPerWeek = Resources.builder()
                .resource(Resources.Type.GOLD, 2100)
                .build();

        // WHEN
        CreatureDwellingsBuilding building = factory.createBuilding(name);

        // THEN
        assertThat(building.isUpgraded()).isFalse();
        assertThat(building.getStatistic())
                .isEqualTo(buildingStatistic);
        assertThat(building.getCreatureTier()).isEqualTo(tier);
        assertThat(building.getUpgradeCost()).isEqualTo(upgradeCost);
        assertThat(building.getCostPerWeek()).isEqualTo(costPerWeek);
    }

    @Test
    void should_get_building_statistic_by_name() {
        // GIVEN
        var factory = new CreatureDwellingsNecropolisFactory();
        var buildingStatistic = CreatureDwellingsNecropolisFactory.NecropolisDwellingsStatistic
                .CURSED_TEMPLE.getBuildingStatistic();

        // WHEN
        Optional<EconomyBuildingStatistic> foundEconomyBuildingStatistic = factory.getStatisticByName("Cursed_Temple");

        // THEN
        assertThat(foundEconomyBuildingStatistic).isPresent();
        assertThat(foundEconomyBuildingStatistic.get()).isEqualTo(buildingStatistic);
    }

    @Test
    void should_get_available_building_statistics() {
        // GIVEN
        var factory = new CreatureDwellingsNecropolisFactory();
        Set<EconomyBuildingStatistic> expectedBuildingStatistics = EnumSet.allOf(
                        CreatureDwellingsNecropolisFactory.NecropolisDwellingsStatistic.class
                ).stream()
                .map(NecropolisStatistic::getBuildingStatistic)
                .collect(Collectors.toSet());

        // WHEN
        Set<EconomyBuildingStatistic> buildingsToBuild = factory.getAllAvailableBuildingsToBuild();

        // THEN
        assertThat(buildingsToBuild).isNotEmpty();
        assertThat(buildingsToBuild).containsExactlyInAnyOrderElementsOf(expectedBuildingStatistics);
    }
}