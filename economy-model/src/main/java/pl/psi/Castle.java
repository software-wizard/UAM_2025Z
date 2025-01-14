package pl.psi;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import lombok.Getter;
import pl.psi.hero.EconomyHero;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static pl.psi.MapTileIf.TileType.ZAMEK;

public class Castle implements MapTileIf{
    private final PropertyChangeSupport observerSupport = new PropertyChangeSupport(this);
    public static final String OPEN_SHOP= "open_shop";

    private static final String imagePath = "economy-gui/src/main/resources/AVXhilg0.png";

    @Override
    public TileType getTileType() {
        return ZAMEK;
    }
    @Override
    public String getImagePath(){
        return imagePath;
    }
//
//    @Override
//    public ImagePattern getImagePattern() {
//        File castle = new File("economy-gui/src/main/resources/AVXhilg0.png");
//        FileInputStream input = null;
//        try {
//            input = new FileInputStream(castle);
//        } catch (
//                FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        return new ImagePattern(new Image(input));
//    }

    public void addObserver(PropertyChangeListener aObserver) {
        observerSupport.addPropertyChangeListener(aObserver);
    }
    @Override
    public void Interact(EconomyHero hero) {
    observerSupport.firePropertyChange(OPEN_SHOP,null,null);
    }

}
