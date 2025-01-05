package pl.psi.building.shop;

import pl.psi.building.model.EconomyBuilding;
import pl.psi.building.model.UpgradableBuilding;
import pl.psi.hero.EconomyHero;

public interface EconomyBuildingShop {

    EconomyBuilding buyBuilding(EconomyHero aBuyer, String aBuildingName);
    UpgradableBuilding buyBuildingUpgrade(EconomyHero aBuyer, EconomyBuilding aBuildingName);
}
