package pl.psi.spells;

import lombok.Getter;

@Getter
public enum SpellName {
    MAGIC_ARROW("Magic Arrow"),
    WEAKEN_ATTACK("Weaken Attack"),
    STRONGER_ATTACK("Stronger Attack"),
    EXTRA_MOVE_RANGE("Extra Move Range"),

    SPLASH_ATTACK("Splash Attack");
    private final String displayName;

    SpellName(String aDisplayName) {
        this.displayName = aDisplayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
