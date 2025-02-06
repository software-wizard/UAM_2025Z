package pl.psi.building.model;

public interface EconomyBuilding {

    void startBuilding();
    void finishBuilding();
    EconomyBuildingStatistic getStatistic();
    boolean isBuilt();

    enum BuildingState {
        BEING_BUILT, BUILT, TO_BUILD
    }
}
