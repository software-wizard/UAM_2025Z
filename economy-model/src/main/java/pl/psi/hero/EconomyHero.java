package pl.psi.hero;

import javafx.scene.paint.ImagePattern;
import lombok.Getter;
import pl.psi.MapTileIf;
import pl.psi.building.town.Town;
import pl.psi.creatures.EconomyCreature;
import pl.psi.resource.Resources;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

@Getter
public class EconomyHero implements PropertyChangeListener
{
    private final String name;
    private final Fraction fraction;
    private final Town town;
    private final Map<String, EconomyCreature> creatures;
    private final Resources resources;
    private final PropertyChangeSupport observerSupport = new PropertyChangeSupport(this);

    public EconomyHero(final String aName, final Fraction aFraction, final Resources aResources, final Town aTown)
    {
        this.town = aTown;
        this.name = aName;
        fraction = aFraction;
        this.resources = aResources;
        creatures = new HashMap<>();
    }

    public void addCreature( final EconomyCreature aCreature ) {
        EconomyCreature creature = aCreature;
        if (creatures.containsValue(aCreature)) {
            EconomyCreature existingCreature = creatures.get(aCreature.getName());
            creature = EconomyCreature.merge(aCreature, existingCreature);
        }
        if( creatures.size() >= 7) {
            throw new IllegalStateException( "Hero has not empty slot for creature");
        }
        creatures.put(creature.getName(), creature);
    }

    public Integer getResourceAmount(Resources.Type resourceType) {
        return resources.getResourceAmount(resourceType);
    }

    public void addResource(final Resources aResources ) {
        resources.add(aResources);
    }

    public Set< EconomyCreature > getCreatures()
    {
        return Set.copyOf(creatures.values());
    }

    public void subtractResource(final Resources aResources) {
        resources.subtract(aResources);
    }

    public boolean canAfford(Resources prerequisites) {
        return resources.canAfford(prerequisites);
    }

    /**
     * Calculates how many creatures can be purchased given the available resources.
     *
     * @param cost the cost of a single purchase
     * @return the maximum number of purchases that can be processing
     */
    public int calculateMaxCreatures(Resources cost) {
        int maxCreatures = Integer.MAX_VALUE;
        for (Resources.Type type : EnumSet.allOf(Resources.Type.class)) {
            int costPerUnit = cost.getResourceAmount(type);
            if (costPerUnit > 0) {
                int availableAmount = resources.getResourceAmount(type);
                maxCreatures = Math.min(maxCreatures, availableAmount / costPerUnit);
            }
        }
        return maxCreatures;
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {

    }

    public MapTileIf.TileType getTileType() {
        return MapTileIf.TileType.HERO;
    }

    public ImagePattern getImagePattern() {
        return null;
    } //TODO Interface

    public void Interact(EconomyHero hero) {//inny bohater wchodzi w interakcje z TYM bohaterem
        //TUTAJ moznaby dac Attack ?
       //tylko trzeba uzupelnic canInteract o interact z mapa bohaterow??
    }

    public enum Fraction
    {
        NECROPOLIS
    }

    @Override
    public String toString() {
        return name;
    }
}
