package pl.psi.gui;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import pl.psi.GameEngine;
import pl.psi.Point;
import pl.psi.creatures.Creature;
import pl.psi.creatures.NecropolisFactory;

public class CreatureTileStrategy implements TileStrategy {
    private GameEngine gameEngine;

    public CreatureTileStrategy(GameEngine aGameEngine) {
        this.gameEngine = aGameEngine;
    }

    @Override
    public void apply(MapTile aMapTile, Point aPoint) {

        if (gameEngine.isCurrentCreature(aPoint)) {

            aMapTile.setBackground(Color.GREEN);
            Creature creature = gameEngine.getCreature(aPoint).get();
            Image creatureIcon = NecropolisFactory.getCreatureImage(creature.getStats().getName());

            aMapTile.setImage(creatureIcon);

        } else if (gameEngine.getCreature(aPoint).isPresent()) {

            aMapTile.setBackground(Color.BROWN);
            Creature creature = gameEngine.getCreature(aPoint).get();
            Image creatureIcon = NecropolisFactory.getCreatureImage(creature.getStats().getName());

            aMapTile.setImage(creatureIcon);


        }
    }
}
