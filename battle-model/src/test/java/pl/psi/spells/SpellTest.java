package pl.psi.spells;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;
import pl.psi.Hero;
import pl.psi.Spell;
import pl.psi.SpellBook;
import pl.psi.SpellName;
import pl.psi.creatures.Creature;
import pl.psi.creatures.CreatureStats;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpellTest {
    final int NOT_IMPORTANT = 100;
    @Test
    void magicArrowCast() {
        Spell magicArrow = new Spell.Builder().name(SpellName.MAGIC_ARROW).damage(10).level(1).manaCost(5).build();
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

}
