package pl.psi;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import pl.psi.hero.EconomyHero;

import java.util.Optional;

public class EconomyBoard {

    private static final int MAX_WITDH = 9;
    private final BiMap< Point, EconomyHero> heroMap = HashBiMap.create();

    private final BiMap<Point,MapTileIf> boardEntitiesMap = HashBiMap.create();

    public EconomyBoard(final EconomyHero hero1, final EconomyHero hero2 )
    {
        addHeroes( hero1, 0 );
        addHeroes( hero2, MAX_WITDH );
        addEntitiesToBoard();
    }

    private void addEntitiesToBoard(){
        MapTileIf castle = new Castle();
        Point castleCoords = new Point(5,5);
        boardEntitiesMap.put(castleCoords,castle);
    }

    private void addHeroes( final EconomyHero hero, final int aPosition )
    {
            heroMap.put( new Point( aPosition, aPosition ), hero);
    }

    Optional< EconomyHero > getEconomyHero(final Point aPoint )
    {
        return Optional.ofNullable( heroMap.get( aPoint ) );
    }

    Optional<Castle> getCastle(final Point aPoint )
    {
        return Optional.ofNullable((Castle) boardEntitiesMap.get( aPoint ) );
    }

    Optional<MapTileIf> getMapTile(final Point aPoint )
    {
        return Optional.ofNullable( boardEntitiesMap.get(aPoint));
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

    // na razie hero ma range poruszania się (hardcoded 5) - TODO stamina?
    boolean canMove( final EconomyHero hero, final Point aPoint )
    {
        if( heroMap.containsKey( aPoint ) )
        {
            return false;
        }
        if( boardEntitiesMap.containsKey( aPoint ) )
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
