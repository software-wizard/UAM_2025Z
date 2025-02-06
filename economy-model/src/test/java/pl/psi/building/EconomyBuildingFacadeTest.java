package pl.psi.building;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.psi.building.factory.EconomyBuildingAbstractFactory;
import pl.psi.building.factory.EconomyBuildingFactory;
import pl.psi.building.model.EconomyBuilding;
import pl.psi.building.model.EconomyBuildingStatistic;
import pl.psi.building.model.UpgradableBuilding;
import pl.psi.building.town.Town;
import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resources;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pl.psi.resource.Resources.Type.GOLD;

class EconomyBuildingFacadeTest {

    private EconomyBuildingFacade economyBuildingFacade;
    private EconomyBuildingAbstractFactory abstractFactory;

    @BeforeEach
    void setUp() {
        abstractFactory = mock(EconomyBuildingAbstractFactory.class);
        this.economyBuildingFacade = new EconomyBuildingFacade(abstractFactory);
    }

    @Test
    void should_build_building_from_name() {
        // GIVEN
        var buildingName = "test";
        var buyer = new EconomyHero(
                "A",
                EconomyHero.Fraction.NECROPOLIS,
                Resources.builder()
                        .resource(GOLD, 1000)
                        .build(),
                Mockito.mock(Town.class)
        );
        var town = mock(Town.class);

        // WHEN
        economyBuildingFacade.buildBuilding(buyer, town, buildingName);

        // THEN
        verify(town).buildBuilding(buyer, buildingName);
    }

    @Test
    void should_throw_exception_when_building_name_is_blank() {
        var town = mock(Town.class);
        var buyer = new EconomyHero(
                "B",
                EconomyHero.Fraction.NECROPOLIS,
                Resources.builder()
                        .resource(GOLD, 1000)
                        .build(),
                town
        );

        assertThatThrownBy(() -> economyBuildingFacade.buildBuilding(buyer, town, ""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void should_find_economy_building_statistic() {
        var buildingName = "testBuilding";
        var fraction = EconomyHero.Fraction.NECROPOLIS;
        var statistic = EconomyBuildingStatistic.builder()
                .name(buildingName)
                .build();

        var factory = mock(EconomyBuildingFactory.class);
        when(factory.getAllAvailableBuildingsToBuild()).thenReturn(Set.of(statistic));

        when(abstractFactory.getAllFactories(fraction)).thenReturn(Set.of(factory));

        var result = economyBuildingFacade.findEconomyBuildingStatistic(buildingName, fraction);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(statistic);
    }

    @Test
    void should_return_empty_when_economy_building_statistic_not_found() {
        var fraction = EconomyHero.Fraction.NECROPOLIS;

        when(abstractFactory.getAllFactories(fraction)).thenReturn(Set.of());

        var result = economyBuildingFacade.findEconomyBuildingStatistic("nonExistentBuilding", fraction);

        assertThat(result).isEmpty();
    }

    @Test
    void should_get_all_available_buildings_of_type() {
        var fraction = EconomyHero.Fraction.NECROPOLIS;
        var type = EconomyBuildingStatistic.Type.DWELLINGS;

        var statistic = EconomyBuildingStatistic.builder()
                .type(type)
                .build();

        var factory = mock(EconomyBuildingFactory.class);
        when(factory.getAllAvailableBuildingsToBuild()).thenReturn(Set.of(statistic));

        when(abstractFactory.getAllFactories(fraction)).thenReturn(Set.of(factory));

        var result = economyBuildingFacade.getAllAvailableBuildingsToBuild(type, fraction);

        assertThat(result).containsExactly(statistic);
    }

    @Test
    void should_return_empty_list_when_no_buildings_available() {
        var fraction = EconomyHero.Fraction.NECROPOLIS;
        var type = EconomyBuildingStatistic.Type.DWELLINGS;

        when(abstractFactory.getAllFactories(fraction)).thenReturn(Set.of());

        var result = economyBuildingFacade.getAllAvailableBuildingsToBuild(type, fraction);

        assertThat(result).isEmpty();
    }

    @Test
    void isBuildingUpgradable_shouldReturnTrueForUpgradableBuilding() {
        // GIVEN
        EconomyBuilding upgradableBuilding = mock(UpgradableBuilding.class);

        // WHEN
        boolean result = economyBuildingFacade.isBuildingUpgradable(upgradableBuilding);

        // THEN
        assertTrue(result, "Expected building to be upgradable");
    }

    @Test
    void isBuildingUpgradable_shouldReturnFalseForNonUpgradableBuilding() {
        // GIVEN
        EconomyBuilding nonUpgradableBuilding = mock(EconomyBuilding.class);

        // WHEN
        boolean result = economyBuildingFacade.isBuildingUpgradable(nonUpgradableBuilding);

        // THEN
        assertFalse(result, "Expected building to not be upgradable");
    }

    @Test
    void upgradeBuilding_shouldUpgradeBuildingSuccessfully() {
        // GIVEN
        Town town = mock(Town.class);
        EconomyHero buyer = mock(EconomyHero.class);
        String buildingToUpgrade = "Cursed_Temple";

        UpgradableBuilding upgradedBuilding = mock(UpgradableBuilding.class);
        when(town.upgradeBuilding(buyer, buildingToUpgrade)).thenReturn(upgradedBuilding);

        // WHEN
        UpgradableBuilding result = economyBuildingFacade.upgradeBuilding(town, buyer, buildingToUpgrade);

        // THEN
        assertNotNull(result, "Upgraded building should not be null");
        assertEquals(upgradedBuilding, result, "Expected the upgraded building to match the mock result");
        verify(town, times(1)).upgradeBuilding(buyer, buildingToUpgrade);
    }

    @Test
    void upgradeBuilding_shouldThrowExceptionIfBuildingCannotBeUpgraded() {
        // GIVEN
        Town town = mock(Town.class);
        EconomyHero buyer = mock(EconomyHero.class);
        String buildingToUpgrade = "Non_Existent_Building";

        when(town.upgradeBuilding(buyer, buildingToUpgrade)).thenThrow(new IllegalArgumentException("Cannot upgrade building"));

        // WHEN & THEN
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> economyBuildingFacade.upgradeBuilding(town, buyer, buildingToUpgrade),
                "Expected upgradeBuilding to throw an exception"
        );

        assertEquals("Cannot upgrade building", exception.getMessage(), "Exception message does not match");
    }
}