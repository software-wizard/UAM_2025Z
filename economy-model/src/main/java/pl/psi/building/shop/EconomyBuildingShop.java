package pl.psi.building.shop;

import pl.psi.building.EconomyBuilding;
import pl.psi.hero.EconomyHero;

public interface EconomyBuildingShop {

    EconomyBuilding buyBuilding(EconomyHero aBuyer, String aBuildingName);
}
