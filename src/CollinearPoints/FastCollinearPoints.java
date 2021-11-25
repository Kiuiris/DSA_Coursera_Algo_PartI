package CollinearPoints;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<>();
    private int numOfSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        Arrays.sort(points);
        Point[] sorted = points.clone();

        for (int i = 0; i < sorted.length; i++) {
            Point p = points[i];
            Arrays.sort(sorted);
            Arrays.sort(sorted, p.slopeOrder());
            Point[] pExcluded = Arrays.copyOfRange(sorted, 1, sorted.length);

            int begin = 0;
            int end = begin;
            while (begin < pExcluded.length) {
                while (end < pExcluded.length && p.slopeTo(pExcluded[end]) == p.slopeTo(pExcluded[begin])) {
                    end++;
                }
                if (end - begin >= 3) {
                    Point pMin, pMax;
                    //System.out.println(pExcluded[begin] + " - " + pExcluded[end - 1] + " - " + p);
                    if (pExcluded[begin].compareTo(p) < 0) {
                        pMin = pExcluded[begin];
                    } else pMin = p;
                    if (pExcluded[end - 1].compareTo(p) > 0) {
                        pMax = pExcluded[end - 1];
                    } else pMax = p;
                    LineSegment newSegment = new LineSegment(pMin, pMax);
                    if (p == pMin) {
                        segments.add(newSegment);
                        numOfSegments++;
                    }
                }
                begin = end;
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return numOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] copy = new LineSegment[numOfSegments];
        for (int i = 0; i < numOfSegments; i++) {
            copy[i] = segments.get(i);
        }
        return copy;
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

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        StdOut.println("Segments in collinear:");
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
        }
        StdOut.println(collinear.numOfSegments);
    }
}
