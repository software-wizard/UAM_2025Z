package pl.psi;

import pl.psi.creatures.Buff;
import pl.psi.creatures.Creature;

public class BuffTile extends Tile {
    private final Buff buff;

    public BuffTile(Buff buff) {
        super(true, TileType.BUFF);
        this.buff = buff;
    }
    public void apply(Creature aCreature){
        aCreature.addBuff(buff);
    }

}
