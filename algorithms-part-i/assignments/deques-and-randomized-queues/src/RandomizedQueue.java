/**
 * Created by yuanruoliang on 2/8/17.
 */

import java.util.Iterator;
// import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int N, capacity;
    private Item[] q;

    // construct an empty randomized queue
    public RandomizedQueue() {
        N = 0;
        capacity = 1;
        q = (Item[]) new Object[capacity];
    }

    public boolean isEmpty() { return N == 0; }

    public int size() { return N; } // return the number of items on the queue

    // add the item
    public void enqueue(Item item) {
        if( item == null ) throw new java.lang.IllegalArgumentException("Argument is null.");
        if( N + 1 > capacity ) resize(capacity * 2);
        q[N++] = item;
        return;
    }

    private void resize(int newcap){
        Item[] q2 = (Item[]) new Object[newcap];
        int idx = 0;
        for( int i = 0; i < capacity; i++ )
            q2[idx++] = q[i];
        q = q2;
        capacity = newcap;
    }

    // remove and return a random item
    public Item dequeue() {
        if( isEmpty() ) throw new java.util.NoSuchElementException("Sampling from empty queue!");

        int i = StdRandom.uniform(N);
        Item tmp = q[i];
        q[i] = q[--N];
        q[N] = null;

        if( N < capacity/4 ) resize( capacity/2 );

        return tmp;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if( isEmpty() ) throw new java.util.NoSuchElementException("Sampling from empty queue!");
        return q[ StdRandom.uniform(N) ];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() { return new RandomizedQueueIterator(); }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int current = 0;
        private final int[] shuffledIdx = new int[N];

        RandomizedQueueIterator(){
            for( int i = 0; i < N; i++ ) shuffledIdx[i] = i;
            StdRandom.shuffle(shuffledIdx);
        }

        public boolean hasNext() {
            return current < N;
        }

        public Item next() {
            if( current >= N || isEmpty() ) throw new java.util.NoSuchElementException();
            return q[ shuffledIdx[ current++ ] ];
        }

        public void remove() { throw new java.lang.UnsupportedOperationException("remove() is not supported!"); }
}

    // unit testing (optional)
    public static void main(String[] args) {
        return;
    }
}

