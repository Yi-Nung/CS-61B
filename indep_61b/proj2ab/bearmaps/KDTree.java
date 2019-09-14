package bearmaps;

import java.util.List;

public class KDTree implements PointSet {
    private static final boolean HORIZONTAL = false;
    private static final boolean VERTICAL = true;
    private Node root;

    private class Node {
        private Point p;
        private boolean orientation;
        private Node left;
        private Node right;
        // left child also the down child, right child also the up child
        private Node(Point p, boolean orientation, Node left, Node right) {
            this.p = p;
            this.orientation = orientation;
        }
    }

    private Node add(Point p, Node n, boolean orientation) {
        if (n == null) {
            return new Node(p, orientation, null, null);
        }
        if (p.equals(n.p)) {
            return n;
        }
        int cmp = comparePoints(p, n.p, orientation);
        if (cmp < 0) {
            n.left = add(p, n.left, !orientation);
        } else if (cmp >= 0) {
            n.right = add(p, n.right, !orientation);
        }
        return n;
    }

    private int comparePoints(Point p, Point q, boolean orientation) {
        if (orientation == HORIZONTAL) {
            return Double.compare(p.getX(), q.getX());
        } else {
            return Double.compare(p.getY(), q.getY());
        }
    }

    public KDTree(List<Point> points) {
        for (Point p: points) {
            root = add(p, root, HORIZONTAL);
        }
    }

    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        return nearestHelper(root, goal, root).p;
    }

    private Node nearestHelper(Node n, Point goal, Node best) {
        if (n == null) {
            return best;
        }
        if (Point.distance(n.p, goal) < Point.distance(best.p, goal)) {
            best = n;
        }
        Node goodSide;
        Node badSide;
        if (comparePoints(goal, n.p, n.orientation) < 0) {
            goodSide = n.left;
            badSide = n.right;
        } else {
            goodSide = n.right;
            badSide = n.left;
        }
        best = nearestHelper(goodSide, goal, best);
        if (n.orientation == HORIZONTAL) {
            if (Math.pow(goal.getX() - n.p.getX(), 2) < Point.distance(best.p, goal)) {
                best = nearestHelper(badSide, goal, best);
            }
        } else {
            if (Math.pow(goal.getY() - n.p.getY(), 2) < Point.distance(best.p, goal)) {
                best = nearestHelper(badSide, goal, best);
            }
        }
        return best;
    }
}
