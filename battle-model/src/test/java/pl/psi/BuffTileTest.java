package pl.psi;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;
import pl.psi.creatures.Buff;
import pl.psi.creatures.Creature;
import pl.psi.creatures.CreatureStats;
import pl.psi.creatures.IncreaseAttackBuff;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuffTileTest {
    @Test
    public void buffTileAddsBuff() {
        Creature creature = new Creature.Builder().statistic( CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .attack(50)
                        .armor(50)
                        .moveRange(5)
                        .build())
                .amount(5)
                .build();

        Board board = new Board(List.of(creature), emptyList());
        Point tilePoint = new Point(2, 2);
        Buff buff = new IncreaseAttackBuff(3, 10);
        BuffTile buffTile = new BuffTile(buff);
        board.addTile(tilePoint, buffTile);

        board.move(creature, tilePoint);

        final List<Buff> expectedBuffList = List.of(buff);
        assertEquals(expectedBuffList, creature.getBuffs());
    }

    @Test
    public void buffAddedByBuffTileWorks() {
        Creature creature = new Creature.Builder().statistic( CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .attack(50)
                        .armor(50)
                        .moveRange(5)
                        .build())
                .amount(5)
                .build();

        Board board = new Board(List.of(creature), emptyList());
        Point tilePoint = new Point(2, 2);
        Buff buff = new IncreaseAttackBuff(3, 10);
        BuffTile buffTile = new BuffTile(buff);
        board.addTile(tilePoint, buffTile);

        board.move(creature, tilePoint);

        final TurnQueue turnQueue = new TurnQueue( List.of(creature), List.of());
        turnQueue.next();
        assertEquals(60, creature.getAttack());
    }
}
