package pl.psi;

import com.google.common.collect.BiMap;
import pl.psi.creatures.Creature;

import java.util.HashMap;

public interface TileGenerationStrategy {
    public HashMap<Point, Tile> generateSpecialTiles(int maxWidth, BiMap<Point, Creature> creatureMap);
}
