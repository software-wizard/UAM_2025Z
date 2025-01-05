package pl.psi;

import pl.psi.creatures.Creature;

public class ObstacleTile extends Tile {
    public ObstacleTile() {
        super(false, TileType.OBSTACLE);
    }

    public void apply(Creature aCreature){
    }
}
