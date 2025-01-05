package pl.psi.hero;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.psi.creatures.EconomyNecropolisFactory;
import pl.psi.resource.Resources;

import java.util.Map;
import java.util.Set;

class EconomyHeroTest
{

    private EconomyHero hero;

    @BeforeEach
    void init()
    {
        hero = new EconomyHero( EconomyHero.Fraction.NECROPOLIS, new Resources(Map.of(Resources.ResourceType.GOLD, 3000)) );
    }

    @Test
    void shouldThrowExceptionWhileHeroHas7CreatureAndYoTryToAddNextOne()
    {
        final EconomyNecropolisFactory factory = new EconomyNecropolisFactory();
        hero.addCreature( factory.create( true, 1, 1 ) );
        hero.addCreature( factory.create( true, 1, 1 ) );
        hero.addCreature( factory.create( true, 1, 1 ) );
        hero.addCreature( factory.create( true, 1, 1 ) );
        hero.addCreature( factory.create( true, 1, 1 ) );
        hero.addCreature( factory.create( true, 1, 1 ) );
        hero.addCreature( factory.create( true, 1, 1 ) );

        assertThrows( IllegalStateException.class, () -> hero.addCreature( factory.create( true, 1, 1 ) ) );
    }

    @Test
    void shouldThrowExceptionWhileYouTrySubstractMoreGoldThanHeroHas()
    {
        assertThrows( IllegalStateException.class, () -> hero.subtractResource( new Resources(Map.of(Resources.ResourceType.GOLD, 3001)) ) );
    }
}