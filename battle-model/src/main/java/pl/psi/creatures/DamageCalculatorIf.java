package pl.psi.creatures;

import pl.psi.GameContext;
import pl.psi.Point;

public interface DamageCalculatorIf
{
    int calculateDamage(Creature aAttacker, Creature aDefender,  Point sourcePoint, Point targetPoint);
}
