import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Comparator;
import java.util.LinkedList;

public class Solver {

    private final int moves;
    private final boolean solveable;
    private SearchNode endGoal;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){

        MinPQ<SearchNode> pq = new MinPQ<>( new Priority() );
        pq.insert( new SearchNode( initial ) );

        MinPQ<SearchNode> pq2 = new MinPQ<>( new Priority() );
        pq2.insert( new SearchNode( initial.twin() ) );
        SearchNode endGoal2;

        do {
            endGoal = pq.delMin();
            endGoal2 = pq2.delMin();

            for(Board neighborBoard : endGoal.board.neighbors()){
                if( endGoal.preNode == null ||
                        !neighborBoard.equals( endGoal.preNode.board ) ) {
                    SearchNode neighborSN = new SearchNode( neighborBoard );
                    neighborSN.preNode = endGoal;
                    neighborSN.moves = endGoal.moves + 1;
                    pq.insert( neighborSN );
                }
            }

            for(Board neighborBoard : endGoal2.board.neighbors()){
                if( endGoal2.preNode == null ||
                        !neighborBoard.equals( endGoal2.preNode.board ) ) {
                    SearchNode neighborSN = new SearchNode( neighborBoard );
                    neighborSN.preNode = endGoal2;
                    neighborSN.moves = endGoal2.moves + 1;
                    pq2.insert( neighborSN );
                }
            }

        } while ( !endGoal.board.isGoal() && !endGoal2.board.isGoal() );

        if( endGoal.board.isGoal() ){
            this.moves = endGoal.moves;
            this.solveable = true;
        } else {
            this.moves = -1;
            this.solveable = false;
        }

        return;
    }

    private class SearchNode{
        private final Board board;
        private int moves;
        private SearchNode preNode = null;
        private final int manhattan;

        public SearchNode(Board b){
            board = b;
            manhattan = board.manhattan();
        }
    }

    private class Priority implements Comparator<SearchNode>{
        public int compare(SearchNode s1, SearchNode s2){
            int priorityS1 = s1.manhattan + s1.moves;
            int priorityS2 = s2.manhattan + s2.moves;
            return priorityS1 - priorityS2;
        }
    }

    // is the initial board solvable?
    public boolean isSolvable(){
        return solveable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){
        return moves;
    }

    private void recoverSolution(LinkedList<Board> ll, SearchNode endGoal){
        if ( endGoal.preNode == null )
            ll.add( endGoal.board );
        else {
            recoverSolution(ll, endGoal.preNode);
            ll.add( endGoal.board );
        }
        return;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution(){
        if( solveable ){
            LinkedList<Board> solution = new LinkedList<>();
            recoverSolution(solution, endGoal);
            return solution;
        }
        return null;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args){

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}