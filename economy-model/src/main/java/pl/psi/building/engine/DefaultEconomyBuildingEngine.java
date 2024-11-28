package pl.psi.building.engine;

import pl.psi.building.EconomyBuilding;

import java.util.HashSet;
import java.util.Set;

class DefaultEconomyBuildingEngine implements EconomyBuildingEngine {

    private final Set<EconomyBuilding> buildingsToBuild;

    DefaultEconomyBuildingEngine(Set<EconomyBuilding> buildingsToBuild) {
        this.buildingsToBuild = new HashSet<>(buildingsToBuild);
    }

    @Override
    public void buildBuilding(EconomyBuilding aBuildingToBuild) {
        aBuildingToBuild.startBuilding();
        buildingsToBuild.add(aBuildingToBuild);
    }

    @Override
    public void nextRound() {
        buildingsToBuild.forEach(EconomyBuilding::finishBuilding);
    }
}
