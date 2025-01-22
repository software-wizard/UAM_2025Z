package pl.psi.creatures;

import pl.psi.Board;
import pl.psi.GameContext;
import pl.psi.Hero;
import pl.psi.Point;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ChanceToDoubleDamageCreature extends Creature
{
    private Hero owner;
    private final Random random;
    private final double DOUBLE_DMG_CHANCE = 0.2;
    private final double SPELL_CAST_CHANCE = 0.2;

    public ChanceToDoubleDamageCreature(final CreatureStatisticIf aStats, final DamageCalculatorIf aCalculator,
                                       final int aAmount)
    {
        super(aStats, aCalculator, aAmount);
        this.random = new Random();
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
    public void attack(Creature aDefender, GameContext context)
    {
        boolean cursed = random.nextDouble() < SPELL_CAST_CHANCE;
        boolean doubleDamage = random.nextDouble() < DOUBLE_DMG_CHANCE;

        if (isAlive()) {
            Point sourcePoint = context.getPosition(this);
            Point targetPoint = context.getPosition(aDefender);
            int damage = getCalculator().calculateDamage(this, aDefender, sourcePoint, targetPoint);
            final int damageWithBonus = getAttackWithBonus();
            System.out.println("Damage: " + damage + "\nDamage with attack bonus: " + damageWithBonus);

            if (doubleDamage)
            {
                damage*=2;
                System.out.println("Double damage dealt!");
            }

            if (cursed)
            {
                System.out.println("Cursed target");
            }
            aDefender.applyDamage(damageWithBonus);
            if (canCounterAttack(aDefender)) {
                System.out.println("Counter attack");
                counterAttack(aDefender, context);
            }
        }

    }









}
