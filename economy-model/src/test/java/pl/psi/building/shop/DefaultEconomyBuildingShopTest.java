package pl.psi.building.shop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.psi.building.EconomyBuilding;
import pl.psi.building.EconomyBuildingType;
import pl.psi.hero.EconomyHero;
import pl.psi.town.Town;

import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.psi.hero.EconomyHero.Fraction.NECROPOLIS;

class DefaultEconomyBuildingShopTest {

    private final DefaultEconomyBuildingShop economyBuildingShop =
            new DefaultEconomyBuildingShop();

    @Test
    @DisplayName("Should successfully buy a building.")
    void should_buy_building() {
        // GIVEN
        var town = Town.builder()
                .buildings(new HashSet<>())
                .fraction(NECROPOLIS)
                .name("Coldreign")
                .build();
        var buyer = new EconomyHero(NECROPOLIS, 1000);
        var buildingToBuy = EconomyBuilding.builder()
                .prerequisites(List.of())
                .fraction(NECROPOLIS)
                .goldCost(100)
                .name("Tomb of Souls")
                .type(EconomyBuildingType.CREATURE_GENERATOR)
                .build();

        // WHEN
        economyBuildingShop.buy(buyer, town, buildingToBuy);

        // THEN
        assertThat(town.getBuildings()).contains(buildingToBuy);
        assertThat(buyer.getGold()).isEqualTo(900);
    }
}