package pl.psi.building.shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.psi.building.factory.EconomyBuildingAbstractFactory;
import pl.psi.building.factory.EconomyBuildingFactory;
import pl.psi.building.model.DefaultEconomyBuilding;
import pl.psi.building.model.EconomyBuilding;
import pl.psi.building.model.EconomyBuildingStatistic;
import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resources;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static pl.psi.hero.EconomyHero.Fraction.NECROPOLIS;

class DefaultEconomyBuildingShopTest {

    private DefaultEconomyBuildingShop economyBuildingShop;
    private EconomyBuildingAbstractFactory abstractFactory;

    @BeforeEach
    void setUp() {
        abstractFactory = Mockito.mock(EconomyBuildingAbstractFactory.class);
        economyBuildingShop = new DefaultEconomyBuildingShop(NECROPOLIS, abstractFactory);
    }

    @Test
    @DisplayName("Should successfully buy a building.")
    void should_buy_building() {
        // GIVEN
        var buildingName = "Tomb of Souls";
        var costOfBuilding = new Resources(Map.of(Resources.ResourceType.GOLD, 100));
        var buyer = new EconomyHero(NECROPOLIS, new Resources(Map.of(Resources.ResourceType.GOLD, 1000)));
        var buildingStatistic = new EconomyBuildingStatistic(buildingName, EconomyBuildingStatistic.EconomyBuildingType.BUILDING, costOfBuilding, List.of());
        var buildingToBuy = new DefaultEconomyBuilding(buildingStatistic);

        // WHEN
        when(abstractFactory.getEconomyBuildingFactory(NECROPOLIS, EconomyBuildingStatistic.EconomyBuildingType.BUILDING))
                .thenReturn(new EconomyBuildingFactory() {
                    @Override
                    public EconomyBuilding createBuilding(String aBuildingName) {
                        return buildingToBuy;
                    }

                    @Override
                    public Optional<EconomyBuildingStatistic> getStatisticByName(String name) {
                        return Optional.empty();
                    }

                    @Override
                    public Set<EconomyBuildingStatistic> getAllAvailableBuildingsToBuild() {
                        return Set.of(buildingStatistic);
                    }
                });
        economyBuildingShop.buyBuilding(buyer, buildingName);

        // THEN
        assertThat(buyer.getResourceAmount(Resources.ResourceType.GOLD)).isEqualTo(900);
    }
}