package pl.psi;

import pl.psi.hero.EconomyHero;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Optional;

public class EconomyBoardEngine {
    public static final String HERO_MOVED = "hero_moved";
    private final PropertyChangeSupport observerSupport = new PropertyChangeSupport(this);
    private final EconomyBoard board;
    private final EconomyTurnQueue economyTurnQueue;

    public void addObserver(final PropertyChangeListener aObserver) {
        observerSupport.addPropertyChangeListener(aObserver);
        //listener refreshGui
        economyTurnQueue.addObserver(aObserver);//tutaj dodaje nienazwane Property?

    }

    public void addObjectObserver(MapTileIf buildingName,final PropertyChangeListener aObserver){
        buildingName.addObserver(aObserver);
    }
    public void addBuildingToBoard(Point buildingCoord, MapTileIf building){
        board.addBuildingToBoard(buildingCoord,building);
    }


    public EconomyBoardEngine(final EconomyHero aHero1, final EconomyHero aHero2) {
        economyTurnQueue = new EconomyTurnQueue(aHero1, aHero2);
        board = new EconomyBoard(aHero1, aHero2);
    }

    public Optional<EconomyHero> getHero(final Point aPoint) {
        return board.getEconomyHero(aPoint);
    }

    public Optional<MapTileIf> getMapTile(final Point aPoint) {
        return board.getMapTile(aPoint);
    }

    public boolean canMove(final Point aPoint) {

       return board.canMove(economyTurnQueue.getCurrentHero(), aPoint);

    }

    public void pass() {
        economyTurnQueue.next();
    }

    public boolean canAttack(final Point point) {
        double distance = board.getPosition(economyTurnQueue.getCurrentHero())
                .distance(point);
        return board.getEconomyHero(point)
                .isPresent()
                && distance < 2 && distance > 0;
    }


    public void move(final Point aPoint) {
        board.move(economyTurnQueue.getCurrentHero(), aPoint);
        observerSupport.firePropertyChange(HERO_MOVED, null, aPoint);
//tutaj jest wywolany event do refreshGui?
    }

    public boolean canInteract(final Point aPoint) {

        return board.canInteract(economyTurnQueue.getCurrentHero(), aPoint);
    }
    public void interact(final Point aPoint) {
        board.interact(economyTurnQueue.getCurrentHero(), aPoint);
    }

}
