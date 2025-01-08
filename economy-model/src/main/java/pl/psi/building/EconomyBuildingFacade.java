package pl.psi.building;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import pl.psi.building.factory.EconomyBuildingAbstractFactory;
import pl.psi.building.model.EconomyBuildingStatistic;
import pl.psi.hero.EconomyHero;
import pl.psi.building.town.Town;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class EconomyBuildingFacade {

    private final EconomyBuildingAbstractFactory abstractFactory;

    public void buildBuilding(EconomyHero aBuyer, Town aTown, String aBuildingName) {
        Preconditions.checkArgument(!aBuildingName.isBlank());
        aTown.buildBuilding(aBuyer, aBuildingName);
    }

    public void upgradeBuilding(EconomyHero aUpgrader, Town aTown, String aBuildingName) {
        aTown.upgradeBuilding(aUpgrader, aBuildingName);
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
            EconomyBuildingStatistic.Type aType,
            EconomyHero.Fraction aFraction
    ) {
        return abstractFactory.getAllFactories(aFraction)
                .stream()
                .flatMap(factory -> factory.getAllAvailableBuildingsToBuild().stream())
                .filter(economyBuildingStatistic -> economyBuildingStatistic.type().equals(aType))
                .collect(Collectors.toSet());
    }
}
