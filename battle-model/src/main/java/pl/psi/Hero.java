package pl.psi;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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

    public void removeCreature(Creature creature) {
        creatures.remove(creature);
        System.out.println(creature.getName() + " has been deleted");
    }
}
