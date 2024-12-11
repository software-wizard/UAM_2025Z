package pl.psi.building;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.psi.building.factory.EconomyBuildingFactory;
import pl.psi.building.shop.EconomyBuildingShop;
import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resource;
import pl.psi.town.Town;

import java.util.Set;

import static org.mockito.Mockito.verify;

class EconomyBuildingFacadeTest {

    private EconomyBuildingShop economyBuildingShopMock;
    private EconomyBuildingFacade economyBuildingFacade;

    @BeforeEach
    void setUp() {
        this.economyBuildingShopMock = Mockito.mock(EconomyBuildingShop.class);
        EconomyBuildingFactory economyBuildingFactoryMock = Mockito.mock(EconomyBuildingFactory.class);
        this.economyBuildingFacade = new EconomyBuildingFacade(economyBuildingShopMock, economyBuildingFactoryMock);
    }

    @Test
    @DisplayName("Should successfully delegate tasks to shop and engine.")
    void should_buy_a_building_and_build_it_in_town() {
        // GIVEN
        var buildingName = "test";
        var buyer = new EconomyHero(
                EconomyHero.Fraction.NECROPOLIS,
                Set.of(new Resource(Resource.ResourceType.GOLD, 1000))
        );
        var town = Town.builder()
                .name("")
                .buildings(Set.of())
                .build();

        // WHEN
        economyBuildingFacade.buildBuilding(buyer, town, buildingName);

        // THEN
        verify(economyBuildingShopMock).buy(buyer, buildingName);
    }

    @Test
    void testBuildBuilding_Fails_WhenBuildingAlreadyBuilt() {
        // GIVEN
        var buildingName = "test";
        var buyer = new EconomyHero(EconomyHero.Fraction.NECROPOLIS, Set.of(new Resource(Resource.ResourceType.GOLD, 1000)));
        var town = Mockito.mock(Town.class);

        // WHEN
        economyBuildingFacade.buildBuilding(buyer, town, buildingName);

        // THEN
        verify(economyBuildingShopMock).buy(buyer, buildingName);
        verify(town).buildBuilding(Mockito.any());
    }
}