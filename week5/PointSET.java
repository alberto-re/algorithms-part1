import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {

    private final TreeSet<Point2D> points;

    public PointSET() {
        points = new TreeSet<>();
    }

    public static void main(String[] args) {
        // comment needed by the grader
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        points.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return points.contains(p);
    }

    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        List<Point2D> pointsInside = new ArrayList<>();
        for (Point2D p : points) {
            if (rect.contains(p)) {
                pointsInside.add(p);
            }
        }
        return pointsInside;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        Point2D nearestPoint = null;
        for (Point2D q : points) {
            if (nearestPoint == null) {
                nearestPoint = q;
            } else {
                if (q.distanceSquaredTo(p) < nearestPoint.distanceSquaredTo(p)) {
                    nearestPoint = q;
                }
            }
        }
        return nearestPoint;
    }
}