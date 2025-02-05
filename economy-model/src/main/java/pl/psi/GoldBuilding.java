package pl.psi;

import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resources;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Random;

import static pl.psi.MapTileIf.TileType.GOLD_BUILDING;
import static pl.psi.resource.Resources.Type.GOLD;

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

    @Override
    public void Interact(EconomyHero hero) {
//        if (canCollectGold()) {
            int goldAmount = collectGold();
            hero.addResource(Resources.builder()
                    .resource(GOLD, goldAmount)
                    .build());

            hero.addResource(Resources.builder().resource(GOLD, goldAmount).build());

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
}
