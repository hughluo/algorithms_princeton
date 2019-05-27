/* *****************************************************************************
 *  Name: Yinchi Luo
 *  Date: 2019/05/26
 *  Description: collinear assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> s;
    //  private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        s = new ArrayList<>(2);
        if (points == null)
            throw new IllegalArgumentException("FATAL: input points array is null!");
        for (int h = 0; h < points.length; h++) {
            for (int i = h + 1; i < points.length; i++) {
                for (int j = i + 1; j < points.length; j++) {
                    for (int k = j + 1; k < points.length; k++) {
                        if (points[i] == null)
                            throw new IllegalArgumentException("FATAL: null point detected!");
                        if (points[i].toString() == "(10000, 0)") {
                            StdOut.println("find");
                        }
                        double slopeHI = points[h].slopeTo(points[i]);
                        double slopeHJ = points[h].slopeTo(points[j]);
                        double slopeHK = points[h].slopeTo(points[k]);
                        if (slopeHI == Double.NEGATIVE_INFINITY) {
                            throw new IllegalArgumentException("FATAL: repeated points detected!");
                        }

                        if (slopeHI == slopeHJ && slopeHI == slopeHK) {
                            Point[] arr = new Point[4];
                            arr[0] = points[h];
                            arr[1] = points[i];
                            arr[2] = points[j];
                            arr[3] = points[k];
                            Arrays.sort(arr);
                            s.add(new LineSegment(arr[0], arr[3]));
                        }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
}
