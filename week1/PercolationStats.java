import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final int trials;
    private final int[] sites;
    private final int n;
    private Double meanCache;
    private Double stddevCache;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.n = n;
        this.trials = trials;
        sites = new int[trials];
        for (int trial = 0; trial < trials; trial++) {
            sites[trial] = percolationThreshold();
        }
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }

    public double mean() {
        if (meanCache == null) {
            meanCache = StdStats.mean(sites) / (n * n);
        }
        return meanCache;
    }

    public double stddev() {
        if (stddevCache == null) {
            stddevCache = StdStats.stddev(sites) / (n * n);
        }
        return stddevCache;
    }

    public double confidenceLo() {
        return mean() - ((CONFIDENCE_95 * stddev()) / Math.sqrt(trials));
    }

    public double confidenceHi() {
        return mean() + ((CONFIDENCE_95 * stddev()) / Math.sqrt(trials));
    }

    private int percolationThreshold() {
        Percolation perc = new Percolation(n);
        while (!perc.percolates()) {
            int x = StdRandom.uniform(n) + 1;
            int y = StdRandom.uniform(n) + 1;
            while (perc.isOpen(x, y)) {
                x = StdRandom.uniform(n) + 1;
                y = StdRandom.uniform(n) + 1;
            }
            perc.open(x, y);
        }
        return perc.numberOfOpenSites();
    }

}