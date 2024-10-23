package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    Map<Point, Creature> map = new HashMap<>();

    public Board(List<Creature> aP1Creature, List<Creature> aP2Creature) {
        for (int i = 0; i < aP1Creature.size(); i++) {
            map.put(new Point(0, i * 2), aP1Creature.get(i));
        }
        for (int i = 0; i < aP2Creature.size(); i++) {
            map.put(new Point(10, i * 2), aP2Creature.get(i));
        }
    }

    public void move(Point aStartPoint, Point aEndPoint) {
        if (isEmpty(aEndPoint)) {
            Creature c = map.get(aStartPoint);
            map.remove(aStartPoint);
            map.put(aEndPoint, c);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    private boolean isEmpty(Point aEndPoint) {
        return map.get(aEndPoint) == null;
    }

    public Creature get(Point aPoint) {
        return map.get(aPoint);
    }

    int size() {
        return map.size();
    }
}
