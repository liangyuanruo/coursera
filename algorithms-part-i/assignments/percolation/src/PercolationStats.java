/**
 * Created by yuanruoliang on 1/8/17.
 */
import java.lang.Math;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    // perform trials independent experiments on an n-by-n grid
    private final double mean, stddev, confidenceLo, confidenceHi;

    public PercolationStats(int n, int trials){
        if( n <= 0 ) throw new IllegalArgumentException("n is less than zero.");
        if( trials <= 0 ) throw new IllegalArgumentException("trials is less than zero.");

        double[] thresholds = new double[trials];

        // Loop over trials
        for ( int i = 0; i < trials; i++ ){

            Percolation p = new Percolation(n); //keep replacing Percolation object

            //Keep opening
            while( !p.percolates() ){
                int x = StdRandom.uniform(n) + 1;
                int y = StdRandom.uniform(n) + 1;
                if( !p.isOpen(x, y) ){
                    p.open(x, y);
                }
            }
            thresholds[i] = (double)p.numberOfOpenSites()/(n*n); //Store result
        }

        this.mean = StdStats.mean(thresholds);
        this.stddev = n == 1 ? Double.NaN : StdStats.stddev(thresholds); //stddev of single value is undefined
        this.confidenceLo = this.mean - 1.96 * this.stddev / Math.sqrt( (double)trials );
        this.confidenceHi = this.mean + 1.96 * this.stddev / Math.sqrt( (double)trials );
    }

    // sample mean of percolation threshold
    public double mean(){return this.mean;}

    // sample standard deviation of percolation threshold
    public double stddev(){return this.stddev;}

    // low  endpoint of 95% confidence interval
    public double confidenceLo(){return confidenceLo;}

    // high endpoint of 95% confidence interval
    public double confidenceHi(){return confidenceHi;}

    public static void main(String[] args){

        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats s = new PercolationStats(n, T);

        StdOut.printf("mean\t\t\t\t\t = %f\n", s.mean());
        StdOut.printf("stddev\t\t\t\t\t = %f\n", s.stddev());
        StdOut.printf("95%% confidence interval\t = [%f, %f]\n", s.confidenceLo(), s.confidenceHi());

        return;
    }
}