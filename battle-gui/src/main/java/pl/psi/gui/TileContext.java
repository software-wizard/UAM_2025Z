package pl.psi.gui;

import pl.psi.Point;

import java.util.ArrayList;
import java.util.List;

public class TileContext {
    private List<TileStrategy> strategies;

    public TileContext() {
        this.strategies = new ArrayList<>();
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
