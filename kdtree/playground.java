/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class playground {
    public static void main(String[] args) {
        while (true) {
            int r = StdRandom.uniform(0, 2);
            if (r == 1) {
                StdOut.println("is 1");
            }
            else {
                StdOut.println("is 2");
            }
        }

    }
}
