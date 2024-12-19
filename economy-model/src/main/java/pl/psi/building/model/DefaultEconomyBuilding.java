package pl.psi.building.model;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
public class DefaultEconomyBuilding extends AbstractBuilding {

    public DefaultEconomyBuilding(EconomyBuildingStatistic statistic) {
        super(statistic);
    }
}
