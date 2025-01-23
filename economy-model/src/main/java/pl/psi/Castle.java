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

import static pl.psi.MapTileIf.TileType.CASTLE;

public class Castle implements MapTileIf{
    private final PropertyChangeSupport observerSupport = new PropertyChangeSupport(this);
    public static final String OPEN_SHOP= "open_shop";
    private static final String imagePath = "economy-gui/src/main/resources/AVXhilg0.png";

    @Override
    public TileType getTileType() {
        return CASTLE;
    }
    @Override
    public String getImagePath(){
        return imagePath;
    }

    public void addObserver(PropertyChangeListener aObserver) {
        observerSupport.addPropertyChangeListener(aObserver);
    }
    @Override
    public void Interact(EconomyHero hero) {
    observerSupport.firePropertyChange(OPEN_SHOP,null,null);
    }

}
