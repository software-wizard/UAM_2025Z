package pl.psi;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import pl.psi.creatures.EconomyNecropolisFactory;
import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resources;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static pl.psi.MapTileIf.TileType.NECROPOLIS_COMBAT_BUILDING;

public class NecropolisCombatBuilding implements MapTileIf {
    private final PropertyChangeSupport observerSupport = new PropertyChangeSupport(this);
    public String START_NECROPOLIS_COMBAT="start_necropolis_combat";
    @Override
    public TileType getTileType() {
        return NECROPOLIS_COMBAT_BUILDING;
    }
    public void addObserver(PropertyChangeListener aObserver) {
        observerSupport.addPropertyChangeListener(aObserver);
    }

    @Override
    public ImagePattern getImagePattern() {
        File goldBuilding = new File("economy-gui/src/main/resources/AVXbnk30.png");
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
    public void Interact(EconomyHero hero) {
        observerSupport.firePropertyChange(START_NECROPOLIS_COMBAT,null,null);
    }

    public EconomyHero createBattleOpponent() {
        EconomyHero opponent = new EconomyHero(EconomyHero.Fraction.NECROPOLIS, new Resources(new HashMap<>()));
        Random rand = new Random();
        int aAmount = rand.nextInt(3) + 3;

        final EconomyNecropolisFactory factory = new EconomyNecropolisFactory();
        opponent.addCreature(factory.create(false, 1, aAmount));
        //inne rzeczy
        return opponent;
    }
}
