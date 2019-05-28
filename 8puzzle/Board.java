import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private int gridLen;
    private int[][] grid;
    private int zeroi;
    private int zeroj;
    private int hamming;
    private int manhattan;

    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        grid = blocks;
        gridLen = grid[0].length;
        findZero();
        hamming = calcHamming();
        manhattan = calcManhattan();
    }

    public int dimension() {
        return gridLen;
    }

    public int hamming() {
        return hamming;
    }

    public int manhattan() {
        return manhattan;
    }

    public boolean isGoal() {
        return equalsGrid(grid, calcGoal());
    }

    public Board twin() {
        int[][] twinGrid = new int[gridLen][gridLen];
        for (int i = 0; i < gridLen; i++) {
            twinGrid[i] = grid[i].clone();
        }
        int count = 0;
        int ai = 0;
        int aj = 0;
        int bi = 0;
        int bj = 0;

        for (int i = 0; i < gridLen; i++) {
            for (int j = 0; j < gridLen; j++) {
                if (twinGrid[i][j] != 0) {
                    if (count == 0) {
                        ai = i;
                        aj = j;
                        count++;
                    }
                    else if (count == 1) {
                        if (i != ai && j != aj) {
                            ai = i;
                            aj = j;
                        }
                        else {
                            bi = i;
                            bj = j;
                            break;
                        }

                    }
                }
            }
        }

        int tmp = twinGrid[ai][aj];
        twinGrid[ai][aj] = twinGrid[bi][bj];
        twinGrid[bi][bj] = tmp;

        Board twin = new Board(twinGrid);
        return twin;
    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.gridLen != that.gridLen) return false;
        return equalsGrid(this.grid, that.grid);

    }

    public Iterable<Board> neighbors() {
        Stack<Board> nbs = new Stack<>();
        // StdOut.printf("zeroi is %d, zeroj is %d \n", zeroi, zeroj);
        if (zeroj - 1 >= 0) { // has left
            // StdOut.println("Has left");
            int[][] gLeft = swap(grid, zeroi, zeroj, zeroi, zeroj - 1);
            Board bLeft = new Board(gLeft);
            nbs.push(bLeft);
        }
        if (zeroj + 1 <= gridLen - 1) { // has right
            // StdOut.println("Has right");
            int[][] gRight = swap(grid, zeroi, zeroj, zeroi, zeroj + 1);
            Board bRight = new Board(gRight);
            nbs.push(bRight);
        }

        if (zeroi - 1 >= 0) { // has up
            // StdOut.println("Has up");
            int[][] gUp = swap(grid, zeroi, zeroj, zeroi - 1, zeroj);
            Board bUp = new Board(gUp);
            nbs.push(bUp);
        }

        if (zeroi + 1 <= gridLen - 1) { // has down
            // StdOut.println("Has down");
            int[][] gDown = swap(grid, zeroi, zeroj, zeroi + 1, zeroj);
            Board bDown = new Board(gDown);
            nbs.push(bDown);
        }
        return nbs;
    }

    public String toString() {

        StringBuilder s = new StringBuilder();
        s.append(gridLen + "\n");
        for (int i = 0; i < gridLen; i++) {
            for (int j = 0; j < gridLen; j++) {
                s.append(String.format("%2d ", grid[i][j]));
            }
            s.append("\n");
        }
        return s.toString();

    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        // StdOut.println("---Initial Board----");

        // StdOut.println(initial);
        // StdOut.println("----Neighbours----");
        // Iterable<Board> nbs = initial.neighbors();
        // for (Board nb : nbs) {
        // StdOut.println(nb);
        // StdOut.println("---nb---");
        // }
        StdOut.println(initial);
        StdOut.println(initial.twin());
    }

    private void findZero() {
        findLoop:
        for (int i = 0; i < gridLen; i++) {
            for (int j = 0; j < gridLen; j++) {
                if (grid[i][j] == 0) {
                    zeroi = i;
                    zeroj = j;
                    break findLoop;
                }
            }
        }
    }

    private int[][] calcGoal() {
        int[][] g = new int[gridLen][gridLen];
        for (int i = 0; i < gridLen; i++) {
            for (int j = 0; j < gridLen; j++) {
                g[i][j] = shouldContain(i, j);
            }
        }
        return g;
    }

    private int shouldContain(int row, int col) {
        if (row == gridLen - 1 && col == gridLen - 1) return 0;
        return row * gridLen + col + 1;
    }

    private int[] shouldBe(int num) {
        int row = (num - 1) / gridLen;
        int col = (num - 1) % gridLen;
        int[] r = { row, col };
        return r;
    }

    private int calcHamming() {
        int score = 0;
        for (int i = 0; i < gridLen; i++) {
            for (int j = 0; j < gridLen; j++) {
                if (i == gridLen - 1 && j == gridLen - 1) {
                    //  do nothing
                }
                else if (grid[i][j] != shouldContain(i, j)) {
                    score++;
                }
            }
        }
        return score;
    }

    private int calcManhattan() {
        int score = 0;
        for (int i = 0; i < gridLen; i++) {
            for (int j = 0; j < gridLen; j++) {
                if (grid[i][j] == 0) {
                    //  do nothing
                }
                else if (grid[i][j] != shouldContain(i, j)) {
                    int num = grid[i][j];
                    int[] shouldBe = shouldBe(num);
                    int shouldi = shouldBe[0];
                    int shouldj = shouldBe[1];
                    score = score + Math.abs(shouldi - i) + Math.abs(shouldj - j);
                }
            }
        }
        return score;
    }

    private boolean equalsGrid(int[][] x, int[][] y) {
        for (int i = 0; i < gridLen; i++) {
            for (int j = 0; j < gridLen; j++) {
                if (x[i][j] != y[i][j]) return false;
            }
        }
        return true;
    }

    private int[][] swap(int[][] gridToSwap, int xi, int xj, int yi, int yj) {
        // StdOut.printf("Now swap (%d, %d) with (%d, %d)\n", xi, xj, yi, yj);
        // StdOut.printf("Before swap: x is %d, y is %d\n", gridToSwap[xi][xj], gridToSwap[yi][yj]);
        int[][] g = new int[gridLen][gridLen];
        for (int i = 0; i < gridLen; i++) {
            g[i] = gridToSwap[i].clone();
        }
        int tmp = g[xi][xj];
        g[xi][xj] = g[yi][yj];
        g[yi][yj] = tmp;
        // StdOut.printf("After swap: x is %d, y is %d\n", g[xi][xj], g[yi][yj]);
        return g;
    }
}
