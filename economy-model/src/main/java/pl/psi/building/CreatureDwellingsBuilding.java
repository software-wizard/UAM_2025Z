package pl.psi.building;

import pl.psi.resource.Resource;

import java.util.Set;

public class CreatureDwellingsBuilding extends EconomyBuilding {

    private final Set<String> creatureNames;
    private final Set<Resource> upgradeCost;
    private final Set<Resource> costPerWeek;
    private boolean isUpgraded = false;

    public CreatureDwellingsBuilding(
            EconomyBuildingStatistic statistic,
            Set<String> creatureNames,
            Set<Resource> upgradeCost,
            Set<Resource> costPerWeek
    ) {
        super(statistic);
        this.creatureNames = creatureNames;
        this.upgradeCost = upgradeCost;
        this.costPerWeek = costPerWeek;
    }

    void upgrade() {
        isUpgraded = true;
    }

    public Set<String> getCreatureNames() {
        return Set.copyOf(creatureNames);
    }

    public Set<Resource> getUpgradeCost() {
        return Set.copyOf(upgradeCost);
    }

    public Set<Resource> getCostPerWeek() {
        return Set.copyOf(costPerWeek);
    }

    public boolean isUpgraded() {
        return isUpgraded;
    }
}
