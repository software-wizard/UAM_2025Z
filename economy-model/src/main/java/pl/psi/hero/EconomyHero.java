package pl.psi.hero;

import lombok.Getter;
import pl.psi.creatures.EconomyCreature;
import pl.psi.resource.Resources;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.ImagePattern;
import pl.psi.MapTileIf;
import pl.psi.creatures.EconomyCreature;


@Getter
public class EconomyHero implements PropertyChangeListener, MapTileIf
{
    private final Fraction fraction;
    private final List< EconomyCreature > creatureList;
    private final Resources resources;

    public EconomyHero( final Fraction aFraction, final Resources aResources )
    {
        fraction = aFraction;
        this.resources = aResources;
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

    public Integer getResourceAmount(Resources.ResourceType resourceType) {
        return resources.getResourceAmount(resourceType);
    }

    public void addResource(final Resources aResources ) {
        resources.add(aResources);
    }

    public List< EconomyCreature > getCreatures()
    {
        return List.copyOf( creatureList );
    }

    public void subtractResource(final Resources aResources) {
        resources.subtract(aResources);
    }

    public boolean canAfford(Resources prerequisites) {
        return resources.canAfford(prerequisites);
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
        NECROPOLIS
    }
}
