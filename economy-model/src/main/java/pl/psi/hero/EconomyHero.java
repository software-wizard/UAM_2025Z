package pl.psi.hero;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import pl.psi.creatures.EconomyCreature;
import pl.psi.resource.Resource;

@Getter
public class EconomyHero
{

    private final Fraction fraction;
    private final List< EconomyCreature > creatureList;
    private final Map<Resource.ResourceType, Resource> resources;

    public EconomyHero( final Fraction aFraction, final Set<Resource> aResources )
    {
        fraction = aFraction;
        resources = aResources.stream()
                .collect(Collectors.toMap(
                        Resource::type,
                        resource -> resource
                ));
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

    public Resource getResource(Resource.ResourceType resourceType) {
        return resources.get( resourceType );
    }

    public void addResource(final Resource aResource ) {
        Resource resource = resources.get(aResource.type());
        Resource summedResource = resource.add(aResource);
        resources.put(aResource.type(), summedResource);
    }

    public List< EconomyCreature > getCreatures()
    {
        return List.copyOf( creatureList );
    }

    public void subtractResource(final Resource aResource) {
        Resource resource = resources.get(aResource.type());
        Resource subtractedResource = resource.subtract(aResource);
        resources.put(aResource.type(), subtractedResource);
    }

    public boolean hasEnoughResources(Set<Resource> prerequisites) {
        return prerequisites.stream().allMatch(this::hasResource);
    }

    private boolean hasResource(Resource resource) {
        var resourceOfType = resources.get(resource.type());
        return resourceOfType != null && resourceOfType.isEqualOrMoreThan(resource);
    }

    public enum Fraction
    {
        NECROPOLIS
    }
}
