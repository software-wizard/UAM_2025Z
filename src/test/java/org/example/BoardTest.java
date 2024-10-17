package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    @Test
    void creatureCanMove() {
        Creature c1 = Creature.builder().moveRange(4).build();
        Board board = new Board(List.of(c1), emptyList());

        board.move(new Point(0, 0), new Point(2, 2));

        assertThat(board.get(new Point(2, 2))).isEqualTo(c1);
    }

    @Test
    void shouldCannotMoveWhenTileIsNotEmpty() {
        Creature c1 = Creature.builder().moveRange(4).build();
        Creature c2 = Creature.builder().moveRange(4).build();
        Board board = new Board(List.of(c1, c2), emptyList());

        board.move(new Point(0, 0), new Point(0, 2));

        assertThat(board.size()).isEqualTo(2);
        assertThat(board.get(new Point(0, 2))).isEqualTo(c2);
        assertThat(board.get(new Point(0, 0))).isEqualTo(c1);
    }
}