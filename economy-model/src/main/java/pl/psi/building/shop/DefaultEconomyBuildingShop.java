package pl.psi.building.shop;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import pl.psi.building.factory.EconomyBuildingAbstractFactory;
import pl.psi.building.model.EconomyBuilding;
import pl.psi.building.model.EconomyBuildingStatistic;
import pl.psi.building.model.UpgradableBuilding;
import pl.psi.hero.EconomyHero;

import java.util.EnumSet;

@RequiredArgsConstructor
class DefaultEconomyBuildingShop implements EconomyBuildingShop {

    private final EconomyHero.Fraction fraction;
    private final EconomyBuildingAbstractFactory abstractFactory;

    @Override
    public EconomyBuilding buyBuilding(EconomyHero aBuyer, String aBuildingName) {
        EconomyBuildingStatistic foundBuildingStatistic = EnumSet.allOf(EconomyBuildingStatistic.EconomyBuildingType.class)
                .stream()
                .map(type -> abstractFactory.getEconomyBuildingFactory(fraction, type))
                .flatMap(factory -> factory.getAllAvailableBuildingsToBuild().stream())
                .filter(economyBuildingStatistic -> economyBuildingStatistic.name().equals(aBuildingName))
                .findFirst()
                .orElseThrow();
        Preconditions.checkState(
                aBuyer.canAfford(foundBuildingStatistic.cost()),
                "Buyer has not enough gold cost to buy a building %s",
                aBuildingName
        );
        aBuyer.subtractResource(foundBuildingStatistic.cost());
        return abstractFactory.getEconomyBuildingFactory(fraction, foundBuildingStatistic.type()).createBuilding(aBuildingName);
    }

    @Override
    public UpgradableBuilding buyBuildingUpgrade(EconomyHero aBuyer, EconomyBuilding aBuildingToUpgrade) {
        if (!(aBuildingToUpgrade instanceof UpgradableBuilding upgradableBuilding)) {
            throw new IllegalArgumentException("Building is not upgradable!");
        }
        Preconditions.checkState(
                aBuyer.canAfford(upgradableBuilding.getUpgradeCost()),
                "Buyer has not enough gold cost to buy a building %s",
                aBuildingToUpgrade.getStatistic().name()
        );
        return upgradableBuilding;
    }
}
