import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private final double[] threshold;
	private static final double FACTOR = 1.96;
	private final double mean;
	private final double stddev;

	// perform trials independent experiments on an n-by-n grid
	public PercolationStats(int n, int trials) {

		if(n <= 0 || trials <= 0) {
			throw new java.lang.IllegalArgumentException();
		}

		int size = n;

		threshold = new double[trials];

		for(int i = 0; i < trials; i++) {
			Percolation pl = new Percolation(n);
			while(!pl.percolates()) {
				pl.open(StdRandom.uniform(1, size+1), StdRandom.uniform(1, size+1));
			}

			threshold[i] = (double)pl.numberOfOpenSites()/(size * size);
		}
		
		mean = StdStats.mean(threshold);
		stddev = StdStats.stddev(threshold);
	}

	// sample mean of percolation threshold
	public double mean() {
		
		return mean;
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		return stddev;
	}

	// low  endpoint of 95% confidence interval
	public double confidenceLo() {
		return mean - FACTOR * stddev/Math.sqrt(threshold.length);
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return mean + FACTOR * stddev/Math.sqrt(threshold.length);
	}

	// test client (described below)
	public static void main(String[] args) {   
		PercolationStats test = new PercolationStats(200, 100);
		System.out.println(test.mean());
		System.out.println(test.stddev());
		System.out.println(test.confidenceLo());
		System.out.println(test.confidenceHi());

	}
}
