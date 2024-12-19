package pl.psi.building.shop;

import pl.psi.building.factory.EconomyBuildingAbstractFactory;
import pl.psi.hero.EconomyHero;

public class EconomyBuildingShopFactory {

    public static EconomyBuildingShop createEconomyBuildingShop(
            EconomyHero.Fraction fraction,
            EconomyBuildingAbstractFactory abstractFactory
    ) {
        return new DefaultEconomyBuildingShop(fraction, abstractFactory);
    }
}
