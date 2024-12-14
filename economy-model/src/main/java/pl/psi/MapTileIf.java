package pl.psi;

import javafx.scene.paint.ImagePattern;

public interface MapTileIf {

    TileType getTileType();

    ImagePattern getImagePattern();

    enum TileType {
        ZAMEK,
        HERO;
    }
}
