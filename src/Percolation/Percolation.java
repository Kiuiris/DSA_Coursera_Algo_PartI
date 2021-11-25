package Percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int gridSize;

    private boolean[] grid;

    private final int gridTop;

    private final int gridBottom;

    private final WeightedQuickUnionUF perco;
    private final WeightedQuickUnionUF full;

    private int numOfOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        this.gridSize = n;
        this.grid = new boolean[gridSize * gridSize + 2];
        this.gridTop = gridSize * gridSize;
        this.gridBottom = gridSize * gridSize + 1;
        grid[gridTop] = true;
        grid[gridBottom] = false;
        this.perco = new WeightedQuickUnionUF(gridSize * gridSize + 2);
        this.full = new WeightedQuickUnionUF(gridSize * gridSize + 1);
        this.numOfOpenSites = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        isValid(row, col);

        if (isOpen(row, col)) return;

        int sitePos = gridIndex(row, col);
        grid[sitePos] = true;
        numOfOpenSites++;

        if (row == 1) {
            full.union(sitePos, gridTop);
            perco.union(sitePos, gridTop);
        }

        if (row == gridSize) {
            perco.union(sitePos, gridBottom);
        }

        if (row < gridSize && isOpen(row + 1, col)) {
            int neighborPos = gridIndex(row + 1, col);
            if (full.find(sitePos) != full.find(neighborPos))
                full.union(sitePos, neighborPos);
            if (perco.find(sitePos) != perco.find(neighborPos))
                perco.union(sitePos, neighborPos);
        }

        if (row > 1 && isOpen(row - 1, col)) {
            int neighborPos = gridIndex(row - 1, col);
            if (full.find(sitePos) != full.find(neighborPos))
                full.union(sitePos, neighborPos);
            if (perco.find(sitePos) != perco.find(neighborPos))
                perco.union(sitePos, neighborPos);
        }

        if (col < gridSize && isOpen(row, col + 1)) {
            int neighborPos = gridIndex(row, col + 1);
            if (full.find(sitePos) != full.find(neighborPos))
                full.union(sitePos, neighborPos);
            if (perco.find(sitePos) != perco.find(neighborPos))
                perco.union(sitePos, neighborPos);
        }

        if (col > 1 && isOpen(row, col - 1)) {
            int neighborPos = gridIndex(row, col - 1);
            if (full.find(sitePos) != full.find(neighborPos))
                full.union(sitePos, neighborPos);
            if (perco.find(sitePos) != perco.find(neighborPos))
                perco.union(sitePos, neighborPos);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        isValid(row, col);
        return grid[gridIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        isValid(row, col);
        return (isOpen(row, col)
                && full.find(gridTop) == full.find(gridIndex(row, col)));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (gridSize == 1) {
            return grid[gridIndex(1, 1)];
        }
        return perco.find(gridTop) == perco.find(gridBottom);
    }

    // check if the site (row, col) is valid
    private void isValid(int row, int col) {
        if (row > gridSize || col > gridSize || row < 1 || col < 1) {
            throw new IllegalArgumentException("Invalid row or col");
        }
    }

    // calculate the index of the site in the grid array
    private int gridIndex(int row, int col) {
        isValid(row, col);
        return (col - 1 + gridSize * (row - 1));
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);
        StdOut.println("gridSize: " + percolation.gridSize);
        StdOut.println("grid length: " + percolation.grid.length);

        StdOut.println("percolates = " + percolation.percolates());

        StdOut.println("isOpen(3, 3) = " + percolation.isOpen(3, 3));
        StdOut.println("(3, 3) gridIndex = " + percolation.gridIndex(3, 3));
    }
}
