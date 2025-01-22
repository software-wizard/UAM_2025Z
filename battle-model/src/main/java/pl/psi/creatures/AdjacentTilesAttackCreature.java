package pl.psi.creatures;

import lombok.Getter;
import pl.psi.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class AdjacentTilesAttackCreature extends Creature
{

    private Hero owner;


    public AdjacentTilesAttackCreature(final CreatureStatisticIf aStats, final DamageCalculatorIf aCalculator,
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
        super.attack(aDefender, context);
        cloudAttack(aDefender, context);
    }


    private void cloudAttack(Creature aDefender, GameContext context)
    {
        Point sourcePoint = context.getPosition(aDefender); //source tutaj to pozycja defendera bo target to kafelki wokół niego
        List<Point> adjacentTiles = getAdjacentTiles(sourcePoint);
        for (Point tile : adjacentTiles)
        {
            Optional<Creature> adjacentCreature = context.getCreature(tile);

            //nie chcemy atakowac naszych jendostek ani na nowo defendera:
            adjacentCreature.ifPresent(creature -> {
                if (creature != aDefender && creature.isAlive() && creature.isAlly(aDefender))
                {
                    int damage = getCalculator().calculateDamage(this, creature, sourcePoint, tile);
                    creature.applyDamage(damage);
                    System.out.println("Cloud attack applied damage to other creatures");
                }
            });

        }

    }

    private List<Point> getAdjacentTiles(Point defenderPosition)
    {
        int x = defenderPosition.getX();
        int y = defenderPosition.getY();

        return List.of(
                new Point(x+1, y),
                new Point(x-1, y),
                new Point(x, y+1),
                new Point(x, y-1),
                new Point(x+1, y+1),
                new Point(x-1, y+1),
                new Point(x+1, y-1),
                new Point(x-1, y-1)

        );

    }

}
