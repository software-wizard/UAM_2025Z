package pl.psi.building.shop;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import pl.psi.building.EconomyBuilding;
import pl.psi.hero.EconomyHero;
import pl.psi.town.Town;

@RequiredArgsConstructor
class DefaultEconomyBuildingShop implements EconomyBuildingShop {

    @Override
    public void buy(EconomyHero aBuyer, Town aTown, EconomyBuilding aBuildingToBuy) {
        Preconditions.checkState(
                aBuyer.getGold() >= aBuildingToBuy.getGoldCost(),
                "Buyer has not enough gold cost to buy a building %s",
                aBuildingToBuy.getName()
        );
        Preconditions.checkState(
                aBuyer.getFraction() == aTown.getFraction(),
                "Buyer fraction does not match town fraction. Buyer fraction: %s. Town fraction: %s",
                aBuildingToBuy.getFraction(),
                aTown.getFraction()
        );
        aBuyer.substractGold(aBuildingToBuy.getGoldCost());
        try {
            aTown.buildBuilding(aBuildingToBuy);
        } catch(final Exception ex) {
            aBuyer.addGold(aBuildingToBuy.getGoldCost());
            throw new IllegalStateException(String.format("Building cannot be built in town %s", aTown.getName()));
        }
    }
}
