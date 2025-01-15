package pl.psi.building.town;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.psi.building.model.EconomyBuilding;
import pl.psi.building.model.EconomyBuildingStatistic;
import pl.psi.building.model.UpgradableBuilding;
import pl.psi.building.shop.EconomyBuildingShop;
import pl.psi.building.town.Town;
import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resources;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

class TownTest {

    private String buildingName;
    private EconomyBuildingStatistic buildingStatistic;
    private EconomyHero hero;
    private EconomyBuilding building;
    private Town town;

    @BeforeEach
    void setUp() {
        EconomyBuildingShop buildingShop = Mockito.mock(EconomyBuildingShop.class);
        buildingName = "Cursed_Temple";
        buildingStatistic = new EconomyBuildingStatistic(
                buildingName,
                EconomyBuildingStatistic.Type.DWELLINGS,
                Resources.builder()
                        .resource(Resources.Type.GOLD, 100)
                        .build(),
                List.of()
        );
        Resources resources = Resources.builder()
                .resource(Resources.Type.GOLD, 100)
                .build();
        hero = new EconomyHero(EconomyHero.Fraction.NECROPOLIS, resources);
        building = Mockito.mock(EconomyBuilding.class);
        town = Town.builder()
                .buildings(Map.of())
                .economyBuildingShop(buildingShop)
                .name(buildingName)
                .fraction(EconomyHero.Fraction.NECROPOLIS)
                .build();
        when(buildingShop.buyBuilding(hero, buildingName))
                .thenReturn(building);
        when(building.isBuilt()).thenReturn(true);
        UpgradableBuilding upgradableBuilding = Mockito.mock(UpgradableBuilding.class);
        doAnswer(invocation -> {
            when(upgradableBuilding.isUpgraded()).thenReturn(true);
            return null;
        }).when(upgradableBuilding).upgrade();
        when(upgradableBuilding.getStatistic()).thenReturn(buildingStatistic);
        when(buildingShop.buyBuildingUpgrade(hero, building)).thenReturn(upgradableBuilding);
    }

    @Test
    void should_build_building() {
        // GIVEN && WHEN
        EconomyBuilding builtBuilding = town.buildBuilding(hero, buildingName);

        // THEN
        assertThat(builtBuilding)
                .isEqualTo(building);
        assertThat(town.getBuildings().size())
                .isEqualTo(1);
    }

    @Test
    void should_find_building_in_town() {
        // GIVEN
        town.getBuildings().put(buildingName, building);

        // WHEN
        Optional<EconomyBuilding> buildingByName = town.findBuildingByName(buildingName);

        // THEN
        assertThat(buildingByName.isPresent()).isTrue();
        assertThat(buildingByName.get()).isEqualTo(building);
    }

    @Test
    void should_check_if_building_is_built_in_town() {
        // GIVEN
        town.getBuildings().put(buildingName, building);

        // WHEN
        boolean buildingAlreadyBuilt = town.isBuildingAlreadyBuilt(buildingName);

        // THEN
        assertThat(buildingAlreadyBuilt).isTrue();
    }

    @Test
    void should_upgrade_building() {
        // GIVEN
        town.getBuildings().put(buildingName, building);

        // WHEN
        UpgradableBuilding upgradedBuilding = town.upgradeBuilding(hero, buildingName);

        // THEN
        assertThat(upgradedBuilding.isUpgraded()).isTrue();
        assertThat(upgradedBuilding.getStatistic()).isEqualTo(buildingStatistic);
    }

    @Test
    void should_throw_exception_when_building_already_built() {
        // GIVEN
        town.getBuildings().put(buildingName, building);

        // WHEN && THEN
        assertThatThrownBy(() -> town.buildBuilding(hero, buildingName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void should_throw_exception_when_building_upgrade_not_found() {
        // GIVEN && WHEN && THEN
        assertThatThrownBy(() -> town.upgradeBuilding(hero, "Non_Existent_Building"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void should_return_empty_when_finding_non_existent_building() {
        // GIVEN && WHEN
        Optional<EconomyBuilding> buildingByName = town.findBuildingByName("Non_Existent_Building");

        // THEN
        assertThat(buildingByName.isEmpty()).isTrue();
    }

    @Test
    void should_handle_payment_rollback_on_failure() {
        // GIVEN
        EconomyBuildingShop buildingShop = Mockito.mock(EconomyBuildingShop.class);
        town = Town.builder()
                .buildings(Map.of())
                .economyBuildingShop(buildingShop)
                .name(buildingName)
                .fraction(EconomyHero.Fraction.NECROPOLIS)
                .build();

        // WHEN
        when(buildingShop.buyBuilding(hero, buildingName)).thenThrow(new RuntimeException("Payment failed"));

        // THEN
        assertThatThrownBy(() -> town.buildBuilding(hero, buildingName))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Payment failed");
        assertThat(town.getBuildings().size()).isEqualTo(0);
    }
}