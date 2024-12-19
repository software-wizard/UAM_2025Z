package pl.psi.building.model;

import lombok.Getter;
import pl.psi.resource.Resources;

import java.util.Set;

public class CreatureDwellingsBuilding extends AbstractBuilding implements UpgradableBuilding {

    private final Set<String> creatureNames;
    @Getter
    private final Resources upgradeCost;
    @Getter
    private final Resources costPerWeek;
    private boolean isUpgraded = false;

    public CreatureDwellingsBuilding(
            EconomyBuildingStatistic statistic,
            Set<String> creatureNames,
            Resources upgradeCost,
            Resources costPerWeek
    ) {
        super(statistic);
        this.creatureNames = creatureNames;
        this.upgradeCost = upgradeCost;
        this.costPerWeek = costPerWeek;
    }

    @Override
    public void upgrade() {
        isUpgraded = true;
    }

    @Override
    public boolean isUpgraded() {
        return isUpgraded;
    }

    public Set<String> getCreatureNames() {
        return Set.copyOf(creatureNames);
    }
}
