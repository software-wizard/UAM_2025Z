package pl.psi.building.shop;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import pl.psi.building.CreatureDwellingsBuilding;
import pl.psi.building.EconomyBuilding;
import pl.psi.building.EconomyBuildingStatistic;
import pl.psi.building.factory.EconomyBuildingFactory;
import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resource;
import pl.psi.town.Town;

import java.util.Set;

@RequiredArgsConstructor
class DefaultEconomyBuildingShop implements EconomyBuildingShop {

    private final EconomyBuildingFactory<EconomyBuilding> economyBuildingFactory;
    private final EconomyBuildingFactory<CreatureDwellingsBuilding> creatureDwellingsFactory;

    @Override
    public EconomyBuilding buyBuilding(EconomyHero aBuyer, String aBuildingName) {
        EconomyBuildingStatistic buildingStatistic = economyBuildingFactory.getStatisticByName(aBuildingName)
                .or(() -> creatureDwellingsFactory.getStatisticByName(aBuildingName))
                .orElseThrow();
        Preconditions.checkState(
                aBuyer.hasEnoughResources(buildingStatistic.cost()),
                "Buyer has not enough gold cost to buy a building %s",
                aBuildingName
        );
        subtractResources(aBuyer, buildingStatistic.cost());
        return buildingStatistic.type() == EconomyBuildingStatistic.EconomyBuildingType.BUILDING
                ? economyBuildingFactory.createBuilding(aBuildingName)
                : creatureDwellingsFactory.createBuilding(aBuildingName);
    }

    private void subtractResources(EconomyHero aHero, Set<Resource> aResourcesToSubtract) {
        aResourcesToSubtract.forEach(aHero::subtractResource);
    }
}
