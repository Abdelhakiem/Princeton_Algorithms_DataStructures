import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> lineSegments = new ArrayList<LineSegment>();

    public FastCollinearPoints(Point[] points) {
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
            Arrays.sort(pointsCopy, pointsCopy[i].slopeOrder());
            ArrayList<Point> startPoints = new ArrayList<Point>();
            ArrayList<Point> endPoints = new ArrayList<Point>();
            double prevSlope = pointsCopy[0].slopeTo(pointsCopy[1]);
            Point startPoint = pointsCopy[1];
            int numOfSuccesiveSlopes = 0;
            int j;
            for (j = 2; j < n; j++) {
                if (pointsCopy[0].slopeTo(pointsCopy[j]) == prevSlope) {
                    numOfSuccesiveSlopes++;
                } else if (numOfSuccesiveSlopes >= 2) {
                    startPoints.add(startPoint);
                    endPoints.add(pointsCopy[j - 1]);
                    numOfSuccesiveSlopes = 0;
                    prevSlope = pointsCopy[0].slopeTo(pointsCopy[j]);
                    startPoint = pointsCopy[j];
                } else {
                    numOfSuccesiveSlopes = 0;
                    prevSlope = pointsCopy[0].slopeTo(pointsCopy[j]);
                    startPoint = pointsCopy[j];
                }
            }
            if (numOfSuccesiveSlopes >= 2) {
                startPoints.add(startPoint);
                endPoints.add(pointsCopy[j - 1]);
            }
            for (int k = 0; k < startPoints.size(); k++) {
                if (pointsCopy[0].compareTo(startPoints.get(k)) < 0) {
                    lineSegments.add(new LineSegment(startPoints.get(0), endPoints.get(k)));
                }
            }
            Arrays.sort(pointsCopy);

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


        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

