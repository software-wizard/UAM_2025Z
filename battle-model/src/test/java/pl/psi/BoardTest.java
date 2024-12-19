package pl.psi;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import pl.psi.creatures.Creature;
import pl.psi.creatures.CreatureStats;

class BoardTest
{
    @Test
    void unitsMoveProperly()
    {
        final Creature creature = new Creature.Builder().statistic( CreatureStats.builder()
            .moveRange( 5 )
            .build() )
            .build();
        final List< Creature > c1 = List.of( creature );
        final List< Creature > c2 = List.of();
        final Board board = new Board( c1, c2 );

        board.move( creature, new Point( 3, 3 ) );

        assertThat( board.getCreature( new Point( 3, 3 ) )
            .isPresent() ).isTrue();
    }

    @Test
    void canMoveToPoint()
    {
        final Creature creature1 = new Creature.Builder().statistic( CreatureStats.builder()
                        .moveRange( 5 )
                        .build() )
                .build();
        final Creature creature2 = new Creature.Builder().statistic( CreatureStats.builder()
                        .moveRange( 10 )
                        .build() )
                .build();
        final List< Creature > c1 = List.of( creature1 );
        final List< Creature > c2 = List.of(creature2);

        final Board board = new Board( c1, c2 );
        PathFindingAlg alg = new PathFindingAlg(board);

        Point startPoint = new Point(0, 1);
        Point endPoint = new Point( 3, 4 );
        board.canMove(creature1, endPoint);

        boolean reachable = alg.canReach(startPoint, endPoint);

        assertThat(reachable).isTrue();
    }

    @Test
    void getPathTest()
    {

        final Creature creature1 = new Creature.Builder().statistic( CreatureStats.builder()
                        .moveRange( 5 )
                        .build() )
                .build();
        final Creature creature2 = new Creature.Builder().statistic( CreatureStats.builder()
                        .moveRange( 10 )
                        .build() )
                .build();
        final List< Creature > c1 = List.of( creature1 );
        final List< Creature > c2 = List.of(creature2);

        final Board board = new Board( c1, c2 );
        PathFindingAlg alg = new PathFindingAlg(board);

        Point startPoint = new Point(0, 1);
        Point endPoint = new Point( 3, 4 );
        board.canMove(creature1, endPoint);

        List<Point> path = alg.findPath(startPoint, endPoint, board);
        if (!path.isEmpty()) {
            System.out.println("Found path: " + path);
        } else {
            System.out.println("No path found :(");
        }
    }


    @Test
    void shouldAvoidOccupiedPoint()
    {
        Creature blockingCreature = new Creature.Builder().statistic( CreatureStats.builder()
                        .moveRange( 5 )
                        .build() )
                .build();

        final List< Creature > c1 = List.of( blockingCreature );
        final List< Creature > c2 = List.of();
        final Board board = new Board( c1, c2 );
        PathFindingAlg alg = new PathFindingAlg(board);

        Point startPoint = new Point(0, 1);
        Point endPoint = new Point( 3, 4);

        board.move(blockingCreature, new Point(1, 1)); //ten punkt chcemy ominąć a normalnie by przez niego przeszedł

        List<Point> path = alg.findPath(startPoint, endPoint, board);

        // taka powinna być ścieżka po ominięciu (1,1)
        assertThat(path).containsExactly(
                new Point(0, 1),
                new Point(0, 2), //ominięcie
                new Point(1, 2),
                new Point(2, 2),
                new Point(2, 3),
                new Point(2, 4),
                new Point(3, 4)
                );
    }

}