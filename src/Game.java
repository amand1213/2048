
import java.util.Random;

public class Game {
    int numOfRows;
    int numOfColumns;

    int[][] board;

    Random rand = new Random();

    public Game(int rows, int columns) {
        numOfRows = rows;
        numOfColumns = columns;

        board = new int[rows][columns];

        for(int i = 0; i < rows; i ++) {
            for(int k = 0; k < columns; k ++) {
                board[i][k] = 0;
            }
        }
    }

    public void start() {
        spawnSquare();
        spawnSquare();
    }
    
    public void spawnSquare() {
        // Get random squares until empty square found

        /// FUTURE OPTIMIZATION: ONLY GET RANDOM FROM EMPTY SQUARES
        ///     if the board is mostly full it might take a bit
        ///     to find an empty square this way
  
        int row;
        int column;

        do {
            int numOfSquares = numOfRows * numOfColumns;
            int randSquare = rand.nextInt(numOfSquares);

            row = randSquare / numOfColumns;
            column = randSquare % numOfColumns;
        } while (board[row][column] != 0);

        // 90% chance to spawn a 2 (2^1 == 2); 10% chance to spawn a 4 (2^2 == 4);
        board[row][column] = (rand.nextDouble() <= 0.9) ? 1 : 2; 
    }

    public void printBoard() {
        for(int i = 0; i < numOfRows; i ++) {
            for(int k = 0; k < numOfColumns; k ++) {
                int num = (int) Math.pow(2, board[i][k]);

                // Print 0 for empty
                if(num == 1) {
                    num = 0;
                }

                System.out.print(num);
            }
            // New line at the end of row
            System.out.println();
        }
        // New line after board
        System.out.println();
    }
}
