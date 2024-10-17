package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TurnQueueTest {

    @Test
    void queueShouldRestartAfterLastCreature() {
        Creature c5 = createCreatureWithMove(5);
        Creature c2 = createCreatureWithMove(2);
        Creature c7 = createCreatureWithMove(7);
        List<Creature> p1List = new ArrayList<>();
        p1List.add(c5);
        List<Creature> p2List = new ArrayList<>();
        p2List.add(c2);
        p2List.add(c7);
        TurnQueue turnQueue = new TurnQueue(p1List, p2List);

        assertThat(c7).isEqualTo(turnQueue.getCurrentCreature());
        turnQueue.next();
        assertThat(c5).isEqualTo(turnQueue.getCurrentCreature());
        turnQueue.next();
        assertThat(c2).isEqualTo(turnQueue.getCurrentCreature());

        turnQueue.next();
        assertThat(c7).isEqualTo(turnQueue.getCurrentCreature());
    }

    private Creature createCreatureWithMove(int aMoveRange) {
        return Creature.builder()
                .moveRange(aMoveRange)
                .build();
    }
}