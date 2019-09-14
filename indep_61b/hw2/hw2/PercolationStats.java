package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double[] fracs;
    private int T;

    /** perform T independent experiments on an N-by-N grid */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException("N and T should be positive.");
        }
        this.T = T;
        fracs = new double[T];
        for (int i = 0; i < T; i += 1) {
            Percolation perc = pf.make(N);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                perc.open(row, col);
            }
            fracs[i] = (double) perc.numberOfOpenSites() / (N * N);
        }
    }

    /** sample mean of percolation threshold */
    public double mean() {
        return StdStats.mean(fracs);
    }

    /** sample standard deviation of percolation threshold */
    public double stddev() {
        return StdStats.stddev(fracs);
    }

    /** low endpoint of 95% confidence interval */
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.pow(T, 0.5);
    }

    /** high endpoint of 95% confidence interval */
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.pow(T, 0.5);
    }
}

