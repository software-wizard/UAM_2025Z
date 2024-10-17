package org.example;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DamageBonusDamageCalculator extends DefaultDamageCalculator {
    private final int factor;

    @Override
    protected int recalcAfterCalculation(int i) {
        return i*factor;
    }
}
