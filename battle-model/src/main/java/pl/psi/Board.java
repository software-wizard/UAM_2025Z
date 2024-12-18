package pl.psi;

import java.util.*;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import pl.psi.creatures.Buff;
import pl.psi.creatures.Creature;
import pl.psi.creatures.IncreaseAttackBuff;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class Board
{
    private static final int MAX_WITDH = 14;
    private static final int OBSTACLE_COUNT = 10;
    private static final int DAMAGE_TILE_COUNT = 10;
    private static final int INCREASE_ATTACK_BUFFTILE_COUNT = 5;
    private final BiMap< Point, Creature > map = HashBiMap.create();
    private final Map<Point, Tile> specialTiles = new HashMap<>();

    public Board( final List< Creature > aCreatures1, final List< Creature > aCreatures2 )
    {
        addCreatures( aCreatures1, 0 );
        addCreatures( aCreatures2, MAX_WITDH );
        generateSpecialTiles();
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
            if(tile!=null){
                tile.apply(aCreature);
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

    private void generateSpecialTiles() {
        Random random = new Random();
        int generatedObstacles = 0;
        int generatedDamageTiles = 0;
        int generatedIncreaseAttackBuffTiles = 0;

        while (generatedObstacles < OBSTACLE_COUNT) {
            int x = random.nextInt(MAX_WITDH);
            int y = random.nextInt(MAX_WITDH);
            Point point = new Point(x, y);

            if (!map.containsKey(point) && !specialTiles.containsKey(point)) {
                addTile(point, new ObstacleTile());
                generatedObstacles++;
            }
        }

        while (generatedDamageTiles < DAMAGE_TILE_COUNT) {
            int x = random.nextInt(MAX_WITDH);
            int y = random.nextInt(MAX_WITDH);
            Point point = new Point(x, y);

            if (!map.containsKey(point) && !specialTiles.containsKey(point)) {
                addTile(point, new DamageTile(20));
                generatedDamageTiles++;
            }
        }

        while (generatedIncreaseAttackBuffTiles < INCREASE_ATTACK_BUFFTILE_COUNT) {
            int x = random.nextInt(MAX_WITDH);
            int y = random.nextInt(MAX_WITDH);
            Point point = new Point(x, y);

            if (!map.containsKey(point) && !specialTiles.containsKey(point)) {
                addTile(point, new BuffTile(new IncreaseAttackBuff(10,10)));
                generatedIncreaseAttackBuffTiles++;
            }
        }

    }

    public Tile getSpecialTiles(Point point) {
        return specialTiles.get(point);
    }
}

