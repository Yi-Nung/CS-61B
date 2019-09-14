package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KDTreeTest {
    private static Random random = new Random(1234);

    // @source https://youtu.be/irkJ4gczM0I

    private static KDTree sampleTree() {
        Point p1 = new Point(2, 3); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);

        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
        return kd;
    }

    public static void sampleTreeReplicates() {
        Point p1 = new Point(2, 3); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(2, 3);

        KDTree kd = new KDTree(List.of(p1, p2));
    }

    @Test
    public void testNearestSimple() {
        KDTree kd = sampleTree();
        Point actual = kd.nearest(0, 7);
        Point expected = new Point(1, 5);
        assertEquals(expected, actual);
    }


    // @source https://youtu.be/lp80raQvE5c
    private Point randomPt() {
        double x = random.nextDouble();
        double y = random.nextDouble();
        return new Point(x, y);
    }

    private List<Point> randomPts(int N) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            points.add(randomPt());
        }
        return points;
    }

    @Test
    public void testNearestRandom() {
        List<Point> points = randomPts(1000);
        KDTree kd = new KDTree(points);
        NaivePointSet nps = new NaivePointSet(points);
        List<Point> goalPoints = randomPts(100);
        for (Point p: goalPoints) {
            Point actual = kd.nearest(p.getX(), p.getY());
            Point expected = nps.nearest(p.getX(), p.getY());
            assertEquals(expected, actual);
        }
    }

    // @source https://youtu.be/3IdQJm9tw6Y
    @Test
    public void testNearestTime() {
        List<Point> points = randomPts(100000);
        KDTree kd = new KDTree(points);
        NaivePointSet nps = new NaivePointSet(points);
        List<Point> goalPoints = randomPts(10000);

        long start1 = System.currentTimeMillis();
        for (Point p: goalPoints) {
            kd.nearest(p.getX(), p.getY());
        }
        long end1 = System.currentTimeMillis();
        System.out.println("KD total time elapsed: " + (end1 - start1) / 1000.0 +  " seconds.");

        long start2 = System.currentTimeMillis();
        for (Point p: goalPoints) {
            nps.nearest(p.getX(), p.getY());
        }
        long end2 = System.currentTimeMillis();
        System.out.println("Naive total time elapsed: " + (end2 - start2) / 1000.0 +  " seconds.");
    }
}
