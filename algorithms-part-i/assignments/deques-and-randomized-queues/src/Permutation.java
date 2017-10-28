/**
 * Created by yuanruoliang on 2/8/17.
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    public static void main(String [] args){

        final int N = Integer.parseInt(args[0]);

        RandomizedQueue<String> rq = new RandomizedQueue<>();

        for( int i = 0; i < N; i++ ) {
            rq.enqueue( StdIn.readString() );
        }

        for( String x : rq ) StdOut.println(x);

    }
}