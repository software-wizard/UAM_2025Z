package pl.psi.building.factory;

import org.junit.jupiter.api.Test;
import pl.psi.building.model.DefaultEconomyBuilding;
import pl.psi.building.model.EconomyBuildingStatistic;
import pl.psi.resource.Resources;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EconomyBuildingNecropolisFactoryTest {

    @Test
    void should_create_building_by_name() {
        // GIVEN
        EconomyBuildingNecropolisFactory factory = new EconomyBuildingNecropolisFactory();
        String buildingName = "TAVERN";

        // WHEN
        DefaultEconomyBuilding result = factory.createBuilding(buildingName);

        // THEN
        assertNotNull(result);
        assertEquals(buildingName, result.getStatistic().name());
        assertEquals(500, result.getStatistic().cost().getResourceAmount(Resources.Type.GOLD));
        assertEquals(5, result.getStatistic().cost().getResourceAmount(Resources.Type.WOOD));
        assertTrue(result.getStatistic().prerequisites().isEmpty());
    }

    @Test
    void should_return_statistic_by_name() {
        // GIVEN
        EconomyBuildingNecropolisFactory factory = new EconomyBuildingNecropolisFactory();
        String buildingName = "Marketplace";

        // WHEN
        Optional<EconomyBuildingStatistic> result = factory.getStatisticByName(buildingName);

        // THEN
        assertTrue(result.isPresent());
        EconomyBuildingStatistic statistic = result.get();
        assertEquals(buildingName, statistic.name());
        assertEquals(500, statistic.cost().getResourceAmount(Resources.Type.GOLD));
        assertEquals(5, statistic.cost().getResourceAmount(Resources.Type.WOOD));
        assertTrue(statistic.prerequisites().isEmpty());
    }

    @Test
    void should_return_empty_optional_for_invalid_building_name() {
        // GIVEN
        EconomyBuildingNecropolisFactory factory = new EconomyBuildingNecropolisFactory();
        String invalidBuildingName = "INVALID_NAME";

        // WHEN
        Optional<EconomyBuildingStatistic> result = factory.getStatisticByName(invalidBuildingName);

        // THEN
        assertTrue(result.isEmpty());
    }

    @Test
    void should_return_all_available_buildings_to_build() {
        // GIVEN
        EconomyBuildingNecropolisFactory factory = new EconomyBuildingNecropolisFactory();

        // WHEN
        Set<EconomyBuildingStatistic> result = factory.getAllAvailableBuildingsToBuild();

        // THEN
        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(stat -> stat.name().equals("Tavern")));
        assertTrue(result.stream().anyMatch(stat -> stat.name().equals("Marketplace")));
        assertTrue(result.stream().anyMatch(stat -> stat.name().equals("Resource_Silo")));
    }

    @Test
    void should_create_building_with_prerequisites() {
        // GIVEN
        EconomyBuildingNecropolisFactory factory = new EconomyBuildingNecropolisFactory();
        String buildingName = "RESOURCE_SILO";

        // WHEN
        DefaultEconomyBuilding result = factory.createBuilding(buildingName);

        // THEN
        assertNotNull(result);
        assertEquals(buildingName, result.getStatistic().name());
        assertEquals(5000, result.getStatistic().cost().getResourceAmount(Resources.Type.GOLD));
        assertEquals(5, result.getStatistic().cost().getResourceAmount(Resources.Type.ORE));
        assertEquals(1, result.getStatistic().prerequisites().size());
        assertEquals("Marketplace", result.getStatistic().prerequisites().getFirst().name());
    }
}
