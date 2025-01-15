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
    private final BiMap< Point, Creature > map = HashBiMap.create();
    private final TileGenerationStrategy tileGenerationStrategy = new RandomTileGeneration(10, 10, 5);
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

    public Tile getSpecialTile(Point point) {
        return specialTiles.get(point);
    }
}

