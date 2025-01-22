package pl.psi;

import pl.psi.creatures.Buff;
import pl.psi.creatures.Creature;

import java.util.Objects;

public class BuffTile extends Tile {
    private final Buff buff;

    public BuffTile(Buff buff) {
        super(true, TileType.INCREASE_ATTACK_BUFF);
        this.buff = buff;
    }
    public void apply(Creature aCreature){
        aCreature.addBuff(buff);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BuffTile other = (BuffTile) obj;
        System.out.println("Comparing Buffs: " + buff + " vs " + other.buff);
        System.out.println("HashCodes: " + buff.hashCode() + " vs " + other.buff.hashCode());
        return buff.equals(other.buff) && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buff, super.hashCode());
    }

}
