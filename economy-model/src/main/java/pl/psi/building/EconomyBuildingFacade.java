package pl.psi.building;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import pl.psi.building.factory.EconomyBuildingAbstractFactory;
import pl.psi.building.model.EconomyBuilding;
import pl.psi.building.model.EconomyBuildingStatistic;
import pl.psi.building.model.UpgradableBuilding;
import pl.psi.building.shop.EconomyBuildingShop;
import pl.psi.hero.EconomyHero;
import pl.psi.town.Town;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class EconomyBuildingFacade {

    private final EconomyBuildingShop economyBuildingShop;
    private final EconomyBuildingAbstractFactory abstractFactory;

    public void buildBuilding(EconomyHero aBuyer, Town aTown, String aBuildingName) {
        Preconditions.checkArgument(!aBuildingName.isBlank());
        EconomyBuilding boughtBuilding = economyBuildingShop.buyBuilding(aBuyer, aBuildingName);
        aTown.buildBuilding(boughtBuilding);
    }

    public void upgradeBuilding(EconomyHero aUpgrader, Town aTown, String aBuildingName) {
        Preconditions.checkArgument(!aBuildingName.isBlank());
        EconomyBuilding economyBuildingToUpgrade = aTown.findBuildingByName(aBuildingName).orElseThrow();
        UpgradableBuilding buildingToUpgrade = economyBuildingShop.buyBuildingUpgrade(aUpgrader, economyBuildingToUpgrade);
        aTown.upgradeBuilding(buildingToUpgrade);
    }

    public Optional<EconomyBuildingStatistic> findEconomyBuildingStatistic(
            String aBuildingName,
            EconomyHero.Fraction aFraction
    ) {
        return abstractFactory.getAllFactories(aFraction)
                .stream()
                .flatMap(factory -> factory.getAllAvailableBuildingsToBuild().stream())
                .filter(economyBuildingStatistic -> economyBuildingStatistic.name().equals(aBuildingName))
                .findFirst();
    }

    public Set<EconomyBuildingStatistic> getAllAvailableBuildingsToBuild(
            EconomyBuildingStatistic.EconomyBuildingType aType,
            EconomyHero.Fraction aFraction
    ) {
        return abstractFactory.getAllFactories(aFraction)
                .stream()
                .flatMap(factory -> factory.getAllAvailableBuildingsToBuild().stream())
                .filter(economyBuildingStatistic -> economyBuildingStatistic.type().equals(aType))
                .collect(Collectors.toSet());
    }
}
