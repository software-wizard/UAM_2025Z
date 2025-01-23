package pl.psi.spells;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.psi.TurnQueue;
import pl.psi.creatures.Creature;
import pl.psi.creatures.CreatureStats;

import java.util.List;


public class SpellBonusTest {

    final int NOT_IMPORTANT = 5;

    @Test
    void weakenAttackBonus() {

        Spell weakenAttack = new Spell.Builder().name(SpellName.WEAKEN_ATTACK).damage(0).manaCost(5).spellBonus(SpellBonusName.WEAKEN_ATTACK).spellBonusRoundsDuration(2).build();

        SpellBook spellBook = new SpellBook(50, List.of(weakenAttack));

        final Creature weakenCreature = new Creature.Builder().statistic(
                        CreatureStats.builder()
                                .maxHp( 30 )
                                .damage( Range.closed( NOT_IMPORTANT, NOT_IMPORTANT ) )
                                .attack( NOT_IMPORTANT )
                                .armor( NOT_IMPORTANT )
                                .build()
                )
                .build();

        final Creature creature = new Creature.Builder().statistic(
                        CreatureStats.builder()
                                .maxHp( 30 )
                                .damage( Range.closed( NOT_IMPORTANT, NOT_IMPORTANT ) )
                                .attack( NOT_IMPORTANT )
                                .armor( NOT_IMPORTANT )
                                .build()
                )
                .build();

        TurnQueue turnQueue = new TurnQueue(List.of( weakenCreature), List.of(creature));

        Assertions.assertEquals(5, weakenCreature.getAttack());
        spellBook.castSpell(weakenAttack, weakenCreature);
        Assertions.assertEquals(0, weakenCreature.getAttack());

        turnQueue.next();
        Assertions.assertEquals(0, weakenCreature.getAttack());

        turnQueue.next();

        turnQueue.next();
        Assertions.assertEquals(5, weakenCreature.getAttack());

    }

    @Test
    void increasedAttackBonus() {

        Spell extraAttack = new Spell.Builder().name(SpellName.STRONGER_ATTACK).damage(0).manaCost(5).spellBonus(SpellBonusName.EXTRA_ATTACK).spellBonusRoundsDuration(2).build();

        SpellBook spellBook = new SpellBook(50, List.of(extraAttack));

        final Creature creatureWithExtraAttack = new Creature.Builder().statistic(
                        CreatureStats.builder()
                                .maxHp( 30 )
                                .damage( Range.closed( NOT_IMPORTANT, NOT_IMPORTANT ) )
                                .attack( NOT_IMPORTANT )
                                .armor( NOT_IMPORTANT )
                                .build()
                )
                .build();

        final Creature creature = new Creature.Builder().statistic(
                        CreatureStats.builder()
                                .maxHp( 30 )
                                .damage( Range.closed( NOT_IMPORTANT, NOT_IMPORTANT ) )
                                .attack( NOT_IMPORTANT )
                                .armor( NOT_IMPORTANT )
                                .build()
                )
                .build();

        Assertions.assertEquals(5, creatureWithExtraAttack.getAttack());

        spellBook.castSpell(extraAttack, creatureWithExtraAttack);
        Assertions.assertEquals(10, creatureWithExtraAttack.getAttack());

        creatureWithExtraAttack.attack(creature);

    }

}
