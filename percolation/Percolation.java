/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


//import edu.princeton.cs.algs4.StdRandom;
//import edu.princeton.cs.algs4.StdStats;

public class Percolation {
    private WeightedQuickUnionUF sites;
    private int gridLen;
    private int indexVirtualBottom;
    private int[] sitesStatus;

    /**
     * create n-by-n grid, with all sites blocked
     *
     * @param n the len of grid n * n
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException("Invalid grid length");
        }

        gridLen = n;
        indexVirtualBottom = gridLen * gridLen + 1;
        sites = new WeightedQuickUnionUF(indexVirtualBottom + 1);
        sitesStatus = new int[indexVirtualBottom + 1];
    }

    // Open site (row, col) if it is not open already
    public void open(int row, int col) {
        //System.out.printf("open %d, %d %n", row, col);

        int index = getIndex(row, col);

        if (!isOpen(row, col)) {
            sitesStatus[index] = 1;
            if (row == 1) {
                sites.union(0, index);
            }

            if (row == gridLen) {
                sites.union(indexVirtualBottom, index);
            }

            connectOpenNeighbor(row, col);
        }
    }

    // Is site (row, col) open?
    public boolean isOpen(int row, int col) {
        int index = getIndex(row, col);
        return sitesStatus[index] == 1;

    }


    // Connect a just have been opened site with his neighboring open site
    private void connectOpenNeighbor(int row, int col) {
        int index = getIndex(row, col);
        int neighbors[] = getNeighbors(row, col);
        for (int i = 0; i < 4; i++) {
            int indexNeighbor = neighbors[i];
            if (indexNeighbor != -1 && sitesStatus[indexNeighbor] == 1) {
                sites.union(index, indexNeighbor);
            }
        }

    }

    private int[] getNeighbors(int row, int col) {
        int neighbors[] = new int[4];
        neighbors[0] = (isValid(row - 1, col)) ? getIndex(row - 1, col) : -1;
        neighbors[1] = (isValid(row + 1, col)) ? getIndex(row + 1, col) : -1;
        neighbors[2] = (isValid(row, col - 1)) ? getIndex(row, col - 1) : -1;
        neighbors[3] = (isValid(row, col + 1)) ? getIndex(row, col + 1) : -1;
        return neighbors;
    }

    private boolean isValid(int row, int col) {
        return !(row < 1 || row > gridLen || col < 1 || col > gridLen);
    }

    // Is site (row, col) full?
    public boolean isFull(int row, int col) {
        return sites.connected(0, getIndex(row, col));
    }

    // Get number of open sites
    public int numberOfOpenSites() {
        int num = 0;
        for (int i = 1; i < indexVirtualBottom; i++) {
            num += sitesStatus[i];
        }
        return num;
    }

    public boolean percolates() {
        // does the system percolate?
        return sites.connected(0, indexVirtualBottom);
    }

    private int getIndex(int row, int col) {
        validate(row, col);
        return row * gridLen - (gridLen - col);
    }

    // Validate the row and column number
    private void validate(int row, int col) {
        if (!isValid(row, col)) {
            String warning = String.format("Invalid row %d or column %d", row, col);
            throw new java.lang.IllegalArgumentException(warning);
        }
    }

    public static void main(String[] args) {
        // test client (optional)
    }
}

