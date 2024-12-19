package pl.psi.hero;

import pl.psi.creatures.EconomyCreature;
import pl.psi.resource.Resources;

public class CreatureShop
{

    public void buy( final EconomyHero aHero, final EconomyCreature aEconomyCreature )
    {
        Resources resourcesNeededToButCreature = aEconomyCreature.getCost();
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
