/* *****************************************************************************
 *  Name: Yinchi Luo
 *  Date: 2019/05/24
 *  Description: percolation assignment for algorithms course by princeton
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {

    private int trialTimes;
    private int gridLen;
    private double[] fractionsOpenSites;
    private double statMean;
    private double statStddev;
    private double statConfidenceLo;
    private double statConfidenceHi;
    private int[] closedSites;

    public PercolationStats(int n, int trials) {
        trialTimes = trials;
        gridLen = n;
        fractionsOpenSites = new double[trials];
        initClosedSites();
        calcStats();
    }

    public double mean() {
        return statMean;
    }

    public double stddev() {
        return statStddev;
    }

    public double confidenceLo() {
        return statConfidenceLo;
    }

    public double confidenceHi() {
        return statConfidenceHi;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats pStats = new PercolationStats(n, T);
        StdOut.printf("mean                    = %f\n", pStats.mean());
        StdOut.printf("stddev                  = %f\n", pStats.stddev());
        StdOut.printf("95%% confidence interval = [%f, %f]\n", pStats.confidenceLo(),
                      pStats.confidenceHi());

    }

    private void initClosedSites() {
        closedSites = new int[gridLen * gridLen];
        for (int i = 0; i < gridLen * gridLen; i++) {
            closedSites[i] = i + 1;
        }
    }

    private int[] indexToRowCol(int index) {
        int row = (index - 1) / gridLen + 1;
        int col = index % gridLen;
        if (col == 0) {
            col = gridLen;
        }
        int[] result = new int[] { row, col };
        return result;
    }

    private void calcStats() {
        for (int i = 0; i < trialTimes; i++) {
            //StdOut.printf("Simulate %d start\n", i);
            fractionsOpenSites[i] = simulate();
            //StdOut.printf("Simulate %d ended, fraction: %f\n", i, fractionsOpenSites[i]);

        }
        statMean = calcMean();
        statStddev = calcStddev();
        statConfidenceLo = calcConfidenceLow95();
        statConfidenceHi = calcConfidenceHigh95();
    }

    private double calcMean() {
        return StdStats.mean(fractionsOpenSites);
    }

    private double calcStddev() {
        return StdStats.stddev(fractionsOpenSites);
    }

    private double calcConfidenceLow95() {
        return statMean - 1.96 * statStddev / Math.sqrt(trialTimes);
    }

    private double calcConfidenceHigh95() {
        return statMean + 1.96 * statStddev / Math.sqrt(trialTimes);
    }


    private double simulate() {
        Percolation p = new Percolation(gridLen);

        StdRandom.shuffle(closedSites);


        for (int i = 0; i < gridLen * gridLen; i++) {
            if (p.percolates()) {
                break;
            }
            else {
                int indexSiteToOpen = closedSites[i];
                int[] rowCol = indexToRowCol(indexSiteToOpen);
                int rowSiteToOpen = rowCol[0];
                int colSiteToOpen = rowCol[1];
                p.open(rowSiteToOpen, colSiteToOpen);
            }
        }
        int numOpen = p.numberOfOpenSites();
        return (double) numOpen / (double) (gridLen * gridLen);
    }

}
