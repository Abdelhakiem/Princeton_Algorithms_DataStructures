import java.util.TreeSet;

import edu.princeton.cs.algs4.Queue;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
    private TreeSet<Point2D> pointsTree;

    // construct an empty set of points
    public PointSET() {
        pointsTree = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pointsTree.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pointsTree.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Inserted point musn't be null!");
        }
        if (!pointsTree.contains(p)) {
            pointsTree.add(p);
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException(" point musn't be null!");
        }
        return pointsTree.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point : pointsTree) {
            point.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException(" point musn't be null!");
        }
        Queue<Point2D> pointsQueue = new Queue<Point2D>();
        for (Point2D point : pointsTree) {
            if (rect.contains(point)) {
                pointsQueue.enqueue(point);
            }
        }
        return pointsQueue;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Inserted point musn't be null!");
        }
        if (isEmpty()) {
            return null;
        }


        Double minDist = pointsTree.first().distanceTo(p);
        Point2D targetPoint = pointsTree.first();
        for (Point2D point : pointsTree) {
            if (point.distanceTo(p) < minDist) {
                minDist = point.distanceTo(p);
                targetPoint = point;
            }
        }
        return new Point2D(targetPoint.x(), targetPoint.y());
    }

    public static void main(String[] args) {
        
    }
}
