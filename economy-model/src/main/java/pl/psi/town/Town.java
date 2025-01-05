package pl.psi.town;

import lombok.Builder;
import lombok.Getter;
import pl.psi.building.model.DefaultEconomyBuilding;
import pl.psi.building.model.EconomyBuilding;
import pl.psi.building.model.EconomyBuildingStatistic;
import pl.psi.building.model.UpgradableBuilding;
import pl.psi.hero.EconomyHero;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Builder(builderClassName = "Builder")
@Getter
public class Town {

    private final String name;
    private final EconomyHero.Fraction fraction;
    private final Set<EconomyBuilding> buildings;

    public void buildBuilding(EconomyBuilding aBuilding) {
        if (buildings.contains(aBuilding)) {
            throw new BuildingAlreadyBuiltException(String.format("Building %s is already built", aBuilding.getStatistic().name()));
        }
        buildings.add(aBuilding);
        aBuilding.startBuilding();
        aBuilding.finishBuilding();
    }

    public Optional<EconomyBuilding> findBuildingByName(String name) {
        return buildings.stream()
                .filter(building -> building.getStatistic().name().equals(name))
                .findFirst();
    }

    public boolean isBuildingAlreadyBuilt(Town aTown, EconomyBuildingStatistic economyBuildingStatistic) {
        return aTown.getBuildings()
                .stream()
                .anyMatch(building -> building.getStatistic().equals(economyBuildingStatistic));
    }

    public void upgradeBuilding(UpgradableBuilding buildingToUpgrade) {
        buildingToUpgrade.upgrade();
    }

    public static class Builder {

        public Builder buildings(Set<EconomyBuilding> buildings) {
            this.buildings = new HashSet<>(buildings);
            return Builder.this;
        }
    }

    public static class BuildingAlreadyBuiltException extends RuntimeException {

        public BuildingAlreadyBuiltException(String message) {
            super(message);
        }
    }
}
