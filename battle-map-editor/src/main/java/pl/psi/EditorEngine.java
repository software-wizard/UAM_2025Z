package pl.psi;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditorEngine {

    @Getter
    private final List<Tile> tiles = new ArrayList<>();

    final int width;
    final int height;

    EditorEngine(int aWidth, int aHeight){
        this.width = aWidth;
        this.height = aHeight;
    }

    public void initializeMap() {
        tiles.clear();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles.add(new Tile(TileType.DEFAULT, x, y));
            }
        }
    }

    public void saveSpecialTilesToFile(String filePath) throws IOException {
        List<Object> specialTiles = new ArrayList<>();
        for (Tile tile : tiles) {
            if (tile.getTileType() == TileType.OBSTACLE) {
                ObstacleTileData data = new ObstacleTileData(tile.getX(), tile.getY(), tile.getTileType().name());
                specialTiles.add(data);
            } else if (tile.getTileType() == TileType.INCREASE_ATTACK_BUFF) {
                BuffTileData data = new BuffTileData(tile.getX(), tile.getY(), tile.getTileType().name(), tile.getValue(), tile.getDuration());
                specialTiles.add(data);
            } else if (tile.getTileType() == TileType.DAMAGE) {
                DamageTileData data = new DamageTileData(tile.getX(), tile.getY(), tile.getTileType().name(), tile.getValue());
                specialTiles.add(data);
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), specialTiles);
    }

    @Getter
    @Setter
    public static class ObstacleTileData {
        private int x;
        private int y;
        private String type;

        public ObstacleTileData(int x, int y, String type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }

    }

    @Getter
    @Setter
    public static class BuffTileData {
        private int x;
        private int y;
        private String type;
        private int value;
        private int duration;

        public BuffTileData(int x, int y, String type, int value, int duration) {
            this.x = x;
            this.y = y;
            this.type = type;
            this.value = value;
            this.duration = duration;
        }

    }

    @Getter
    @Setter
    public static class DamageTileData {
        private int x;
        private int y;
        private String type;
        private int value;

        public DamageTileData(int x, int y, String type, int value) {
            this.x = x;
            this.y = y;
            this.type = type;
            this.value = value;
        }
    }

}
