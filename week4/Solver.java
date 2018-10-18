import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Solver {

    private final List<SearchNode> solutionSteps = new ArrayList<>();
    private final List<Board> steps = new ArrayList<>();

    public Solver(Board initial) {

        if (initial == null) {
            throw new IllegalArgumentException("Constructor needs one argument");
        }

        SearchNode startNode = new SearchNode(initial, 0, null);
        solutionSteps.add(startNode);
        if (!startNode.board.isGoal()) {

            MinPQ<SearchNode> pq = new MinPQ<>(new SearchNodeComparator());
            MinPQ<SearchNode> pqTwin = new MinPQ<>(new SearchNodeComparator());

            for (Board neighborBoard : startNode.board.neighbors()) {
                pq.insert(new SearchNode(neighborBoard, 1, startNode));
            }

            Board twin = initial.twin();
            SearchNode startNodeTwin = new SearchNode(twin, 0, null);
            for (Board neighborBoard : twin.neighbors()) {
                pqTwin.insert(new SearchNode(neighborBoard, 1, startNodeTwin));
            }

            while (!pq.isEmpty() && !pqTwin.isEmpty()) {
                SearchNode curNode = pq.delMin();
                solutionSteps.add(curNode);
                if (curNode.board.isGoal()) {
                    break;
                }
                for (Board neighborBoard : curNode.board.neighbors()) {
                    SearchNode neighbor = new SearchNode(neighborBoard, curNode.nOfMoves + 1, curNode);
                    if (curNode.prev != null && !neighbor.equals(curNode.prev))
                        pq.insert(neighbor);
                }

                SearchNode curNodeTwin = pqTwin.delMin();
                if (curNodeTwin.board.isGoal()) {
                    break;
                }
                for (Board neighborBoard : curNodeTwin.board.neighbors()) {
                    SearchNode neighbor = new SearchNode(neighborBoard, curNodeTwin.nOfMoves + 1, curNodeTwin);
                    if (curNodeTwin.prev != null && !neighbor.equals(curNodeTwin.prev))
                        pqTwin.insert(neighbor);
                }
            }
        }

        SearchNode curNode = solutionSteps.get(solutionSteps.size() - 1);
        if (curNode.board.isGoal()) {
            steps.add(curNode.board);
            while (curNode.prev != null) {
                curNode = curNode.prev;
                steps.add(0, curNode.board);
            }
        }
    }

    public static void main(String[] args) {
        // comment needed by the grader
    }

    public boolean isSolvable() {
        return !steps.isEmpty();
    }

    public int moves() {
        return steps.size() - 1;
    }

    public Iterable<Board> solution() {
        if (steps.isEmpty()) {
            return null;
        }
        return steps;
    }

    private class SearchNode {

        private final Board board;
        private final int nOfMoves;
        private final SearchNode prev;
        private final int manhattan;

        SearchNode(Board board, int nOfMoves, SearchNode prev) {
            this.board = board;
            this.nOfMoves = nOfMoves;
            this.prev = prev;
            this.manhattan = board.manhattan();
        }

        @Override
        public boolean equals(Object y) {
            if (y == this) {
                return true;
            }
            if (y == null) {
                return false;
            }
            if (y.getClass() != this.getClass()) {
                return false;
            }
            SearchNode that = (SearchNode) y;
            if (!this.board.equals(that.board)) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            return Objects.hash(board, nOfMoves, prev, manhattan);
        }
    }

    private class SearchNodeComparator implements Comparator<SearchNode> {

        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            int priotity1 = o1.manhattan + o1.nOfMoves;
            int priotity2 = o2.manhattan + o2.nOfMoves;
            if (priotity1 < priotity2) {
                return -1;
            } else if (priotity1 > priotity2) {
                return 1;
            } else {
                return 0;
            }
        }
    }

}