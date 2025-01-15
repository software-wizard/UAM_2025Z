package pl.psi.building.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.psi.resource.Resources;

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
                .prerequisites(null)
                .build();

        building = new AbstractBuilding(statistic) {};
    }

    @Test
    void shouldStartBuilding() {
        // GIVEN && WHEN
        building.startBuilding();

        // THEN
        assertThat(building.isBuilt()).isFalse();
    }

    @Test
    void shouldFinishBuilding() {
        // GIVEN
        building.startBuilding();

        // WHEN
        building.finishBuilding();

        // THEN
        assertThat(building.isBuilt()).isTrue();
    }

    @Test
    void shouldHaveCorrectInitialBuiltState() {
        // GIVEN && WHEN &&  THEN
        assertThat(building.isBuilt()).isFalse();
    }

    @Test
    void shouldReturnCorrectStatistic() {
        // GIVEN && WHEN && THEN
        assertThat(building.getStatistic()).isEqualTo(statistic);
    }
}
