package pl.psi;

import pl.psi.creatures.Creature;

import java.util.Objects;

public class ObstacleTile extends Tile {
    public ObstacleTile() {
        super(false, TileType.OBSTACLE);
    }

    public void apply(Creature aCreature){
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass());
    }
}
