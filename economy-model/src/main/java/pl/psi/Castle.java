package pl.psi;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import pl.psi.hero.EconomyHero;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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

    @Override
    public void Interact(EconomyHero hero) {

    }

}
