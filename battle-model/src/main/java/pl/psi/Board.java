package pl.psi;

import java.util.*;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import org.checkerframework.common.returnsreceiver.qual.This;
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
    private final TileGenerationStrategy tileGenerationStrategy = new RandomTileGeneration(OBSTACLE_COUNT, DAMAGE_TILE_COUNT, INCREASE_ATTACK_BUFFTILE_COUNT);
    private Map<Point, Tile> specialTiles = new HashMap<>();


    public Board(List<Creature> aCreatures1, List<Creature> aCreatures2) {
        addCreatures(aCreatures1, 0);
        addCreatures(aCreatures2, MAX_WITDH);
        this.specialTiles = this.tileGenerationStrategy.generateSpecialTiles(MAX_WITDH, map);

    }

    public Board( final List< Creature > aCreatures1, final List< Creature > aCreatures2, final Map<Point, Tile> aSpecialTiles )
    {
        addCreatures( aCreatures1, 0 );
        addCreatures( aCreatures2, MAX_WITDH );
        this.specialTiles = aSpecialTiles;
    }


    public int getWidth() {
        return MAX_WITDH;
    }
    public int getHeight() {
        return MAX_WITDH;
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
            PathFindingAlg alg = new PathFindingAlg(this);
            Point startPoint = getPosition(aCreature);
            int creatureMoveRange = aCreature.getMoveRangeWithBonus();
            List<Point> path = alg.findPath(startPoint, aPoint, creatureMoveRange);

            //dla kazdefo punktu z path - kreatura musi przejsc przez kazdy punkt z listy:
            for (Point point : path)
            {
                map.inverse()
                        .remove( aCreature );
                map.put( point, aCreature );

            }

            Tile tile = specialTiles.get(aPoint);
            if(tile!=null)
            {
                tile.apply(aCreature);
            }

        }

        else
        {
            throw new IllegalArgumentException("Cannot move to this target: "+aPoint);
        }

    }



    public boolean canMove(final Creature aCreature, final Point aPoint) {
        if (map.containsKey(aPoint)) {
            return false;
        }
        if (specialTiles.containsKey(aPoint) && !specialTiles.get(aPoint).isPassable()) {
            return false;
        }
        /*final Point oldPosition = getPosition(aCreature);
        return aPoint.distance(oldPosition.getX(), oldPosition.getY()) < aCreature.getMoveRangeWithBonus();*/
        Point startPoint = getPosition(aCreature);
        PathFindingAlg alg = new PathFindingAlg(this);

        return alg.canReach(startPoint, aPoint, aCreature.getMoveRangeWithBonus());
    }



    public Point getPosition(Creature aCreature)
    {
        return map.inverse()
            .get( aCreature );
    }

    public void addTile(Point point, Tile tile) {
        specialTiles.put(point, tile);
    }

    public Tile getSpecialTile(Point point) {
        return specialTiles.get(point);
    }
}

