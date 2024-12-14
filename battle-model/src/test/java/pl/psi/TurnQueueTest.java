package pl.psi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import pl.psi.creatures.Creature;
import pl.psi.creatures.CreatureStats;

class TurnQueueTest
{
    @Test
    void shouldAddPawnsCorrectly()
    {
        final Creature creature1 = new Creature.Builder().statistic( CreatureStats.builder()
            .build() )
            .build();
        final Creature creature2 = new Creature.Builder().statistic( CreatureStats.builder()
            .build() )
            .build();
        final Creature creature3 = new Creature.Builder().statistic( CreatureStats.builder()
            .build() )
            .build();
        final TurnQueue turnQueue = new TurnQueue( List.of( creature1, creature2 ), List.of( creature3 ) );

        assertEquals( turnQueue.getCurrentCreature(), creature1 );
        turnQueue.next();
        assertEquals( turnQueue.getCurrentCreature(), creature2 );
        turnQueue.next();
        assertEquals( turnQueue.getCurrentCreature(), creature3 );
        turnQueue.next();
        assertEquals( turnQueue.getCurrentCreature(), creature1 );
    }

    @Test
    void x(){
        c1 - moverange 2 (0,1)
                c2 - moverange 10
        new Board(List.of(c1,c2), empty())
                board.move(c2 -> 1,1);
        board.canMove(c1,2,1);

        alg.getPath((0,1), (2,1), board); -> null
    }
}