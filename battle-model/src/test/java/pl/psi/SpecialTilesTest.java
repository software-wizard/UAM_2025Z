package org.example;
import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;
import pl.psi.*;
import pl.psi.creatures.Creature;
import pl.psi.creatures.CreatureStats;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class SpecialTilesTest {

    @Test
    void creatureCanNotEnterObstacleTile() {
        final Creature c1 = new Creature.Builder().statistic( CreatureStats.builder()
                        .moveRange( 5 )
                        .build() )
                .build();
        Board board = new Board(List.of(c1), List.of());
        board.addTile(new Point(2,2), new ObstacleTile(new Point(2,2)));
        boolean result = board.canMove(c1, new Point(2, 2));
        assertThat(result).isFalse();
    }

    @Test
    public void damageTileGivesDamage() {
        Creature creature = new Creature.Builder().statistic( CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .attack(50)
                        .armor(50)
                        .moveRange(5)
                        .build())
                .amount(1)
                .build();

        Board board = new Board(List.of(creature), emptyList());
        Point damageTilePoint = new Point(2, 2);
        board.addTile(damageTilePoint, new DamageTile(damageTilePoint, 30));

        Point startPosition = board.getPosition(creature);
        board.move(creature, damageTilePoint);

        assertEquals(70, creature.getCurrentHp(), "Creature should have 70 HP after taking 30 damage.");
        assertEquals(damageTilePoint, board.getPosition(creature), "Creature should be on the DamageTile.");
    }

    @Test
    public void damageTileReducesAmountWhenDamageExceedsCurrentHp() {
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
        Point damageTilePoint = new Point(2, 2);
        board.addTile(damageTilePoint, new DamageTile(damageTilePoint, 250));

        board.move(creature, damageTilePoint);

        assertEquals(3, creature.getAmount(), "Creature should have 3 units left after taking 250 damage.");
        assertEquals(50, creature.getCurrentHp(), "The remaining unit should have 50 HP after taking excess damage.");
    }

}