package pl.psi.building;

import java.util.List;
import java.util.Map;

class EconomyBuildingNecropolisFactory implements EconomyBuildingFactory {

    @Override
    public Map<EconomyBuildingType, List<EconomyBuilding>> createBuilding() {
        return Map.of();
    }
}
