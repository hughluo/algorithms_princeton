import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.util.ArrayList;


public class KdTree {
    // construct an empty set of points
    private int size;
    private Node root;


    public KdTree() {
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point to insert is null");
        if (!isEmpty() && contains(p)) return;

        if (size == 0) {
            root = new Node(p, true);
            root.setRect(0.0, 0.0, 1.0, 1.0);
            size++;
        }
        else {
            insertRecursive(root, p, 0.0, 0.0, 1.0, 1.0);
            size++;
        }
    }

    private void insertRecursive(Node current, Point2D p, double xmin, double ymin, double xmax,
                                 double ymax) {
        if (!current.isXKey()) { // y as key
            if (p.y() < current.py()) {
                if (current.getLB() == null) {
                    Node n = new Node(p, true);
                    n.setRect(xmin, ymin, xmax, current.py());
                    current.setLB(n);
                }
                else insertRecursive(current.getLB(), p, xmin, ymin, xmax, current.py());
            }
            else {
                if (current.getRT() == null) {
                    Node n = new Node(p, true);
                    n.setRect(xmin, current.py(), xmax, ymax);
                    current.setRT(n);
                }
                else insertRecursive(current.getRT(), p, xmin, current.py(), xmax, ymax);
            }

        }
        else { //  x as key
            if (p.x() < current.px()) {
                if (current.getLB() == null) {
                    Node n = new Node(p, false);
                    n.setRect(xmin, ymin, current.px(), ymax);
                    current.setLB(n);
                }
                else insertRecursive(current.getLB(), p, xmin, ymin, current.px(), ymax);
            }
            else {
                if (current.getRT() == null) {
                    Node n = new Node(p, false);
                    n.setRect(current.px(), ymin, xmax, ymax);
                    current.setRT(n);
                }
                else insertRecursive(current.getRT(), p, current.px(), ymin, xmax, ymax);
            }
        }
    }


    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point to contains is null");
        if (isEmpty()) return false;
        return searchRecursive(root, p);
    }


    private boolean searchRecursive(Node current, Point2D p) {
        if (current == null) {
            return false;
        }

        if (!current.isXKey()) {
            if (p.y() < current.py()) {
                return searchRecursive(current.getLB(), p);
            }
            else {
                if (p.y() == current.py() && p.x() == current.px()) return true;
                else return searchRecursive(current.getRT(), p);
            }

        }
        else { // horizontal
            if (p.x() < current.px()) {
                return searchRecursive(current.getLB(), p);
            }
            else {
                if (p.y() == current.py() && p.x() == current.px()) return true;
                else return searchRecursive(current.getRT(), p);
            }
        }

    }


    // draw all points to standard draw
    public void draw() {
        //
        drawPoint();
        //drawLine();

    }

    private void drawPoint() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        drawPointRecursive(root);
    }


    private void drawPointRecursive(Node current) {
        if (current == null) return;
        StdDraw.point(current.px(), current.py());
        //  draw rect
        //current.getRect().draw();
        drawPointRecursive(current.getLB());
        drawPointRecursive(current.getRT());
    }

    private void drawLine() {
        StdDraw.setPenRadius();
        drawLineRecursive(root, 0.0, 1.0, 0.0, 1.0);
    }

    private void drawLineRecursive(Node current, double xLo, double xHi, double yLo, double yHi) {
        if (current == null) return;
        if (current.isXKey()) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(current.px(), yLo, current.px(), yHi);
            drawLineRecursive(current.getLB(), xLo, current.px(), yLo, yHi);
            drawLineRecursive(current.getRT(), current.px(), xHi, yLo, yHi);

        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(xLo, current.py(), xHi, current.py());
            drawLineRecursive(current.getLB(), xLo, xHi, yLo, current.py());
            drawLineRecursive(current.getRT(), xLo, xHi, current.py(), yHi);
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("RectHV to insert is null");
        return rangeRecursive(rect, root, null);
    }

    private ArrayList<Point2D> rangeRecursive(RectHV rect, Node current, ArrayList<Point2D> pts) {
        if (current == null) return pts;
        if (pts == null) pts = new ArrayList<Point2D>(2);
        if (!rect.intersects(current.getRect())) {
            return pts;
        }

        if (rect.contains(current.p)) {
            pts.add(current.p);
        }
        rangeRecursive(rect, current.getLB(), pts);
        rangeRecursive(rect, current.getRT(), pts);
        return pts;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point to nearest is null");
        if (isEmpty()) return null;
        return nearestRecursive(p, root, root).getPoint();
    }

    private Node nearestRecursive(Point2D p, Node champion, Node current) {
        if (current == null) return champion;

        double min = champion.getPoint().distanceSquaredTo(p);
        if (current.getPoint().distanceSquaredTo(p) < min) {
            champion = current;
        }

        if (current.getRect().distanceSquaredTo(p) > min) return champion;

        if (current.getLB() != null && current.getLB().getRect().contains(p)) {
            champion = nearestRecursive(p, champion, current.getLB());
            champion = nearestRecursive(p, champion, current.getRT());
        }
        else {
            champion = nearestRecursive(p, champion, current.getRT());
            champion = nearestRecursive(p, champion, current.getLB());
        }
        return champion;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree kdt = new KdTree();
        kdt.insert(new Point2D(0.7, 0.2));
        kdt.insert(new Point2D(0.5, 0.4));
        kdt.insert(new Point2D(0.2, 0.3));
        kdt.insert(new Point2D(0.4, 0.7));
        kdt.insert(new Point2D(0.9, 0.6));
        kdt.draw();
        StdDraw.setPenColor(Color.orange);
        Point2D p = new Point2D(0.2, 0.2);
        p.draw();
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(Color.CYAN);
        kdt.nearest(new Point2D(0.2, 0.2)).draw();
        //
        // StdOut.println(kdt.size);
        // StdOut.println(kdt.contains(new Point2D(0.7, 0.2)));
        // StdOut.println(kdt.contains(new Point2D(0.5, 0.4)));
        // StdOut.println(kdt.contains(new Point2D(0.2, 0.3)));
        // StdOut.println(kdt.contains(new Point2D(0.4, 0.7)));
        // StdOut.println(kdt.contains(new Point2D(0.9, 0.6)));
        // StdOut.println(kdt.contains(new Point2D(0.7, 0.7)));


    }


    private static class Node {
        private final Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private final boolean isXKey;

        public Node(Point2D pt, boolean isXAsKey) {
            p = pt;
            isXKey = isXAsKey;
        }

        public void setRect(double xmin, double ymin, double xmax, double ymax) {
            rect = new RectHV(xmin, ymin, xmax, ymax);
        }

        public Point2D getPoint() {
            return p;
        }

        public RectHV getRect() {
            return rect;
        }

        public boolean isXKey() {
            return isXKey;
        }

        public double px() {
            return p.x();
        }

        public double py() {
            return p.y();
        }

        public void setLB(Node n) {
            lb = n;
        }

        public void setRT(Node n) {
            rt = n;
        }

        public Node getLB() {
            return lb;
        }

        public Node getRT() {
            return rt;
        }
    }
}
