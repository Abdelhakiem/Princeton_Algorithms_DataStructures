import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;


public class KdTree {
    private static boolean Vertical = true;
    private static boolean Horizental = false;
    private Node root;
    private int size;

    private class Node {
        private Point2D point;
        private Node left, right;

        Node(Point2D point) {
            this.point = point;
        }
    }

    public KdTree() {
        size = 0;
        root = null;
    }

    // is the set empty?
    public boolean isEmpty() {
        return (size() == 0);
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Inserted point musn't be null!");
        }
        root = insert(root, p, Vertical);

    }

    private Node insert(Node node, Point2D p, boolean Direction) {
        if (node == null) {
            size++;
            return new Node(p);
        }
        int cmpx = Double.compare(node.point.x(), p.x());
        int cmpy = Double.compare(node.point.y(), p.y());

        if (cmpx == 0 && cmpy == 0) {
            return node;
        }
        if (Direction == Vertical) {
            if (cmpx > 0) {
                node.left = insert(node.left, p, !Direction);
            } else {
                node.right = insert(node.right, p, !Direction);
            }


        } else {

            if (cmpy > 0) {
                node.left = insert(node.left, p, !Direction);
            } else {
                node.right = insert(node.right, p, !Direction);
            }
        }
        return node;
    }


    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException(" point musn't be null!");
        }
        return contains(root, p, Vertical);
    }

    private boolean contains(Node node, Point2D p, boolean Direction) {
        if (node == null) {
            return false;
        }
        int cmpx = Double.compare(node.point.x(), p.x());
        int cmpy = Double.compare(node.point.y(), p.y());

        if (cmpx == 0 && cmpy == 0) {
            return true;
        }
        if (Direction == Vertical) {
            if (cmpx > 0) {
                return contains(node.left, p, !Direction);
            } else {
                return contains(node.right, p, !Direction);
            }


        } else {

            if (cmpy > 0) {
                return contains(node.left, p, !Direction);
            } else {
                return contains(node.right, p, !Direction);
            }
        }

    }


    // draw all points to standard draw
    public void draw() {
        StdDraw.clear();
        draw(root, Vertical, 0, 1, 0, 1);
    }

    private void draw(Node node, boolean Direction, double minHorizental, double maxHorizental, double minVertical, double maxVertical) {

        if (node == null) {
            return;
        }


        //Draw the current point, in Black color:
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        StdDraw.point(node.point.x(), node.point.y());
        //the subdivisions in red (for vertical splits) and blue (for horizontal splits)
        if (Direction == Vertical) {

            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.01);
            double x = node.point.x();
            StdDraw.line(x, minVertical, x, maxVertical);


            // Draw left subtree:
            draw(node.left, !Direction, minHorizental, x, minVertical, maxVertical);
            // Draw right subtree:
            draw(node.right, !Direction, x, maxHorizental, minVertical, maxVertical);

        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.01);
            double y = node.point.y();
            StdDraw.line(minHorizental, y, maxHorizental, y);
            // Draw left subtree:
            draw(node.left, !Direction, minHorizental, maxHorizental, minVertical, y);
            // Draw right subtree:
            draw(node.right, !Direction, minHorizental, maxHorizental, y, maxVertical);

        }
    }


    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException(" point musn't be null!");
        }
        Queue<Point2D> pointsQueue = new Queue<Point2D>();
        range(root, Vertical, rect, pointsQueue);
        return pointsQueue;
    }

    private void range(Node node, boolean Direction, RectHV rect, Queue<Point2D> pointsQueue) {
        if (node == null) {
            return;
        }

        if (rect.contains(node.point)) {
            pointsQueue.enqueue(node.point);
        }

        if (Direction == Vertical) {

            if (rect.xmin() <= node.point.x() && node.point.x() <= rect.xmax()) {
                range(node.left, !Direction, rect, pointsQueue);
                range(node.right, !Direction, rect, pointsQueue);
            } else if (rect.xmax() < node.point.x()) {
                range(node.left, !Direction, rect, pointsQueue);
            } else if (rect.xmin() > node.point.x()) {
                range(node.right, !Direction, rect, pointsQueue);
            }

        } else {

            if (rect.ymin() <= node.point.y() && node.point.y() <= rect.ymax()) {
                range(node.left, !Direction, rect, pointsQueue);
                range(node.right, !Direction, rect, pointsQueue);
            } else if (rect.ymax() < node.point.y()) {
                range(node.left, !Direction, rect, pointsQueue);
            } else if (rect.ymin() > node.point.y()) {
                range(node.right, !Direction, rect, pointsQueue);
            }

        }

    }


    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Inserted point musn't be null!");
        }
        return nearest(root, root, root.point.distanceSquaredTo(p), p, Vertical, new RectHV(0, 0, 1, 1));
    }

    private Point2D nearest(Node node, Node minNode, double minDist, Point2D p, boolean Direction, RectHV rect) {
        if (node == null) {
            return minNode == null ? null : minNode.point;
        }
        // check for current node:
        double currentDist = node.point.distanceSquaredTo(p);
        if (currentDist < minDist) {
            minDist = currentDist;
            minNode = node;
        }
        if (rect.distanceSquaredTo(p) > minDist) {
            return minNode == null ? null : minNode.point;
        }

        RectHV ldRect, ruRect;
        if (Direction == Vertical) {
            ldRect = new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax());
            ruRect = new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
        } else {
            ldRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y());
            ruRect = new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax());
        }
        Point2D ldNearest = nearest(node, minNode, minDist, p, !Direction, ldRect);
        Point2D ruNearest = nearest(node, minNode, minDist, p, !Direction, ruRect);

        return ldNearest.distanceSquaredTo(p) < ruNearest.distanceSquaredTo(p) ?
                ldNearest : ruNearest;

    }


    public static void main(String[] args) {


    }
}
