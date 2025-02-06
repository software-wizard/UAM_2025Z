package pl.psi.building.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import pl.psi.resource.Resources;

@EqualsAndHashCode
abstract class AbstractBuilding implements EconomyBuilding {

    @Getter
    private final EconomyBuildingStatistic statistic;
    private EconomyBuilding.BuildingState builtState = EconomyBuilding.BuildingState.TO_BUILD;

    protected AbstractBuilding(EconomyBuildingStatistic statistic) {
        this.statistic = statistic;
    }

    @Override
    public void startBuilding() {
        builtState = EconomyBuilding.BuildingState.BEING_BUILT;
    }

    @Override
    public void finishBuilding() {
        builtState = EconomyBuilding.BuildingState.BUILT;
    }

    @Override
    public boolean isBuilt() {
        return builtState == EconomyBuilding.BuildingState.BUILT;
    }
}
