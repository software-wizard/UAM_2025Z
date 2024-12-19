package pl.psi.building;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.psi.building.factory.EconomyBuildingAbstractFactory;
import pl.psi.building.model.DefaultEconomyBuilding;
import pl.psi.building.model.EconomyBuildingStatistic;
import pl.psi.building.shop.EconomyBuildingShop;
import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resources;
import pl.psi.town.Town;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EconomyBuildingFacadeTest {

    private EconomyBuildingShop economyBuildingShopMock;
    private EconomyBuildingFacade economyBuildingFacade;

    @BeforeEach
    void setUp() {
        this.economyBuildingShopMock = Mockito.mock(EconomyBuildingShop.class);
        EconomyBuildingAbstractFactory abstractFactory = Mockito.mock(EconomyBuildingAbstractFactory.class);
        this.economyBuildingFacade = new EconomyBuildingFacade(economyBuildingShopMock, abstractFactory);
    }

    @Test
    @DisplayName("Should successfully delegate tasks to shop and engine.")
    void should_buy_a_building_and_build_it_in_town() {
        // GIVEN
        var buildingName = "test";
        var buyer = new EconomyHero(
                EconomyHero.Fraction.NECROPOLIS,
                new Resources(Map.of(Resources.ResourceType.GOLD, 1000))
        );
        var building = new DefaultEconomyBuilding(
                new EconomyBuildingStatistic(
                        "test",
                        EconomyBuildingStatistic.EconomyBuildingType.BUILDING,
                        new Resources(Map.of(Resources.ResourceType.GOLD, 100)),
                        List.of()
                )
        );
        var town = Town.builder()
                .name("")
                .buildings(Set.of())
                .build();

        // WHEN
        when(economyBuildingShopMock.buyBuilding(buyer, buildingName))
                .thenReturn(building);
        economyBuildingFacade.buildBuilding(buyer, town, buildingName);

        // THEN
        verify(economyBuildingShopMock).buyBuilding(buyer, buildingName);
    }

    @Test
    void testBuildBuilding_Fails_WhenBuildingAlreadyBuilt() {
        // GIVEN
        var buildingName = "test";
        var buyer = new EconomyHero(EconomyHero.Fraction.NECROPOLIS, new Resources(Map.of(Resources.ResourceType.GOLD, 1000)));
        var town = Mockito.mock(Town.class);

        // WHEN
        economyBuildingFacade.buildBuilding(buyer, town, buildingName);

        // THEN
        verify(economyBuildingShopMock).buyBuilding(buyer, buildingName);
        verify(town).buildBuilding(Mockito.any());
    }
}