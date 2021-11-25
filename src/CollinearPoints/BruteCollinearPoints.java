package CollinearPoints;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<>();
    private int numOfSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        numOfSegments = 0;
        Arrays.sort(points);
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                double iToJ = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < points.length - 1; k++) {
                    double iToK = points[i].slopeTo(points[k]);
                    if (iToJ != iToK) continue;
                    for (int l = k + 1; l < points.length; l++) {
                        double iToL = points[i].slopeTo(points[l]);
                        if (iToK == iToL) {
                            LineSegment ps = new LineSegment(points[i], points[l]);
                            segments.add(ps);
                            numOfSegments++;
                            break;
                        }
                    }
                }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        StdOut.println("Segments in collinear:");
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
        }
        StdOut.println(collinear.numOfSegments);
    }
}
