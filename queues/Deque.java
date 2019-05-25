import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private Node sentinel;
    private int size = 0;
    public Deque(){
        sentinel = new Node();
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    public boolean isEmpty(){
        return size == 0;
    }
    public int size(){
        return size;
    }
    public void addFirst(Item item) {
        if (isEmpty()) {
            addVeryFirstNode(item);
        } else {
            Node first = new Node();
            first.item = item;

            sentinel.next.prev = first;
            first.next = sentinel.next;
            first.prev = sentinel;
            sentinel.next = first;
            size++;
        }
    }
    public void addLast(Item item) {
        if (isEmpty()) {
            addVeryFirstNode(item);
        } else {
            Node last = new Node();
            last.item = item;

            sentinel.prev.next = last;
            last.prev = sentinel.prev;
            last.next = sentinel;
            sentinel.prev = last;
            size++;
        }
    }

    private void addVeryFirstNode(Item item){
        Node veryFirst = new Node();
        veryFirst.item = item;
        sentinel.next = veryFirst;
        sentinel.prev = veryFirst;
        veryFirst.next = sentinel;
        veryFirst.prev = sentinel;
        size++;
    }

    public Item removeFirst() {
        if (sentinel.next == sentinel) {
           throw new java.util.NoSuchElementException("Deque is empty!");
        } else {
            Item item = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size--;
            return item;
        }
    }
    public Item removeLast() {
        if (sentinel.prev == sentinel) {
            throw new java.util.NoSuchElementException("Deque is empty!");
         } else {
             Item item = sentinel.prev.item;
             sentinel.prev = sentinel.prev.prev;
             sentinel.prev.next = sentinel;
             size--;
             return item;
         }
     }
    //return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = sentinel.next;

        @Override
        public boolean hasNext() {
            return current != sentinel;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("We avoid support remove to Iterator!");
        }
    }
    public static void main(String[] args)  {
        Deque<String> myDeque = new Deque<String>();
        StdOut.printf("Size is %d\n", myDeque.size());
        myDeque.addFirst("first");
        myDeque.addFirst("evenFirst");
        myDeque.addLast("last");
        myDeque.addLast("evenLast");
        myDeque.addFirst("evenevenFirst");

        for(String s : myDeque) {
            StdOut.println(s);
        }
        
        StdOut.printf("Size is %d\n", myDeque.size());
        StdOut.println(myDeque.removeLast());
        StdOut.printf("Size is %d\n", myDeque.size());
        StdOut.println(myDeque.removeFirst());
        StdOut.printf("Size is %d\n", myDeque.size());
        StdOut.println(myDeque.removeFirst());
        StdOut.println(myDeque.removeFirst());
        StdOut.println(myDeque.removeFirst());

    }

    private class Node {
        Item item;
        Node next;
        Node prev;
    }
}
