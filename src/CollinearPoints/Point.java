package CollinearPoints;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private final int x;
    private final int y;

    // constructs the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // draws this point
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draws the line segment from this point to that point
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // string representation
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // compare two points by y-coordinates, breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (that == null) throw new IllegalArgumentException();
         if ((this.y == that.y && this.x > that.x)
                 || this.y > that.y) {
             return 1;
         } else if ((this.y == that.y && this.x < that.x)
                 || this.y < that.y) {
             return -1;
         } else return 0;
    }

    // the slope between this point and that point
    public double slopeTo(Point that) {
        if (that == null) throw new IllegalArgumentException();
        double subX = this.x - that.x;
        double subY = this.y - that.y;
        if (subY == 0 && subX != 0) {
            // vertical
            return 0;
        } else if (subY == 0 && subX == 0) {
            // degenerate
            subY = -1;
        } else if (subX == 0) {
            // horizontal
            subY = 1;
        }
        //StdOut.println(this.y + " - " + that.y + " / " + this.x + " - " + that.y);
        //StdOut.println("slope = " + (subY / subX));
        return subY / subX;
    }

    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        return new pointComparator();
    }

    private class pointComparator implements Comparator<Point> {
        Point point = new Point(x, y);

        @Override
        public int compare(Point p1, Point p2) {
            if (point.slopeTo(p1) > point.slopeTo(p2)) {
                return 1;
            } else if (point.slopeTo(p1) == point.slopeTo(p2)) {
                return 0;
            } else {
                return -1;
            }
        }
    }
}
