package pl.psi.building.model;

import pl.psi.resource.Resources;

public interface UpgradableBuilding extends EconomyBuilding {

    void upgrade();
    boolean isUpgraded();
    Resources getUpgradeCost();
    int getCreatureTier();
}
