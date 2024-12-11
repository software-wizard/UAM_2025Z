package pl.psi.building.shop;

import pl.psi.building.EconomyBuilding;
import pl.psi.hero.EconomyHero;
import pl.psi.town.Town;

public interface EconomyBuildingShop {

    EconomyBuilding buy(EconomyHero aBuyer, String aBuildingName);

    boolean hasHeroEnoughResourcesToBuyBuilding(EconomyHero aHero, String aBuildingName);
}
