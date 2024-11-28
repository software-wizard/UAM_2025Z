package pl.psi.building;

import pl.psi.hero.EconomyHero;

public final class EconomyBuildingFactoryStaticProvider {

    public static EconomyBuildingFactory getEconomyBuildingFactory(EconomyHero.Fraction fraction) {
        return switch (fraction) {
            case NECROPOLIS -> new EconomyBuildingNecropolisFactory();
        };
    }
}
