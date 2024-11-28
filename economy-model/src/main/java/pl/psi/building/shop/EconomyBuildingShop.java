package pl.psi.building.shop;

import pl.psi.building.EconomyBuilding;
import pl.psi.hero.EconomyHero;
import pl.psi.town.Town;

public interface EconomyBuildingShop {

    void buy(EconomyHero aBuyer, Town aTown, EconomyBuilding aBuildingToBuy);
}
