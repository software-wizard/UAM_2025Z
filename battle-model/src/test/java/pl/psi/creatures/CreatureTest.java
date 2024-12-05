package pl.psi.creatures;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import pl.psi.TurnQueue;

import com.google.common.collect.Range;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
@Disabled
public class CreatureTest
{
    private static final int NOT_IMPORTANT = 100;
    private static final Range< Integer > NOT_IMPORTANT_DMG = Range.closed( 0, 0 );
    @Test
    void creatureShouldAttackProperly()
    {
        // given
        final Creature angel = new Creature.Builder().statistic( CreatureStats.builder()
            .maxHp( NOT_IMPORTANT )
            .damage( Range.closed( 10, 10 ) )
            .attack( 50 )
            .armor( NOT_IMPORTANT )
            .build() )
            .build();
        final Creature dragon = new Creature.Builder().statistic( CreatureStats.builder()
            .maxHp( 100 )
            .damage( NOT_IMPORTANT_DMG )
            .attack( NOT_IMPORTANT )
            .armor( 10 )
            .build() )
            .build();
        // when
        angel.attack( dragon );
        // then
        assertThat( dragon.getCurrentHp() ).isEqualTo( 70 );
    }

    @Test
    void creatureShouldNotHealCreatureEvenHasLowerAttackThanDefenderArmor()
    {
        final Creature angel = new Creature.Builder().statistic( CreatureStats.builder()
            .maxHp( NOT_IMPORTANT )
            .damage( NOT_IMPORTANT_DMG )
            .attack( 1 )
            .armor( NOT_IMPORTANT )
            .build() )
            .build();
        final Creature dragon = new Creature.Builder().statistic( CreatureStats.builder()
            .maxHp( 100 )
            .damage( NOT_IMPORTANT_DMG )
            .attack( NOT_IMPORTANT )
            .armor( 10 )
            .build() )
            .build();
        // when
        angel.attack( dragon );
        // then
        assertThat( dragon.getCurrentHp() ).isEqualTo( 100 );
    }

    @Test
    void defenderShouldCounterAttack()
    {
        final Creature attacker = new Creature.Builder().statistic( CreatureStats.builder()
            .maxHp( 100 )
            .damage( NOT_IMPORTANT_DMG )
            .attack( NOT_IMPORTANT )
            .armor( 10 )
            .build() )
            .build();
        final Creature defender = new Creature.Builder().statistic( CreatureStats.builder()
            .maxHp( NOT_IMPORTANT )
            .damage( Range.closed( 10, 10 ) )
            .attack( 10 )
            .build() )
            .build();
        // when
        attacker.attack( defender );
        // then
        assertThat( attacker.getCurrentHp() ).isEqualTo( 90 );
    }

    @Test
    void defenderShouldNotCounterAttackWhenIsDie()
    {
        final Creature attacker = new Creature.Builder().statistic( CreatureStats.builder()
            .maxHp( 100 )
            .damage( NOT_IMPORTANT_DMG )
            .attack( 1000 )
            .armor( 10 )
            .build() )
            .build();
        final Creature defender = new Creature.Builder().statistic( CreatureStats.builder()
            .maxHp( NOT_IMPORTANT )
            .damage( NOT_IMPORTANT_DMG )
            .attack( 20 )
            .armor( 5 )
            .build() )
            .build();
        // when
        attacker.attack( defender );
        // then
        assertThat( attacker.getCurrentHp() ).isEqualTo( 100 );
    }

    @Test
    void defenderShouldCounterAttackOnlyOncePerTurn()
    {
        final Creature attacker = new Creature.Builder().statistic( CreatureStats.builder()
            .maxHp( 100 )
            .damage( NOT_IMPORTANT_DMG )
            .attack( NOT_IMPORTANT )
            .armor( 10 )
            .build() )
            .build();

        final Creature defender = new Creature.Builder().statistic( CreatureStats.builder()
            .maxHp( NOT_IMPORTANT )
            .damage( Range.closed( 10, 10 ) )
            .attack( 10 )
            .build() )
            .build();

        // when
        attacker.attack( defender );
        attacker.attack( defender );
        // then
        assertThat( attacker.getCurrentHp() ).isEqualTo( 90 );
    }

    @Test
    void counterAttackCounterShouldResetAfterEndOfTurn()
    {
        final Creature attacker = new Creature.Builder().statistic( CreatureStats.builder()
            .maxHp( 100 )
            .damage( NOT_IMPORTANT_DMG )
            .build() )
            .build();

        final Creature defender = new Creature.Builder().statistic( CreatureStats.builder()
            .maxHp( 100 )
            .damage( Range.closed( 10, 10 ) )
            .build() )
            .build();

        final TurnQueue turnQueue = new TurnQueue( List.of( attacker ), List.of( defender ) );

        attacker.attack( defender );
        attacker.attack( defender );
        assertThat( attacker.getCurrentHp() ).isEqualTo( 90 );
        turnQueue.next();
        turnQueue.next();
        attacker.attack( defender );
        assertThat( attacker.getCurrentHp() ).isEqualTo( 80 );
        // end of turn
    }

    @Test
    void creatureShouldResurrect()
    {
        Creature VampireLord = new NecropolisFactory().create(true, 4, 10);

        final Creature dragon = new Creature.Builder().statistic( CreatureStats.builder()
                        .maxHp( NOT_IMPORTANT )
                        .damage( Range.closed( 10, 10 ) )
                        .attack( 30 )
                        .armor( 10 )
                        .build() )
                .build();

        int initialAmount = VampireLord.getAmount();
        VampireLord.setCurrentHp(20);
        int initialHp = VampireLord.getCurrentHp();

        VampireLord.attack(dragon);


        assertThat(VampireLord.getAmount()).isEqualTo(initialAmount);
        assertThat(VampireLord.getCurrentHp()).isGreaterThan(initialHp);

    }

    // zapytac
    @Test
    void creatureShouldNotResurrectIfAttacksUndead()
    {
        Creature VampireLord = new ResurrectAfterAttackCreature(
                new NecropolisFactory().create(true, 4, 1));
        Creature Zombie = new NecropolisFactory().create(true, 2, 30);

        int initialAmount = VampireLord.getAmount();

        VampireLord.attack(Zombie);

        assertThat(VampireLord.getAmount()).isLessThan( initialAmount );

    }

    @Test
    void creatureShouldHealAfterEndOfTurn()
    {
        final Creature attacker = new Creature.Builder().statistic( CreatureStats.builder()
            .maxHp( 100 )
            .damage( Range.closed( 10, 10 ) )
            .build() )
            .build();

        final Creature selfHealAfterEndOfTurnCreature = new SelfHealAfterTurnCreature( new Creature.Builder()
            .statistic( CreatureStats.builder()
                    .maxHp( NOT_IMPORTANT )
                    .damage( Range.closed( 10, 10 ) )
                    .attack( 50 )
                    .armor( NOT_IMPORTANT )
                    .build() )
                .build());

        final TurnQueue turnQueue =
            new TurnQueue( List.of( attacker ), List.of( selfHealAfterEndOfTurnCreature ) );

        attacker.attack( selfHealAfterEndOfTurnCreature );
        assertThat( selfHealAfterEndOfTurnCreature.getCurrentHp() ).isEqualTo( 90 );
        turnQueue.next();
        turnQueue.next();
        assertThat( selfHealAfterEndOfTurnCreature.getCurrentHp() ).isEqualTo( 100 );
    }
}
