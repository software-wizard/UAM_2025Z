package pl.psi;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import pl.psi.hero.EconomyHero;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static pl.psi.MapTileIf.TileType.GOLD_BUILDING;

public class GoldBuilding implements MapTileIf{
    Boolean collectedGold;

    GoldBuilding(){
        collectedGold = false;
    }
    @Override
    public TileType getTileType() {
        return GOLD_BUILDING;
    }

    @Override
    public ImagePattern getImagePattern() {
        File goldBuilding = new File("economy-gui/src/main/resources/AVTgold0.png");
        FileInputStream input = null;
        try {
            input = new FileInputStream(goldBuilding);
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new ImagePattern(new Image(input));
    }
    @Override
    public void Interact(EconomyHero hero){
        if(canCollectGold()){
            int goldAmount = collectGold();
            hero.addGold(goldAmount);
            collectedGold = true;
        }
    }
    private int collectGold(){//zwraca losowa ilosc gold
        Random rand = new Random();
        int goldAmount = (rand.nextInt(6)+5)*100;
        return goldAmount;
    }
    private Boolean canCollectGold(){
        return !collectedGold;
    }
}
