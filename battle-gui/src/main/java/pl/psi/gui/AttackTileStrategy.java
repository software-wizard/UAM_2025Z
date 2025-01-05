package pl.psi.gui;

import pl.psi.GameEngine;
import pl.psi.Point;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class AttackTileStrategy implements TileStrategy {
    private GameEngine gameEngine;

    public AttackTileStrategy(GameEngine aGameEngine) {
        this.gameEngine = aGameEngine;
    }

    @Override
    public void apply(MapTile mapTile, Point point) {
        if (gameEngine.canAttack(point)) {
            mapTile.setBackground(Color.RED);
            mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> gameEngine.attack(point));
        }
    }
}
