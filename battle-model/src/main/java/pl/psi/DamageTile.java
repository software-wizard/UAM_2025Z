package pl.psi;

import pl.psi.creatures.Creature;

public class DamageTile extends Tile{
    final private int givenDamage;

    public DamageTile(Point point, int aGivenDamage) {
        super(point, true);
        this.givenDamage = aGivenDamage;
    }

    public void apply(Creature aCreature){
        aCreature.takeDamage(this.getGivenDamage());
    }
    public int getGivenDamage() {
        return givenDamage;
    }
}
