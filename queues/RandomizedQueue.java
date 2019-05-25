import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] arrayQueue;
    private int len = 0;

    public RandomizedQueue() {
        arrayQueue = (Item[]) new Object[2];
    }
    
    private void resize(int capacity){
        Item[] copy = (Item[])new Object[capacity];
        for (int i = 0; i < len; i++)
        copy[i] = arrayQueue[i];
        arrayQueue = copy;
    }
    public boolean isEmpty() {
        return len == 0;
    }            
    public int size() {
        return len;
    }                 
    public void enqueue(Item item) {
        if (len == arrayQueue.length) resize(2*arrayQueue.length);    // double size of array if necessary
        arrayQueue[len++] = item;
    }          
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        int r = StdRandom.uniform(0, len);
        Item tmp = arrayQueue[len-1];
        Item item = arrayQueue[r];
        arrayQueue[r] = tmp;
        arrayQueue[len-1] = null;                                    // to avoid loitering 
        len--;
        // shrink size of array if necessary
        if (len > 0 && len == arrayQueue.length/4) resize(arrayQueue.length/2);    
        return item;
    }                    
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        int r = StdRandom.uniform(0, len);
        return arrayQueue[r];
    }                     
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i;
        private Item[] a;
        
        public RandomizedQueueIterator() {
            i = len - 1;
            a = arrayQueue.clone();
        }
        @Override
        public boolean hasNext() {
            return i >= 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item tmp = a[i];
            int r = StdRandom.uniform(0, i+1);
            Item item = a[r];
            a[r] = tmp;
            i--;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("We avoid support remove to Iterator!");
        }
    }
    public static void main(String[] args){
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        q.enqueue("first");
        q.enqueue("second");
        q.enqueue("third");
        q.enqueue("fourth");
        q.enqueue("fifth");
        for(String s : q){ StdOut.println(s);}
        StdOut.println("End loop");
        StdOut.println(q.dequeue());
        StdOut.println(q.dequeue());
        StdOut.println(q.dequeue());
        StdOut.println(q.dequeue());
        StdOut.println(q.dequeue());
    }


 }
 