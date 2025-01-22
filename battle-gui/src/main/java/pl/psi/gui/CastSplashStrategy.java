package pl.psi.gui;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import pl.psi.*;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Objects;
import java.util.Optional;

public class CastSplashStrategy implements TileStrategy {
    private final GameEngine gameEngine;
    private final SharedState sharedState;

    public CastSplashStrategy(GameEngine aGameEngine, SharedState aSharedState) {
        this.gameEngine = aGameEngine;
        this.sharedState = aSharedState;
    }

    @Override
    public void apply(MapTile mapTile, Point point) {
        if (
                sharedState.getSelectedSpellIdx() > 0
        ) {
            SpellBook spellBook = gameEngine.getCurrentHero().getSpellBook();
            Spell splashSpell = spellBook.getSpells().get(sharedState.getSelectedSpellIdx());
            mapTile.setBackground(Color.LIGHTPINK);
            int radius = splashSpell.getRadius();
            mapTile.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> highlightRadius(point, true, radius));
            mapTile.addEventHandler(MouseEvent.MOUSE_EXITED, e -> highlightRadius(point, false, radius));

           mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                splashSpell.castSplashSpell(point, gameEngine);
                sharedState.resetSelectedSpellIdx();
                sharedState.refreshGui();
            });
        }
    }

    private void highlightRadius(Point center, boolean highlight, int radius) {
        int centerX = center.getX();
        int centerY = center.getY();
        for (int dx = centerX - radius; dx <= centerX + radius; dx++) {
            for (int dy = centerY - radius; dy <= centerY + radius; dy++) {
                Point targetPoint = new Point( centerX + dx, centerY + dy);
                MapTile theMapTile = this.getGridMapTile(sharedState.getGridPane(), targetPoint);
                if (theMapTile != null) {
                    theMapTile.setBackground(highlight ? Color.LIGHTBLUE : Color.BLUE);
                }
            }
        }
    }

    private MapTile getGridMapTile(GridPane aGridMap, Point aPoint) {
        if (aGridMap != null) {
            for (Node node : aGridMap.getChildren()) {
                if (GridPane.getColumnIndex(node) != null && GridPane.getRowIndex(node) != null &&
                        GridPane.getColumnIndex(node) == aPoint.getX() && GridPane.getRowIndex(node) == aPoint.getY()) {
                    return (MapTile) node;
                }
            }
        }
        return null;
    }
}
