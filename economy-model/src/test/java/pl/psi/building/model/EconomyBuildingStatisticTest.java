package pl.psi.building.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resources;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.psi.resource.Resources.Type.GOLD;

class EconomyBuildingStatisticTest {

    private EconomyHero buyer;

    @BeforeEach
    void setUp() {
        buyer = mock(EconomyHero.class);
    }

    @Test
    void should_return_true_when_buyer_has_enough_resources() {
        // GIVEN
        Resources buildingCost = Resources.builder().resource(GOLD, 100).build();
        EconomyBuildingStatistic statistic = EconomyBuildingStatistic.builder()
                .name("Test Building")
                .type(EconomyBuildingStatistic.Type.BUILDING)
                .cost(buildingCost)
                .prerequisites(List.of())
                .build();

        // WHEN
        when(buyer.canAfford(buildingCost)).thenReturn(true);
        boolean result = statistic.hasEnoughResourcesToBuild(buyer);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    void should_return_false_when_buyer_does_not_have_enough_resources() {
        // GIVEN
        Resources buildingCost = Resources.builder().resource(GOLD, 500).build();
        EconomyBuildingStatistic statistic = EconomyBuildingStatistic.builder()
                .name("Expensive Building")
                .type(EconomyBuildingStatistic.Type.BUILDING)
                .cost(buildingCost)
                .prerequisites(List.of())
                .build();

        // WHEN
        when(buyer.canAfford(buildingCost)).thenReturn(false);
        boolean result = statistic.hasEnoughResourcesToBuild(buyer);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    void should_handle_prerequisites_correctly() {
        // GIVEN
        Resources prerequisiteCost = Resources.builder().resource(GOLD, 50).build();
        EconomyBuildingStatistic prerequisite = EconomyBuildingStatistic.builder()
                .name("Prerequisite Building")
                .type(EconomyBuildingStatistic.Type.BUILDING)
                .cost(prerequisiteCost)
                .prerequisites(List.of())
                .build();

        Resources buildingCost = Resources.builder().resource(GOLD, 100).build();
        EconomyBuildingStatistic mainBuilding = EconomyBuildingStatistic.builder()
                .name("Main Building")
                .type(EconomyBuildingStatistic.Type.BUILDING)
                .cost(buildingCost)
                .prerequisites(List.of(prerequisite))
                .build();

        // WHEN
        when(buyer.canAfford(prerequisiteCost)).thenReturn(true);
        when(buyer.canAfford(buildingCost)).thenReturn(true);
        boolean canBuildMain = mainBuilding.hasEnoughResourcesToBuild(buyer);
        boolean canBuildPrerequisite = prerequisite.hasEnoughResourcesToBuild(buyer);

        // THEN
        assertThat(canBuildMain).isTrue();
        assertThat(canBuildPrerequisite).isTrue();
    }
}
