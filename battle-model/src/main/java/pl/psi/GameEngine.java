package pl.psi;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pl.psi.creatures.Creature;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class GameEngine implements PropertyChangeListener{

    public static final String CREATURE_MOVED = "CREATURE_MOVED";
    private final TurnQueue turnQueue;
    private final Board board;
    private final Hero aHero1;
    private final Hero aHero2;
    private final Collection<Creature> creatures;
    private final PropertyChangeSupport observerSupport = new PropertyChangeSupport(this);
    private final List<Hero> heroes = new ArrayList<>();

    public GameEngine(final Hero aHero1, final Hero aHero2) {
        turnQueue = new TurnQueue(aHero1.getCreatures(), aHero2.getCreatures());
        board = new Board(aHero1.getCreatures(), aHero2.getCreatures());
        heroes.add(aHero1);
        heroes.add(aHero2);
        this.aHero1 = aHero1;
        this.aHero2 = aHero2;
        creatures = Stream.concat(aHero1.getCreatures().stream(), aHero2.getCreatures().stream())
                .collect(Collectors.toList());
        creatures.forEach(creature -> creature.addObserver(this));
    }

    public void attack(final Point point) {
        board.getCreature(point)
                .ifPresent(defender -> turnQueue.getCurrentCreature()
                        .attack(defender));
        pass();
    }

    public boolean canMove(final Point aPoint) {
        return board.canMove(turnQueue.getCurrentCreature(), aPoint);
    }

    public void move(final Point aPoint) {
        board.move(turnQueue.getCurrentCreature(), aPoint);
        observerSupport.firePropertyChange(CREATURE_MOVED, null, aPoint);
    }

    public Optional<Creature> getCreature(final Point aPoint) {
        return board.getCreature(aPoint);
    }

    public Hero getCurrentHero() {
        Creature currentCreature = turnQueue.getCurrentCreature();
        for (Hero hero : heroes) {
            if (hero.getCreatures().contains(currentCreature)) {
                return hero;
            }
        }
        throw new IllegalStateException("getCurrentHero: Hero must be returned");
    }

    public void pass() {
        turnQueue.next();
    }

    public void addObserver(final PropertyChangeListener aObserver) {
        observerSupport.addPropertyChangeListener(aObserver);
        turnQueue.addObserver(aObserver);
    }

    public boolean canAttack(final Point point) {
        double distance = board.getPosition(turnQueue.getCurrentCreature())
                .distance(point);
        return board.getCreature(point)
                .isPresent()
                && distance < 2 && distance > 0;
    }

    public boolean isCurrentCreature(Point aPoint) {
        return Optional.of(turnQueue.getCurrentCreature()).equals(board.getCreature(aPoint));
    }

    public Tile getTile(Point point) {
        return board.getSpecialTile(point);
    }

    public void checkIfBattleIsOver(Hero aHero1, Hero aHero2) {
            if(aHero1.getCreatures().isEmpty()) {
                System.out.println("End Battle");
                observerSupport.firePropertyChange("end_battle", false, true);
            }
            if(aHero2.getCreatures().isEmpty()) {
                System.out.println("End Battle");
                observerSupport.firePropertyChange("end_battle", false, true);
        }
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if("dead".equals(evt.getPropertyName())){
            Creature deadCreature = (Creature) evt.getSource();
            turnQueue.next();
            turnQueue.removeCreature(deadCreature);
            board.removeCreature(deadCreature);
            for (Hero hero : heroes) {
                if (hero.getCreatures().contains(deadCreature)) {
                    hero.removeCreature(deadCreature);
                }
            }
            checkIfBattleIsOver(aHero1, aHero2);
        }
    }
}