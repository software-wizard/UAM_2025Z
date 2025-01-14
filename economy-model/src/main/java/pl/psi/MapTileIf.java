package pl.psi;

import javafx.scene.paint.ImagePattern;
import pl.psi.hero.EconomyHero;

import java.beans.PropertyChangeListener;

public interface MapTileIf {

    void addObserver(PropertyChangeListener aObserver);
    TileType getTileType();

    ImagePattern getImagePattern();

    void Interact(EconomyHero hero);
    enum TileType {
        GOLD_BUILDING,
        ZAMEK,
        HERO,
        NECROPOLIS_COMBAT_BUILDING;
    }
}
