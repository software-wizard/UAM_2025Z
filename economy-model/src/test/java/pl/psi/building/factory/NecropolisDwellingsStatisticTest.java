package pl.psi.building.factory;

import org.junit.jupiter.api.Test;
import pl.psi.building.model.EconomyBuildingStatistic;
import pl.psi.resource.Resources;

import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NecropolisDwellingsStatisticTest {

    @Test
    void all_dwellings_should_have_valid_statistics() {
        // GIVEN
        var dwellings = EnumSet.allOf(CreatureDwellingsNecropolisFactory.NecropolisDwellingsStatistic.class);

        // WHEN & THEN
        dwellings.forEach(dwelling -> {
            assertNotNull(dwelling.getBuildingStatistic(), dwelling.name() + " has null buildingStatistic");
            assertNotNull(dwelling.getUpgradeCost(), dwelling.name() + " has null upgradeCost");
            assertNotNull(dwelling.getCostPerWeek(), dwelling.name() + " has null costPerWeek");
            assertTrue(dwelling.getCreatureTier() > 0, dwelling.name() + " has invalid creature tier");
        });
    }

    @Test
    void dwelling_should_have_correct_prerequisites() {
        // GIVEN
        var graveyard = CreatureDwellingsNecropolisFactory.NecropolisDwellingsStatistic.GRAVEYARD;

        // WHEN
        List<EconomyBuildingStatistic> prerequisites = graveyard.getBuildingStatistic().prerequisites();

        // THEN
        assertEquals(1, prerequisites.size());
        assertEquals("Cursed_Temple", prerequisites.getFirst().name());
    }

    @Test
    void dwelling_should_have_correct_cost() {
        // GIVEN
        var mausoleum = CreatureDwellingsNecropolisFactory.NecropolisDwellingsStatistic.MAUSOLEUM;

        // WHEN
        Resources cost = mausoleum.getBuildingStatistic().cost();

        // THEN
        assertNotNull(cost);
        assertEquals(1, cost.getResourceAmount(Resources.Type.ORE));
        assertEquals(1, cost.getResourceAmount(Resources.Type.SULFUR));
        assertEquals(2000, cost.getResourceAmount(Resources.Type.GOLD));
    }

    @Test
    void dwelling_should_have_correct_upgrade_cost() {
        // GIVEN
        var dragonVault = CreatureDwellingsNecropolisFactory.NecropolisDwellingsStatistic.DRAGON_VAULT;

        // WHEN
        Resources upgradeCost = dragonVault.getUpgradeCost();

        // THEN
        assertNotNull(upgradeCost);
        assertEquals(20, upgradeCost.getResourceAmount(Resources.Type.MERCURY));
        assertEquals(15000, upgradeCost.getResourceAmount(Resources.Type.GOLD));
    }

    @Test
    void dwelling_should_have_correct_cost_per_week() {
        // GIVEN
        var hallOfDarkness = CreatureDwellingsNecropolisFactory.NecropolisDwellingsStatistic.HALL_OF_DARKNESS;

        // WHEN
        Resources costPerWeek = hallOfDarkness.getCostPerWeek();

        // THEN
        assertNotNull(costPerWeek);
        assertEquals(6000, costPerWeek.getResourceAmount(Resources.Type.GOLD));
    }
}
