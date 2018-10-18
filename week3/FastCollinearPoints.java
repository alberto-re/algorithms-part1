import java.util.*;

public class FastCollinearPoints {

    private final List<LineSegment> segments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {

        validateOrThrowException(points);
        List<Point[]> minMax = new ArrayList<>();

        for (Point p : points) {

            Point[] sortedPoints = Arrays.copyOf(points, points.length);
            Arrays.sort(sortedPoints, p.slopeOrder());

            Double lastSlope = null;
            ArrayList<Point> sameSlopePoints = new ArrayList<>();
            sameSlopePoints.add(p);

            for (Point q : sortedPoints) {
                double slope = p.slopeTo(q);

                if (!(lastSlope != null && lastSlope == slope)) {
                    addSegmentIfFourOrMorePoints(sameSlopePoints, minMax);
                    sameSlopePoints = new ArrayList<>();
                    sameSlopePoints.add(p);
                }
                sameSlopePoints.add(q);
                lastSlope = slope;
            }

            addSegmentIfFourOrMorePoints(sameSlopePoints, minMax);
        }

        uniqueSegments(minMax);
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

    private void uniqueSegments(List<Point[]> minMax) {
        Collections.sort(minMax, new Comparator<Point[]>() {
            @Override
            public int compare(Point[] o1, Point[] o2) {
                int c = o1[0].compareTo(o2[0]);
                if (c == 0) {
                    c = o1[1].compareTo(o2[1]);
                }
                return c;
            }
        });
        Point[] lastMinMax = new Point[2];
        for (Point[] minMaxPoint : minMax) {
            if (lastMinMax == null || (lastMinMax[0] != minMaxPoint[0] || lastMinMax[1] != minMaxPoint[1])) {
                segments.add(new LineSegment(minMaxPoint[0], minMaxPoint[1]));
            }
            lastMinMax = minMaxPoint;
        }
    }

    private void addSegmentIfFourOrMorePoints(ArrayList<Point> sameSlopePoints, List<Point[]> minMax) {
        if (sameSlopePoints.size() > 3) {
            Collections.sort(sameSlopePoints);
            Point[] minMaxPoint = new Point[]{sameSlopePoints.get(0), sameSlopePoints.get(sameSlopePoints.size() - 1)};
            minMax.add(minMaxPoint);
        }
    }

    private void validateOrThrowException(Point[] points) {
        if (points != null) {
            for (Point p : points) {
                if (p == null) {
                    throw new IllegalArgumentException("Null point");
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
            throw new IllegalArgumentException("Null point");
        }
    }

}