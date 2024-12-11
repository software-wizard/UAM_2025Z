package pl.psi.building.shop;

import pl.psi.building.factory.EconomyBuildingFactory;

public class EconomyBuildingShopFactory {

    public static EconomyBuildingShop createEconomyBuildingShop(EconomyBuildingFactory economyBuildingFactory) {
        return new DefaultEconomyBuildingShop(economyBuildingFactory);
    }
}
