/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class playground {
    public static void main(String[] args) {
        int[] r = StdRandom.permutation(5, 2);
        for (int e : r) {
            StdOut.println(e);
        }
    }
}
