import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private boolean[][] grid;
	private static final int top = 0;
	private final int bottom;
	private final int size;
	private final WeightedQuickUnionUF wqf;
	private final WeightedQuickUnionUF checkFull;
	private int count=0;

	// create n-by-n grid, with all sites blocked
	public Percolation(int n) {
		if(n <= 0) {
			throw new java.lang.IllegalArgumentException();
		}

		size = n;
		grid = new boolean[size][size];
		bottom = size * size + 1;
		wqf = new WeightedQuickUnionUF(size * size + 2);
		checkFull = new WeightedQuickUnionUF(size * size + 1);
	}    

	private int getIndex (int row, int col) {
		return size * (row-1) + col;
	}

	// open site (row, col) if it is not open already
	public void open(int row, int col) {


		if (row > size || col > size || row <= 0 || col <= 0) {
			throw new java.lang.IllegalArgumentException();
		}

		if (grid[row-1][col-1]) {
			return;
		}else {
			grid[row-1][col-1] = true;
			count++;
		}

		if (row == 1) {
			wqf.union(getIndex(row, col), top);
			checkFull.union(getIndex(row, col), top);
		}

		if(row == size) {
			wqf.union(getIndex(row, col), bottom);
		}

		// check block on the top
		if (row > 1 && isOpen(row-1, col)) {
			wqf.union(getIndex(row, col), getIndex(row-1, col));
			checkFull.union(getIndex(row, col), getIndex(row-1, col));
		}

		// check block on the bottom 
		if (row < size && isOpen(row+1, col)) {
			wqf.union(getIndex(row, col), getIndex(row+1, col));
			checkFull.union(getIndex(row, col), getIndex(row+1, col));
		}

		// check block on the left
		if (col > 1 && isOpen(row, col-1)) {
			wqf.union(getIndex(row, col), getIndex(row, col-1));
			checkFull.union(getIndex(row, col), getIndex(row, col-1));
		}

		// check block on the left
		if (col < size && isOpen(row, col+1)) {
			wqf.union(getIndex(row, col), getIndex(row, col+1));
			checkFull.union(getIndex(row, col), getIndex(row, col+1));
		}
	}   

	// is site (row, col) open?
	public boolean isOpen(int row, int col) {
		if(row <= size && col <= size && row > 0 && col > 0) {
			return grid[row-1][col-1];
		}else {
			throw new java.lang.IllegalArgumentException();
		}
	}      

	// is site (row, col) full?
	public boolean isFull(int row, int col) {

		if(row <= size && col <= size && row > 0 && col > 0) {  
			return checkFull.connected(top, getIndex(row,col));
		}else {
			throw new java.lang.IllegalArgumentException();
		}
	}  

	// number of open sites
	public int numberOfOpenSites() {

		return count;
	}       

	// does the system percolate?
	public boolean percolates() {
		return wqf.connected(top, bottom); 
	}              

	// test client (optional)
	public static void main(String[] args) { 
		Percolation test = new Percolation(3);
		test.open(1, 3);
		test.open(2, 3);
		test.open(3, 3);
		test.open(3, 1);



		System.out.println(Boolean.toString(test.isFull(3,1)));
	}

}
