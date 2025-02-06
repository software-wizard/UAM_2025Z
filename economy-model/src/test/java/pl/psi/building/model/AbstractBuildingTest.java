package pl.psi.building.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.psi.resource.Resources;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AbstractBuildingTest {

    private AbstractBuilding building;
    private EconomyBuildingStatistic statistic;

    @BeforeEach
    void setUp() {
        statistic = EconomyBuildingStatistic.builder()
                .name("Test Building")
                .type(EconomyBuildingStatistic.Type.BUILDING)
                .cost(Resources.builder().resource(Resources.Type.GOLD, 100).build())
                .prerequisites(List.of())
                .build();
        building = new AbstractBuilding(statistic) {};
    }

    @Test
    void should_start_building() {
        // GIVEN && WHEN
        building.startBuilding();

        // THEN
        assertThat(building.isBuilt()).isFalse();
    }

    @Test
    void should_finish_building() {
        // GIVEN
        building.startBuilding();

        // WHEN
        building.finishBuilding();

        // THEN
        assertThat(building.isBuilt()).isTrue();
    }

    @Test
    void should_have_correct_initial_built_state() {
        // GIVEN && WHEN &&  THEN
        assertThat(building.isBuilt()).isFalse();
    }

    @Test
    void should_return_correct_statistic() {
        // GIVEN && WHEN && THEN
        assertThat(building.getStatistic()).isEqualTo(statistic);
    }

    @Test
    void should_return_true_when_call_equals() {
        // GIVEN
        var secondBuilding = new AbstractBuilding(statistic) {};

        // WHEN && THEN
        assertThat(building.equals(secondBuilding)).isTrue();
    }

    @Test
    void should_return_true_when_call_hashcode() {
        // GIVEN
        var secondBuilding = new AbstractBuilding(statistic) {};

        // WHEN && THEN
        assertThat(building.hashCode()).isEqualTo(secondBuilding.hashCode());
    }

    @Test
    void should_return_false_when_call_equals() {
        // GIVEN
        statistic = statistic.toBuilder()
                .name("differentName")
                .build();
        var secondBuilding = new AbstractBuilding(statistic) {};

        // WHEN && THEN
        assertThat(building.equals(secondBuilding)).isFalse();
    }

    @Test
    void should_return_false_when_call_hashcode() {
        // GIVEN
        statistic = statistic.toBuilder()
                .name("differentName")
                .build();
        var secondBuilding = new AbstractBuilding(statistic) {};

        // WHEN && THEN
        assertThat(building.hashCode()).isNotEqualTo(secondBuilding.hashCode());
    }
}
