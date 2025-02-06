package pl.psi.spells;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pl.psi.creatures.Creature;
import pl.psi.creatures.CreatureStats;

import java.util.ArrayList;

public class SpellBookTest {

    final int NOT_IMPORTANT = 100;


    @Test
    void cannotCastWithoutEnoughMana() {
        SpellBook spellBook = new SpellBook(3, new ArrayList<>());
        Spell magicArrow = new Spell.Builder().name(SpellName.MAGIC_ARROW).damage(10).manaCost(5).build();
        spellBook.addSpell(magicArrow);

        final Creature targetCreature = new Creature.Builder()
                .statistic(
                        CreatureStats.builder()
                                .maxHp( 30 )
                                .damage( Range.closed( NOT_IMPORTANT, NOT_IMPORTANT ) )
                                .attack( NOT_IMPORTANT )
                                .armor( NOT_IMPORTANT )
                                .name("Target creature")
                                .build()
                )
                .build();

        assertThrows(IllegalStateException.class, () -> spellBook.castSpell(magicArrow, targetCreature));
    }
}
