import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF quickFindUF;
    private boolean[][] grid;
    private int n;
    private int numberOfOpen = 0;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Index out of bounds");

        this.n = n;
        quickFindUF = new WeightedQuickUnionUF((n * n) + 2);
        grid = new boolean[n][n];
        for (int i = 1; i <= n; i++) {
            quickFindUF.union(0, map(1, i));
            quickFindUF.union((n * n) + 1, map(n, i));
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!validate(row, col)) throw new IllegalArgumentException("Index out of bounds");

        if (!isOpen(row, col)) {
            numberOfOpen++;
            grid[row - 1][col - 1] = true;
            if (validate(row - 1, col) && isOpen(row - 1, col)) quickFindUF.union(map(row, col), map(row - 1, col));
            if (validate(row + 1, col) && isOpen(row + 1, col)) quickFindUF.union(map(row, col), map(row + 1, col));
            if (validate(row, col - 1) && isOpen(row, col - 1)) quickFindUF.union(map(row, col), map(row, col - 1));
            if (validate(row, col + 1) && isOpen(row, col + 1)) quickFindUF.union(map(row, col), map(row, col + 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!validate(row, col)) throw new IllegalArgumentException("Index out of bounds");

        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!validate(row, col)) throw new IllegalArgumentException("Index out of bounds");
        return isOpen(row, col) && quickFindUF.find(map(row, col)) == quickFindUF.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpen;
    }


    // does the system percolate?
    public boolean percolates() {
        return quickFindUF.find(0) == quickFindUF.find((n * n) + 1);
    }

    private int map(int i, int j) {
        return ((n * (i - 1)) + j);
    }

    private boolean validate(int row, int col) {
        if ((row > 0) && (row <= n) && (col > 0) && (col <= n)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        
    }
}
