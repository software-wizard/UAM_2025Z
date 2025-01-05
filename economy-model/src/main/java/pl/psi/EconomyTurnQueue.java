package pl.psi;

import pl.psi.hero.EconomyHero;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class EconomyTurnQueue {

    public static final String END_OF_TURN = "END_OF_TURN";
    public static final String NEXT_HERO = "NEXT_HERO";
    private final Collection<EconomyHero> heroes;
    private final Queue<EconomyHero> heroesQueue;
    private final PropertyChangeSupport observerSupport = new PropertyChangeSupport(this);
    private EconomyHero currentHero;
    private int roundNumber;



    public EconomyTurnQueue(final EconomyHero aHero1,
                     final EconomyHero aHero2) {
        heroes = Arrays.asList(aHero1, aHero2);
        heroesQueue = new LinkedList<>();
        initQueue();
        heroes.forEach(observerSupport::addPropertyChangeListener);
        next();
    }

    private void initQueue() {
        heroesQueue.addAll(heroes);
    }

    public EconomyHero getCurrentHero() {
        return currentHero;
    }

    public void next() {
        EconomyHero oldHero = currentHero;
        if (heroesQueue.isEmpty()) {
            endOfTurn();
        }


        currentHero = heroesQueue.poll();

        observerSupport.firePropertyChange(NEXT_HERO, oldHero, currentHero);
    }

    private void endOfTurn() {
        roundNumber++;
        initQueue();
        observerSupport.firePropertyChange(END_OF_TURN, roundNumber - 1, roundNumber);
    }

    void addObserver(PropertyChangeListener aObserver) {
        observerSupport.addPropertyChangeListener(aObserver);
    }

}

