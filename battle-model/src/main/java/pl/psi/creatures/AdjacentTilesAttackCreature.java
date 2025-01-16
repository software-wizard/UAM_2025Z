package pl.psi.creatures;

import lombok.Getter;
import pl.psi.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class AdjacentTilesAttackCreature extends Creature
{
    private Board board;

    private Hero owner;


    public AdjacentTilesAttackCreature(final CreatureStatisticIf aStats, final DamageCalculatorIf aCalculator,
                                       final int aAmount)
    {
        super(aStats, aCalculator, aAmount);
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
        super.attack(aDefender);
        cloudAttack(aDefender);
    }


    private void cloudAttack(Creature aDefender)
    {
        Point defenderPosition = board.getPosition(aDefender);
        List<Point> adjacentTiles = getAdjacentTiles(defenderPosition);
        for (Point tile : adjacentTiles)
        {
            Optional<Creature> adjacentCreature = board.getCreature(tile);

            //nie chcemy atakowac naszych jendostek
            adjacentCreature.ifPresent(creature -> {
                if (creature != aDefender && creature.isAlive())
                {
                    int damage = getCalculator().calculateDamage(this, creature);
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

        List<Point> adjacentTiles = List.of(
                new Point(x+1, y),
                new Point(x-1, y),
                new Point(x, y+1),
                new Point(x, y-1),
                new Point(x+1, y+1),
                new Point(x-1, y+1),
                new Point(x+1, y-1),
                new Point(x-1, y-1)

        );
        return adjacentTiles;

    }

}
