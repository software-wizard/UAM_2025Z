package pl.psi;
import pl.psi.creatures.Creature;

public abstract class Tile {
    private final Point point;
    private final boolean passable;

    public Tile(Point point, boolean passable) {
        this.point = point;
        this.passable = passable;
    }

    public abstract void apply(Creature aCreature);
    public boolean isPassable() {
        return passable;
    }

}
