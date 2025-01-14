package pl.psi;

import java.util.*;

public class PathFindingAlg
{
    private final Board board;


    public PathFindingAlg(Board board) {
        this.board = board;
    }

    public List<Point> findPath (Point start, Point end, int moveRange)
    {
        PriorityQueue<Node> unvisitedNodes = new PriorityQueue<Node>(Comparator.comparingInt(node -> node.cost));
        Map<Point, Integer> distances = new HashMap<>();
        Map<Point, Point> previous = new HashMap<>();   //lista poprzedników aby odwtorzyć path
        Set<Point> visited = new HashSet<>();

        distances.put(start, 0);
        unvisitedNodes.add(new Node(start, 0));

        while (!unvisitedNodes.isEmpty())
        {
            Node current = unvisitedNodes.poll(); //usuwamy node o najnizszym priorytecie
            Point currentPoint = current.point;

            if (visited.contains(currentPoint)) continue;
            visited.add(currentPoint);

            if (currentPoint.equals(end))   //warunek na wyjscie z alg
            {
                List<Point> path = reconstructPath(previous, start, end);

                if (path.size() - 1 > moveRange)
                {
                    System.out.println("Not enough speed to reach this target!");
                    return Collections.emptyList();
                }
                return path;
            }

            for (Point neighbor : getNeighbors(currentPoint))
            {
                int newDistance = distances.get(currentPoint) + getCost(neighbor); //relaksacja
                if (newDistance < distances.getOrDefault(neighbor, Integer.MAX_VALUE))
                {
                    distances.put(neighbor, newDistance);
                    previous.put(neighbor, currentPoint);
                    unvisitedNodes.add(new Node(neighbor, newDistance));
                }
            }
        }
        return Collections.emptyList(); //gdy path nie istnieje
    }


    private List<Point> reconstructPath(Map<Point, Point> previous, Point start, Point end)
    {
        List<Point> path = new ArrayList<>();
        Point current = end;

        while (current != start)
        {
            path.add(current);
            current = previous.get(current);

            if (current == null)
            {
                return Collections.emptyList();
            }
        }
        path.add(start);
        Collections.reverse(path);
        return path;
    }

    // czy znajdzie sie sciezka do punktu docelowego
    public boolean canReach(Point start, Point end, int moveRange)
    {
        return !findPath(start, end, moveRange).isEmpty();
    }



    // znajdz sasiadujace punkty/wierzcholki (4 sasiadow max dla naszej kraty):
    private List<Point> getNeighbors(Point point)
    {
        List<Point> neighbors = new ArrayList<>();
        int x = point.getX();
        int y = point.getY();

        addNeighbor(neighbors, new Point(x-1, y));
        addNeighbor(neighbors, new Point(x+1, y));
        addNeighbor(neighbors, new Point(x, y-1));
        addNeighbor(neighbors, new Point(x, y+1));

        return neighbors;
    }

    private void addNeighbor(List<Point> neighbors, Point point)
    {
        if (isValidPoint(point))
        {
            neighbors.add(point);
        }
    }



    // czy punkt jest na kracie jeszcze i czy tile nie jest przeszkodą:
    private boolean isValidPoint(Point point)
    {
        int width = board.getWidth();
        int height = board.getHeight();

        return point.getX() >= 0 && point.getX() < width && point.getY() >= 0 && point.getY() < height
                && (board.getSpecialTile(point) == null || board.getSpecialTile(point).isPassable());
    }



    // czy pole jest zajete przez kreature:
    private boolean isOccupied(Point point)
    {
        return board.getCreature(point).isPresent();
    }



    private int getCost(Point to)
    {
        Tile tile = board.getSpecialTile(to);
        if (tile == null)
        {
            return 1;
        }

        //dla zajetego przez kreature punktu:
        if (isOccupied(to))
        {
            return 1000;
        }

        return switch (tile.getType()) {
            case DAMAGE -> 2;
            case OBSTACLE -> 1000;
            default -> 1;
        };
    }
}
