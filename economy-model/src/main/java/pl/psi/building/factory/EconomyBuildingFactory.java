package pl.psi.building.factory;

import pl.psi.building.model.EconomyBuilding;
import pl.psi.building.model.EconomyBuildingStatistic;

import java.util.Optional;
import java.util.Set;

public interface EconomyBuildingFactory {

    EconomyBuilding createBuilding(String aBuildingName);

    Optional<EconomyBuildingStatistic> getStatisticByName(String name);

    Set<EconomyBuildingStatistic> getAllAvailableBuildingsToBuild();
}
