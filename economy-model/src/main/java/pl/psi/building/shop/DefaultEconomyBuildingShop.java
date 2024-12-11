package pl.psi.building.shop;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import pl.psi.building.EconomyBuilding;
import pl.psi.building.factory.EconomyBuildingFactory;
import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resource;
import pl.psi.town.Town;

import java.util.Set;

@RequiredArgsConstructor
class DefaultEconomyBuildingShop implements EconomyBuildingShop {

    private final EconomyBuildingFactory economyBuildingFactory;

    @Override
    public EconomyBuilding buy(EconomyHero aBuyer, String aBuildingName) {
        var building = economyBuildingFactory.createBuilding(aBuildingName);
        Preconditions.checkState(
                aBuyer.hasEnoughResources(building.getBuildingCost()),
                "Buyer has not enough gold cost to buy a building %s",
                aBuildingName
        );
        subtractResources(aBuyer, building.getBuildingCost());
        return building;
    }

    @Override
    public boolean hasHeroEnoughResourcesToBuyBuilding(EconomyHero aHero, String aBuildingName) {
        EconomyBuilding building = economyBuildingFactory.createBuilding(aBuildingName);
        return aHero.hasEnoughResources(building.getBuildingCost());
    }

    private void subtractResources(EconomyHero aHero, Set<Resource> aResourcesToSubtract) {
        aResourcesToSubtract.forEach(aHero::subtractResource);
    }
}
