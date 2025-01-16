package pl.psi.creatures;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import pl.psi.*;

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
                        .moveRange(20)
            .build() )
            .build();
        final Creature dragon = new Creature.Builder().statistic( CreatureStats.builder()
            .maxHp( 100 )
            .damage( NOT_IMPORTANT_DMG )
            .attack( NOT_IMPORTANT )
            .armor( 10 )
                        .moveRange(20)
            .build() )
            .build();

        List< Creature > c1 = List.of( angel );
        List< Creature > c2 = List.of( dragon );
        Board board = new Board(c1, c2);
        board.move(angel, new Point(0, 0));
        board.move(dragon, new Point(1, 0));
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
                        .moveRange(20)
            .build() )
            .build();
        final Creature dragon = new Creature.Builder().statistic( CreatureStats.builder()
            .maxHp( 100 )
            .damage( NOT_IMPORTANT_DMG )
            .attack( NOT_IMPORTANT )
            .armor( 10 )
                        .moveRange(20)
            .build() )
            .build();

        List< Creature > c1 = List.of( angel );
        List< Creature > c2 = List.of( dragon );
        Board board = new Board(c1, c2);
        board.move(angel, new Point(0, 0));
        board.move(dragon, new Point(1, 0));
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
                        .moveRange(20)
            .build() )
            .build();
        final Creature defender = new Creature.Builder().statistic( CreatureStats.builder()
            .maxHp( NOT_IMPORTANT )
            .damage( Range.closed( 10, 10 ) )
            .attack( 10 )
                        .moveRange(20)
            .build() )
            .build();

        List< Creature > c1 = List.of( attacker );
        List< Creature > c2 = List.of( defender );
        Board board = new Board(c1, c2);
        board.move(attacker, new Point(0, 0));
        board.move(defender, new Point(1, 0));
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
                .moveRange(20)
            .build() )
            .build();
        final Creature defender = new Creature.Builder().statistic( CreatureStats.builder()
            .maxHp( NOT_IMPORTANT )
            .damage( NOT_IMPORTANT_DMG )
            .attack( 20 )
            .armor( 5 )
                .moveRange(20)
            .build() )
            .build();

        List< Creature > c1 = List.of( attacker );
        List< Creature > c2 = List.of( defender );
        Board board = new Board(c1, c2);
        board.move(attacker, new Point(0, 0));
        board.move(defender, new Point(1, 0));
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
                .moveRange(20)
            .build() )
            .build();

        final Creature defender = new Creature.Builder().statistic( CreatureStats.builder()
            .maxHp( NOT_IMPORTANT )
            .damage( Range.closed( 10, 10 ) )
            .attack( 10 )
                .armor( 10 )
            .build() )
            .build();

        List< Creature > c1 = List.of( attacker );
        List< Creature > c2 = List.of( defender );
        Board board = new Board(c1, c2);
        board.move(attacker, new Point(0, 0));
        board.move(defender, new Point(1, 0));

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
                        .moveRange(20)
            .build() )
            .build();

        final Creature defender = new Creature.Builder().statistic( CreatureStats.builder()
            .maxHp( 100 )
            .damage( Range.closed( 10, 10 ) )
                        .moveRange(20)
            .build() )
            .build();

        final TurnQueue turnQueue = new TurnQueue( List.of( attacker ), List.of( defender ) );
        List< Creature > c1 = List.of( attacker );
        List< Creature > c2 = List.of( defender );
        Board board = new Board(c1, c2);
        board.move(attacker, new Point(0, 0));
        board.move(defender, new Point(1, 0));

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
                        .moveRange(20)
                        .build() )
                .build();

        List< Creature > c1 = List.of( VampireLord );
        List< Creature > c2 = List.of( dragon );
        Board board = new Board(c1, c2);
        board.move(VampireLord, new Point(0, 0));
        board.move(dragon, new Point(1, 0));

        int initialAmount = VampireLord.getAmount();
        VampireLord.setCurrentHp(20);
        //wykorzystac stałą, get mozę popsuć
        int initialHp = VampireLord.getCurrentHp();

        VampireLord.attack(dragon);


        assertThat(VampireLord.getAmount()).isEqualTo(initialAmount);
        assertThat(VampireLord.getCurrentHp()).isGreaterThan(initialHp);

    }

    // zapytac
    @Test
    void creatureShouldNotResurrectIfAttacksUndead()
    {
        Creature VampireLord =
                new NecropolisFactory().create(true, 4, 1);
        Creature Zombie = new NecropolisFactory().create(true, 2, 30);

        List< Creature > c1 = List.of( VampireLord );
        List< Creature > c2 = List.of( Zombie );
        Board board = new Board(c1, c2);
        board.move(VampireLord, new Point(0, 0));
        board.move(Zombie, new Point(1, 0));

        int initialAmount = VampireLord.getAmount();

        VampireLord.attack(Zombie);

        assertThat(VampireLord.getAmount()).isLessThan( initialAmount ); // zly zapis -> konkreten wartosci jakich sie spodziewam
        // mozna podejrzec w debugu wartosc

    }

    @Test
    void creatureShouldHealAfterEndOfTurn()
    {
        final Creature attacker = new Creature.Builder().statistic( CreatureStats.builder()
            .maxHp( 100 )
            .damage( Range.closed( 10, 10 ) )
                        .moveRange(20)
            .build() )
            .build();

        final Creature selfHealAfterEndOfTurnCreature = new SelfHealAfterTurnCreature( new Creature.Builder()
            .statistic( CreatureStats.builder()
                    .maxHp( NOT_IMPORTANT )
                    .damage( Range.closed( 10, 10 ) )
                    .attack( 50 )
                    .armor( NOT_IMPORTANT )
                    .moveRange(20)
                    .build() )
                .build());

        List< Creature > c1 = List.of( attacker );
        List< Creature > c2 = List.of( selfHealAfterEndOfTurnCreature );
        Board board = new Board(c1, c2);
        board.move(attacker, new Point(0, 0));
        board.move(selfHealAfterEndOfTurnCreature, new Point(1, 0));

        final TurnQueue turnQueue =
            new TurnQueue( List.of( attacker ), List.of( selfHealAfterEndOfTurnCreature ) );

        attacker.attack( selfHealAfterEndOfTurnCreature );
        assertThat( selfHealAfterEndOfTurnCreature.getCurrentHp() ).isEqualTo( 90 );
        turnQueue.next();
        turnQueue.next();
        assertThat( selfHealAfterEndOfTurnCreature.getCurrentHp() ).isEqualTo( 100 );
    }

    @Test
    void shouldApplyFullRangeDamage()
    {
        //atakujacy:
        Creature Lich = new Creature.Builder().statistic( CreatureStats.builder()
                    .maxHp( NOT_IMPORTANT )
                    .damage( Range.closed( 10, 10 ) )
                    .attack( 50 )
                    .armor( NOT_IMPORTANT)
                    .isRanged( true )
                    .moveRange(20)
                    .build() )
            .build();

        //defender:
        Creature Skeleton = new Creature.Builder().statistic( CreatureStats.builder()
                        .maxHp( 100 )
                        .damage( NOT_IMPORTANT_DMG )
                        .attack( NOT_IMPORTANT )
                        .armor( 10)
                        .isRanged( false )
                        .moveRange(20)
                        .build() )
                .build();


        List< Creature > c1 = List.of( Lich );
        List< Creature > c2 = List.of( Skeleton );
        Board board = new Board(c1, c2);

        //odleglosc miedzy 1 a 10 zapewni pelny dmg bez zadnych kar:
        board.move(Lich, new Point(0, 0));
        board.move(Skeleton, new Point(9, 0));

        //czy zadamy pelne obrazenia:
        int fullDmg = 30;
        int dealtDmg = Lich.getCalculator().calculateDamage(Lich, Skeleton);
        assertThat(dealtDmg).isEqualTo(fullDmg);
    }

    @Test
    void meleePenaltyForRangedUnitsTest()
    {

        //atakujacy:
        Creature Lich = new Creature.Builder().statistic( CreatureStats.builder()
                        .maxHp( NOT_IMPORTANT )
                        .damage( Range.closed( 10, 10 ) )
                        .attack( 50 )
                        .armor( NOT_IMPORTANT)
                        .isRanged( true )
                        .moveRange(20)
                        .build() )
                .build();

        //defender:
        Creature Skeleton = new Creature.Builder().statistic( CreatureStats.builder()
                        .maxHp( 100 )
                        .damage( NOT_IMPORTANT_DMG )
                        .attack( NOT_IMPORTANT )
                        .armor( 10)
                        .isRanged( false )
                        .moveRange(20)
                        .build() )
                .build();


        List< Creature > c1 = List.of( Lich );
        List< Creature > c2 = List.of( Skeleton );
        Board board = new Board(c1, c2);



        //ustaw pozycje licha tak by był zaraz obok celu:
        board.move(Lich, new Point(0, 0));
        board.move(Skeleton, new Point(1, 0));

        int damageWithoutPenalty = 30;
        int dealtDamage = Lich.getCalculator().calculateDamage(Lich, Skeleton);

        assertThat(dealtDamage).isEqualTo((int) (damageWithoutPenalty*0.5));
    }

    @Test
    void shouldHalveDamageWhenTargetIsTooFar()
    {
        //DLA ranged creature
        //atakujacy:
        Creature Lich = new Creature.Builder().statistic( CreatureStats.builder()
                        .maxHp( NOT_IMPORTANT )
                        .damage( Range.closed( 10, 10 ) )
                        .attack( 50 )
                        .armor( NOT_IMPORTANT)
                        .isRanged( true )
                        .moveRange(20)
                        .build() )
                .build();

        //defender:
        Creature Skeleton = new Creature.Builder().statistic( CreatureStats.builder()
                        .maxHp( 100 )
                        .damage( NOT_IMPORTANT_DMG )
                        .attack( NOT_IMPORTANT )
                        .armor( 10)
                        .isRanged( false )
                        .moveRange(20)
                        .build() )
                .build();


        List< Creature > c1 = List.of( Lich );
        List< Creature > c2 = List.of( Skeleton );
        Board board = new Board(c1, c2);
        board.move(Lich, new Point(0, 0));
        board.move(Skeleton, new Point(11, 0));

        int damageWithoutPenalty = 30;
        int dealtDamage = Lich.getCalculator().calculateDamage(Lich, Skeleton);

        assertThat(dealtDamage).isEqualTo((int) (damageWithoutPenalty*0.5));
    }

    @Test
    void shouldAttackAdjacentCreatures()
    {
        Creature Lich =
                new NecropolisFactory().create(false, 5, 1);

        //defender:
        Creature Skeleton1 = new Creature.Builder().statistic( CreatureStats.builder()
                        .maxHp( 100 )
                        .damage( NOT_IMPORTANT_DMG )
                        .attack( NOT_IMPORTANT )
                        .armor( 10)
                        .isRanged( false )
                        .moveRange(20)
                        .build() )
                .build();

        Creature Skeleton2 = new Creature.Builder().statistic( CreatureStats.builder()
                        .maxHp( 100 )
                        .damage( NOT_IMPORTANT_DMG )
                        .attack( NOT_IMPORTANT )
                        .armor( 10)
                        .isRanged( false )
                        .moveRange(20)
                        .build() )
                .build();

        List< Creature > c1 = List.of( Lich );
        List< Creature > c2 = List.of( Skeleton1, Skeleton2 );
        Hero hero1 = new Hero( c1 );
        Hero hero2 = new Hero( c2 );
        final GameEngine gameEngine =
                new GameEngine( hero1, hero2 );
        Board board = new Board(c1, c2);
        board.move(Lich, new Point(0, 0));
        board.move(Skeleton1, new Point(9, 0));
        board.move(Skeleton2, new Point(9, 1));


        Skeleton2.setCurrentHp(30);
        Lich.attack(Skeleton1);
        int skeleton2Hp = Skeleton2.getCurrentHp();
        assertThat(skeleton2Hp).isLessThan(30);

    }


    @Test
    void shouldHaveChanceForDoubleDamage()
    {
        Creature dreadKnight =
                new NecropolisFactory().create(true, 6, 1);

        //defender:
        Creature Skeleton1 = new Creature.Builder().statistic( CreatureStats.builder()
                        .maxHp( 100 )
                        .damage( NOT_IMPORTANT_DMG )
                        .attack( NOT_IMPORTANT )
                        .armor( 10)
                        .isRanged( false )
                        .moveRange(20)
                        .build() )
                .build();


        List< Creature > c1 = List.of( dreadKnight );
        List< Creature > c2 = List.of( Skeleton1 );
        Hero hero1 = new Hero( c1 );
        Hero hero2 = new Hero( c2 );
        final GameEngine gameEngine =
                new GameEngine( hero1, hero2 );
        Board board = new Board(c1, c2);
        board.move(dreadKnight, new Point(0, 0));
        board.move(Skeleton1, new Point(1, 0));

        Skeleton1.setCurrentHp(30);
        dreadKnight.attack(Skeleton1);
        int skeleton1Hp = Skeleton1.getCurrentHp();
        assertThat(skeleton1Hp).isLessThan(30);
    }

    @Test
    void defenderShouldNotCounterAttack()
    {
        //vampire lord ma takiego skilla

        Creature vampireLord =
                new NecropolisFactory().create(true, 4, 1);

        //defender:
        Creature Skeleton1 = new Creature.Builder().statistic( CreatureStats.builder()
                        .maxHp( 100 )
                        .damage( Range.closed(10, 10) )
                        .attack( NOT_IMPORTANT )
                        .armor( 10)
                        .isRanged( false )
                        .moveRange(20)
                        .build() )
                .build();


        List< Creature > c1 = List.of( vampireLord );
        List< Creature > c2 = List.of( Skeleton1 );

        Board board = new Board(c1, c2);
        board.move(vampireLord, new Point(0, 0));
        board.move(Skeleton1, new Point(1, 0));

        vampireLord.setCurrentHp(5);
        vampireLord.attack(Skeleton1);

        assertEquals(1, vampireLord.getAmount());

    }
}
