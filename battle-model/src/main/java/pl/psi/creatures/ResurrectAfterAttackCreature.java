package pl.psi.creatures;

import com.google.common.collect.Range;

public class ResurrectAfterAttackCreature  extends Creature{

    private final Creature decorated;

    public ResurrectAfterAttackCreature( final Creature aDecorated )
    {
        decorated = aDecorated;
    }

    @Override
    public CreatureStatisticIf getStats()
    {
        return decorated.getStats();
    }

    @Override
    public int getAmount()
    {
        return decorated.getAmount();
    }

    @Override
    public int getCounterAttackCounter()
    {
        return decorated.getCounterAttackCounter();
    }

    @Override
    public DamageCalculatorIf getCalculator()
    {
        return decorated.getCalculator();
    }


    @Override
    public void attack( final Creature aDefender )
    {
        int dealtDamage = decorated.getCalculator().calculateDamage(this, aDefender); //wyciaganiete przed atak!
        decorated.attack( aDefender );

        // po ataku uzywa nowej zdolnosci resurectCreatures - ale tylko gdy NIE atakowała undeada:
        if (!aDefender.isUndead())
        {
            resurrectCreatures(dealtDamage);
        }
    }


    private void resurrectCreatures(final int dealtDamage) // odwrotnosc applydamage?
    {
        // łącznie będziemy leczyc tyle ile zadalismy w attack()
        int hpToRestore = dealtDamage;


        while(hpToRestore > 0 && getAmount() > 0)
        {
            // ile brakuje zycia jednostce - o ile jeszcze uleczyc
            int missingHp = getStats().getMaxHp() - getCurrentHp();

            if (missingHp > 0)
            {
                // ulecz kreature:
                int restoredHp = Math.min(hpToRestore, missingHp); // aby nie przywrocic wiecej niz brakuje jednostce lub wiecej niz mozemy
                setCurrentHp(getCurrentHp() + restoredHp);
                hpToRestore -= restoredHp;
            }
            else
            {
                // gdy missingHp = 0, to przywracamy całą jednostke na pole bitwy:
                setAmount(getAmount() + 1);
                hpToRestore -= getStats().getMaxHp();
            }
        }
    }

    @Override
    public boolean isAlive()
    {
        return decorated.isAlive();
    }

    @Override
    public int getCurrentHp()
    {
        return decorated.getCurrentHp();
    }

    @Override
    protected void setCurrentHp( final int aCurrentHp )
    {
        decorated.setCurrentHp( aCurrentHp );
    }

    @Override
    Range< Integer > getDamage()
    {
        return decorated.getDamage();
    }

    @Override
    public int getAttack()
    {
        return decorated.getAttack();
    }

    @Override
    int getArmor()
    {
        return decorated.getArmor();
    }

    @Override
    protected void restoreCurrentHpToMax()
    {
        decorated.restoreCurrentHpToMax();
    }
}
