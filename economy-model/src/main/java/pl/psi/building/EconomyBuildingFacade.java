package pl.psi.building;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import pl.psi.building.factory.EconomyBuildingFactory;
import pl.psi.building.shop.EconomyBuildingShop;
import pl.psi.hero.EconomyHero;
import pl.psi.town.Town;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class EconomyBuildingFacade {

    private final EconomyBuildingShop economyBuildingShop;
    private final EconomyBuildingFactory<EconomyBuilding> economyBuildingFactory;
    private final EconomyBuildingFactory<CreatureDwellingsBuilding> economyDwellingsFactory;

    public void buildBuilding(EconomyHero aBuyer, Town aTown, String aBuildingName) {
        Preconditions.checkArgument(!aBuildingName.isBlank());
        EconomyBuilding boughtBuilding = economyBuildingShop.buyBuilding(aBuyer, aBuildingName);
        aTown.buildBuilding(boughtBuilding);
    }

    public Optional<EconomyBuildingStatistic> findEconomyBuildingStatistic(String aBuildingName) {
        return Stream.concat(
                economyDwellingsFactory.getAllAvailableBuildingsToBuild().stream(),
                economyBuildingFactory.getAllAvailableBuildingsToBuild().stream()
        ).filter(building -> building.name().equals(aBuildingName)).findFirst();
    }

    public void upgradeDwellingsBuilding(EconomyHero aBuyer, CreatureDwellingsBuilding aBuilding) {
        aBuilding.upgrade();
    }

    public Set<EconomyBuildingStatistic> getAllAvailableBuildingsToBuild(EconomyBuildingStatistic.EconomyBuildingType type) {
        return type == EconomyBuildingStatistic.EconomyBuildingType.BUILDING
                ? economyBuildingFactory.getAllAvailableBuildingsToBuild()
                : economyDwellingsFactory.getAllAvailableBuildingsToBuild();
    }
}
