package pl.psi;

import com.google.common.collect.BiMap;
import pl.psi.creatures.Creature;
import pl.psi.creatures.IncreaseAttackBuff;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomTileGeneration implements TileGenerationStrategy{
    private final int obstacleCount;
    private final int damageTileCount;
    private final int buffTileCount;

    public RandomTileGeneration(int obstacleCount, int damageTileCount, int buffTileCount) {
        this.obstacleCount = obstacleCount;
        this.damageTileCount = damageTileCount;
        this.buffTileCount = buffTileCount;
    }
    public HashMap<Point, Tile> generateSpecialTiles(int maxWidth, BiMap<Point, Creature> creatureMap){
        HashMap<Point, Tile> specialTiles = new HashMap<>();
        Random random = new Random();

        // Generate obstacles
        for (int i = 0; i < obstacleCount; i++) {
            Point point = generateUniquePoint(maxWidth, specialTiles, creatureMap, random);
            specialTiles.put(point, new ObstacleTile());
        }

        // Generate damage tiles
        for (int i = 0; i < damageTileCount; i++) {
            Point point = generateUniquePoint(maxWidth, specialTiles, creatureMap, random);
            specialTiles.put(point, new DamageTile(20));
        }

        // Generate buff tiles
        for (int i = 0; i < buffTileCount; i++) {
            Point point = generateUniquePoint(maxWidth, specialTiles, creatureMap, random);
            specialTiles.put(point, new BuffTile(new IncreaseAttackBuff(10, 10)));
        }
        return specialTiles;
    }

    private Point generateUniquePoint(int maxWidth, Map<Point, Tile> specialTiles, BiMap<Point, Creature> creatureMap, Random random) {
        Point point;
        do {
            int x = random.nextInt(maxWidth);
            int y = random.nextInt(maxWidth);
            point = new Point(x, y);
        } while (creatureMap.containsKey(point) || specialTiles.containsKey(point));
        return point;
    }
}
