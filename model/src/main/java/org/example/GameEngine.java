package org.example;

import java.util.Optional;

public class GameEngine {
    private final Board board;
    private final TurnQueue queue;

    public GameEngine(Hero aP1, Hero aP2) {
        board = new Board(aP1.getCreatures(), aP2.getCreatures());
        queue = new TurnQueue(aP1.getCreatures(), aP2.getCreatures());
    }


    public void pass() {
        queue.next();
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
        board.move(board.get(queue.getCurrentCreature()), aTargetPoint);
    }
}
