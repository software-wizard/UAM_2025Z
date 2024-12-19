package pl.psi;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import pl.psi.creatures.CreatureStatistic;
import pl.psi.creatures.EconomyCreature;
import pl.psi.creatures.EconomyNecropolisFactory;
import pl.psi.hero.EconomyHero;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

import static pl.psi.MapTileIf.TileType.GOLD_BUILDING;
import static pl.psi.MapTileIf.TileType.NECROPOLIS_COMBAT_BUILDING;

public class NecropolisCombatBuilding implements MapTileIf{

    @Override
    public TileType getTileType() {
        return NECROPOLIS_COMBAT_BUILDING;
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

    }
    public EconomyHero createBattleOpponent(){
        EconomyHero opponent = new EconomyHero(EconomyHero.Fraction.NECROPOLIS,0);
        Random rand = new Random();
        int aAmount = rand.nextInt(3)+3;

        final EconomyNecropolisFactory factory = new EconomyNecropolisFactory();
        opponent.addCreature( factory.create( false, 1, aAmount) );
        //inne rzeczy
        return opponent;
    }
}
