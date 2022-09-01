import edu.princeton.cs.algs4.Queue;

public class Board {
    private int[][] boardTiles;
    private Integer size;

    public Board(int[][] tiles) {
        if (tiles == null) throw new IllegalArgumentException("the tiles array con not be null");
        size = tiles[0].length;
        boardTiles = copy2DArray(tiles);

    }

    public String toString() {
        StringBuilder boardString = new StringBuilder();
        boardString.append(size.toString() + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boardString.append(Integer.toString(boardTiles[i][j]) + " ");
            }
            if (i != size - 1) boardString.append("\n");
        }
        return boardString.toString();
    }


    public int dimension() {
        return size;
    }


  
    public int hamming() {
        int hammingNumber = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((boardTiles[i][j] != 0) && (boardTiles[i][j] != (i * size + j + 1))) {
                    hammingNumber++;
                }
            }
        }
        return hammingNumber;
    }

  
    public int manhattan() {
        int manhattanDistance = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (boardTiles[i][j] != 0) {
                    manhattanDistance += (
                            abs(((boardTiles[i][j] - 1) % size) - j) +
                                    abs(((boardTiles[i][j] - 1) / size) - i));
                }
            }
        }
        return manhattanDistance;
    }


    public boolean isGoal() {
        return (hamming() == 0);
    }

    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;

        if (y.getClass() == this.getClass()) {
            if (((Board) (y)).size != this.size) return false;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (this.boardTiles[i][j] != ((Board) (y)).boardTiles[i][j]) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }


    public Iterable<Board> neighbors() {


        Queue<Board> queue = new Queue<Board>();
        int[] ilist = {-1, 1, 0, 0};
        int[] jlist = {0, 0, -1, 1};
        int zeroi = -1, zeroj = -1;
        boolean flag = false;
        for (int i = 0; i < size && !flag; i++) {
            for (int j = 0; j < size && !flag; j++) {
                if (boardTiles[i][j] == 0) {
                    flag = true;
                    zeroi = i;
                    zeroj = j;
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            int tempi = zeroi + ilist[i];
            int tempj = zeroj + jlist[i];
            if (valid(tempi, tempj)) {
                int[][] newArray = copy2DArray(boardTiles);
                exch(newArray, zeroi, zeroj, tempi, tempj);
                Board newBoard = new Board(newArray);
                queue.enqueue(newBoard);
            }
        }
        return queue;
    }


    
    public Board twin() {
        int[][] arrayCopy = copy2DArray(boardTiles);
        boolean found = false;
        for (int i = 0; i < size && !found; i++) {
            for (int j = 1; j < size && !found; j++) {

                if (arrayCopy[i][j] != 0 && arrayCopy[i][j - 1] != 0) {
                    exch(arrayCopy, i, j, i, j - 1);
                    found = true;
                }
            }
        }
        return new Board(arrayCopy);
    }

    private void exch(int[][] arr, int i1, int j1, int i2, int j2) {
        int temp = arr[i1][j1];
        arr[i1][j1] = arr[i2][j2];
        arr[i2][j2] = temp;
    }

    private boolean valid(int i, int j) {
        return ((i >= 0) && (j >= 0) && (i < size) && (j < size));
    }

    private int[][] copy2DArray(int[][] original) {
        int[][] newCopy = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newCopy[i][j] = original[i][j];
            }
        }
        return newCopy;
    }

    private static int abs(int num) {
        return (num < 0 ? -1 * num : num);
    }


    public static void main(String[] args) {

    }
}

