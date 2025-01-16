package pl.psi.creatures;

import pl.psi.Board;
import pl.psi.Hero;

public class NoEnemyRetaliationCreature extends Creature
{
    private Board board;

    private Hero owner;


    public NoEnemyRetaliationCreature(final CreatureStatisticIf aStats, final DamageCalculatorIf aCalculator,
                                        final int aAmount)
    {
        super(aStats, aCalculator, aAmount);
    }

    @Override
    public void initializeBoard(final Board aBoard) {
        super.initializeBoard(aBoard);
        this.board = aBoard;
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
    public void attack( final Creature aDefender )
    {
        int dealtDamage = getCalculator().calculateDamage(this, aDefender);
        aDefender.applyDamage(dealtDamage);

    }


}
