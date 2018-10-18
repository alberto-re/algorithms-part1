import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {

  public static void main(String[] args) {

    int k = Integer.parseInt(args[0]);
    int n = 0;

    RandomizedQueue<String> rq = new RandomizedQueue<>();

    // https://en.wikipedia.org/wiki/Reservoir_sampling
    while (!StdIn.isEmpty()) {
      String value = StdIn.readString();
      n += 1;
      if (n <= k) {
        rq.enqueue(value);
      } else {
        if (StdRandom.uniform() <= (double) k / (double) n) {
          rq.dequeue();
          rq.enqueue(value);
        }
      }
    }

    Iterator<String> rqIterator = rq.iterator();
    for (int i = 0; i < k; i++) {
      StdOut.println(rqIterator.next());
    }
  }

}