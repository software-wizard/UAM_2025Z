package pl.psi.creatures;

import pl.psi.Board;
import pl.psi.GameContext;
import pl.psi.Point;

import java.util.Random;

public class RangedCreatureDamageCalculator extends AbstractCalculateDamageStrategy
{


    public RangedCreatureDamageCalculator()
    {
        super(new Random());

    }

    @Override
    public int calculateDamage( final Creature aAttacker, final Creature aDefender,  Point sourcePoint, Point targetPoint )
    {
        int baseDamage = super.calculateDamage(aAttacker, aDefender, sourcePoint, targetPoint);

        int distance = (int) Math.round(sourcePoint.distance(targetPoint.getX(), targetPoint.getY()));

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
