package pl.psi;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static pl.psi.MapTileIf.TileType.ZAMEK;

public class Castle implements MapTileIf{


    @Override
    public TileType getTileType() {
        return ZAMEK;
    }

    @Override
    public ImagePattern getImagePattern() {
        File castle = new File("economy-gui/src/main/resources/AVXhilg0.png");
        FileInputStream input = null;
        try {
            input = new FileInputStream(castle);
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new ImagePattern(new Image(input));
    }
}
