package pl.psi;

import lombok.Getter;

public enum SpellName {
    MAGIC_ARROW("Magic Arrow"),
    BOOST_DAMAGE("Boost Damage"),
    WEAKEN_ATTACK("Weaken Attack"),
    STRONGER_ATTACK("Stronger Attack"),
    EXTRA_MOVE_RANGE("Extra Move Range"),

    SPLASH_ATTACK("Splash Attack");
    @Getter
    private final String displayName;

    SpellName(String aDisplayName) {
        this.displayName = aDisplayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
