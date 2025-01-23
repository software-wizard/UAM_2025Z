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
import pl.psi.building.model.UpgradableBuilding;
import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resources;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static pl.psi.hero.EconomyHero.Fraction.NECROPOLIS;
import static pl.psi.resource.Resources.Type.GOLD;

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
        var costOfBuilding = Resources.builder().resource(GOLD, 100).build();
        var buyer = new EconomyHero("A", NECROPOLIS, Resources.builder().resource(GOLD, 1000).build());
        var buildingStatistic = new EconomyBuildingStatistic(buildingName, EconomyBuildingStatistic.Type.BUILDING, costOfBuilding, List.of());
        var buildingToBuy = new DefaultEconomyBuilding(buildingStatistic);

        // WHEN
        when(abstractFactory.getEconomyBuildingFactory(NECROPOLIS, EconomyBuildingStatistic.Type.BUILDING))
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
        assertThat(buyer.getResourceAmount(GOLD)).isEqualTo(900);
    }

    @Test
    @DisplayName("Should throw exception when buyer cannot afford building.")
    void should_throw_exception_when_buyer_cannot_afford_building() {
        // GIVEN
        var buildingName = "Tomb of Souls";
        var costOfBuilding = Resources.builder().resource(GOLD, 2000).build();
        var buyer = new EconomyHero("A", NECROPOLIS, Resources.builder().resource(GOLD, 1000).build());
        var buildingStatistic = new EconomyBuildingStatistic(buildingName, EconomyBuildingStatistic.Type.BUILDING, costOfBuilding, List.of());

        // WHEN
        when(abstractFactory.getEconomyBuildingFactory(NECROPOLIS, EconomyBuildingStatistic.Type.BUILDING))
                .thenReturn(new EconomyBuildingFactory() {
                    @Override
                    public EconomyBuilding createBuilding(String aBuildingName) {
                        return new DefaultEconomyBuilding(buildingStatistic);
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

        // THEN
        assertThatThrownBy(() -> economyBuildingShop.buyBuilding(buyer, buildingName))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Buyer has not enough gold cost to buy a building");
    }

    @Test
    @DisplayName("Should successfully upgrade a building.")
    void should_upgrade_building() {
        // GIVEN
        var buyer = new EconomyHero("A", NECROPOLIS, Resources.builder().resource(GOLD, 1000).build());
        var upgradableBuilding = Mockito.mock(UpgradableBuilding.class);

        // WHEN
        when(upgradableBuilding.getStatistic()).thenReturn(EconomyBuildingStatistic.builder().build());
        when(upgradableBuilding.getUpgradeCost()).thenReturn(Resources.builder().resource(GOLD, 500).build());
        UpgradableBuilding upgradedBuilding = economyBuildingShop.buyBuildingUpgrade(buyer, upgradableBuilding);

        // THEN
        assertThat(upgradedBuilding).isEqualTo(upgradableBuilding);
        assertThat(buyer.getResourceAmount(GOLD)).isEqualTo(500);
    }

    @Test
    @DisplayName("Should throw exception when building is not upgradable.")
    void should_throw_exception_when_building_not_upgradable() {
        // GIVEN
        var buyer = new EconomyHero("A", NECROPOLIS, Resources.builder().resource(GOLD, 1000).build());
        var nonUpgradableBuilding = Mockito.mock(EconomyBuilding.class);

        // WHEN && THEN
        assertThatThrownBy(() -> economyBuildingShop.buyBuildingUpgrade(buyer, nonUpgradableBuilding))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Building is not upgradable!");
    }

    @Test
    @DisplayName("Should rollback resources after a failed building purchase.")
    void should_refund_resources_after_failed_purchase() {
        // GIVEN
        var buildingStatistic = EconomyBuildingStatistic.builder()
                .cost(Resources.builder().resource(GOLD, 100).build())
                .build();
        var building = Mockito.mock(EconomyBuilding.class);
        var buyer = new EconomyHero("A", NECROPOLIS, Resources.builder().resource(GOLD, 1000).build());

        // WHEN
        when(building.getStatistic()).thenReturn(buildingStatistic);
        economyBuildingShop.refund(buyer, building);

        // THEN
        assertThat(buyer.getResourceAmount(GOLD)).isEqualTo(900);
    }
}