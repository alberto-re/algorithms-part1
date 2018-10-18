import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private final ArrayList<LineSegment> segments = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {

        validateOrThrowException(points);

        Point[] sortedPoints = Arrays.copyOf(points, points.length);

        Arrays.sort(sortedPoints);

        for (int i = 0; i < sortedPoints.length; i++) {
            Point a = sortedPoints[i];
            for (int j = i + 1; j < sortedPoints.length; j++) {
                Point b = sortedPoints[j];
                for (int k = j + 1; k < sortedPoints.length; k++) {
                    Point c = sortedPoints[k];
                    for (int m = k + 1; m < sortedPoints.length; m++) {
                        Point d = sortedPoints[m];
                        double ab = a.slopeTo(b);
                        double bc = b.slopeTo(c);
                        double cd = c.slopeTo(d);
                        if (ab == bc && bc == cd) {
                            segments.add(new LineSegment(a, d));
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        // comment needed by the grader
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }

    private void validateOrThrowException(Point[] points) {

        if (points != null) {
            for (Point p : points) {
                if (p == null) {
                    throw new IllegalArgumentException("Null argument");
                }
            }
            for (int i = 0; i < points.length; i++) {
                for (int j = i + 1; j < points.length; j++) {
                    if (points[i].compareTo(points[j]) == 0) {
                        throw new IllegalArgumentException("Duplicated elements");
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Null argument");
        }
    }

}
