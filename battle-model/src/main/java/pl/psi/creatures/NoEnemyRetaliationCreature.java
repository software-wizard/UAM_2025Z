package pl.psi.creatures;

import pl.psi.Board;
import pl.psi.GameContext;
import pl.psi.Hero;
import pl.psi.Point;

public class NoEnemyRetaliationCreature extends Creature
{
    private Hero owner;


    public NoEnemyRetaliationCreature(final CreatureStatisticIf aStats, final DamageCalculatorIf aCalculator,
                                        final int aAmount)
    {
        super(aStats, aCalculator, aAmount);
    }


    public void initializeOwner(final Hero aOwner) {
        super.initializeOwner(aOwner);
        this.owner = aOwner;
    }

    @Override
    public DamageCalculatorIf getCalculator()
    {
        return super.getCalculator();
    }

    @Override
    public boolean isAlly(Creature otherCreature) {
        return super.isAlly(otherCreature);

    }


    @Override
    public void attack(final Creature aDefender, GameContext context)
    {
        Point sourcePoint = context.getPosition(this);
        Point targetPoint = context.getPosition(aDefender);
        int dealtDamage = getCalculator().calculateDamage(this, aDefender, sourcePoint, targetPoint);
        aDefender.applyDamage(dealtDamage);
        //bez kontry

    }


}
