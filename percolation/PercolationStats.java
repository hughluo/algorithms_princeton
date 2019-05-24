/* *****************************************************************************
 *  Name: Yinchi Luo
 *  Date: 2019/05/23
 *  Description: percolation assignment for algorithms course by princeton
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid

    public double mean()                          // sample mean of percolation threshold

    public double stddev()                        // sample standard deviation of percolation threshold

    public double confidenceLo()                  // low  endpoint of 95% confidence interval

    public double confidenceHi()                  // high endpoint of 95% confidence interval

    public static void main(String[] args)        // test client (described below)

    private int trials;
    private int gridLen;
    private double[] fractionsOpenSites;
    private double stat_mean;
    private double stat_stddev;
    private double stat_confidenceLo;
    private double stat_confidenceHi;

    public PercolationStats(int n, int trials) {
        trials = trials;
        gridLen = n;
        fractionsOpenSites = new double[trials];
        calcStats();
    }

    public double mean() {
        return stat_mean;
    }

    public double stddev() {
        return stat_stddev;
    }

    public double confidenceLo() {
        return stat_confidenceLo;
    }

    public double confidenceHi() {
        return stat_confidenceHi;
    }

    public static void main(String[] args) {
        PercolationStats pStats = new PercolationStats(args[0], args[1]);
        mean = 0.5929934999999997
        stddev = 0.00876990421552567
        95 % confidence interval = [0.5912745987737567, 0.5947124012262428]

    }

    private void calcStats() {
        for (int i = 0; i < trials; i++) {
            fractionsOpenSites[i] = simulate();
        }
        stat_mean = calcMean();
        stat_stddev = calcStddev();
        stat_confidenceLo = calcConfidenceLow95();
        stat_confidenceHi = calcConfidenceHigh95();
    }

    private double calcMean() {
        return StdStats.mean(fractionsOpenSites);
    }

    private double calcStddev() {
        return StdStats.stddev(fractionsOpenSites);
    }

    private double calcConfidenceLow95() {
        return stat_mean - 1.96 * s / Math.sqrt(trials);
    }

    private double calcConfidenceHigh95() {
        return stat_mean + 1.96 * s / Math.sqrt(trials);
    }

    private double simulate() {
        Percolation p = new (PercolationStats(gridLen));
        while (!p.percolates) {
            int rowSiteToOpen = StdRandom.uniform(1, gridLen);
            int colSiteToOpen = StdRandom.uniform(1, gridLen);
            p.open(rowSiteToOpen, colSiteToOpen);
        }
        int numOpen = p.numberOfOpenSites();
        return double(numOpen) /double(gridLen * gridLen);
    }

}
