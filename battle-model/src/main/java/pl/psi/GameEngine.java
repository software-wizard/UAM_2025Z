package pl.psi;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import pl.psi.creatures.Creature;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class GameEngine {

    public static final String CREATURE_MOVED = "CREATURE_MOVED";
    private final TurnQueue turnQueue;
    private final Board board;
    private final PropertyChangeSupport observerSupport = new PropertyChangeSupport(this);

    private final List<Hero> heroes = new ArrayList<>();

    public GameEngine(final Hero aHero1, final Hero aHero2) {
        turnQueue = new TurnQueue(aHero1.getCreatures(), aHero2.getCreatures());
        board = new Board(aHero1.getCreatures(), aHero2.getCreatures());
        heroes.add(aHero1);
        heroes.add(aHero2);

        initializeOwners(aHero1, aHero2);
    }

    private void initializeOwners(final Hero aHero1, final Hero aHero2) {
        for (Creature creature : aHero1.getCreatures()) {
            creature.initializeOwner(aHero1);
        }
        for (Creature creature : aHero2.getCreatures()) {
            creature.initializeOwner(aHero2);
        }
    }

    public void attack(final Point point) {
        Optional<Creature> defender = board.getCreature(point);
        Creature attacker = turnQueue.getCurrentCreature();

        defender.ifPresent(def ->
        {
            //Point sourcePoint = board.getPosition(attacker);
            attacker.attack(def, board);
        });

        pass();
    }

    public boolean canMove(final Point aPoint) {
        return board.canMove(turnQueue.getCurrentCreature(), aPoint);
    }

    public void move(final Point aPoint) {
        board.move(turnQueue.getCurrentCreature(), aPoint);
        observerSupport.firePropertyChange(CREATURE_MOVED, null, aPoint);
    }

    public List<Point> getPath(Point targetPoint)
    {
        Creature currentCreature = turnQueue.getCurrentCreature();
        Point startPoint = board.getPosition(currentCreature);
        PathFindingAlg alg = new PathFindingAlg(board);

        List<Point> path = alg.findPath(startPoint, targetPoint, currentCreature.getMoveRange());
        if (!path.isEmpty() && path.getFirst().equals(startPoint)) {
            path.removeFirst();
        }
        return path;
    }

    public List<Point> getMoveRadius(Point creaturePosition)
    {
        int x = creaturePosition.getX();
        int y = creaturePosition.getY();

        List<Point> moveRadius = new ArrayList<>();
        Optional<Creature> creature = getCreature(creaturePosition);


        if (creature.isEmpty()){return List.of();}
        int moveRange = creature.get().getMoveRange();

        for (int dx = -moveRange; dx <= moveRange; dx++)
        {
            for (int dy = -moveRange; dy <= moveRange; dy++){
                Point p = new Point(x + dx, y + dy);

                if (isValidMove(p) && canMove(p) && board.getSpecialTile(p) == null) {
                    moveRadius.add(p);
                }
            }
        }

        return moveRadius;
    }


    public Optional<Creature> getCreature(final Point aPoint) {
        return board.getCreature(aPoint);
    }

    public Hero getCurrentHero(){
        Creature currentCreature = turnQueue.getCurrentCreature();
        for(Hero hero : heroes){
            if(hero.getCreatures().contains(currentCreature)){
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
        Creature currentCreature = turnQueue.getCurrentCreature();
        boolean isRanged = currentCreature.isRanged();

        //for ranged creatures:
        if (isRanged)
        {
            return board.getCreature(point)
                    .isPresent() && distance > 0;
        }
        // for any other creature:
        else
        {
            return board.getCreature(point)
                    .isPresent()
                    && distance < 2 && distance > 0;
        }

    }

    public boolean isCurrentCreature(Point aPoint) {
        return Optional.of(turnQueue.getCurrentCreature()).equals(board.getCreature(aPoint));
    }

    public Tile getTile(Point point) {
        return board.getSpecialTile(point);
    }

    public boolean isOccupied(Point point) {
        return board.getCreature(point).isPresent();
    }

    public boolean isValidMove(Point point) {
        return !isOccupied(point) && isWithinBounds(point);
    }

    public boolean isWithinBounds(Point point) {
        int width = board.getWidth();
        int height = board.getHeight();

        return point.getX() >= 0 && point.getX() < width && point.getY() >= 0 && point.getY() < height;
    }
}
