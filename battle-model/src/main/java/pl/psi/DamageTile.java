package pl.psi;

public class DamageTile extends Tile{
    final private int givenDamage;

    public DamageTile(Point point, int aGivenDamage) {
        super(point, true);
        this.givenDamage = aGivenDamage;
    }

    public int getGivenDamage() {
        return givenDamage;
    }
}
