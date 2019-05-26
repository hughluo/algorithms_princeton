import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;
import java.util.Iterator;


public class Permutation {
    public static void main(String[] args){
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        while(true){
            try {
                String s = StdIn.readString();
                q.enqueue(s);
            } catch (NoSuchElementException e) {
               break;
            }
        }
        Iterator<String> iter = q.iterator();
        for(int i = 0; i < k; i++) {
            StdOut.println(iter.next());
        }
    }
 }