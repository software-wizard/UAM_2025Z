package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreatureTest {
    @Test
    void x() {
        Creature attacker = new Creature();
        Creature defender = new Creature();
        defender.setCurrentHp(20);
        defender.setMaxHp(20);
        defender.setAmount(2);
        attacker.setDamage(30);

        attacker.attack(defender);

        assertThat(defender.getCurrentHp()).isEqualTo(10);
        assertThat(defender.getAmount()).isEqualTo(1);
    }

    @Test
    void y() {
        Creature attacker = new Creature();
        Creature defender = new Creature();
        defender.setCurrentHp(9);
        defender.setMaxHp(20);
        defender.setAmount(2);
        attacker.setDamage(30);

        attacker.attack(defender);

//        assertThat(defender.getCurrentHp()).isEqualTo(10);
        assertThat(defender.getAmount()).isEqualTo(0);
    }
}