/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> s;

    public FastCollinearPoints(Point[] points) {
        s = new ArrayList<>(2);
        if (points == null)
            throw new IllegalArgumentException("FATAL: input points array is null!");
        Arrays.sort(points);
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("FATAL: null point detected!");
            Point origin = points[i];
            Arrays.sort(points, i + 1, points.length, origin.slopeOrder());
            int counter;
            for (int j = i + 1; j < points.length; j = j + counter + 1) {
                counter = 0;
                for (int k = j + 1; k < points.length; k++) {
                    if (origin.slopeTo(points[j]) == origin.slopeTo(points[k])) {
                        counter++;
                        if (counter >= 2 && k == points.length - 1) {
                            Arrays.sort(points, j, points.length);
                            int compareResult = origin.compareTo(points[j]);
                            if (compareResult > 0) {
                                s.add(new LineSegment(points[j], points[k]));
                            }
                            else if (compareResult < 0) {
                                s.add(new LineSegment(origin, points[k]));
                            }
                            else {
                                throw new IllegalArgumentException(
                                        "FATAL: repeated points detected!");
                            }
                        }
                    }
                    else {
                        if (counter >= 2) {
                            Arrays.sort(points, j, k);
                            int compareResult = origin.compareTo(points[j]);
                            if (compareResult > 0) {
                                s.add(new LineSegment(points[j], points[k - 1]));
                            }
                            else if (compareResult < 0) {
                                s.add(new LineSegment(origin, points[k - 1]));
                            }
                            else {
                                throw new IllegalArgumentException(
                                        "FATAL: repeated points detected!");
                            }
                        }
                        break;
                    }
                }
            }
        }

    }

    public int numberOfSegments() {
        return s.size();
    }

    public LineSegment[] segments() {
        LineSegment[] lineSegments = s.toArray(new LineSegment[0]);
        return lineSegments;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
