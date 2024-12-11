package pl.psi.spells;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pl.psi.Spell;
import pl.psi.SpellBook;
import pl.psi.creatures.Creature;
import pl.psi.creatures.CreatureStats;

import java.util.ArrayList;
import java.util.List;

public class SpellBookTest {

    final int NOT_IMPORTANT = 100;

    @Test
    void magicArrowCast() {
        Spell magicArrow = new Spell.Builder().name("Magic Arrow").damage(10).level(1).manaCost(5).build();
        SpellBook spellBook = new SpellBook(50, List.of(magicArrow));

        final Creature targetCreature = new Creature.Builder()
                .statistic(
                        CreatureStats.builder()
                    .maxHp( 30 )
                    .damage( Range.closed( NOT_IMPORTANT, NOT_IMPORTANT ) )
                    .attack( NOT_IMPORTANT )
                    .armor( NOT_IMPORTANT )
                    .build()
                )
        .build();

        spellBook.castSpell(magicArrow, targetCreature);

        assertEquals(45, spellBook.getMana()); // 50-5=45
        assertEquals(20, targetCreature.getCurrentHp()); // 30-10
    }

    @Test
    void cannotCastWithoutEnoughMana() {
        SpellBook spellBook = new SpellBook(3, new ArrayList<>());
        Spell magicArrow = new Spell.Builder().name("Magic Arrow").damage(10).level(1).manaCost(5).build();
        spellBook.addSpell(magicArrow);

        final Creature targetCreature = new Creature.Builder()
                .statistic(
                        CreatureStats.builder()
                                .maxHp( 30 )
                                .damage( Range.closed( NOT_IMPORTANT, NOT_IMPORTANT ) )
                                .attack( NOT_IMPORTANT )
                                .armor( NOT_IMPORTANT )
                                .build()
                )
                .build();

        assertThrows(IllegalStateException.class, () -> spellBook.castSpell(magicArrow, targetCreature));
    }
}
