package pl.psi.gui;

import pl.psi.GameEngine;
import pl.psi.Point;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class MoveTileStrategy implements TileStrategy {
    private GameEngine gameEngine;
    private SharedState sharedState;

    public MoveTileStrategy(GameEngine aGameEngine, SharedState aSharedState) {
        this.gameEngine = aGameEngine;
        this.sharedState = aSharedState;
    }

    @Override
    public void apply(MapTile mapTile, Point point) {
        if (
                gameEngine.canMove(point)
                && sharedState.getSelectedSpellIdx() == -1
        ) {
            mapTile.setBackground(Color.GREY);
            mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                sharedState.setSelectedSpellIdx(-1);
                gameEngine.move(point);
                sharedState.refreshGui();
            });
        }
    }
}
