package pl.psi;

import pl.psi.hero.EconomyHero;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Optional;

public class EconomyBoardEngine {
    public static final String HERO_MOVED = "HERO_MOVED";
    private final PropertyChangeSupport observerSupport = new PropertyChangeSupport(this);
    private final EconomyBoard board;
    private final EconomyTurnQueue economyTurnQueue;

    public void addObserver(final PropertyChangeListener aObserver) {
        observerSupport.addPropertyChangeListener(aObserver);
        economyTurnQueue.addObserver(aObserver);
    }

    public EconomyBoardEngine(final EconomyHero aHero1, final EconomyHero aHero2) {
        economyTurnQueue = new EconomyTurnQueue(aHero1, aHero2);
        board = new EconomyBoard(aHero1, aHero2);
    }

    public Optional<EconomyHero> getHero(final Point aPoint) {
        return board.getEconomyHero(aPoint);
    }

    public Optional<Castle> getCastle(final Point aPoint) {
        return board.getCastle(aPoint);
    }

    public boolean canMove(final Point aPoint) {

       return board.canMove(economyTurnQueue.getCurrentHero(), aPoint);

    }

    public void move(final Point aPoint) {
        board.move(economyTurnQueue.getCurrentHero(), aPoint);
        observerSupport.firePropertyChange(HERO_MOVED, null, aPoint);
    }

}
