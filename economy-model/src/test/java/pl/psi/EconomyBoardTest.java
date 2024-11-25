package pl.psi;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.psi.hero.EconomyHero;

public class EconomyBoardTest {

    private EconomyBoard board;
    private EconomyHero hero1;
    private EconomyHero hero2;



    @BeforeEach
    void init() {
        hero1 = new EconomyHero( EconomyHero.Fraction.NECROPOLIS, 3000 );
        hero2 = new EconomyHero( EconomyHero.Fraction.NECROPOLIS, 2000 );

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
