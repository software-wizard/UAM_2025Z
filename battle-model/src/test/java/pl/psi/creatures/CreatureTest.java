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
        board.removeSpecialTiles();

        board.move(angel, new Point(0, 0));
        board.move(dragon, new Point(1, 0));
        // when
        angel.attack( dragon, board );
        // then
        assertThat( dragon.getCurrentHp() ).isEqualTo( 50 );
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
        board.removeSpecialTiles();

        board.move(attacker, new Point(0, 0));
        board.move(defender, new Point(1, 0));
        // when
        attacker.attack( defender, board );
        // then
        assertThat( attacker.getCurrentHp() ).isEqualTo( 100 );
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
        board.removeSpecialTiles();

        board.move(attacker, new Point(0, 0));
        board.move(defender, new Point(1, 0));

        attacker.attack( defender, board );
        attacker.attack( defender, board );
        assertThat( attacker.getCurrentHp() ).isEqualTo( 90 );
        turnQueue.next();
        turnQueue.next();
        attacker.attack( defender, board );
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
        board.removeSpecialTiles();

        board.move(VampireLord, new Point(0, 0));
        board.move(dragon, new Point(1, 0));

        int initialAmount = VampireLord.getAmount();
        VampireLord.setCurrentHp(20);
        //wykorzystac stałą, get mozę popsuć
        int initialHp = VampireLord.getCurrentHp();

        VampireLord.attack(dragon, board);


        assertThat(VampireLord.getAmount()).isGreaterThanOrEqualTo(initialAmount);
        assertThat(VampireLord.getCurrentHp()).isGreaterThan(initialHp);

    }

    @Test
    void creatureShouldNotResurrectIfAttacksUndead()
    {
        Creature VampireLord =
                new NecropolisFactory().create(true, 4, 1);
        Creature Zombie = new NecropolisFactory().create(true, 2, 30);

        List< Creature > c1 = List.of( VampireLord, Zombie );
        List< Creature > c2 = List.of(  );
        Board board = new Board(c1, c2);
        board.removeSpecialTiles();

        board.move(VampireLord, new Point(0, 0));
        board.move(Zombie, new Point(1, 0));

        int initialAmount = VampireLord.getAmount();  //rowna sie 1

        VampireLord.attack(Zombie, board);
        int newAmount = VampireLord.getAmount();

        //resurrect powinien sie nie aktywowac:
        assertThat(newAmount).isLessThan( initialAmount );

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
        board.removeSpecialTiles();

        //odleglosc miedzy 1 a 10 zapewni pelny dmg bez zadnych kar:
        board.move(Lich, new Point(0, 0));
        board.move(Skeleton, new Point(9, 0));
        Point sourcePoint = board.getPosition(Lich);
        Point targetPoint = board.getPosition(Skeleton);

        //czy zadamy pelne obrazenia:
        int fullDmg = 30;
        int dealtDmg = Lich.getCalculator().calculateDamage(Lich, Skeleton,sourcePoint,targetPoint );
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
        board.removeSpecialTiles();


        //ustaw pozycje licha tak by był zaraz obok celu:
        board.move(Lich, new Point(0, 0));
        board.move(Skeleton, new Point(1, 0));
        Point sourcePoint = board.getPosition(Lich);
        Point targetPoint = board.getPosition(Skeleton);

        int damageWithoutPenalty = 30;
        //powinna zostac zastosowana kara -50% obrazen:
        int dealtDamage = Lich.getCalculator().calculateDamage(Lich, Skeleton, sourcePoint, targetPoint );

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
        board.removeSpecialTiles();

        board.move(Lich, new Point(0, 0));
        board.move(Skeleton, new Point(11, 0));
        Point sourcePoint = board.getPosition(Lich);
        Point targetPoint = board.getPosition(Skeleton);

        int damageWithoutPenalty = 30;
        //powinna zostac zastosowana kara -50% obrazen:
        int dealtDamage = Lich.getCalculator().calculateDamage(Lich, Skeleton, sourcePoint, targetPoint );

        assertThat(dealtDamage).isEqualTo((int) (damageWithoutPenalty*0.5));
    }

    @Test
    void shouldAttackAdjacentCreatures()
    {
        //attacker:
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

        //kreaturka obok defendera:
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
        Board board = new Board(c1, c2);
        board.removeSpecialTiles();

        board.move(Lich, new Point(0, 0));
        board.move(Skeleton1, new Point(9, 0));
        board.move(Skeleton2, new Point(9, 1));

        Skeleton2.setCurrentHp(30);
        Lich.attack(Skeleton1, board);
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
        Board board = new Board(c1, c2);
        board.removeSpecialTiles();

        board.move(dreadKnight, new Point(0, 0));
        board.move(Skeleton1, new Point(1, 0));


        Skeleton1.setCurrentHp(30);
        dreadKnight.attack(Skeleton1, board);
        int skeleton1Hp = Skeleton1.getCurrentHp();
        assertThat(skeleton1Hp).isLessThan(30);
    }

    @Test
    void defenderShouldNotCounterAttack()
    {
        //umiejetnosc vampire

        //attacker:
        Creature vampire =
                new NecropolisFactory().create(false, 4, 1);

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


        List< Creature > c1 = List.of( vampire );
        List< Creature > c2 = List.of( Skeleton1 );

        Board board = new Board(c1, c2);
        board.removeSpecialTiles();

        board.move(vampire, new Point(0, 0));
        board.move(Skeleton1, new Point(1, 0));

        vampire.setCurrentHp(5);
        //kontratak zabiłby teraz wampira, ale ma skill
        vampire.attack(Skeleton1, board);
        int amountAfterAttack = vampire.getAmount();

        assertEquals(1, amountAfterAttack);

    }

    @Test
    void shouldHaveAChanceToCastSpell()
    {
        //test dla BlackNight
        Creature blackKnight =
                new NecropolisFactory().create(false, 6, 1);

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


        List< Creature > c1 = List.of( blackKnight );
        List< Creature > c2 = List.of( Skeleton1 );

        Board board = new Board(c1, c2);
        board.removeSpecialTiles();

        board.move(blackKnight, new Point(0, 0));
        board.move(Skeleton1, new Point(1, 0));


        Skeleton1.setCurrentHp(30);
        blackKnight.attack(Skeleton1, board);
        int skeleton1Hp = Skeleton1.getCurrentHp();
        assertThat(skeleton1Hp).isGreaterThan(13);



    }
}
