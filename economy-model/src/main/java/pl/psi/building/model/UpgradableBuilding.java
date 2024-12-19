package pl.psi.building.model;

import pl.psi.resource.Resources;

public interface UpgradableBuilding {

    void upgrade();
    boolean isUpgraded();
    Resources getUpgradeCost();
}
