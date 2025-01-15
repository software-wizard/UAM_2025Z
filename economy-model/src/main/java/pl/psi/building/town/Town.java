package pl.psi.building.town;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import pl.psi.building.model.EconomyBuilding;
import pl.psi.building.model.UpgradableBuilding;
import pl.psi.building.payment.Payment;
import pl.psi.building.shop.EconomyBuildingShop;
import pl.psi.hero.EconomyHero;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Builder(builderClassName = "Builder")
public class Town {

    private final String name;
    @Getter
    private final EconomyHero.Fraction fraction;
    private final EconomyBuildingShop economyBuildingShop;
    @Getter(AccessLevel.PACKAGE)
    private final Map<String, EconomyBuilding> buildings;

    private Town(String aName,
                EconomyHero.Fraction aFraction,
                EconomyBuildingShop aEconomyBuildingShop,
                Map<String, EconomyBuilding> aBuildings
    ) {
        this.name = aName;
        this.fraction = aFraction;
        this.economyBuildingShop = aEconomyBuildingShop;
        this.buildings = new HashMap<>(aBuildings);
    }

    public EconomyBuilding buildBuilding(EconomyHero aHero, String aBuildingName) {
        Preconditions.checkArgument(!buildings.containsKey(aBuildingName));
        Payment<EconomyBuilding> payment = getPayment(aHero, aBuildingName);
        try {
            return payment.execute();
        } catch (Exception e) {
            payment.rollback();
            throw new RuntimeException("Building construction failed: " + e.getMessage(), e);
        }
    }

    public Optional<EconomyBuilding> findBuildingByName(String name) {
        return Optional.ofNullable(buildings.get(name));
    }

    public boolean isBuildingAlreadyBuilt(String name) {
        return findBuildingByName(name)
                .map(EconomyBuilding::isBuilt)
                .orElse(false);
    }

    public UpgradableBuilding upgradeBuilding(EconomyHero aBuyer, String aBuildingToUpgrade) {
        EconomyBuilding economyBuilding = findBuildingByName(aBuildingToUpgrade).orElseThrow();
        UpgradableBuilding upgradableBuilding = economyBuildingShop.buyBuildingUpgrade(aBuyer, economyBuilding);
        upgradableBuilding.upgrade();
        return upgradableBuilding;
    }

    private Payment<EconomyBuilding> getPayment(EconomyHero aHero, String aBuildingName) {
        return new Payment<>(
                () -> {
                    EconomyBuilding boughtBuilding = economyBuildingShop.buyBuilding(aHero, aBuildingName);
                    buildings.put(aBuildingName, boughtBuilding);
                    boughtBuilding.startBuilding();
                    boughtBuilding.finishBuilding();
                    return boughtBuilding;
                },
                building -> {
                    economyBuildingShop.rollback(aHero, building);
                    buildings.remove(aBuildingName);
                }
        );
    }

    public static class Builder {

        public Town build() {
            if (economyBuildingShop == null) {
                throw new IllegalArgumentException("Economy building shop not set");
            }
            return new Town(name, fraction, economyBuildingShop, buildings);
        }

        public Builder buildings(Map<String, EconomyBuilding> buildings) {
            this.buildings = new HashMap<>(buildings);
            return Builder.this;
        }
    }
}
