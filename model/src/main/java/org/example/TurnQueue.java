package org.example;

import java.util.*;

public class TurnQueue {
    private final Set<Creature> allCreatures;
    Queue<Creature> queue;
    public TurnQueue(List<Creature> p1List, List<Creature> p2List) {
        queue = new LinkedList<>();
        allCreatures = new HashSet<>();
        allCreatures.addAll(p1List);
        allCreatures.addAll(p2List);
        initQueue();
    }

    private void initQueue() {
        queue.addAll(allCreatures
                .stream().sorted(Comparator.comparingInt(Creature::getMoveRange).reversed()).toList());
    }

    public void next() {
        Creature creatureCandidate = queue.poll();
        if(queue.isEmpty()){
            initQueue();
        }
    }

    public Creature getCurrentCreature() {
        return queue.peek();
    }
}
