import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import java.lang.Math;

public class Board {

    private final int[][] blocks;
    private final int n;

    // construct a board from an n-by-n array of blocks
    public Board(int[][] blocks){

        this.n = blocks.length;
        this.blocks = new int[this.n][this.n];

        for ( int i = 0; i < n; i++ )
            for ( int j = 0; j < n; j++ )
                this.blocks[i][j] = blocks[i][j];
    }
    // (where blocks[i][j] = block in row i, column j)

    // board dimension n
    public int dimension(){
        return this.n;
    }

    // check if a block is in the correct place
    private boolean inPlace(int i, int j) {
        if( blocks[i][j] == 0 ) return true; //empty square always in correct location
        return i * n + j+1 == blocks[i][j];
    }

    // number of blocks out of place
    public int hamming(){
        int count = 0;
        for ( int i = 0; i < n; i++ )
            for ( int j = 0; j < n; j++ )
                if( !inPlace(i,j) ) count++;
        return count;
    }

    // Manhattan distance of a single block
    private int manhattanDist(int i, int j) {
        if( blocks[i][j] == 0 ) return 0; //empty square always in correct location

        int correctRow = (blocks[i][j] - 1) / this.n;
        int correctCol = (blocks[i][j] - 1) % this.n;

        return Math.abs(i - correctRow) + Math.abs(j - correctCol);
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan(){
        int dist = 0;
        for ( int i = 0; i < n; i++ )
            for ( int j = 0; j < n; j++ )
                dist += manhattanDist(i, j);
        return dist;
    }

    // is this board the goal board?
    public boolean isGoal(){
        for ( int i = 0; i < n; i++ )
            for ( int j = 0; j < n; j++ )
                if( !inPlace(i, j) ) return false;
        return true;
    }

    private void swapBlocks(int[][] blocks, int i1, int j1, int i2, int j2) {
        int tmp = blocks[i2][j2];
        blocks[i2][j2] = blocks[i1][j1];
        blocks[i1][j1] = tmp;
    }

    private int[][] blockCopy(){
        int[][] newBlocks = new int[n][n];
        //Copy array
        for ( int i = 0; i < n; i++ )
            for ( int j = 0; j < n; j++ )
                newBlocks[i][j] = blocks[i][j];
        return newBlocks;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin(){
        int[][] newBlocks = blockCopy();
        int i1, j1, i2, j2;

        do { //Randomly find two random blocks to switch
            i1 = StdRandom.uniform(this.n);
            j1 = StdRandom.uniform(this.n);
            i2 = StdRandom.uniform(this.n);
            j2 = StdRandom.uniform(this.n);
            //System.out.printf("i1 = %d\n", i1);
            //System.out.printf("j1 = %d\n", j1);
            //System.out.printf("i2 = %d\n", i2);
            //System.out.printf("j2 = %d\n", j2);
        } while( ( i1 == j1 && i2 == j2 ) || //Not the same block
                   newBlocks[i1][j1] == 0 || //Not empty squares
                   newBlocks[i2][j2] == 0   );

        swapBlocks(newBlocks, i1, j1, i2, j2);

        return new Board(newBlocks);
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y){
        if ( y == this ) return true; //Same object
        if ( y == null ) return false; //Empty object
        if ( this.getClass() != y.getClass() ) return false; //Same class

        Board that = (Board) y;
        if ( that.dimension() != this.dimension() ) return false; //Check dimensions

        // Compare blocks in every position
        for ( int i = 0; i < n; i++ )
            for ( int j = 0; j < n; j++ )
                if( this.blocks[i][j] != that.blocks[i][j] )
                    return false;

        return true;
    }

    private void swap(int[][] blocks, int i, int j, int k, int l){
        int tmp = blocks[i][j];
        blocks[i][j] = blocks[k][l];
        blocks[k][l] = tmp;
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        Stack<Board> s = new Stack<>();

        //Find blank "zero" square
        int iZero = 0, jZero = 0;

        find:
        for ( iZero = 0; iZero < n; iZero++ )
            for ( jZero = 0; jZero < n; jZero++ )
                if ( blocks[iZero][jZero] == 0 )
                    break find;

        //Not found
        if ( blocks[iZero][jZero] != 0 ) return null;

        if ( iZero - 1 >= 0){
            int[][] copy = blockCopy();
            swap(copy, iZero, jZero, iZero - 1, jZero);
            s.push( new Board( copy ));
        }
        if ( jZero - 1 >= 0){
            int[][] copy = blockCopy();
            swap(copy, iZero, jZero, iZero, jZero - 1);
            s.push( new Board( copy ));
        }
        if ( iZero + 1 < n){
            int[][] copy = blockCopy();
            swap(copy, iZero, jZero, iZero + 1, jZero);
            s.push( new Board( copy ));
        }
        if ( jZero + 1 < n){
            int[][] copy = blockCopy();
            swap(copy, iZero, jZero, iZero, jZero + 1);
            s.push( new Board( copy ));
        }
        return s;
    }

    // string representation of this board (in the output format specified below)
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {

        int a[][] = {{1,2,3},{4,5,6},{7,8,0}};

        Board b = new Board(a);
        Board twin = b.twin();

        StdOut.println(b.toString());
        StdOut.printf("Hamming distance: %d\n", b.hamming());
        StdOut.printf("Manhattan distance: %d\n", b.manhattan());
        StdOut.printf("isGoal: %s\n", b.isGoal());

        StdOut.println("\nTwin:");
        StdOut.println(twin.toString());
        StdOut.printf("Hamming distance: %d\n", twin.hamming());
        StdOut.printf("Manhattan distance: %d\n", twin.manhattan());
        StdOut.printf("isGoal: %s\n", twin.isGoal());

        StdOut.println("\nNeighbours:");
        for ( Board x : b.neighbors() ){
            StdOut.println(x.toString());
        }

    }
}