package pl.psi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.HashBiMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.psi.creatures.Creature;
import pl.psi.creatures.IncreaseAttackBuff;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class JsonTileGenerationTest {
    private static final String TEST_JSON_FILE = "test_tiles.json";

    @BeforeEach
    void setUp() throws IOException {
        // Create a test JSON file with sample tile definitions
        String jsonContent = """
                [
                  {"x": 1, "y": 2, "type": "Obstacle"},
                  {"x": 3, "y": 4, "type": "Damage", "value": 10}
                ]
                """;
        Files.writeString(Path.of(TEST_JSON_FILE), jsonContent);
    }

    @Test
    void shouldGenerateSpecialTilesCorrectly() {
        JsonTileGeneration jsonTileGeneration = new JsonTileGeneration(TEST_JSON_FILE);
        HashBiMap<Point, Creature> creatures = HashBiMap.create();

        HashMap<Point, Tile> specialTiles = jsonTileGeneration.generateSpecialTiles(10, creatures);

        HashMap<Point, Tile> expectedSpecialTiles = new HashMap<>();
        expectedSpecialTiles.put(new Point(1,2), new ObstacleTile());
        expectedSpecialTiles.put(new Point(3,4), new DamageTile(10));

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
        // Given
        JsonTileGeneration jsonTileGeneration = new JsonTileGeneration("non_existent_file.json");
        HashBiMap<Point, Creature> creatureMap = HashBiMap.create();

        // When / Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> jsonTileGeneration.generateSpecialTiles(10, creatureMap));
        assertTrue(exception.getMessage().contains("Failed to load tiles from JSON file"));
    }
}
