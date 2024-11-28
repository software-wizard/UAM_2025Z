package pl.psi.building;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.psi.building.engine.EconomyBuildingEngine;
import pl.psi.building.shop.EconomyBuildingShop;
import pl.psi.hero.EconomyHero;
import pl.psi.town.Town;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class EconomyBuildingFacadeTest {

    private EconomyBuildingShop economyBuildingShopMock;
    private EconomyBuildingEngine economyBuildingEngineMock;
    private EconomyBuildingFacade economyBuildingFacade;

    @BeforeEach
    void setUp() {
        this.economyBuildingShopMock = Mockito.mock(EconomyBuildingShop.class);
        this.economyBuildingEngineMock = Mockito.mock(EconomyBuildingEngine.class);
        this.economyBuildingFacade = new EconomyBuildingFacade(
                economyBuildingShopMock, economyBuildingEngineMock
        );
    }

    @Test
    @DisplayName("Should successfully delegate tasks to shop and engine.")
    void should_delegate_building_task_to_engine_and_shop() {
        // GIVEN
        var building = EconomyBuilding.builder()
                .name("")
                .type(EconomyBuildingType.CREATURE_GENERATOR)
                .fraction(EconomyHero.Fraction.NECROPOLIS)
                .prerequisites(List.of())
                .goldCost(100)
                .build();
        var buyer = new EconomyHero(EconomyHero.Fraction.NECROPOLIS, 1000);
        var town = Town.builder()
                .name("")
                .buildings(Set.of())
                .build();

        // WHEN
        economyBuildingFacade.buildBuilding(buyer, town, building);

        // THEN
        verify(economyBuildingShopMock).buy(buyer, town, building);
        verify(economyBuildingEngineMock).buildBuilding(building);
    }

    @Test
    void testBuildBuilding_Fails_WhenBuildingStateIsNotToBuild() {
        // GIVEN
        var building = Mockito.mock(EconomyBuilding.class);
        var buyer = new EconomyHero(EconomyHero.Fraction.NECROPOLIS, 1000);
        var town = Town.builder()
                .name("")
                .buildings(Set.of())
                .build();

        // WHEN
        when(building.getBuiltState()).thenReturn(EconomyBuilding.BuildingState.BUILT);

        // THEN
        assertThatThrownBy(() -> economyBuildingFacade.buildBuilding(buyer, town, building))
                .isInstanceOf(IllegalStateException.class);
        verifyNoInteractions(economyBuildingShopMock, economyBuildingEngineMock);
        assertThat(buyer.getGold()).isEqualTo(1000);
    }

    @Test
    void testBuildBuilding_Fails_WhenBuildingAlreadyBuilt() {
        // GIVEN
        var building = EconomyBuilding.builder()
                .name("")
                .type(EconomyBuildingType.CREATURE_GENERATOR)
                .fraction(EconomyHero.Fraction.NECROPOLIS)
                .prerequisites(List.of())
                .goldCost(100)
                .build();
        var buyer = new EconomyHero(EconomyHero.Fraction.NECROPOLIS, 1000);
        var town = Mockito.mock(Town.class);
        Set<EconomyBuilding> buildings = new HashSet<>();
        buildings.add(building);

        // WHEN
        when(town.getBuildings()).thenReturn(buildings);

        // GIVEN
        assertThatThrownBy(() -> economyBuildingFacade.buildBuilding(buyer, town, building))
                .isInstanceOf(IllegalStateException.class);
        verifyNoInteractions(economyBuildingShopMock, economyBuildingEngineMock);
    }
}