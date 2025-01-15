package pl.psi;

import java.util.List;

import org.junit.jupiter.api.Test;

import pl.psi.creatures.CastleCreatureFactory;
import pl.psi.creatures.Creature;
import pl.psi.creatures.CreatureStatistic;
import pl.psi.creatures.NecropolisFactory;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class GameEngineTest
{
    @Test
    void shoudWorksHeHe()
    {
        final CastleCreatureFactory creatureFactory = new CastleCreatureFactory();
        final GameEngine gameEngine =
            new GameEngine( new Hero( List.of( creatureFactory.create( 1, false, 5 ) ) ),
                new Hero( List.of( creatureFactory.create( 1, false, 5 ) ) ) );

        gameEngine.attack( new Point( 1, 1 ) );
    }

    @Test
    void shouldAssignHeroesAsOwnersToCreatures() {

        final NecropolisFactory creatureFactory = new NecropolisFactory();
        Hero hero1 = new Hero( List.of( creatureFactory.create( false, 1, 5 ) ) );
        Hero hero2 = new Hero( List.of( creatureFactory.create( false, 1, 5 ) ) );
        final GameEngine gameEngine =
                new GameEngine( hero1, hero2 );


        assertThat(hero1.getCreatures().getFirst().getOwner()).isEqualTo(hero1);
        assertThat(hero2.getCreatures().getFirst().getOwner()).isEqualTo(hero2);

        //this should be useful for checking if the creature is your ally or enemy
    }
}
