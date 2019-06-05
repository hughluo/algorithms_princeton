import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class SAP {
    private final Digraph DG;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException("Argument is null");
        DG = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return calcAncestorAndLength(v, w)[1];
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return calcAncestorAndLength(v, w)[0];
    }

    private ArrayList<ArrayList<Integer>> allAncestors(int v) {
        return allAncestorsRecursive(v, 1, null);
    }

    private ArrayList<ArrayList<Integer>> allAncestorsRecursive(
            int current, int dist, ArrayList<ArrayList<Integer>> ancestors) {
        if (ancestors == null) {
            ancestors = new ArrayList<ArrayList<Integer>>(2);
            ancestors.add(new ArrayList<Integer>(1)); // skip first entry
        }
        if (DG.adj(current) == null) return ancestors;
        for (int v : DG.adj(current)) {
            ancestors.get(dist).add(v);
            allAncestorsRecursive(v, dist + 1, ancestors);
        }
        return ancestors;
    }

    private int[] calcAncestorAndLength(int v, int w) {
        ArrayList<ArrayList<Integer>> va = allAncestors(v);
        ArrayList<ArrayList<Integer>> wa = allAncestors(w);
        int len = Integer.MAX_VALUE;
        int shortestAncestor = -1;
        for (int i = 1; i < va.size(); i++) {
            for (int j = 0; j < va.get(j).size(); j++) {
                for (int k = 1; k < wa.size(); k++) {
                    for (int l = 0; l < wa.get(k).size(); l++) {
                        if (va.get(i).get(j) == wa.get(k).get(l)) {
                            if (i + k < len) {
                                shortestAncestor = va.get(i).get(j);
                                len = i + k;
                            }

                        }
                    }
                }

            }
        }
        int[] r = { shortestAncestor, len };
        return r;

    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException("Argument is null");
        int len = -1;
        return len;

    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException("Argument is null");
        int a = -1;
        return a;

    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }

    }
}
