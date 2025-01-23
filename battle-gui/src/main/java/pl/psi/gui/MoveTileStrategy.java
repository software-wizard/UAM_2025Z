package pl.psi.gui;

import pl.psi.GameEngine;
import pl.psi.Point;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class MoveTileStrategy implements TileStrategy {
    private GameEngine gameEngine;
    private SharedState sharedState;
    private TileContext tileContext;

    private List<Point> currentPath;

    public MoveTileStrategy(GameEngine aGameEngine, SharedState aSharedState, TileContext aTileContext) {
        this.gameEngine = aGameEngine;
        this.sharedState = aSharedState;
        this.tileContext = aTileContext;
        this.currentPath = null;
    }

    @Override
    public void apply(MapTile mapTile, Point point)
    {
        if (sharedState.getSelectedSpellIdx() == -1)
        {
            mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (currentPath == null)
                {
                    if (gameEngine.canMove(point)) {

                        currentPath = gameEngine.getPath(point);
                        //sharedState.refreshGui();
                        showPathOnScreen(currentPath, Color.GREY);
                    }
                }
                else
                {
                    if (currentPath.contains(point))
                    {
                        gameEngine.move(point);
                        sharedState.setSelectedSpellIdx(-1);
                        sharedState.refreshGui();
                    }
                    showPathOnScreen(currentPath, Color.WHITE);
                    currentPath = null;
                }

            });
        }
    }

    private void showPathOnScreen(List<Point>path, Color color)
    {
        //path.removeFirst();
        for (Point p : path)
        {
            MapTile tile = tileContext.getTile(p);
            if (tile != null)
            {
                if(!gameEngine.isOccupied(p)) {
                    tile.setBackground(color);
                }
            }
            else {
                System.out.println("No tile found for point: " + p);
            }
        }

    }
}
