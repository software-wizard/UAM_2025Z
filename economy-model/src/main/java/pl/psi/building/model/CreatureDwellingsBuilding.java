package pl.psi.building.model;

import lombok.Getter;
import pl.psi.resource.Resources;

public class CreatureDwellingsBuilding extends AbstractBuilding implements UpgradableBuilding {

    @Getter
    private final Resources upgradeCost;
    @Getter
    private final Resources costPerWeek;
    @Getter private final int creatureTier;
    private boolean isUpgraded = false;

    public CreatureDwellingsBuilding(
            EconomyBuildingStatistic statistic,
            int creatureTier,
            Resources upgradeCost,
            Resources costPerWeek
    ) {
        super(statistic);
        this.upgradeCost = upgradeCost;
        this.costPerWeek = costPerWeek;
        this.creatureTier = creatureTier;
    }

    @Override
    public void upgrade() {
        isUpgraded = true;
    }

    @Override
    public boolean isUpgraded() {
        return isUpgraded;
    }
}
