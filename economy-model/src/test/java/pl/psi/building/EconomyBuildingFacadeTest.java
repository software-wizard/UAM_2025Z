package pl.psi.building;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.psi.building.factory.EconomyBuildingAbstractFactory;
import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resources;
import pl.psi.building.town.Town;

import static org.mockito.Mockito.verify;
import static pl.psi.resource.Resources.Type.GOLD;

class EconomyBuildingFacadeTest {

    private EconomyBuildingFacade economyBuildingFacade;

    @BeforeEach
    void setUp() {
        EconomyBuildingAbstractFactory abstractFactory = Mockito.mock(EconomyBuildingAbstractFactory.class);
        this.economyBuildingFacade = new EconomyBuildingFacade(abstractFactory);
    }

    @Test
    void should_build_building_from_name() {
        // GIVEN
        var buildingName = "test";
        var buyer = new EconomyHero(
                EconomyHero.Fraction.NECROPOLIS,
                Resources.builder()
                        .resource(GOLD, 1000)
                        .build()
        );
        var town = Mockito.mock(Town.class);

        // WHEN
        economyBuildingFacade.buildBuilding(buyer, town, buildingName);

        // THEN
        verify(town).buildBuilding(buyer, buildingName);
    }
}