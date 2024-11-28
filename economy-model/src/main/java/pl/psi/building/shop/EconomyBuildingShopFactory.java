package pl.psi.building.shop;

public class EconomyBuildingShopFactory {

    public static EconomyBuildingShop createEconomyBuildingShop() {
        return new DefaultEconomyBuildingShop();
    }
}
