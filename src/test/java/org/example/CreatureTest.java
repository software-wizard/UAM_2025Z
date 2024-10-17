package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreatureTest {
    @Test
    void x() {
        Creature attacker = new Creature(new DefaultDamageCalculator());
        Creature defender = new Creature(new DefaultDamageCalculator());
        defender.setCurrentHp(20);
        defender.setMaxHp(20);
        defender.setAmount(2);
        attacker.setDamage(30);
        attacker.setAmount(1);
        attacker.setMaxHp(20);

        attacker.attack(defender);

        assertThat(defender.getCurrentHp()).isEqualTo(10);
        assertThat(defender.getAmount()).isEqualTo(1);
    }

    @Test
    void y() {
        Creature attacker = new Creature(new DefaultDamageCalculator());
        Creature defender = new Creature(new DefaultDamageCalculator());
        defender.setCurrentHp(9);
        defender.setMaxHp(20);
        defender.setAmount(2);
        attacker.setDamage(30);

        attacker.attack(defender);

//        assertThat(defender.getCurrentHp()).isEqualTo(10);
        assertThat(defender.getAmount()).isEqualTo(0);
    }

    @Test
    void z() {
        Creature attacker = new Creature(new DefaultDamageCalculator());
        Creature defender = new Creature(new DefaultDamageCalculator());
        defender.setCurrentHp(20);
        defender.setMaxHp(20);
        defender.setAmount(1);
        defender.setDamage(3);

        attacker.setDamage(10);
        attacker.setCurrentHp(21);
        attacker.setMaxHp(21);
        attacker.setAmount(1);

        attacker.attack(defender);

        assertThat(attacker.getCurrentHp()).isEqualTo(18);
        assertThat(attacker.getAmount()).isEqualTo(1);
    }

    @Test
    void attackerHas50PercentChanceToDoubleDamage() {
        Creature attacker = new Creature(new DamageBonusDamageCalculator(2));
        Creature defender = new Creature(new DefaultDamageCalculator());
        defender.setCurrentHp(23);
        defender.setMaxHp(23);
        defender.setAmount(1);
        defender.setDamage(3);

        attacker.setDamage(4);
        attacker.setCurrentHp(21);
        attacker.setMaxHp(21);
        attacker.setAmount(1);

        attacker.attack(defender);

        assertThat(defender.getCurrentHp()).isEqualTo(15);
        assertThat(defender.getAmount()).isEqualTo(1);
    }
}