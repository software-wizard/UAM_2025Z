package pl.psi;

import java.util.*;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import pl.psi.creatures.Creature;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class Board
{
    private static final int MAX_WITDH = 14;
    private static final int OBSTACLE_COUNT = 10;
    private final BiMap< Point, Creature > map = HashBiMap.create();
    private final Map<Point, Tile> specialTiles = new HashMap<>();

    public Board( final List< Creature > aCreatures1, final List< Creature > aCreatures2 )
    {
        addCreatures( aCreatures1, 0 );
        addCreatures( aCreatures2, MAX_WITDH );
        generateObstacles();
    }

    private void addCreatures( final List< Creature > aCreatures, final int aXPosition )
    {
        for( int i = 0; i < aCreatures.size(); i++ )
        {
            map.put( new Point( aXPosition, i * 2 + 1 ), aCreatures.get( i ) );
        }
    }

    Optional< Creature > getCreature( final Point aPoint )
    {
        return Optional.ofNullable( map.get( aPoint ) );
    }

    public void move( final Creature aCreature, final Point aPoint )
    {
        if( canMove( aCreature, aPoint ) )
        {
            map.inverse()
                .remove( aCreature );
            map.put( aPoint, aCreature );

            Tile tile = specialTiles.get(aPoint);
            if (tile instanceof DamageTile) {
                DamageTile damageTile = (DamageTile) tile;
                aCreature.takeDamage(damageTile.getGivenDamage());
            }
        }

    }

    public boolean canMove(final Creature aCreature, final Point aPoint) {
        if (map.containsKey(aPoint)) {
            return false;
        }
        if (specialTiles.containsKey(aPoint) && !specialTiles.get(aPoint).isPassable()) {
            return false;
        }
        final Point oldPosition = getPosition(aCreature);
        return aPoint.distance(oldPosition.getX(), oldPosition.getY()) < aCreature.getMoveRange();
    }
    public Point getPosition(Creature aCreature)
    {
        return map.inverse()
            .get( aCreature );
    }

    public void addTile(Point point, Tile tile) {
        specialTiles.put(point, tile);
    }

    private void generateObstacles() {
        Random random = new Random();
        int generatedObstacles = 0;

        while (generatedObstacles < OBSTACLE_COUNT) {
            int x = random.nextInt(MAX_WITDH);
            int y = random.nextInt(MAX_WITDH);
            Point point = new Point(x, y);

            if (!map.containsKey(point) && !specialTiles.containsKey(point)) {
                addTile(point, new ObstacleTile(point));
                generatedObstacles++;
            }
        }
    }

    public boolean isObstacleTile(Point point) {
        Tile tile = specialTiles.get(point);
        return tile != null && !tile.isPassable();
    }

}
