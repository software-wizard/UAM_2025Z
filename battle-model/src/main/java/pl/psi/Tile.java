package pl.psi;

public abstract class Tile {
    private final Point point;
    private final boolean passable;

    public Tile(Point point, boolean passable) {
        this.point = point;
        this.passable = passable;
    }

    public boolean isPassable() {
        return passable;
    }

}
