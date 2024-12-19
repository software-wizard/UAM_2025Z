package pl.psi.building.factory;

import pl.psi.building.model.EconomyBuildingStatistic;
import pl.psi.hero.EconomyHero;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public class EconomyBuildingAbstractFactory {

    public EconomyBuildingFactory getEconomyBuildingFactory(
            EconomyHero.Fraction fraction,
            EconomyBuildingStatistic.EconomyBuildingType buildingType
    ) {
        return switch (buildingType) {
            case BUILDING -> getEconomyBuildingFactory(fraction);
            case DWELLINGS -> getEconomyDwellingsFactory(fraction);
        };
    }

    public Set<EconomyBuildingFactory> getAllFactories(EconomyHero.Fraction fraction) {
        return EnumSet.allOf(EconomyBuildingStatistic.EconomyBuildingType.class)
                .stream()
                .map(type -> getEconomyBuildingFactory(fraction, type))
                .collect(Collectors.toSet());
    }

    private EconomyBuildingFactory getEconomyBuildingFactory(EconomyHero.Fraction fraction) {
        return switch (fraction) {
            case NECROPOLIS -> new EconomyBuildingNecropolisFactory();
        };
    }

    private EconomyBuildingFactory getEconomyDwellingsFactory(EconomyHero.Fraction fraction) {
        return switch (fraction) {
            case NECROPOLIS -> new CreatureDwellingsNecropolisFactory();
        };
    }
}
