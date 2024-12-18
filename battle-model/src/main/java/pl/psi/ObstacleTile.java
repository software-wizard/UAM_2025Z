package pl.psi;

import pl.psi.creatures.Creature;

public class ObstacleTile extends Tile {
    public ObstacleTile(Point point) {
        super(point, false);  // ObstacleTile zawsze jest nieprzechodnie
    }

    public void apply(Creature aCreature){
    }
}
