package pl.psi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.psi.resource.Resources.Type.GOLD;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import pl.psi.building.town.Town;
import pl.psi.creatures.EconomyNecropolisFactory;
import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resources;

import java.util.Map;

class EconomyEngineTest
{

    private EconomyEngine economyEngine;
    private EconomyHero h1;
    private EconomyHero h2;
    private EconomyNecropolisFactory creatureFactory;

    @BeforeEach
    void init()
    {
        h1 = new EconomyHero(
                "A",
                EconomyHero.Fraction.NECROPOLIS,
                Resources.builder()
                        .resource(GOLD, 1000)
                        .build(),
                Mockito.mock(Town.class)
        );
        economyEngine = new EconomyEngine( h1 );
        creatureFactory = new EconomyNecropolisFactory();
    }

    @Test
    void shouldChangeActiveHeroAfterPass()
    {
        assertEquals( h1, economyEngine.getActiveHero() );
     //   economyEngine.pass();
        assertEquals( h2, economyEngine.getActiveHero() );
    }

    @Test
    void shouldCountRoundCorrectly()
    {
      //  assertEquals( 1, economyEngine.getRoundNumber() );
     //   economyEngine.pass();
       // assertEquals( 1, economyEngine.getRoundNumber() );
      //  economyEngine.pass();
      //  assertEquals( 2, economyEngine.getRoundNumber() );
    }

    @Test
    void shouldBuyCreatureCreatureInCorrectHero()
    {
        economyEngine.buy( creatureFactory.create( false, 1, 1 ) );
        assertEquals( 940, h1.getResourceAmount(GOLD) );
        assertEquals( 1000, h2.getResourceAmount(GOLD) );
        economyEngine.buy( creatureFactory.create( false, 2, 1 ) );
        assertEquals( 900, h2.getResourceAmount(GOLD) );
        assertEquals( 940, h1.getResourceAmount(GOLD) );
    }
}