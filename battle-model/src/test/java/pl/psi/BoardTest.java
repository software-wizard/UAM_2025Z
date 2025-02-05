package pl.psi;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
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
        //usun pola specjalne - aby losowe przeszkody nie psuły testów:
        board.removeSpecialTiles();
        board.move( creature, new Point( 3, 3 ) );

        assertThat( board.getCreature( new Point( 3, 3 ) )
            .isPresent() ).isTrue();
    }

    @Test
    void canReachPointTest()
    {
        final Creature creature1 = new Creature.Builder().statistic( CreatureStats.builder()
                        .moveRange( 10 )
                        .build() )
                .build();
        final List< Creature > c1 = List.of( creature1 );
        final List< Creature > c2 = List.of();

        final Board board = new Board( c1, c2 );
        //usun pola specjalne - aby losowe przeszkody nie psuły testów:
        board.removeSpecialTiles();
        PathFindingAlg alg = new PathFindingAlg(board);

        Point startPoint = new Point(0, 1);
        Point endPoint = new Point( 3, 4 );
        board.canMove(creature1, endPoint);

        boolean reachable = alg.canReach(startPoint, endPoint, creature1.getMoveRange());

        assertThat(reachable).isTrue();
    }

    @Test
    void getPathTest()
    {

        final Creature creature1 = new Creature.Builder().statistic( CreatureStats.builder()
                        .moveRange( 10 )
                        .build() )
                .build();
        final List< Creature > c1 = List.of( creature1 );
        final List< Creature > c2 = List.of();

        final Board board = new Board( c1, c2 );
        //usun pola specjalne - aby losowe przeszkody nie psuły testów:
        board.removeSpecialTiles();
        PathFindingAlg alg = new PathFindingAlg(board);

        Point startPoint = new Point(0, 1);
        Point endPoint = new Point( 3, 4 );
        board.canMove(creature1, endPoint);

        List<Point> path = alg.findPath(startPoint, endPoint, creature1.getMoveRange());
        assertThat(path).isNotEmpty();
        System.out.println("Found path: " + path);

    }


    @Test
    void shouldAvoidObstacles()
    {
        final Creature creature1 = new Creature.Builder().statistic( CreatureStats.builder()
                        .moveRange( 15 )
                        .build() )
                .build();

        final List< Creature > c1 = List.of( creature1 );
        final List< Creature > c2 = List.of();
        final Board board = new Board( c1, c2 );
        board.removeSpecialTiles();

        Point startPoint = new Point(0, 1);
        Point endPoint = new Point( 3, 4);

        board.addTile(new Point(2, 2), new ObstacleTile());
        board.addTile(new Point(2, 3), new ObstacleTile());
        board.addTile(new Point(2, 4), new ObstacleTile());

        PathFindingAlg alg = new PathFindingAlg(board);
        List<Point> path = alg.findPath(startPoint, endPoint, creature1.getMoveRange());

        List<Point> expectedPath = List.of(
                new Point(0, 1),
                new Point(1, 1),
                new Point(2, 1),
                new Point(3, 1),
                new Point(3, 2),
                new Point(3, 3),
                new Point(3, 4)
        );

        assertThat(path).isEqualTo( expectedPath);
        System.out.println("New path: " + path);

    }

    @Test
    void shouldAvoidCreatures()
    {
        final Creature creature1 = new Creature.Builder().statistic( CreatureStats.builder()
                        .moveRange( 10 )
                        .build() )
                .build();

        Creature blockingCreature = new Creature.Builder().statistic( CreatureStats.builder()
                        .moveRange( 10 )
                        .build() )
                .build();
        Creature blockingCreature2 = new Creature.Builder().statistic( CreatureStats.builder()
                        .moveRange( 10 )
                        .build() )
                .build();
        Creature blockingCreature3 = new Creature.Builder().statistic( CreatureStats.builder()
                        .moveRange( 10 )
                        .build() )
                .build();

        final List< Creature > c1 = List.of( creature1, blockingCreature, blockingCreature2, blockingCreature3 );
        final List< Creature > c2 = List.of();
        final Board board = new Board( c1, c2 );

        //usun pola specjalne - nie chce aby losowo generowana obstacle psuł mi test:
        board.removeSpecialTiles();
        PathFindingAlg alg = new PathFindingAlg(board);

        Point startPoint = new Point(0, 1);
        Point endPoint = new Point( 3, 4);

        board.move(blockingCreature, new Point(2, 2));
        board.move(blockingCreature2, new Point(2, 3));
        board.move(blockingCreature3, new Point(2, 4));

        List<Point> path = alg.findPath(startPoint, endPoint, creature1.getMoveRange());

        List<Point> expectedPath = List.of(
                new Point(0, 1),
                new Point(1, 1),
                new Point(2, 1),
                new Point(3, 1),
                new Point(3, 2),
                new Point(3, 3),
                new Point(3, 4)
        );

        assertThat(path).isEqualTo(expectedPath);
        System.out.println("New path: " + path);
    }

    @Test
    void notEnoughMoveRange()
    {
        final Board board = new Board( new ArrayList<>(), new ArrayList<>() );
        PathFindingAlg alg = new PathFindingAlg(board);

        final Creature creature1 = new Creature.Builder().statistic( CreatureStats.builder()
                        .moveRange( 5 )
                        .build() )
                .build();

        int moveRange = creature1.getMoveRange();
        Point startPoint = new Point(0, 1);
        Point endPoint = new Point( 3, 4 );
        List<Point> path = alg.findPath(startPoint, endPoint, moveRange); //6 punktow (bez startpoint)

        assertThat( path ).isEmpty();
    }

}