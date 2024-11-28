package pl.psi.building;

import java.util.List;
import java.util.Map;

public interface EconomyBuildingFactory {

    Map<EconomyBuildingType, List<EconomyBuilding>> createBuilding();
}
