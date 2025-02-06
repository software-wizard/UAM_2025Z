package pl.psi.spells;

import lombok.Getter;

@Getter
public enum SpellMasterityLevel {
    BASIC(1),
    ADVANCED(2),
    EXPERT(3);

    final int level;

    SpellMasterityLevel(int aLevel) {
        level = aLevel;
    }
}
