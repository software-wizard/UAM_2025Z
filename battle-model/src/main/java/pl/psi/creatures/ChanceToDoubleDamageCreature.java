package pl.psi.creatures;

import pl.psi.Board;
import pl.psi.Hero;
import pl.psi.Point;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ChanceToDoubleDamageCreature extends Creature
{
    private Board board;

    private Hero owner;
    private final Random random;
    private final double DOUBLE_DMG_CHANCE = 0.2;
    private final double CURSE_CHANCE = 0.2;

    public ChanceToDoubleDamageCreature(final CreatureStatisticIf aStats, final DamageCalculatorIf aCalculator,
                                       final int aAmount)
    {
        super(aStats, aCalculator, aAmount);
        this.random = new Random();
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
    public void attack(Creature aDefender)
    {
        boolean cursed = random.nextDouble() < CURSE_CHANCE;
        boolean doubleDamage = random.nextDouble() < DOUBLE_DMG_CHANCE;

        if (isAlive()) {
            int damage = getCalculator().calculateDamage(this, aDefender);
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
                counterAttack(aDefender);
            }
        }

    }









}
