package pl.psi;
import pl.psi.creatures.Creature;

public abstract class Tile {
    private final boolean passable;
    private final TileType type;

    public Tile(boolean passable, TileType type) {
        this.passable = passable;
        this.type = type;
    }

    public abstract void apply(Creature aCreature);
    public boolean isPassable() {
        return passable;
    }

    public TileType getType() {
        return type;
    }
}
