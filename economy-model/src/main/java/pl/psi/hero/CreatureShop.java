package pl.psi.hero;

import pl.psi.creatures.EconomyCreature;
import pl.psi.resource.Resource;

public class CreatureShop
{

    public void buy( final EconomyHero aHero, final EconomyCreature aEconomyCreature )
    {
        var resourcesNeededToButCreature = new Resource(
                Resource.ResourceType.GOLD,
                aEconomyCreature.getGoldCost() * aEconomyCreature.getAmount()
        );
        aHero.subtractResource(resourcesNeededToButCreature);
        try
        {
            aHero.addCreature( aEconomyCreature );
        }
        catch( final Exception e )
        {
            aHero.addResource( resourcesNeededToButCreature );
            throw new IllegalStateException( "hero cannot consume more creature" );
        }
    }
}
