package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GameEngineTest {

    @Test
    void shouldAnswerCanMoveProperly(){
        Hero p1 = new Hero(List.of());
        Hero p2 = new Hero(List.of(Creature.builder().moveRange(1).build()));
        GameEngine engine = new GameEngine(p2,p1);

        assertThat(engine.get(new Point(0,0))).isNotEmpty();

        assertThat(engine.canMove(new Point(0,0))).isFalse();
        assertThat(engine.canMove(new Point(0,1))).isTrue();
        assertThat(engine.canMove(new Point(1,0))).isTrue();
        assertThat(engine.canMove(new Point(1,1))).isFalse();
    }
}