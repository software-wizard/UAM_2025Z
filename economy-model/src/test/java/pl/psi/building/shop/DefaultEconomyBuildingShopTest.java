package pl.psi.building.shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.psi.building.EconomyBuilding;
import pl.psi.building.EconomyBuildingStatistic;
import pl.psi.building.factory.EconomyBuildingFactory;
import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resource;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static pl.psi.hero.EconomyHero.Fraction.NECROPOLIS;

class DefaultEconomyBuildingShopTest {

    private DefaultEconomyBuildingShop economyBuildingShop;
    private EconomyBuildingFactory economyBuildingFactory;

    @BeforeEach
    void setUp() {
        economyBuildingFactory = Mockito.mock(EconomyBuildingFactory.class);
        economyBuildingShop = new DefaultEconomyBuildingShop(economyBuildingFactory);
    }

    @Test
    @DisplayName("Should successfully buy a building.")
    void should_buy_building() {
        // GIVEN
        var buildingName = "Tomb of Souls";
        var costOfBuilding = Set.of(new Resource(Resource.ResourceType.GOLD, 100));
        var buyer = new EconomyHero(NECROPOLIS, Set.of(new Resource(Resource.ResourceType.GOLD, 1000)));
        var buildingStatistic = new EconomyBuildingStatistic(buildingName, costOfBuilding, List.of());
        var buildingToBuy = new EconomyBuilding(buildingStatistic);

        // WHEN
        when(economyBuildingFactory.createBuilding(buildingName)).thenReturn(buildingToBuy);
        economyBuildingShop.buy(buyer, buildingName);

        // THEN
        assertThat(buyer.getResource(Resource.ResourceType.GOLD).amount()).isEqualTo(900);
    }
}