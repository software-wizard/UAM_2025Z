package pl.psi.creatures;

import pl.psi.GameContext;
import pl.psi.Hero;
import pl.psi.Point;

import java.util.Random;

public class ChanceToCastSpellCreature extends Creature
{
    private Hero owner;
    private final Random random;
    private final double SPELL_CAST_CHANCE = 0.2;

    public ChanceToCastSpellCreature(final CreatureStatisticIf aStats, final DamageCalculatorIf aCalculator,
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
        if (this.getStats().isUpgraded()) {
            return new DoubleDamageCalculator(random);
        }
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

        if (isAlive()) {
            Point sourcePoint = context.getPosition(this);
            Point targetPoint = context.getPosition(aDefender);
            int damage = getCalculator().calculateDamage(this, aDefender, sourcePoint, targetPoint);
            final int damageWithBonus = getAttackWithBonus();
            System.out.println("Damage: " + damage + "\nDamage with attack bonus: " + damageWithBonus);

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
