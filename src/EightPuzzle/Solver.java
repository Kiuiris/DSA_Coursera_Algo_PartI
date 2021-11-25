package EightPuzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Solver {
    private Node solutionPath;
    private int moves;
    private boolean solvable;

    private class Node implements Comparable<Node> {
        private final Board board;
        private final int moves;
        private final Node prev;
        private final int priority;

        public Node(Board board, int moves, Node prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            this.priority = board.manhattan() + moves;
        }

        @Override
        public int compareTo(Node o) {
            if (this.priority > o.priority) {
                return 1;
            }
            if (this.priority < o.priority) {
                return -1;
            }
            return 0;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        this.moves = 0;
        this.solvable = true;

        MinPQ<Node> search = new MinPQ<>();
        search.insert(new Node(initial, 0, null));

        MinPQ<Node> canSolve = new MinPQ<>();
        canSolve.insert(new Node(initial.twin(), 0, null));

        while (solutionPath == null && isSolvable()) {
            Node curSearch = search.delMin();
            if (curSearch.board.isGoal()) {
                solutionPath = curSearch;
                moves = curSearch.moves;
                return;
            }

            for (Board i: curSearch.board.neighbors()) {
                if (curSearch.prev != null && i.equals(curSearch.prev.board)) {
                    continue;
                }
                search.insert(new Node(i, curSearch.moves + 1, curSearch));
            }

            //Node curTwinSearch
            curSearch = canSolve.delMin();
            if (curSearch.board.isGoal()) {
                solvable = false;
                moves = -1;
                return;
            }

            for (Board i: curSearch.board.neighbors()) {
                if (curSearch.prev != null && i.equals(curSearch.prev.board)) {
                    continue;
                }
                canSolve.insert(new Node(i, curSearch.moves + 1, curSearch));
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        Board[] solution = new Board[solutionPath.moves + 1];

        for (int i = solution.length - 1; i >= 0; i--) {
            solution[i] = solutionPath.board;
            solutionPath = solutionPath.prev;
        }

        ArrayList<Board> result = new ArrayList<>();
        for (Board b: solution) {
            result.add(b);
        }

        return result;
    }

    //public Iterable<Board> solutionList() {return solution;}

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
