import java.util.ArrayList;
import java.util.List;

public class Board {

    private final int[][] blocks;
    private final int n;
    private int emptyX;
    private int emptyY;

    public Board(int[][] board) {

        if (board == null) {
            throw new IllegalArgumentException("Missing argument");
        }

        n = board.length;
        blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = board[i][j];
                if (board[i][j] == 0) {
                    emptyY = i;
                    emptyX = j;
                }
            }
        }
    }

    public static void main(String[] args) {
        // comment needed by the grader
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        int outOfPlace = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                // Do not count the blank square when computing the Hamming or Manhattan priorities
                if (i == n - 1 && j == n - 1)
                    break;

                int expValue = j + 1 + i * n;
                if ((i + 1) * (j + 1) == n * n) {
                    expValue = 0;
                }
                if (blocks[i][j] != expValue) {
                    outOfPlace++;
                }
            }
        }
        return outOfPlace;
    }

    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                boolean outOfPlace = false;
                int expValue = j + 1 + i * n;
                if ((i + 1) * (j + 1) == n * n) {
                    expValue = 0;
                }
                if (blocks[i][j] != expValue) {
                    outOfPlace = true;
                }
                if (outOfPlace) {
                    if (blocks[i][j] > 0) {
                        int expCol = Math.round((blocks[i][j] - 1) / n);
                        int expRow = blocks[i][j] - (n * expCol) - 1;
                        distance += Math.abs(expRow - j) + Math.abs(expCol - i);
                    }
                }
            }
        }
        return distance;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public Board twin() {
        int[][] twinBlocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                twinBlocks[i][j] = blocks[i][j];
            }
        }

        int x1 = -1;
        int y1 = -1;
        int x2 = -1;
        int y2 = -1;

        outer:
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (x1 == -1 && (i != emptyY || j != emptyX)) {
                    x1 = j;
                    y1 = i;
                } else if (x1 != -1 && (i != emptyY || j != emptyX)) {
                    x2 = j;
                    y2 = i;
                    break outer;
                }
            }
        }

        int swp = twinBlocks[y1][x1];
        twinBlocks[y1][x1] = twinBlocks[y2][x2];
        twinBlocks[y2][x2] = swp;
        return new Board(twinBlocks);
    }

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
        Board that = (Board) y;
        if (this.n != that.n) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (that.blocks[i][j] != this.blocks[i][j])
                    return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<>();
        if (0 <= emptyY - 1 && emptyY - 1 < n) {
            neighbors.add(new Board(swapBlocks(emptyX, emptyY, emptyX, emptyY - 1)));
        }
        if (0 <= emptyY + 1 && emptyY + 1 < n) {
            neighbors.add(new Board(swapBlocks(emptyX, emptyY, emptyX, emptyY + 1)));
        }
        if (0 <= emptyX - 1 && emptyX - 1 < n) {
            neighbors.add(new Board(swapBlocks(emptyX, emptyY, emptyX - 1, emptyY)));
        }
        if (0 <= emptyX + 1 && emptyX + 1 < n) {
            neighbors.add(new Board(swapBlocks(emptyX, emptyY, emptyX + 1, emptyY)));
        }
        return neighbors;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    private int[][] swapBlocks(int x1, int y1, int x2, int y2) {
        int[][] neighborBlocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                neighborBlocks[i][j] = blocks[i][j];
            }
        }
        int swp = neighborBlocks[y2][x2];
        neighborBlocks[y2][x2] = neighborBlocks[y1][x1];
        neighborBlocks[y1][x1] = swp;
        return neighborBlocks;
    }

}