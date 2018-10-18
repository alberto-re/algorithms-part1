import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private final WeightedQuickUnionUF ufPercolation;
    private final WeightedQuickUnionUF ufFullness;
    private boolean[] sites;
    private int openSites;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;

        ufPercolation = new WeightedQuickUnionUF(n * n + 2);
        ufFullness = new WeightedQuickUnionUF(n * n + 1);

        sites = new boolean[n * n + 2];
        sites[0] = true;
        for (int i = 1; i < sites.length; i++) {
            sites[i] = false;
        }
        openSites = 0;
    }

    public void open(int row, int col) {
        validateIndices(row, col);
        if (sites[xyTo1D(row, col)])
            return;
        sites[xyTo1D(row, col)] = true;
        openSites = openSites + 1;
        connectNeighbors(row, col);
    }

    public boolean isOpen(int row, int col) {
        validateIndices(row, col);
        return sites[xyTo1D(row, col)];
    }

    public boolean isFull(int row, int col) {
        validateIndices(row, col);
        if (sites[xyTo1D(row, col)]) {
            if (ufFullness.connected(0, xyTo1D(row, col))) {
                return true;
            }
        }
        return false;
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return ufPercolation.connected(0, n * n + 1);
    }

    private void validateIndices(int row, int col) {
        if (!isValidIndex(row, col)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean isValidIndex(int row, int col) {
        return row >= 1 && col >= 1 && row <= n && col <= n;
    }

    private int xyTo1D(int row, int col) {
        return 1 + (col - 1 + ((row - 1) * n));
    }

    private void connectNeighbors(int row, int col) {
        if (row == 1) {
            ufPercolation.union(0, xyTo1D(row, col));
            ufFullness.union(0, xyTo1D(row, col));
        }
        if (isValidIndex(row - 1, col) && isOpen(row - 1, col)) {
            ufPercolation.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            ufFullness.union(xyTo1D(row, col), xyTo1D(row - 1, col));
        }
        if (isValidIndex(row + 1, col) && isOpen(row + 1, col)) {
            ufPercolation.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            ufFullness.union(xyTo1D(row, col), xyTo1D(row + 1, col));
        }
        if (isValidIndex(row, col - 1) && isOpen(row, col - 1)) {
            ufPercolation.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            ufFullness.union(xyTo1D(row, col), xyTo1D(row, col - 1));
        }
        if (isValidIndex(row, col + 1) && isOpen(row, col + 1)) {
            ufPercolation.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            ufFullness.union(xyTo1D(row, col), xyTo1D(row, col + 1));
        }
        if (row == n) {
            ufPercolation.union(xyTo1D(row, col), n * n + 1);
            sites[n * n + 1] = true;
        }
    }

}
