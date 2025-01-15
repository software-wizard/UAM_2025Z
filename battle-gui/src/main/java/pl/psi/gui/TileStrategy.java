package pl.psi.gui;

import pl.psi.Point;

public interface TileStrategy {
    void apply(MapTile aMapTile, Point aPoint);
}
