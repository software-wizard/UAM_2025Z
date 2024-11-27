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
    private final List<Spell> spells;

    public Hero( final List< Creature > aCreatures, final List<Spell> aSpells )
    {
        creatures = aCreatures;
        spells = aSpells;
    }

    public Hero(final List<Creature> aCreatures) {
        this(aCreatures, new ArrayList<>());
        Spell defaultSpell = new Spell("Magic Arrow", 5, 1, 5);
        spells.add(defaultSpell);
        System.out.println("Default spell added: " + spells.size());
    }
}
