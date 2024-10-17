package org.example;

public class DefaultDamageCalculator {

    public static final int MAX_ATTACK_DIFF = 60;
    public static final int MAX_DEFENCE_DIFF = 12;
    public static final double DEFENCE_BONUS = 0.025;
    public static final double ATTACK_BONUS = 0.05;

    public int calculateDamage(final Creature aAttacker, final Creature aDefender) {
        final int randValue = aAttacker.getDamage();

        double oneCreatureDamageToDeal;
        if (aAttacker.getAttack() >= aDefender.getDamage()) {
            int attackPoints = aAttacker.getAttack() - aDefender.getDamage();
            if (attackPoints > MAX_ATTACK_DIFF) {
                attackPoints = MAX_ATTACK_DIFF;
            }
            oneCreatureDamageToDeal = randValue * (1 + attackPoints * ATTACK_BONUS);
        } else {
            int defencePoints = aDefender.getDefence() - aAttacker.getAttack();
            if (defencePoints > MAX_DEFENCE_DIFF) {
                defencePoints = MAX_DEFENCE_DIFF;
            }
            oneCreatureDamageToDeal = randValue * (1 - defencePoints * DEFENCE_BONUS);
        }

        if (oneCreatureDamageToDeal < 0) {
            oneCreatureDamageToDeal = 0;
        }
        return recalcAfterCalculation((int) (aAttacker.getAmount() * oneCreatureDamageToDeal));
    }

    protected int recalcAfterCalculation(int i) {
        return i;
    }
}
