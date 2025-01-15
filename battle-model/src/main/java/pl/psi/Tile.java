package pl.psi;
import pl.psi.creatures.Creature;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tile other = (Tile) obj;
        return passable == other.passable && type == other.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(passable, type);
    }
}
