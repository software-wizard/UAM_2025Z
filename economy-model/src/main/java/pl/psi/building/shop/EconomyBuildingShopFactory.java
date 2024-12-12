package pl.psi.building.shop;

import pl.psi.building.CreatureDwellingsBuilding;
import pl.psi.building.EconomyBuilding;
import pl.psi.building.factory.EconomyBuildingFactory;

public class EconomyBuildingShopFactory {

    public static EconomyBuildingShop createEconomyBuildingShop(
            EconomyBuildingFactory<EconomyBuilding> economyBuildingFactory,
            EconomyBuildingFactory<CreatureDwellingsBuilding> creatureDwellingsBuildingEconomyBuildingFactory) {
        return new DefaultEconomyBuildingShop(economyBuildingFactory, creatureDwellingsBuildingEconomyBuildingFactory);
    }
}
