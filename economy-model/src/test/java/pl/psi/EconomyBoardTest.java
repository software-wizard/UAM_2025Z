package pl.psi;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.psi.resource.Resources.ResourceType.GOLD;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resources;

import java.util.Map;

public class EconomyBoardTest {

    private EconomyBoard board;
    private EconomyHero hero1;
    private EconomyHero hero2;



    @BeforeEach
    void init() {
        hero1 = new EconomyHero( EconomyHero.Fraction.NECROPOLIS, new Resources(Map.of(GOLD,3000)) );
        hero2 = new EconomyHero( EconomyHero.Fraction.NECROPOLIS, new Resources(Map.of(GOLD,2000)) );

        board = new EconomyBoard(hero1, hero2);
    }

    @Test
    void shouldCheckIfHeroesAreOnBoard( ) {

        assertThat( board.getEconomyHero( new Point( 0, 0 ) )
                .isPresent() ).isTrue();

        assertThat( board.getEconomyHero( new Point( 9, 9 ) )
                .isPresent() ).isTrue();
    }

}
