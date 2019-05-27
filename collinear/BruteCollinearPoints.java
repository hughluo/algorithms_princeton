/* *****************************************************************************
 *  Name: Yinchi Luo
 *  Date: 2019/05/26
 *  Description: collinear assignment
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;
import java.lang.IllegalArgumentException;
import java.lang.NullPointerException;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> s;
    private Point[] p;
    //  private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        s = new ArrayList<>(2);
        p = points.clone();
        try {
            if (p == null)
                throw new IllegalArgumentException("FATAL: input points array is null!");
        }
        catch (NullPointerException e) {
            throw new IllegalArgumentException("FATAL: input points array is null!");
        }
        for (int h = 0; h < p.length; h++) {
            for (int i = h + 1; i < p.length; i++) {
                for (int j = i + 1; j < p.length; j++) {
                    for (int k = j + 1; k < p.length; k++) {
                        if (p[h].compareTo(p[i]) == 0 || p[h].compareTo(p[j]) == 0
                                || p[h].compareTo(p[k]) == 0) {
                            throw new IllegalArgumentException("FATAL: duplicate points detected!");
                        }

                        if (p[h] == null || p[i] == null || p[j] == null || p[k] == null) {
                            throw new IllegalArgumentException("FATAL: null point detected!");
                        }
                        double slopeHI, slopeHJ, slopeHK;
                        try {
                            slopeHI = p[h].slopeTo(p[i]);
                            slopeHJ = p[h].slopeTo(p[j]);
                            slopeHK = p[h].slopeTo(p[k]);
                        }
                        catch (NullPointerException e) {
                            throw new IllegalArgumentException("FATAL: null point detected!");
                        }

                        if (slopeHI == Double.NEGATIVE_INFINITY
                                || slopeHJ == Double.NEGATIVE_INFINITY
                                || slopeHK == Double.NEGATIVE_INFINITY) {
                            throw new IllegalArgumentException("FATAL: duplicate points detected!");
                        }

                        if (slopeHI == slopeHJ && slopeHI == slopeHK) {
                            Point[] arr = new Point[4];
                            arr[0] = p[h];
                            arr[1] = p[i];
                            arr[2] = p[j];
                            arr[3] = p[k];
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
        // // read the n points from a file
        // In in = new In(args[0]);
        // int n = in.readInt();
        // Point[] points = new Point[n];
        // for (int i = 0; i < n; i++) {
        //     int x = in.readInt();
        //     int y = in.readInt();
        //     points[i] = new Point(x, y);
        // }
        //
        // // draw the points
        // StdDraw.enableDoubleBuffering();
        // StdDraw.setXscale(0, 32768);
        // StdDraw.setYscale(0, 32768);
        // for (Point p : points) {
        //     p.draw();
        // }
        // StdDraw.show();
        //
        // // print and draw the line segments
        // BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        // for (LineSegment segment : collinear.segments()) {
        //     StdOut.println(segment);
        //     segment.draw();
        // }
        // StdDraw.show();

    }
}
