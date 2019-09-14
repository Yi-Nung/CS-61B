package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF ds;
    private WeightedQuickUnionUF dsNoBw;
    private int[][] arr;
    private int N;
    private int op;

    /** create N-by-N grid, with all sites initially blocked */
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("N should be positive.");
        }
        ds = new WeightedQuickUnionUF(N * N + 2);
        dsNoBw = new WeightedQuickUnionUF(N * N + 1);
        arr = new int[N][N];
        this.N = N;
        op = 0;
    }

    /** Helper method to change the grid into one dimension index */
    private int xyTo1D(int r, int c) {
        return N * r + c;
    }

    /** open the site (row, col) if it is not open already
     *  opened sites have value 1, and blocked sites have value 0.
     * */
    public void open(int row, int col) {
        if (row < 0 || row > N - 1 || col < 0 || col > N - 1) {
            throw new java.lang.IndexOutOfBoundsException("invalid row or column indices");
        }
        if (isOpen(row, col)) {
            return;
        }
        op += 1;
        arr[row][col] = 1;
        int ind = xyTo1D(row, col);
        if (row == 0) {
            ds.union(ind, N * N);
            dsNoBw.union(ind, N * N);
        }
        if (row == N - 1) {
            ds.union(ind, N * N + 1);
        }
        if (row > 0 && isOpen(row - 1, col)) {
            ds.union(ind, ind - N);
            dsNoBw.union(ind, ind - N);
        }
        if (row < N - 1 && isOpen(row + 1, col)) {
            ds.union(ind, ind + N);
            dsNoBw.union(ind, ind + N);
        }
        if (col > 0 && isOpen(row, col - 1)) {
            ds.union(ind, ind - 1);
            dsNoBw.union(ind, ind - 1);
        }
        if (col < N - 1 && isOpen(row, col + 1)) {
            ds.union(ind, ind + 1);
            dsNoBw.union(ind, ind + 1);
        }
    }

    /** is the site (row, col) open? */
    public boolean isOpen(int row, int col) {
        if (row < 0 || row > N - 1 || col < 0 || col > N - 1) {
            throw new java.lang.IndexOutOfBoundsException("invalid row or column indices");
        }
        return arr[row][col] == 1;
    }

    /** is the site (row, col) full? */
    public boolean isFull(int row, int col) {
        if (row < 0 || row > N - 1 || col < 0 || col > N - 1) {
            throw new java.lang.IndexOutOfBoundsException("invalid row or column indices");
        }
        int ind = xyTo1D(row, col);
        return dsNoBw.connected(ind, N * N);
    }

    /** number of open sites */
    public int numberOfOpenSites() {
        return op;
    }

    /** does the system percolate? */
    public boolean percolates() {
        return ds.connected(N * N, N * N + 1);
    }

    // use for unit testing(not required, but keep this here for the autograder)
    public static void main(String[] args) { };

}
