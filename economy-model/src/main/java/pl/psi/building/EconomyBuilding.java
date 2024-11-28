package pl.psi.building;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.psi.hero.EconomyHero;

import java.util.List;

@Builder(builderClassName = "Builder")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EconomyBuilding {

    private final String name;
    private final EconomyBuildingType type;
    private final EconomyHero.Fraction fraction;
    private final int goldCost;
    private final List<EconomyBuilding> prerequisites;
    @lombok.Builder.Default
    private BuildingState builtState = BuildingState.TO_BUILD;

    public static class Builder {

        public Builder prerequisites(List<EconomyBuilding> prerequisites) {
            this.prerequisites = List.copyOf(prerequisites);
            return Builder.this;
        }

        public EconomyBuilding build() throws IllegalArgumentException, NullPointerException {
            Preconditions.checkNotNull(name);
            Preconditions.checkNotNull(fraction);
            Preconditions.checkArgument(goldCost > 0);
            Preconditions.checkNotNull(prerequisites);
            return new EconomyBuilding(name, type, fraction, goldCost, prerequisites);
        }
    }

    public void startBuilding() {
        builtState = BuildingState.BEING_BUILT;
    }

    public void finishBuilding() {
        builtState = BuildingState.BUILT;
    }

    public enum BuildingState {
        BEING_BUILT, BUILT, TO_BUILD
    }
}
