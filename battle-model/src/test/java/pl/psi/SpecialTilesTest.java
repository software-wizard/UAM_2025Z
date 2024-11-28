package org.example;
import org.junit.jupiter.api.Test;
import pl.psi.Board;
import pl.psi.Point;
import pl.psi.Tile;
import pl.psi.creatures.Creature;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SpecialTilesTest {

    @Test
    void creatureCanNotEnterObstacleTile() {
        Creature c1 = Creature.builder().moveRange(4).build();
        Board board = new Board(List.of(c1), emptyList(), List.of(new Point(2, 2)));

        boolean result = board.canMove(c1, new Point(2, 2));
        assertThat(result).isFalse();
    }

    @Test
    void damageTileGivesDamage() {
        Creature c1 = Creature.builder().moveRange(4).maxHp(20).build();
        Board board = new Board(List.of(c1), emptyList());

        Tile damageTile = Tile.createDamageTile(new Point(3, 3), 10);
        board.addTile(damageTile);

        board.move(new Point(0, 0), new Point(3, 3));
        assertThat(c1.getCurrentHp()).isEqualTo(10);
    }

    @Test
    void creatureReceivesBuffFromTile() {
        Creature c1 = Creature.builder().moveRange(4).attack(10).build();
        Board board = new Board(List.of(c1), emptyList());
        Tile buffTile = Tile.createBuffTile(new Point(2, 2), 5);

        board.addTile(buffTile);
        board.move(c1, new Point(2, 2));

        assertThat(c1.getAttack()).isEqualTo(15);
    }

    @Test
    void creatureReceivesDebuffFromTile() {
        Creature c1 = Creature.builder().moveRange(4).attack(10).build();
        Board board = new Board(List.of(c1), emptyList());
        Tile debuffTile = Tile.createDebuffTile(new Point(2, 2), -3);

        board.addTile(debuffTile);
        board.move(c1, new Point(2, 2));

        assertThat(c1.getAttack()).isEqualTo(7);
    }
}