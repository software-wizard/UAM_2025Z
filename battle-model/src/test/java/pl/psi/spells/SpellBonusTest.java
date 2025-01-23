package pl.psi.spells;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.psi.*;
import pl.psi.creatures.Creature;
import pl.psi.creatures.CreatureStats;
import pl.psi.creatures.SpellBonusStatistic;

import java.util.List;


public class SpellBonusTest {

    final int NOT_IMPORTANT = 6;
    final Point SOURCE_NOT_IMPORTANT = new Point(0, 0);
    final Point TARGET_NOT_IMPORTANT = new Point(1, 0);

    @Test
    void weakenAttackBonus() {

        Spell weakenAttack = new Spell.Builder().name(SpellName.WEAKEN_ATTACK).damage(0).manaCost(5).spellBonus(SpellBonusStatistic.WEAKEN_ATTACK).spellBonusRoundsDuration(2).build();

        SpellBook spellBook = new SpellBook(50, List.of(weakenAttack));

        final Creature weakenCreature = new Creature.Builder().statistic(
                        CreatureStats.builder()
                                .maxHp( 30 )
                                .damage( Range.closed( NOT_IMPORTANT, NOT_IMPORTANT ) )
                                .attack( NOT_IMPORTANT )
                                .armor( NOT_IMPORTANT )
                                .moveRange(20)
                                .build()
                )
                .build();

        final Creature creature = new Creature.Builder().statistic(
                        CreatureStats.builder()
                                .maxHp( 30 )
                                .damage( Range.closed( NOT_IMPORTANT, NOT_IMPORTANT ) )
                                .attack( NOT_IMPORTANT )
                                .armor( NOT_IMPORTANT )
                                .moveRange(20)
                                .build()
                )
                .build();

        TurnQueue turnQueue = new TurnQueue(List.of( weakenCreature), List.of(creature));
        List< Creature > c1 = List.of( weakenCreature );
        List< Creature > c2 = List.of( creature );
        Board board = new Board(c1, c2);
        board.move(weakenCreature, new Point(0, 0));
        board.move(creature, new Point(1, 0));

        spellBook.castSpell(weakenAttack, weakenCreature);

        turnQueue.next();

        weakenCreature.attack(creature, board);
        Assertions.assertEquals(29, creature.getCurrentHp());

        turnQueue.next();

        weakenCreature.attack(creature, board);
        Assertions.assertEquals(28, creature.getCurrentHp());

        turnQueue.next();

        weakenCreature.attack(creature, board);
        Assertions.assertEquals(22, creature.getCurrentHp());

    }

    @Test
    void increasedAttackBonus() {

        Spell extraAttack = new Spell.Builder().name(SpellName.STRONGER_ATTACK).damage(0).manaCost(5).spellBonus(SpellBonusStatistic.EXTRA_ATTACK).spellBonusRoundsDuration(2).build();

        SpellBook spellBook = new SpellBook(50, List.of(extraAttack));

        final Creature creatureWithExtraAttack = new Creature.Builder().statistic(
                        CreatureStats.builder()
                                .maxHp( 30 )
                                .damage( Range.closed( NOT_IMPORTANT, NOT_IMPORTANT ) )
                                .attack( NOT_IMPORTANT )
                                .armor( NOT_IMPORTANT )
                                .moveRange(20)
                                .build()
                )
                .build();

        final Creature creature = new Creature.Builder().statistic(
                        CreatureStats.builder()
                                .maxHp( 30 )
                                .damage( Range.closed( NOT_IMPORTANT, NOT_IMPORTANT ) )
                                .attack( NOT_IMPORTANT )
                                .armor( NOT_IMPORTANT )
                                .moveRange(20)
                                .build()
                )
                .build();

        Assertions.assertEquals(30, creature.getCurrentHp());

        List< Creature > c1 = List.of( creatureWithExtraAttack );
        List< Creature > c2 = List.of( creature );
        Board board = new Board(c1, c2);
        board.move(creatureWithExtraAttack, new Point(0, 0));
        board.move(creature, new Point(1, 0));

        creatureWithExtraAttack.attack(creature, board);
        Assertions.assertEquals(24, creature.getCurrentHp());

        spellBook.castSpell(extraAttack, creatureWithExtraAttack);
        creatureWithExtraAttack.attack(creature, board);

        Assertions.assertEquals(13, creature.getCurrentHp());

    }

}
