package pl.psi;

import lombok.Getter;
import pl.psi.creatures.Creature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpellBook {
    @Getter
    private boolean isCastingSpell = false;

    @Getter
    private Spell selectedSpell = null;

    @Getter
    private int mana = 0;

    @Getter
    private List<Spell> spells;

    public SpellBook(int initialMana, List<Spell> initialSpells) {
        this.spells = initialSpells;
        this.mana = initialMana;
    }

    public boolean canCastSpell(Spell spell) {
        return mana >= spell.getManaCost();
    }

    public void castSpell(Spell spell, Creature target) {
        if (!canCastSpell(spell)) {
            throw new IllegalStateException("missing mana");
        }
        spell.castSpell(target);
        reduceMana(spell.getManaCost());
    }

    private void reduceMana(int amount) {
        mana -= amount;
    }

    public void addSpell(Spell spell) {
        spells.add(spell);
    }
}
