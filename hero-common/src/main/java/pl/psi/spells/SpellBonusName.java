package pl.psi.spells;

public enum SpellBonusName {
    WEAKEN_ATTACK ("WEAKEN ATTACK"),
    EXTRA_ATTACK ("EXTRA ATTACK"),
    EXTRA_MOVE_RANGE ("EXTRA MOVE RANGE"),
    NONE ("NONE");

    private final String name;


    SpellBonusName(String aName){
        name = String.format("Spell: %s bonus", aName);;
    }

    public String toString() {
        return this.name;
    }
}
