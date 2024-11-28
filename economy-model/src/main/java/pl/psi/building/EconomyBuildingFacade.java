package pl.psi.building;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import pl.psi.building.engine.EconomyBuildingEngine;
import pl.psi.building.shop.EconomyBuildingShop;
import pl.psi.hero.EconomyHero;
import pl.psi.town.Town;

@RequiredArgsConstructor
public class EconomyBuildingFacade {

    private final EconomyBuildingShop economyBuildingShop;
    private final EconomyBuildingEngine economyBuildingEngine;

    public void buildBuilding(EconomyHero aBuyer, Town aTown, EconomyBuilding aBuildingToBuy) {
        Preconditions.checkState(
                aBuildingToBuy.getBuiltState() == EconomyBuilding.BuildingState.TO_BUILD,
                "Incorrect state of building to build. Actual state: %s",
                aBuildingToBuy.getBuiltState()
        );
        Preconditions.checkState(
                !aTown.getBuildings().contains(aBuildingToBuy),
                "Building %s is already built",
                aBuildingToBuy.getName()
        );
        economyBuildingShop.buy(aBuyer, aTown, aBuildingToBuy);
        economyBuildingEngine.buildBuilding(aBuildingToBuy);
    }

    public void nextRound() {
        economyBuildingEngine.nextRound();
    }
}
