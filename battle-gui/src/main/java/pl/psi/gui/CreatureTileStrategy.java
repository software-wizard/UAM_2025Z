package pl.psi.gui;

import javafx.scene.paint.Color;
import pl.psi.GameEngine;
import pl.psi.Point;

public class CreatureTileStrategy implements TileStrategy {
    private GameEngine gameEngine;

    public CreatureTileStrategy(GameEngine aGameEngine) {
        this.gameEngine = aGameEngine;
    }

    @Override
    public void apply(MapTile aMapTile, Point aPoint) {
        if (gameEngine.isCurrentCreature(aPoint)) {
            aMapTile.setBackground(Color.GREEN);
        } else if (gameEngine.getCreature(aPoint).isPresent()) {
            aMapTile.setBackground(Color.BROWN);
        }
    }
}
