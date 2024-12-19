package pl.psi.building.model;

import pl.psi.resource.Resources;

public interface EconomyBuilding {

    Resources getBuildingCost();
    void startBuilding();
    void finishBuilding();
    EconomyBuildingStatistic getStatistic();

    enum BuildingState {
        BEING_BUILT, BUILT, TO_BUILD
    }
}
