package org.example;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Optional;

public class GameEngine {
    public static final String CREATURE_MOVED = "CREATURE_MOVED";
    public static final String PASS = "PASS";
    private final Board board;
    private final TurnQueue queue;
    private final PropertyChangeSupport listenerSupport;

    public GameEngine(Hero aP1, Hero aP2) {
        board = new Board(aP1.getCreatures(), aP2.getCreatures());
        queue = new TurnQueue(aP1.getCreatures(), aP2.getCreatures());
        listenerSupport = new PropertyChangeSupport(this);
    }

    public void pass() {
        queue.next();
        listenerSupport.firePropertyChange(PASS, null, null);
    }

    public Optional<Creature> get(Point aPoint) {
        return board.get(aPoint);
    }

    public boolean isCurrentCreature(Creature aC) {
        return aC.equals(queue.getCurrentCreature());
    }

    public boolean canMove(Point aTargetPoint) {
        if (board.get(aTargetPoint).isPresent()) {
            return false;
        }
        Creature currentCreature = queue.getCurrentCreature();
        Point sourcePoint = board.get(currentCreature);
        return aTargetPoint.distance(sourcePoint) < currentCreature.getMoveRange();
    }

    public void move(Point aTargetPoint) {
        Point sourcePoint = board.get(queue.getCurrentCreature());
        board.move(sourcePoint, aTargetPoint);
        listenerSupport.firePropertyChange(CREATURE_MOVED, sourcePoint, aTargetPoint);
    }

    public void addPropertyChangeListener(PropertyChangeListener aListener) {
        listenerSupport.addPropertyChangeListener(aListener);
    }

    public void removePropertyChangeListener(PropertyChangeListener aListener) {
        listenerSupport.removePropertyChangeListener(aListener);
    }
}
