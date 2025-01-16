package pl.psi.gui;

import pl.psi.GameEngine;
import pl.psi.Point;

import java.util.ArrayList;
import java.util.List;

public class ColorTile {
    private List<TileStrategy> strategies = new ArrayList<>();

    public ColorTile(GameEngine aGameEngine, SharedState aSharedState) {
        this.addStrategy(new CreatureTileStrategy(aGameEngine));
        this.addStrategy(new MoveTileStrategy(aGameEngine, aSharedState));
        this.addStrategy(new AttackTileStrategy(aGameEngine));
        this.addStrategy(new TileTypeStrategy(aGameEngine));
        this.addStrategy(new CastTileStrategy(aGameEngine, aSharedState));
    }

    public void addStrategy(TileStrategy aStrategy) {
        strategies.add(aStrategy);
    }
    public void applyStrategies(MapTile aMapTile, Point aPoint) {
        for (TileStrategy strategy : strategies) {
            strategy.apply(aMapTile, aPoint);
        }
    }
}
