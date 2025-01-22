package pl.psi.creatures;

import com.google.common.collect.Range;
import pl.psi.Board;
import pl.psi.GameContext;
import pl.psi.Hero;
import pl.psi.Point;

public class ResurrectAfterAttackCreature  extends Creature{


    private Hero owner;


    public ResurrectAfterAttackCreature(final CreatureStatisticIf aStats, final DamageCalculatorIf aCalculator,
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

        int dealtDamage = getCalculator().calculateDamage(this, aDefender, sourcePoint, targetPoint); //wyciaganiete przed atak!
        super.attack(aDefender, context);

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
