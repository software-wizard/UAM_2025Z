package pl.psi.gui;

import pl.psi.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileContext {
    private List<TileStrategy> strategies;
    private Map<Point, MapTile> tilesMap;

    public TileContext()
    {
        this.strategies = new ArrayList<>();
        this.tilesMap = new HashMap<>();
    }

    public void addStrategy(TileStrategy aStrategy) {
        strategies.add(aStrategy);
    }

    public void applyStrategies(MapTile aMapTile, Point aPoint) {
        for (TileStrategy strategy : strategies) {
            strategy.apply(aMapTile, aPoint);
        }
        tilesMap.put(aPoint, aMapTile);
    }


    public MapTile getTile(Point aPoint) {
        return tilesMap.get(aPoint);
    }

    public void clearTiles() {
        tilesMap.clear();
    }
}
