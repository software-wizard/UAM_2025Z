package pl.psi.building.factory;

import pl.psi.building.CreatureDwellingsBuilding;
import pl.psi.building.EconomyBuilding;
import pl.psi.hero.EconomyHero;

public final class EconomyBuildingFactoryStaticProvider {

    public static EconomyBuildingFactory<EconomyBuilding> getEconomyBuildingFactory(EconomyHero.Fraction fraction) {
        return switch (fraction) {
            case NECROPOLIS -> new EconomyBuildingNecropolisFactory();
        };
    }

    public static EconomyBuildingFactory<CreatureDwellingsBuilding> getEconomyDwellingsFactory(EconomyHero.Fraction fraction) {
        return switch (fraction) {
            case NECROPOLIS -> new CreatureDwellingsNecropolisFactory();
        };
    }
}
