package pl.psi;

import java.util.*;

public class PathFindingAlg
{
    private final Board board;


    public PathFindingAlg(Board board) {
        this.board = board;
    }

    public List<Point> findPath (Point start, Point end, Board board)
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
                return reconstructPath(previous, start, end);
            }

            for (Point neighbor : getNeighbors(currentPoint))
            {
                int newDistance = distances.get(currentPoint) + getCost(currentPoint, neighbor); //relaksacja
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
    public boolean canReach(Point start, Point end)
    {
        return !findPath(start, end, board).isEmpty();
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

    // czy punkt jest nna kracie jeszcze:
    private boolean isValidPoint(Point point) {
        return point.getX() >= 0 && point.getX() < 14 && point.getY() >= 0 && point.getY() < 14;
    }

    // czy pole jest zajete przez kreature:
    private boolean isOccupied(Point point)
    {
        return board.getCreature(point).isPresent();
    }



    private int getCost(Point from, Point to)
    {
        if (isOccupied(to))
        {
            return 1000; //koszt taki aby zawsze omijać to pole
        }
        return 1;
    }
}
