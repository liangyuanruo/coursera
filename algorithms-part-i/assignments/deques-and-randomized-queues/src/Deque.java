/**
 * Created by yuanruoliang on 2/8/17.
 */

import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

    private Node head = null, tail = null;
    private int count = 0;

    // construct an empty deque
    public Deque(){}

    // Doubly-Linked List
    private class Node {
        private Item item;
        private Node prev, next;
    }

    // is the deque empty?
    public boolean isEmpty(){ return count == 0; }

    // return the number of items on the deque
    public int size(){ return count; }

    // add the item to the front
    public void addFirst(Item item){
        if( item == null ) throw new java.lang.IllegalArgumentException("Item is null!");

        Node tmp = new Node();
        tmp.item = item;
        if( head != null ) {
            tmp.next = head;
            head.prev = tmp;
        }
        head = tmp;

        if( tail == null ) tail = tmp; //When adding head item

        count++;

        return;
    }

    // add the item to the end
    public void addLast(Item item){
        if( item == null ) throw new java.lang.IllegalArgumentException("Item is null!");

        Node tmp = new Node();
        tmp.item = item;
        if( tail != null ) {
            tmp.prev = tail;
            tail.next = tmp;
        }
        tail = tmp;

        if( head == null ) head = tmp; //When adding head item

        count++;

        return;
    }

    // remove and return the item from the front
    public Item removeFirst(){
        if( count == 0 ) throw new java.util.NoSuchElementException("Deque is empty!");

        Node tmp = head;
        head = head.next;
        if( count == 1 ){ //last item
            head = tail = null;
        }
        count--;

        return tmp.item;
    }

    // remove and return the item from the end
    public Item removeLast(){
        if( count == 0 ) throw new java.util.NoSuchElementException("Deque is empty!");

        Node tmp = tail;
        tail = tail.prev;
        if( count == 1 ){ //last item
            head = tail = null;
        }
        count--;

        return tmp.item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator(){ return new DequeIterator(); }

    private class DequeIterator implements Iterator<Item>{
        private Node current = head;

        public boolean hasNext(){ return current != null; }

        public void remove(){
            throw new java.lang.UnsupportedOperationException("remove() is not supported!");
        }

        public Item next(){
            if( current == null ) throw new java.util.NoSuchElementException("Iterating on empty Deque!");

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args){

        Deque<Integer> deque = new Deque<>();
        deque.addLast(1);
        deque.addFirst(2);
        StdOut.println( deque.removeFirst() );
        StdOut.println( deque.removeLast() );
        deque.addLast(8);
        StdOut.println( deque.removeFirst() );

    }   // unit testing (optional)
}
