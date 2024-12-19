package pl.psi.hero;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.ImagePattern;
import pl.psi.MapTileIf;
import pl.psi.creatures.EconomyCreature;

public class EconomyHero implements PropertyChangeListener, MapTileIf
{
    private final Fraction fraction;
    private final List< EconomyCreature > creatureList;
    private int gold;

    public EconomyHero( final Fraction aFraction, final int aGold )
    {
        fraction = aFraction;
        gold = aGold;
        creatureList = new ArrayList<>();
    }

    public void addCreature( final EconomyCreature aCreature )
    {
        if( creatureList.size() >= 7 )
        {
            throw new IllegalStateException( "Hero has not empty slot for creature" );
        }
        creatureList.add( aCreature );
    }

    public int getGold()
    {
        return gold;
    }

    public void addGold( final int aAmount )
    {
        gold += aAmount;
    }

    public List< EconomyCreature > getCreatures()
    {
        return List.copyOf( creatureList );
    }

    void substractGold( final int aAmount )
    {
        if( aAmount > gold )
        {
            throw new IllegalStateException( "Hero has not enought money" );
        }
        gold -= aAmount;
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {

    }

    @Override
    public TileType getTileType() {
        return TileType.HERO;
    }

    @Override
    public ImagePattern getImagePattern() {
        return null;
    } //TODO Interface

    @Override
    public void Interact(EconomyHero hero) {//inny bohater wchodzi w interakcje z TYM bohaterem
        //TUTAJ moznaby dac Attack ?
       //tylko trzeba uzupelnic canInteract o interact z mapa bohaterow??
    }

    public enum Fraction
    {
        NECROPOLIS;
    }
}
