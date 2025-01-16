package pl.psi.creatures;

import com.google.common.collect.Range;
import pl.psi.Board;
import pl.psi.Hero;

public class ResurrectAfterAttackCreature  extends Creature{

    private Board board;

    private Hero owner;


    public ResurrectAfterAttackCreature(final CreatureStatisticIf aStats, final DamageCalculatorIf aCalculator,
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
        int dealtDamage = getCalculator().calculateDamage(this, aDefender); //wyciaganiete przed atak!
        aDefender.applyDamage(dealtDamage);

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


}
