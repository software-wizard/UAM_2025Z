package pl.psi;

import lombok.Getter;
import pl.psi.creatures.Creature;

import java.util.Objects;

@Getter
public class DamageTile extends Tile{
    final private int givenDamage;

    public DamageTile(int aGivenDamage) {
        super(true, TileType.DAMAGE);
        this.givenDamage = aGivenDamage;
    }

    public void apply(Creature aCreature){
        aCreature.takeDamage(this.getGivenDamage());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DamageTile other = (DamageTile) obj;
        return givenDamage == other.givenDamage && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(givenDamage, super.hashCode());
    }

}
