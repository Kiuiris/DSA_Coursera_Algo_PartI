package EightPuzzle;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;

public class Board {
    private final int size;
    private final int[][] tiles;
    private final int[][] goalBoard;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        size = tiles.length;
        this.tiles = tiles;
        goalBoard = new int[size][size];
        int num = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == size - 1 && j == size - 1) {
                    goalBoard[i][j] = 0;
                    break;
                }
                goalBoard[i][j] = num;
                num++;
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder boardRep = new StringBuilder();
        boardRep.append(size);
        boardRep.append('\n');
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boardRep.append(tiles[i][j]);
                boardRep.append(" ");
            }
            boardRep.append('\n');
        }
        return boardRep.toString();
    }

    // board dimension n
    public int dimension() {
        return size;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == size - 1 && j == size - 1) {
                    break;
                }
                if (tiles[i][j] != goalBoard[i][j]) count++;
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;
        // x and y coordinates of the goal tiles
        int[] x = new int[size * size];
        int[] y = new int[size * size];
        int index = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                x[index] = i;
                y[index] = j;
                index++;
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int tile = tiles[i][j];
                if (tile == 0) continue;
                int diff = Math.abs(i - x[tile - 1]) + Math.abs(j - y[tile - 1]);
                sum += diff;
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (this.tiles[i][j] != this.goalBoard[i][j]) {
                    //System.out.println("i: " + i + ", j: " + j
                            //+ ", tiles: " + tiles[i][j] + ", goal: " + goalBoard[i][j]);
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y == this) return true;
        if (y.getClass() == this.getClass()) {
            Board other = (Board) y;
            if (other.size != this.size) return false;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (other.tiles[i][j] != this.tiles[i][j]) {
                        //System.out.println("hi");
                        return false;
                    }
                }
            }
            return true;
        }
        //System.out.println("hi");
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();

        int blankX = 0, blankY = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] == 0) {
                    blankX = i;
                    blankY = j;
                    break;
                }
            }
            if (tiles[blankX][blankY] == 0) break;
        }

        if (blankX > 0) {
            int[][] copy = copyTiles(tiles, size);
            copy[blankX][blankY] = tiles[blankX - 1][blankY];
            copy[blankX - 1][blankY] = 0;
            neighbors.add(new Board(copy));
        }

        if (blankX < size - 1) {
            int[][] copy = copyTiles(tiles, size);
            copy[blankX][blankY] = tiles[blankX + 1][blankY];
            copy[blankX + 1][blankY] = 0;
            neighbors.add(new Board(copy));
        }

        if (blankY > 0) {
            int[][] copy = copyTiles(tiles, size);
            copy[blankX][blankY] = tiles[blankX][blankY - 1];
            copy[blankX][blankY - 1] = 0;
            neighbors.add(new Board(copy));
        }

        if (blankY < size - 1) {
            int[][] copy = copyTiles(tiles, size);
            copy[blankX][blankY] = tiles[blankX][blankY + 1];
            copy[blankX][blankY + 1] = 0;
            neighbors.add(new Board(copy));
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] copy = copyTiles(tiles, size);
        int randX1 = 0, randY1 = 0, randX2 = randX1 + 1, randY2 = randY1 + 1;
        if (copy[randX1][randY1] == 0) randX1++;
        if (copy[randX2][randY2] == 0) randX2++;
        // System.out.println("randX1: " + randX1 + ", randY1: " + randY1 + ", randX2: " + randX2 + ", randY2: " + randY2);
        int temp = copy[randX1][randY1];
        copy[randX1][randY1] = copy[randX2][randY2];
        copy[randX2][randY2] = temp;
        return new Board(copy);
    }

    private int[][] copyTiles(int[][] tiles, int size) {
        int[][] copy = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                copy[i][j] = tiles[i][j];
            }
        }
        return copy;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        in = new In(args[1]);
        n = in.readInt();
        tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial2 = new Board(tiles);
        System.out.println(initial);
        //System.out.println(initial.dimension());
        //System.out.println(initial.hamming());
        System.out.println("mahattan: " + initial.manhattan());
        //System.out.println(initial.isGoal());
        System.out.println(initial.equals(initial2));
        System.out.println(initial.twin());
        //System.out.println(initial);
        //for (Board b: initial.neighbors()) {System.out.println(b);}
    }
}