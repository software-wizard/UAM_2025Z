package pl.psi.building.factory;

import pl.psi.building.CreatureDwellingsBuilding;
import pl.psi.building.EconomyBuilding;

public interface EconomyBuildingFactory {

    EconomyBuilding createBuilding(String name);

    CreatureDwellingsBuilding createDwellings(String name);
}
