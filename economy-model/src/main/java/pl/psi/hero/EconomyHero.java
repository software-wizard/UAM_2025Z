package pl.psi.hero;

import lombok.Getter;
import pl.psi.creatures.EconomyCreature;
import pl.psi.resource.Resources;

import java.util.ArrayList;
import java.util.List;

@Getter
public class EconomyHero
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

    void addCreature( final EconomyCreature aCreature )
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

    public enum Fraction
    {
        NECROPOLIS
    }
}
