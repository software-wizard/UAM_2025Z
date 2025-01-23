package pl.psi.creatures;

import pl.psi.Point;

import java.util.Random;


public class DoubleDamageCalculator extends AbstractCalculateDamageStrategy
{

    private final double DOUBLE_DMG_CHANCE = 0.2;
    private Random random;

    public DoubleDamageCalculator(Random random)
    {
        super(random);
        this.random = random;
    }

    @Override
    public int calculateDamage(final Creature aAttacker, final Creature aDefender, Point sourcePoint, Point targetPoint )
    {
        boolean doubleDamage = random.nextDouble() < DOUBLE_DMG_CHANCE;
        System.out.println("Using double dmg calculator");
        int baseDamage = super.calculateDamage(aAttacker, aDefender, sourcePoint, targetPoint);
        if (doubleDamage)
        {
            System.out.println("Double damage applied!");
            baseDamage *= 2;
        }

        return baseDamage;
    }
}
