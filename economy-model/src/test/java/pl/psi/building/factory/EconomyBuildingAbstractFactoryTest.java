package pl.psi.building.factory;

import org.junit.jupiter.api.Test;
import pl.psi.building.model.EconomyBuildingStatistic;
import pl.psi.hero.EconomyHero;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EconomyBuildingAbstractFactoryTest {

    @Test
    void should_return_economy_building_factory_for_necropolis_building() {
        // GIVEN
        EconomyBuildingAbstractFactory factory = new EconomyBuildingAbstractFactory();
        EconomyHero.Fraction fraction = EconomyHero.Fraction.NECROPOLIS;
        EconomyBuildingStatistic.Type buildingType = EconomyBuildingStatistic.Type.BUILDING;

        // WHEN
        EconomyBuildingFactory result = factory.getEconomyBuildingFactory(fraction, buildingType);

        // THEN
        assertNotNull(result);
        assertInstanceOf(EconomyBuildingNecropolisFactory.class, result);
    }

    @Test
    void should_return_economy_dwellings_factory_for_necropolis() {
        // GIVEN
        EconomyBuildingAbstractFactory factory = new EconomyBuildingAbstractFactory();
        EconomyHero.Fraction fraction = EconomyHero.Fraction.NECROPOLIS;
        EconomyBuildingStatistic.Type buildingType = EconomyBuildingStatistic.Type.DWELLINGS;

        // WHEN
        EconomyBuildingFactory result = factory.getEconomyBuildingFactory(fraction, buildingType);

        // THEN
        assertNotNull(result);
        assertInstanceOf(CreatureDwellingsNecropolisFactory.class, result);
    }

    @Test
    void should_return_all_factories_for_necropolis() {
        // GIVEN
        EconomyBuildingAbstractFactory factory = new EconomyBuildingAbstractFactory();
        EconomyHero.Fraction fraction = EconomyHero.Fraction.NECROPOLIS;

        // WHEN
        Set<EconomyBuildingFactory> result = factory.getAllFactories(fraction);

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(f -> f instanceof EconomyBuildingNecropolisFactory));
        assertTrue(result.stream().anyMatch(f -> f instanceof CreatureDwellingsNecropolisFactory));
    }

    @Test
    void should_throw_exception_for_unsupported_fraction() {
        // GIVEN
        EconomyBuildingAbstractFactory factory = new EconomyBuildingAbstractFactory();

        // WHEN && THEN
        assertThrows(
                NullPointerException.class,
                () -> factory.getEconomyBuildingFactory(null, EconomyBuildingStatistic.Type.BUILDING)
        );
    }
}
