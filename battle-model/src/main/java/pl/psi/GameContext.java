package pl.psi;

import pl.psi.creatures.Creature;

import java.util.Optional;

public interface GameContext
{
    public Optional<Creature> getCreature(Point aPoint);
    public Point getPosition(Creature aCreature);
}
