package pl.psi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.psi.creatures.EconomyNecropolisFactory;
import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resources;

import java.util.Map;
import java.util.Set;

class EconomyEngineTest
{

    private EconomyEngine economyEngine;
    private EconomyHero h1;
    private EconomyHero h2;
    private EconomyNecropolisFactory creatureFactory;

    @BeforeEach
    void init()
    {
        h1 = new EconomyHero( EconomyHero.Fraction.NECROPOLIS, new Resources(Map.of(Resources.ResourceType.GOLD, 1000)) );
        h2 = new EconomyHero( EconomyHero.Fraction.NECROPOLIS, new Resources(Map.of(Resources.ResourceType.GOLD, 1000)) );
        economyEngine = new EconomyEngine( h1, h2 );
        creatureFactory = new EconomyNecropolisFactory();
    }

    @Test
    void shouldChangeActiveHeroAfterPass()
    {
        assertEquals( h1, economyEngine.getActiveHero() );
        economyEngine.pass();
        assertEquals( h2, economyEngine.getActiveHero() );
    }

    @Test
    void shouldCountRoundCorrectly()
    {
        assertEquals( 1, economyEngine.getRoundNumber() );
        economyEngine.pass();
        assertEquals( 1, economyEngine.getRoundNumber() );
        economyEngine.pass();
        assertEquals( 2, economyEngine.getRoundNumber() );
    }

    @Test
    void shouldBuyCreatureCreatureInCorrectHero()
    {
        economyEngine.buy( creatureFactory.create( false, 1, 1 ) );
        assertEquals( 940, h1.getResourceAmount(Resources.ResourceType.GOLD) );
        assertEquals( 1000, h2.getResourceAmount(Resources.ResourceType.GOLD) );
        economyEngine.pass();
        economyEngine.buy( creatureFactory.create( false, 2, 1 ) );
        assertEquals( 900, h2.getResourceAmount(Resources.ResourceType.GOLD) );
        assertEquals( 940, h1.getResourceAmount(Resources.ResourceType.GOLD) );
    }
}