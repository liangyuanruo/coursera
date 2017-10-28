import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF quickUnion;
    private final WeightedQuickUnionUF quickUnionTopOnly; //To avoid "backflow" bug

    private final int gridSize;
    private final boolean[][] grid;

    private final int VIRTUAL_TOP;
    private final int VIRTUAL_BOTTOM;

    public Percolation(int n) { // create N-by-N grid, with all sites blocked
        if ( n <= 0 ) throw new IllegalArgumentException("N must be at least 1");

        gridSize = n;
        grid = new boolean[n][n];

        quickUnion = new WeightedQuickUnionUF(n * n + 2);
        quickUnionTopOnly = new WeightedQuickUnionUF(n * n + 1);

        VIRTUAL_TOP = 0;
        VIRTUAL_BOTTOM = n * n + 1;
    }

    private void connectIfOpen(int fieldIndex, int i, int j) {
        if ( isValidIdx(i,j) && isOpen(i, j) ) {
            int otherIdx = getUnionIdx(i, j);
            quickUnion.union(otherIdx, fieldIndex);
            quickUnionTopOnly.union(otherIdx, fieldIndex);
        }

    }

    public void open(int row, int col) {    // open site (row i, column j) if it is not open already
        if( !isValidIdx(row, col) ) throw new IllegalArgumentException("Invalid indices supplied.");

        if ( !isOpen(row, col) ) {
            int fieldIndex = getUnionIdx(row, col);

            if (row == 1) {
                quickUnion.union(VIRTUAL_TOP, fieldIndex);
                quickUnionTopOnly.union(VIRTUAL_TOP, fieldIndex);
            }
            if (row == gridSize) {
                quickUnion.union(VIRTUAL_BOTTOM, fieldIndex);
            }
            grid[row - 1][col - 1] = true;
            connectIfOpen(fieldIndex, row + 1, col);
            connectIfOpen(fieldIndex, row - 1, col);
            connectIfOpen(fieldIndex, row, col - 1);
            connectIfOpen(fieldIndex, row, col + 1);

        }
    }
    private int getUnionIdx(int i, int j) { return (i - 1) * gridSize + j; }

    private boolean isValidIdx(int i, int j){
        if (i < 1 || i > gridSize || j < 1 || j > gridSize) return false;
        return true;
    }

    public boolean isOpen(int row, int col) {   // is site (row i, column j) open?
        if (!isValidIdx(row, col)) throw new IllegalArgumentException("Invalid indices supplied.");
        return grid[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {   // is site (row i, column j) connected to top?
        if( !isValidIdx(row, col) ) throw new IllegalArgumentException("Invalid indices supplied.");
        if ( isOpen(row, col) ) {
            int fieldIndex = getUnionIdx(row, col);
            return quickUnionTopOnly.connected(VIRTUAL_TOP, fieldIndex);
        }
        return false;
    }

    public int numberOfOpenSites(){
        int count = 0;
        for ( int i = 1; i <= gridSize; i++ ){
            for ( int j = 1; j <= gridSize; j++ ){
                if( isOpen(i, j) ) count++;
            }
        }
        return count;
    }

    public boolean percolates() {   // does the system percolate?
        return quickUnion.connected(VIRTUAL_TOP, VIRTUAL_BOTTOM);
    }

}