package pl.psi.building;

import lombok.*;
import pl.psi.resource.Resource;

import java.util.Set;

@EqualsAndHashCode
public class EconomyBuilding {


    protected final EconomyBuildingStatistic statistic;
    private BuildingState builtState = BuildingState.TO_BUILD;

    public EconomyBuilding(EconomyBuildingStatistic statistic) {
        this.statistic = statistic;
    }

    public Set<Resource> getBuildingCost() {
        return Set.copyOf(statistic.cost());
    }

    public String getName() {
        return statistic.name();
    }

    public void startBuilding() {
        builtState = BuildingState.BEING_BUILT;
    }

    public void finishBuilding() {
        builtState = BuildingState.BUILT;
    }

    public enum BuildingState {
        BEING_BUILT, BUILT, TO_BUILD
    }
}
