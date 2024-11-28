package pl.psi.building.engine;

import pl.psi.building.EconomyBuilding;

public interface EconomyBuildingEngine {

    void buildBuilding(EconomyBuilding aBuildingToBuild);
    void nextRound();
}
