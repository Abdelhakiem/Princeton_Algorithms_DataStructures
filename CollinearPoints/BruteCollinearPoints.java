import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private ArrayList<LineSegment> lineSegments = new ArrayList<LineSegment>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Point array cann't be null");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("Point can't be null");
        }

        Point[] pointsCopy = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            pointsCopy[i] = points[i];
        }
        Arrays.sort(pointsCopy);
        for (int i = 0; i < pointsCopy.length - 1; i++) {
            if (pointsCopy[i].compareTo(pointsCopy[i + 1]) == 0)
                throw new IllegalArgumentException("duplicated points are not allowed");
        }
        int n = pointsCopy.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int m = k + 1; m < n; m++) {
                        if (Double.compare(pointsCopy[i].slopeTo(pointsCopy[j]), pointsCopy[i].slopeTo(pointsCopy[k])) == 0
                                && Double.compare(pointsCopy[i].slopeTo(pointsCopy[k]), pointsCopy[i].slopeTo(pointsCopy[m])) == 0) {
                            lineSegments.add(new LineSegment(pointsCopy[i], pointsCopy[m]));
                        }
                    }
                }
            }
        }

    }


    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        LineSegment[] arrSegments = new LineSegment[numberOfSegments()];
        for (int i = 0; i < numberOfSegments(); i++) {
            arrSegments[i] = lineSegments.get(i);
        }
        return arrSegments;
    }

    public static void main(String[] args) {


        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();


        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
