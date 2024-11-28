package pl.psi.building.engine;

import java.util.Set;

public class EconomyBuildingEngineFactory {

    public static EconomyBuildingEngine createEconomyBuildingEngine() {
        return new DefaultEconomyBuildingEngine(Set.of());
    }
}
