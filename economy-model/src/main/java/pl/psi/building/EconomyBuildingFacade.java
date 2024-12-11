package pl.psi.building;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import pl.psi.building.factory.EconomyBuildingFactory;
import pl.psi.building.shop.EconomyBuildingShop;
import pl.psi.hero.EconomyHero;
import pl.psi.town.Town;

@RequiredArgsConstructor
public class EconomyBuildingFacade {

    private final EconomyBuildingShop economyBuildingShop;
    private final EconomyBuildingFactory economyBuildingFactory;

    public void buildBuilding(EconomyHero aBuyer, Town aTown, String aBuildingName) {
        Preconditions.checkArgument(!aBuildingName.isBlank());
        EconomyBuilding boughtBuilding = economyBuildingShop.buy(aBuyer, aBuildingName);
        aTown.buildBuilding(boughtBuilding);
    }

    public boolean hasHeroEnoughResourcesToBuildBuilding(EconomyHero aBuyer, String aBuildingName) {
        Preconditions.checkArgument(!aBuildingName.isBlank());
        var building = economyBuildingFactory.createBuilding(aBuildingName);
        return aBuyer.hasEnoughResources(building.getBuildingCost());
    }

    public void upgradeDwellingsBuilding(EconomyHero aBuyer, CreatureDwellingsBuilding aBuilding) {
        aBuilding.upgrade();
    }

    public boolean hasHeroEnoughResourcesToUpgradeDwellings(EconomyHero aBuyer, String aDwellingsName) {
        Preconditions.checkArgument(!aDwellingsName.isBlank());
        CreatureDwellingsBuilding building = economyBuildingFactory.createDwellings(aDwellingsName);
        return aBuyer.hasEnoughResources(building.getUpgradeCost());
    }
}
