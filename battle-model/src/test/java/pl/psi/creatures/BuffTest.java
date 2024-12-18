package pl.psi.creatures;
import org.junit.jupiter.api.Test;
import pl.psi.Board;
import pl.psi.TurnQueue;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class BuffTest {

    @Test
    public void buffIsAddedToCreature(){
        final Creature creature = new Creature.Builder().statistic( CreatureStats.builder()
                        .moveRange( 5 )
                        .attack(5)
                        .build() )
                .build();


        final List< Creature > c1 = List.of( creature );
        final List< Creature > c2 = List.of();
        final Board board = new Board( c1, c2 );

        IncreaseAttackBuff buff = new IncreaseAttackBuff(3,5);
        creature.addBuff(buff);

        final List<Buff> expectedBuffList = List.of(buff);
        assertEquals(expectedBuffList, creature.getBuffs());
    }

    @Test
    public void attackBuffBoostsCreaturesAttack(){

        final Creature creature1 = new Creature.Builder().statistic( CreatureStats.builder()
                        .moveRange( 5 )
                        .attack(5)
                        .build() )
                .build();
        final TurnQueue turnQueue = new TurnQueue( List.of( creature1), List.of() );

        IncreaseAttackBuff buff = new IncreaseAttackBuff(3,5);
        creature1.addBuff(buff);
        turnQueue.next();
        turnQueue.next();
        turnQueue.next();
        turnQueue.next();
        turnQueue.next();
        turnQueue.next();
        assertEquals(10, creature1.getAttack());
    }

    @Test
    public void buffIsNotActiveAfterItShouldEnd(){
        final Creature creature1 = new Creature.Builder().statistic( CreatureStats.builder()
                        .moveRange( 5 )
                        .attack(5)
                        .build() )
                .build();
        final TurnQueue turnQueue = new TurnQueue( List.of( creature1), List.of() );

        IncreaseAttackBuff buff = new IncreaseAttackBuff(3,5);
        creature1.addBuff(buff);

        turnQueue.next();
        turnQueue.next();
        turnQueue.next();
        assertEquals(5, creature1.getAttack());
    }
}
