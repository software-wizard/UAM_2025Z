package pl.psi;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import pl.psi.hero.EconomyHero;

import java.util.Optional;

import static pl.psi.MapTileIf.TileType.*;

public class EconomyBoard {

    private static final int MAX_WITDH = 9;
    private final BiMap< Point, EconomyHero> heroMap = HashBiMap.create();

    private final BiMap<Point,MapTileIf> boardObjectsMap = HashBiMap.create();

    private void removeGoldBuilding(MapTileIf goldBuilding){
        if(boardObjectsMap.containsValue(goldBuilding)){
            boardObjectsMap.inverse()
                    .remove(goldBuilding);
        }
    }

    public EconomyBoard(final EconomyHero hero1, final EconomyHero hero2)
    {
        addHeroes( hero1, 0 );
        addHeroes( hero2, MAX_WITDH );
        //addObjectsToBoard();
    }
    public void addBuildingToBoard(Point buildingCoord,MapTileIf building){
            boardObjectsMap.put(buildingCoord,building);
            if(building.getTileType()==GOLD_BUILDING){
                building.addObserver((e)->removeGoldBuilding(building));
            }
    }

    private void addHeroes( final EconomyHero hero, final int aPosition )
    {
            heroMap.put( new Point( aPosition, aPosition ), hero);
    }

    Optional< EconomyHero > getEconomyHero(final Point aPoint )
    {
        return Optional.ofNullable( heroMap.get( aPoint ) );
    }

    Optional<MapTileIf> getMapTile(final Point aPoint )
    {
        return Optional.ofNullable( boardObjectsMap.get(aPoint));
    }


    void move( final EconomyHero hero, final Point aPoint )
    {
        if( canMove( hero, aPoint ) )
        {
            heroMap.inverse()
                    .remove( hero );
            heroMap.put( aPoint, hero );

        }
    }

    void interact(final EconomyHero hero,final Point aPoint){
        if(canInteract(hero,aPoint)){
            MapTileIf mapTileObject = boardObjectsMap.get(aPoint);
            mapTileObject.Interact(hero);
            //interakcja z obiektem po ruchu postaci
        }
    }

    Boolean canInteract(final EconomyHero hero,final Point aPoint){

            double distance = getPosition(hero)
                    .distance(aPoint);
            return getMapTile(aPoint)
                    .isPresent()
                    && distance < 2 && distance > 0;

    }

    boolean canMove( final EconomyHero hero, final Point aPoint )
    {
        if( heroMap.containsKey( aPoint ) )
        {
            return false;
        }
        if( boardObjectsMap.containsKey( aPoint ) )
        {
        return false;
        }
        final Point oldPosition = getPosition( hero );
        return aPoint.distance( oldPosition.getX(), oldPosition.getY() ) < 5;
    }

    Point getPosition( EconomyHero hero )
    {
        return heroMap.inverse()
                .get( hero );
    }
}
