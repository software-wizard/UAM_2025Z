package pl.psi.gui;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pl.psi.GameEngine;
import pl.psi.Point;
import pl.psi.Tile;

public class TileTypeStrategy implements TileStrategy {
    private GameEngine gameEngine;

    public TileTypeStrategy(GameEngine aGameEngine) {
        this.gameEngine = aGameEngine;
    }

    @Override
    public void apply(MapTile aMapTile, Point aPoint) {
        Tile tile = gameEngine.getTile(aPoint);
        boolean canMoveOnTile = gameEngine.canMove(aPoint);

        if (tile != null) {
            switch (tile.getType()) {
                case OBSTACLE -> aMapTile.setBackground(Color.BLACK);
                case DAMAGE -> aMapTile.setBackground(canMoveOnTile ? Color.DARKORANGE : Color.ORANGE);
                case BUFF -> aMapTile.setBackground(canMoveOnTile ? Color.DARKBLUE : Color.BLUE);
            }
        }
    }

}
