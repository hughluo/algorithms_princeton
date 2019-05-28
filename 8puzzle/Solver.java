/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private MinPQ<SearchNode> pqMain;
    private MinPQ<SearchNode> pqTwin;
    private SearchNode nodeGoal;
    private Stack<Board> solution;
    private boolean solvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("initial board is null");
        solution = new Stack<>();

        pqMain = new MinPQ<SearchNode>();
        SearchNode nodeInit = new SearchNode(initial, 0, null);
        pqMain.insert(nodeInit);

        pqTwin = new MinPQ<SearchNode>();
        SearchNode nodeInitTwin = new SearchNode(initial.twin(), 0, null);
        pqTwin.insert(nodeInitTwin);


        int move = 0;
        while (true) {
            move++;
            // Main Priority Queue
            SearchNode nodeCurrent = pqMain.delMin();
            if (nodeCurrent.board.isGoal()) {
                solvable = true;
                nodeGoal = nodeCurrent;
                createSolution();
                break;
            }
            Iterable<Board> nbs = nodeCurrent.board.neighbors();
            for (Board n : nbs) {
                if (nodeCurrent.lastNode == null || !n.equals(nodeCurrent.lastNode.board)) {
                    pqMain.insert(new SearchNode(n, move, nodeCurrent));
                }
            }

            // Twin Priority Queue
            SearchNode nodeCurrentTwin = pqTwin.delMin();
            if (nodeCurrentTwin.board.isGoal()) {
                solvable = false;
                solution = null;
                break;
            }
            Iterable<Board> nbsTwin = nodeCurrentTwin.board.neighbors();
            for (Board n : nbsTwin) {
                if (nodeCurrentTwin.lastNode == null || !n.equals(nodeCurrentTwin.lastNode.board)) {
                    pqTwin.insert(new SearchNode(n, move, nodeCurrentTwin));
                }
            }

        }
    }


    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        else return nodeGoal.mv;

    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

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

    private class SearchNode implements Comparable<SearchNode> {
        private int mv;
        private int manhattan;
        private Board board;
        private SearchNode lastNode;

        public SearchNode(Board b, int move, SearchNode predecessor) {
            mv = move;
            board = b;
            lastNode = predecessor;
            manhattan = b.manhattan();
        }

        @Override
        public int compareTo(SearchNode that) {
            return (manhattan + mv) - (that.manhattan + that.mv);
        }
    }

    private void createSolution() {
        SearchNode n = nodeGoal;
        while (true) {
            solution.push(n.board);
            if (n.lastNode == null) {
                break;
            }
            n = n.lastNode;
        }
    }

}

