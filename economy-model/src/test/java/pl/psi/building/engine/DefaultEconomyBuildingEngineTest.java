package pl.psi.building.engine;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.psi.building.EconomyBuilding;
import pl.psi.building.EconomyBuildingType;
import pl.psi.hero.EconomyHero;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultEconomyBuildingEngineTest {

    private final DefaultEconomyBuildingEngine engine =
            new DefaultEconomyBuildingEngine(Set.of());

    @Test
    @DisplayName("Engine should start building building.")
    void engine_should_start_building_building() {
        // GIVEN
        var building = EconomyBuilding.builder()
                .name("Test")
                .type(EconomyBuildingType.CREATURE_GENERATOR)
                .fraction(EconomyHero.Fraction.NECROPOLIS)
                .prerequisites(List.of())
                .goldCost(100)
                .build();

        // WHEN
        engine.buildBuilding(building);

        // THEN
        assertThat(building.getBuiltState())
                .isEqualTo(EconomyBuilding.BuildingState.BEING_BUILT);
    }
}