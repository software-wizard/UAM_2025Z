package pl.psi;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import pl.psi.hero.EconomyHero;

import java.util.Optional;

public class EconomyBoard {

    private static final int MAX_WITDH = 9;
    private final BiMap< Point, EconomyHero> map = HashBiMap.create();

    public EconomyBoard(final EconomyHero hero1, final EconomyHero hero2 )
    {
        addHeroes( hero1, 0 );
        addHeroes( hero2, MAX_WITDH );
    }

    private void addHeroes( final EconomyHero hero, final int aPosition )
    {
            map.put( new Point( aPosition, aPosition ), hero);
    }

    Optional< EconomyHero > getEconomyHero(final Point aPoint )
    {
        return Optional.ofNullable( map.get( aPoint ) );
    }

    void move( final EconomyHero hero, final Point aPoint )
    {
        if( canMove( hero, aPoint ) )
        {
            map.inverse()
                    .remove( hero );
            map.put( aPoint, hero );
        }
    }

    // na razie hero ma range poruszania się (hardcoded 5) - TODO stamina?
    boolean canMove( final EconomyHero hero, final Point aPoint )
    {
        if( map.containsKey( aPoint ) )
        {
            return false;
        }
        final Point oldPosition = getPosition( hero );
        return aPoint.distance( oldPosition.getX(), oldPosition.getY() ) < 5;
    }

    Point getPosition( EconomyHero hero )
    {
        return map.inverse()
                .get( hero );
    }
}
