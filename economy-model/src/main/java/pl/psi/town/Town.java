package pl.psi.town;

import lombok.Builder;
import lombok.Getter;
import pl.psi.building.EconomyBuilding;
import pl.psi.hero.EconomyHero;

import java.util.HashSet;
import java.util.Set;

@Builder(builderClassName = "Builder")
@Getter
public class Town {

    private final String name;
    private final EconomyHero.Fraction fraction;
    private final Set<EconomyBuilding> buildings;

    public void buildBuilding(EconomyBuilding aBuildingToBuild) {
        buildings.add(aBuildingToBuild);
    }

    public static class Builder {

        public Builder buildings(Set<EconomyBuilding> buildings) {
            this.buildings = new HashSet<>(buildings);
            return Builder.this;
        }
    }
}
