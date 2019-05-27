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
    private Point[] p;
    private ArrayList<Line> lines;

    private class Line {
        public final double k;
        public final double b;

        public Line(double slope, double intercept) {
            k = slope;
            b = intercept;
        }
    }


    public FastCollinearPoints(Point[] points) {
        s = new ArrayList<>(2);
        p = points.clone();
        Arrays.sort(p);
        for (int i = 0; i < p.length; i++) {
            Arrays.sort(p, i, p.length);
            Point origin = p[i];
            Arrays.sort(p, i, p.length, origin.slopeOrder());
            int counter;

            for (int j = i + 1; j < p.length; j = j + counter + 1) {
                if (origin.slopeTo(p[j]) == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException("repeated point!");
                }
                counter = 0;
                for (int k = j + 1; k < p.length; k++) {
                    if (origin.slopeTo(p[j]) == origin.slopeTo(p[k])) {
                        counter++;
                        if (counter == 2) {
                            Arrays.sort(p, j, j + 2);
                            int compareResult = origin.compareTo(p[j]);
                            if (compareResult > 0) {
                                s.add(new LineSegment(p[j], p[k]));
                                break;
                            }
                            else if (compareResult < 0) {
                                s.add(new LineSegment(origin, p[k]));
                                break;
                            }
                            else {
                                throw new IllegalArgumentException(
                                        "FATAL: repeated points detected!");
                            }
                        }
                    }
                    else {
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
