package pl.psi.building.factory;

import pl.psi.building.EconomyBuilding;
import pl.psi.building.EconomyBuildingStatistic;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EconomyBuildingFactory<T extends EconomyBuilding> {

    T createBuilding(String aBuildingName);

    Optional<EconomyBuildingStatistic> getStatisticByName(String name);

    Set<EconomyBuildingStatistic> getAllAvailableBuildingsToBuild();
}
