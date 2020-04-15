import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats{

    private double[] probablities;
    private int totalTrails;
    private Percolation pr;
    private int confidenceConstant;

    public PercolationStats(int n, int trails){
            if(n <= 0 || trails <= 0){
                throw new IllegalArgumentException("n and t should be greater than 0");
            }
            totalTrails = trails;
            probablities = new double[totalTrails];

            for(int trail = 0; trail < totalTrails; trail++){
                pr = new Percolation(n);
                int openSites = 0;
                while (!pr.percolates()){
                    int row = StdRandom.uniform(1,n+1);
                    int col = StdRandom.uniform(1,n+1);
                    if(!pr.isOpen(row,col)){
                        pr.open(row,col);
                    }
                }
                double probablity = (double) pr.numberOfOpenSites() / (n*n);
                probablities[trail] = probablity;
            }
    }

    public double mean() {
        return StdStats.mean(probablities);
    }

    /**
     * Sample standard deviation of percolation threshold.
     */
    public double stddev() {
        return StdStats.stddev(probablities);
    }

    /**
     * Returns lower bound of the 95% confidence interval.
     */
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(totalTrails));
    }

    /**
     * Returns upper bound of the 95% confidence interval.
     */
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(totalTrails));
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(N, T);

        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = [ " + ps.confidenceLo() + ", " + ps.confidenceHi() + " ]");
    }


}