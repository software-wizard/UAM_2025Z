package pl.psi;

public class ObstacleTile extends Tile {
    public ObstacleTile(Point point) {
        super(point, false);  // ObstacleTile zawsze jest nieprzechodnie
    }
}
