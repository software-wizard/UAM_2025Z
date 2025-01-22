package pl.psi;

import com.google.common.collect.BiMap;
import lombok.Getter;
import lombok.Setter;
import pl.psi.creatures.Creature;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.psi.creatures.IncreaseAttackBuff;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class JsonTileGeneration implements TileGenerationStrategy {

    private final String jsonFilePath;

    public JsonTileGeneration(String jsonFilePath) {
        this.jsonFilePath = jsonFilePath;
    }

    @Override
    public HashMap<Point, Tile> generateSpecialTiles(int maxWidth, BiMap<Point, Creature> creatureMap) {
        HashMap<Point, Tile> specialTiles = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try{
            List<TileDefinition> tileDefinitions = objectMapper.readValue(
                    new File(jsonFilePath),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, TileDefinition.class)
            );
            for(TileDefinition tileDef: tileDefinitions ){
                Point point = new Point(tileDef.getX(), tileDef.getY());
                if (creatureMap.containsKey(point) || specialTiles.containsKey(point)) {
                    throw new IllegalStateException("Conflict detected at point: " + point);
                }

                switch (tileDef.getType()) {
                    case "OBSTACLE":
                        specialTiles.put(point, new ObstacleTile());
                        break;
                    case "DAMAGE":
                        specialTiles.put(point, new DamageTile(tileDef.getValue()));
                        break;
                    case "INCREASE_ATTACK_BUFF":
                        specialTiles.put(point, new BuffTile(new IncreaseAttackBuff(tileDef.getDuration(), tileDef.getValue() )));
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown tile type: " + tileDef.getType());
                }
            }
        }catch (IOException e) {
            throw new RuntimeException("Failed to load tiles from JSON file: " + jsonFilePath, e);
        }
        return specialTiles;
    }

    @Setter
    @Getter
    private static class TileDefinition {
        private int x;
        private int y;
        private String type;
        private int value;
        private int duration;

    }
}
