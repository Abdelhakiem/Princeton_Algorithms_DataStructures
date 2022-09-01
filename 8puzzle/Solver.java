import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class Solver {

    private final SearchNode solution;
    private boolean solvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {


        if (initial == null) throw new IllegalArgumentException("can't solve for null search node");

        MinPQ<SearchNode> boardPQ = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinBoardPQ = new MinPQ<SearchNode>();


        boardPQ.insert(new SearchNode(initial, 0, null));
        Board initialTwin = initial.twin();
        twinBoardPQ.insert(new SearchNode(initialTwin, 0, null));

        while (true) {

            SearchNode minNode = boardPQ.delMin();
            SearchNode minTwinNode = twinBoardPQ.delMin();


            if (minNode.board.isGoal()) {
                solution = minNode;
                solvable = true;
                break;
            }
            if (minTwinNode.board.isGoal()) {
                solution = null;
                solvable = false;
                break;
            }


            addNeighbours(boardPQ, minNode);
            addNeighbours(twinBoardPQ, minTwinNode);
         
        }
    }

    private void addNeighbours(MinPQ<SearchNode> searchNodeMinPQ, SearchNode searchNode) {
        for (Board board : searchNode.board.neighbors()) {
            if (searchNode.prevoius == null || !searchNode.prevoius.board.equals(board)) {
                searchNodeMinPQ.insert(new SearchNode(board, searchNode.moves + 1, searchNode));
            }
        }
    }


    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (solvable) {
            return solution.moves;
        }
        return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!solvable) return null;

        final Stack<Board> boardStack = new Stack<Board>();
        SearchNode currNode = solution;
        while (currNode != null) {
            boardStack.push(currNode.board);
            currNode = currNode.prevoius;
        }
        return boardStack;

    }

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final SearchNode prevoius;
        private final int moves;

        SearchNode(Board board, int moves, SearchNode prevoius) {
            this.board = board;
            this.moves = moves;
            this.prevoius = prevoius;
        }

        public int compareTo(SearchNode thatNode) {
            int thisPriority = this.board.manhattan() + this.moves;
            int thatPriority = thatNode.board.manhattan() + thatNode.moves;
            if (thisPriority > thatPriority) {
                return 1;
            } else if (thisPriority == thatPriority) {
                return 0;
            } else {
                return -1;
            }
        }

    }


    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        Solver solver = new Solver(initial);

        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
