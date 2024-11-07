package org.example;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.List;
import java.util.Optional;

public class Board {
    public final static int WIDTH = 10;
    public final static int HEIGHT = 10;
    BiMap<Point, Creature> map = HashBiMap.create();

    public Board(List<Creature> aP1Creature, List<Creature> aP2Creature) {
        for (int i = 0; i < aP1Creature.size(); i++) {
            map.put(new Point(0, i * 2), aP1Creature.get(i));
        }
        for (int i = 0; i < aP2Creature.size(); i++) {
            map.put(new Point(WIDTH - 1, i * 2), aP2Creature.get(i));
        }
    }

    public void move(Point aStartPoint, Point aEndPoint) {
        if (isEmpty(aEndPoint)) {
            Creature c = map.get(aStartPoint);
            map.remove(aStartPoint);
            map.put(aEndPoint, c);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private boolean isEmpty(Point aEndPoint) {
        return map.get(aEndPoint) == null;
    }

    public Optional<Creature> get(Point aPoint) {
        return Optional.ofNullable(map.get(aPoint));
    }

    public Point get(Creature aPoint) {
        Preconditions.checkNotNull(aPoint);
        return map.inverse().get(aPoint);
    }

    int size() {
        return map.size();
    }
}
