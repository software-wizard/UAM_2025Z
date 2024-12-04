package pl.psi;

import pl.psi.hero.EconomyHero;

import java.util.Optional;

public class EconomyBoardEngine {

    private final EconomyBoard board;

    public EconomyBoardEngine(final EconomyHero aHero1, final EconomyHero aHero2) {
        board = new EconomyBoard(aHero1, aHero2);
    }

    public Optional<EconomyHero> getHero(final Point aPoint) {
        return board.getEconomyHero(aPoint);
    }

    public Optional<Castle> getCastle(final Point aPoint) {
        return board.getCastle(aPoint);
    }

  /*  public boolean canMove(final Point aPoint) {

    //   return board.canMove(EconomyTurnQueue.getCurrentHero(), aPoint);

    }*/

  /*  public void move(final Point aPoint) {
     //   board.move(EconomyTurnQueue.getCurrentHero(), aPoint);
      //  observerSupport.firePropertyChange(CREATURE_MOVED, null, aPoint);
    }
*/
}
