package pl.psi.building.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.psi.resource.Resources;

import static org.assertj.core.api.Assertions.assertThat;

class CreatureDwellingsBuildingTest {

    private CreatureDwellingsBuilding building;
    private EconomyBuildingStatistic statistic;
    private Resources upgradeCost;
    private Resources costPerWeek;

    @BeforeEach
    void setUp() {
        statistic = EconomyBuildingStatistic.builder()
                .name("Dwelling Test")
                .type(EconomyBuildingStatistic.Type.DWELLINGS)
                .cost(Resources.builder().resource(Resources.Type.GOLD, 500).build())
                .prerequisites(null)
                .build();

        upgradeCost = Resources.builder().resource(Resources.Type.GOLD, 300).build();
        costPerWeek = Resources.builder().resource(Resources.Type.GOLD, 100).build();

        building = new CreatureDwellingsBuilding(
                statistic,
                1,
                upgradeCost,
                costPerWeek
        );
    }

    @Test
    void shouldReturnCorrectStatistic() {
        // GIVEN && WHEN && THEN
        assertThat(building.getStatistic()).isEqualTo(statistic);
    }

    @Test
    void shouldReturnCorrectUpgradeCost() {
        // GIVEN && WHEN && THEN
        assertThat(building.getUpgradeCost()).isEqualTo(upgradeCost);
    }

    @Test
    void should_return_correct_cost_per_week() {
        // GIVEN && WHEN && THEN
        assertThat(building.getCostPerWeek()).isEqualTo(costPerWeek);
    }

    @Test
    void should_start_as_not_upgraded() {
        // GIVEN && WHEN && THEN
        assertThat(building.isUpgraded()).isFalse();
    }

    @Test
    void should_upgrade_building() {
        // GIVEN && WHEN
        building.upgrade();

        // THEN
        assertThat(building.isUpgraded()).isTrue();
    }

    @Test
    void should_return_correct_creature_names() {
        // GIVEN && WHEN && THEN
        assertThat(building.getCreatureTier()).isEqualTo(1);
    }
}
