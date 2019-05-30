/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.TreeSet;

public class PointSET {
    // construct an empty set of points
    private TreeSet<Point2D> pts;

    public PointSET() {
        pts = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pts.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pts.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point to insert is null");
        pts.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point to contains is null");
        return pts.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D pt : pts) pt.draw();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("RectHV to insert is null");
        TreeSet<Point2D> result = new TreeSet<Point2D>();
        for (Point2D pt : pts) if (rect.contains(pt)) result.add(pt);
        return result;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point to nearest is null");
        if (isEmpty()) return null;
        Point2D result = new Point2D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        for (Point2D pt : pts) if (p.distanceTo(pt) < p.distanceTo(result)) result = pt;
        return result;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
    }
}
