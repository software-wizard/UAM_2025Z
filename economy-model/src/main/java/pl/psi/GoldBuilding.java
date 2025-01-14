package pl.psi;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resources;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Random;

import static pl.psi.MapTileIf.TileType.GOLD_BUILDING;
import static pl.psi.resource.Resources.ResourceType.GOLD;

public class GoldBuilding implements MapTileIf {
    private final PropertyChangeSupport observerSupport = new PropertyChangeSupport(this);
    public String COLLECT_GOLD = "collect_gold";

    private static final String imagePath = "economy-gui/src/main/resources/AVTgold0.png";


    public void addObserver(PropertyChangeListener aObserver) {
        observerSupport.addPropertyChangeListener(aObserver);
    }
    // Boolean collectedGold;

//    GoldBuilding() {
//        collectedGold = false;
//    }
    //ZAMIAST TEGO USUNIECIE BUDYNKU Z LISTY

    @Override
    public TileType getTileType() {
        return GOLD_BUILDING;
    }
    @Override
    public String getImagePath(){
        return imagePath;
    }
//
//    @Override
//    public ImagePattern getImagePattern() {
//        File goldBuilding = new File("economy-gui/src/main/resources/AVTgold0.png");
//        FileInputStream input = null;
//        try {
//            input = new FileInputStream(goldBuilding);
//        } catch (
//                FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        return new ImagePattern(new Image(input));
//    }

    @Override
    public void Interact(EconomyHero hero) {
//        if (canCollectGold()) {
            int goldAmount = collectGold();
            hero.addResource(new Resources(Map.of(GOLD, goldAmount)));

            observerSupport.firePropertyChange(COLLECT_GOLD,null,goldAmount);
            //usuwanie zlota z board
            //collectedGold = true;

            //usunac na mapie budynek
//        }
    }

    private int collectGold() {//zwraca losowa ilosc gold
        Random rand = new Random();
        int goldAmount = (rand.nextInt(6) + 5) * 100;
        return goldAmount;
    }

//    private Boolean canCollectGold() {
//        return !collectedGold;
//    }
}
