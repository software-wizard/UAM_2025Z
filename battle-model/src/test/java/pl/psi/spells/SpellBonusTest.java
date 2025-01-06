package pl.psi.spells;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.psi.Spell;
import pl.psi.SpellBook;
import pl.psi.SpellName;
import pl.psi.TurnQueue;
import pl.psi.creatures.Creature;
import pl.psi.creatures.CreatureStats;
import pl.psi.creatures.SpellBonusStatistic;

import java.util.List;


public class SpellBonusTest {

    final int NOT_IMPORTANT = 6;

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


        spellBook.castSpell(weakenAttack, weakenCreature);

        turnQueue.next();

        weakenCreature.attack(creature);
        Assertions.assertEquals(29, creature.getCurrentHp());

        turnQueue.next();

        weakenCreature.attack(creature);
        Assertions.assertEquals(28, creature.getCurrentHp());

        turnQueue.next();

        weakenCreature.attack(creature);
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

        Assertions.assertEquals(30, creature.getCurrentHp());

        creatureWithExtraAttack.attack(creature);
        Assertions.assertEquals(24, creature.getCurrentHp());

        spellBook.castSpell(extraAttack, creatureWithExtraAttack);
        creatureWithExtraAttack.attack(creature);

        Assertions.assertEquals(13, creature.getCurrentHp());

    }

}
