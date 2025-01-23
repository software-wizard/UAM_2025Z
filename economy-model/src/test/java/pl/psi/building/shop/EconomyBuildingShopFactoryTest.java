package pl.psi.building.shop;

import org.junit.jupiter.api.Test;
import pl.psi.building.factory.EconomyBuildingAbstractFactory;
import pl.psi.hero.EconomyHero;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EconomyBuildingShopFactoryTest {

    @Test
    void create_economy_building_shop_should_return_valid_instance() {
        // GIVEN
        EconomyHero.Fraction fraction = EconomyHero.Fraction.NECROPOLIS;
        EconomyBuildingAbstractFactory abstractFactory = mock(EconomyBuildingAbstractFactory.class);

        // WHEN
        EconomyBuildingShop result = EconomyBuildingShopFactory.createEconomyBuildingShop(fraction, abstractFactory);

        // THEN
        assertNotNull(result);
        assertInstanceOf(DefaultEconomyBuildingShop.class, result);
    }

    @Test
    void create_economy_building_shop_should_not_interact_with_factory_methods() {
        // GIVEN
        EconomyHero.Fraction fraction = EconomyHero.Fraction.NECROPOLIS;
        EconomyBuildingAbstractFactory abstractFactory = mock(EconomyBuildingAbstractFactory.class);

        // WHEN
        EconomyBuildingShopFactory.createEconomyBuildingShop(fraction, abstractFactory);

        // THEN
        verifyNoInteractions(abstractFactory);
    }
}
