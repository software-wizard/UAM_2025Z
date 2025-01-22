package pl.psi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class EditorEngineTest {

    @Test
    void shouldCreateCorrectNumberOfTiles() {
        EditorEngine engine = new EditorEngine(15, 10);
        engine.initializeMap();
        List<Tile> tiles = engine.getTiles();
        assertNotNull(tiles);
        assertEquals(150, tiles.size());
    }

    @Test
    public void shouldSaveListOfMapTilesToJsonProperly() throws IOException {
        EditorEngine engine = new EditorEngine(15, 10);
        engine.initializeMap();
        engine.getTiles().set(0, new Tile(TileType.OBSTACLE, 0, 0));
        engine.getTiles().set(1, new Tile(TileType.DAMAGE, 0, 1));
        engine.getTiles().set(2, new Tile(TileType.OBSTACLE, 0, 2));

        engine.saveSpecialTilesToFile("test.json");

        File jsonFile = new File("test.json");
        ObjectMapper objectMapper = new ObjectMapper();
        Object jsonObject = objectMapper.readValue(jsonFile, Object.class);
        String jsonString = objectMapper.writeValueAsString(jsonObject);

        String expected = """
                [ 
                { "x" : 0, "y" : 0, "type" : "OBSTACLE"}, 
                { "x" : 0, "y" : 1, "type" : "DAMAGE", "value" : 0},
                { "x" : 0, "y" : 2, "type" : "OBSTACLE"} 
                ]
                """;
        expected = expected.replaceAll("\\s+","");
        assertEquals(expected, jsonString);
    }

}
