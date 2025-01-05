package pl.psi;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import pl.psi.hero.EconomyHero;

import java.util.Optional;

import static pl.psi.MapTileIf.TileType.NECROPOLIS_COMBAT_BUILDING;
import static pl.psi.MapTileIf.TileType.ZAMEK;

public class EconomyBoard {

    private static final int MAX_WITDH = 9;
    private final BiMap< Point, EconomyHero> heroMap = HashBiMap.create();

    private final BiMap<Point,MapTileIf> boardObjectsMap = HashBiMap.create();

    public EconomyBoard(final EconomyHero hero1, final EconomyHero hero2 )
    {
        addHeroes( hero1, 0 );
        addHeroes( hero2, MAX_WITDH );
        addObjectsToBoard();
    }

    private void addObjectsToBoard(){
        MapTileIf castle = new Castle();
        Point castleCoords = new Point(5,5);
        boardObjectsMap.put(castleCoords,castle);

        MapTileIf goldBuilding = new GoldBuilding();
        Point goldBuildingCoords = new Point(4,3);
        boardObjectsMap.put(goldBuildingCoords,goldBuilding);

        MapTileIf necropolisCombatBuilding = new NecropolisCombatBuilding();
        Point necComCoord = new Point(7,6);
        boardObjectsMap.put(necComCoord,necropolisCombatBuilding);
    }

    private void addHeroes( final EconomyHero hero, final int aPosition )
    {
            heroMap.put( new Point( aPosition, aPosition ), hero);
    }

    Optional< EconomyHero > getEconomyHero(final Point aPoint )
    {
        return Optional.ofNullable( heroMap.get( aPoint ) );
    }

    Boolean isCastle(final Point aPoint )
    {
        if(getMapTile(aPoint).isPresent()){
            MapTileIf maybeCastle = boardObjectsMap.get(aPoint);
            return maybeCastle.getTileType() == ZAMEK;
        }
        return false;
    }
    Boolean isCombatBuilding(final Point aPoint )
    {
        if(getMapTile(aPoint).isPresent()){
            MapTileIf maybeCombatBuilding = boardObjectsMap.get(aPoint);
            return maybeCombatBuilding.getTileType() == NECROPOLIS_COMBAT_BUILDING;
        }
        return false;
    }
    EconomyHero createCombatBuildingOpponent(final Point aPoint){

        NecropolisCombatBuilding necropolisCombatBuilding = (NecropolisCombatBuilding)boardObjectsMap.get(aPoint);
        return necropolisCombatBuilding.createBattleOpponent();
            //interakcja z obiektem po ruchu postaci

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

    // na razie hero ma range poruszania się (hardcoded 5) - TODO stamina?
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
