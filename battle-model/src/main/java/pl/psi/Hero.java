package pl.psi;

import java.util.ArrayList;
import java.util.List;

import pl.psi.creatures.Creature;

import lombok.Getter;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class Hero
{
    @Getter
    private final List< Creature > creatures;

    @Getter
    private SpellBook spellBook;

    public Hero( final List< Creature > aCreatures, int aInitMana, final List<Spell> aInitSpells )
    {
        creatures = aCreatures;
        this.spellBook = new SpellBook(aInitMana, aInitSpells);
    }

    public Hero(final List<Creature> aCreatures) {
        this(aCreatures, 0, new ArrayList<>());
    }
}
