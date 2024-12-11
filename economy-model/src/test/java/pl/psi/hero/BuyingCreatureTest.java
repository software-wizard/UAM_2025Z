package pl.psi.hero;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.psi.EconomyEngine;
import pl.psi.creatures.EconomyNecropolisFactory;
import pl.psi.resource.Resource;

import java.util.Set;

public class BuyingCreatureTest
{

    private final EconomyNecropolisFactory creatureFactory = new EconomyNecropolisFactory();
    private EconomyHero hero1;
    private EconomyEngine economyEngine;
    private EconomyHero hero2;

    @BeforeEach
    void init()
    {
        hero1 = new EconomyHero( EconomyHero.Fraction.NECROPOLIS, Set.of(new Resource(Resource.ResourceType.GOLD, 1000)) );
        hero2 = new EconomyHero( EconomyHero.Fraction.NECROPOLIS, Set.of(new Resource(Resource.ResourceType.GOLD, 1000)) );
        economyEngine = new EconomyEngine( hero1, hero2 );
    }

    @Test
    void heroShouldCanBuyCreature()
    {
        economyEngine.buy( creatureFactory.create( false, 1, 1 ) );

        assertEquals( 940, hero1.getResource(Resource.ResourceType.GOLD) );
    }

    @Test
    void heroShouldCanBuyMoreThanOneCreatureInOneStack()
    {
        economyEngine.buy( creatureFactory.create( false, 1, 2 ) );

        assertEquals( 880, hero1.getResource(Resource.ResourceType.GOLD) );
    }

    @Test
    void heroShouldCanBuyMoreThanOneCreatureInFewStack()
    {
        economyEngine.buy( creatureFactory.create( false, 1, 2 ) );
        economyEngine.buy( creatureFactory.create( true, 2, 2 ) );

        assertEquals( 630, hero1.getResource(Resource.ResourceType.GOLD) );
    }

    @Test
    void heroCannotBuyCreatureWhenHasNotEnoughtGold()
    {
        assertThrows( IllegalStateException.class,
            () -> economyEngine.buy( creatureFactory.create( false, 1, 100 ) ) );
        assertEquals( 1000, hero1.getResource(Resource.ResourceType.GOLD) );
        assertEquals( 0, hero1.getCreatures()
            .size() );
    }

    @Test
    void heroCannotBuyCreatureIfHeIsFull()
    {
        economyEngine.buy( creatureFactory.create( false, 1, 1 ) );
        economyEngine.buy( creatureFactory.create( false, 1, 1 ) );
        economyEngine.buy( creatureFactory.create( false, 1, 1 ) );
        economyEngine.buy( creatureFactory.create( false, 1, 1 ) );
        economyEngine.buy( creatureFactory.create( false, 1, 1 ) );
        economyEngine.buy( creatureFactory.create( false, 1, 1 ) );
        economyEngine.buy( creatureFactory.create( false, 1, 1 ) );
        assertThrows( IllegalStateException.class,
            () -> economyEngine.buy( creatureFactory.create( false, 1, 1 ) ) );

        assertEquals( 580, hero1.getResource(Resource.ResourceType.GOLD) );
        assertEquals( 7, hero1.getCreatures()
            .size() );
    }
}
