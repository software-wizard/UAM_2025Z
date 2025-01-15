package pl.psi.creatures;

import pl.psi.Board;
import pl.psi.Point;

import java.util.Random;

public class RangedCreatureDamageCalculator extends AbstractCalculateDamageStrategy
{
    Board board;

    public RangedCreatureDamageCalculator(Board board)
    {
        super(new Random());
        this.board = board;

    }

    @Override
    public int calculateDamage( final Creature aAttacker, final Creature aDefender)
    {
        int baseDamage = super.calculateDamage(aAttacker, aDefender);

        Point attackerPosition = board.getPosition(aAttacker);
        Point defenderPosition = board.getPosition(aDefender);
        int distance = (int) Math.round(attackerPosition.distance(defenderPosition.getX(), defenderPosition.getY()));

        if (distance > 10)
        {
            baseDamage *= 0.5;
        }
        else if (distance < 2)
        {
            baseDamage *= 0.5;
        }

        return baseDamage;
    }
}
