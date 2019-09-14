package bearmaps;

import java.util.ArrayList;
import java.util.List;

public class NaivePointSet implements PointSet {
    private List<Point> myPoints;

    // @source https://youtu.be/FGnw5tNHeiQ

    public NaivePointSet(List<Point> points) {
        myPoints = new ArrayList<Point>();
        for (Point p: points) {
            myPoints.add(p);
        }
    }

    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        Point best = myPoints.get(0);
        for (Point p: myPoints) {
            if (Point.distance(p, goal) < Point.distance(best, goal)) {
                best = p;
            }
        }
        return best;
    }
}
