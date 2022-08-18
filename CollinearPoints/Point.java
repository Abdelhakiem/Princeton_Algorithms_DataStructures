import java.util.Comparator;
import java.util.Arrays;

import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public double slopeTo(Point that) {
        if ((that.x - this.x) == 0 && (that.y - this.y) == 0) return Double.NEGATIVE_INFINITY;
        else if ((that.x - this.x) == 0 && (that.y - this.y) > 0) return Double.POSITIVE_INFINITY;
        else if ((that.y - this.y) == 0) return +0.0;
        return ((double) (that.y - this.y) / (that.x - this.x));
    }


    public int compareTo(Point that) {
        if (this.y < that.y || ((this.y == that.y) && (this.x < that.x)))
            return -1;
        else if (this.y == that.y && this.x == that.x)
            return 0;
        return 1;
    }

 
    public Comparator<Point> slopeOrder() {
        return new OrderBySlope(this);
    }

    private class OrderBySlope implements Comparator<Point> {
        Point point0;

        public OrderBySlope(Point p) {
            point0 = p;
        }

        public int compare(Point p1, Point p2) {
            double slope01 = point0.slopeTo(p1);
            double slope02 = point0.slopeTo(p2);
            return Double.compare(slope01, slope02);
        }
    }

    public static void main(String[] args) {

    }
}
