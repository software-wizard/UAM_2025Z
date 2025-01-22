package pl.psi;

import com.google.common.collect.HashBiMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.psi.creatures.Creature;
import pl.psi.creatures.IncreaseAttackBuff;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


public class JsonTileGenerationTest {
    private static final String TEST_JSON_FILE = "test_tiles.json";

    @BeforeEach
    void setUp() throws IOException {
        String jsonContent = """
                [
                  {"x": 1, "y": 2, "type": "OBSTACLE"},
                  {"x": 3, "y": 4, "type": "DAMAGE", "value": 10},
                  {"x": 5, "y": 8, "type": "INCREASE_ATTACK_BUFF", "value": 10, "duration": 5}
                ]
                """;
        Files.writeString(Path.of(TEST_JSON_FILE), jsonContent);
    }

    @Test
    void shouldGenerateSpecialTilesCorrectly() {
        JsonTileGeneration jsonTileGeneration = new JsonTileGeneration(TEST_JSON_FILE);

        HashMap<Point, Tile> specialTiles = jsonTileGeneration.generateSpecialTiles(10, HashBiMap.create());

        HashMap<Point, Tile> expectedSpecialTiles = new HashMap<>();
        expectedSpecialTiles.put(new Point(1,2), new ObstacleTile());
        expectedSpecialTiles.put(new Point(3,4), new DamageTile(10));
        expectedSpecialTiles.put(new Point(5,8), new BuffTile(new IncreaseAttackBuff(5,10)));

        assertNotNull(specialTiles);
        assertEquals(expectedSpecialTiles, specialTiles);
    }

    @Test
    void shouldThrowExceptionForUnknownTileType() throws IOException {

        String invalidJsonContent = """
                [
                  {"x": 1, "y": 2, "type": "UnknownType"}
                ]
                """;
        Files.writeString(Path.of(TEST_JSON_FILE), invalidJsonContent);

        JsonTileGeneration jsonTileGeneration = new JsonTileGeneration(TEST_JSON_FILE);
        HashBiMap<Point, Creature> creatureMap = HashBiMap.create();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> jsonTileGeneration.generateSpecialTiles(10, creatureMap));
        assertTrue(exception.getMessage().contains("Unknown tile type"));
    }

    @Test
    void shouldThrowRuntimeExceptionForInvalidJsonFile() {
        JsonTileGeneration jsonTileGeneration = new JsonTileGeneration("non_existent_file.json");
        HashBiMap<Point, Creature> creatureMap = HashBiMap.create();

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> jsonTileGeneration.generateSpecialTiles(10, creatureMap));
        assertTrue(exception.getMessage().contains("Failed to load tiles from JSON file"));
    }
}
