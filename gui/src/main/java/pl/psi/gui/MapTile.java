package pl.psi.gui;


import javafx.scene.shape.Rectangle;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;

public class MapTile extends Rectangle {
    MapTile() {
        super(60, 60);
        setFill(WHITE);
        setStroke(BLACK);
    }
}
