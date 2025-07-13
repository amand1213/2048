
import java.util.Random;

/**
 * This class represents the main game logic and board state for the 2048 game.
 * It contains the logic for spawning squares, moving squares, and printing the board.
 */
public class Game {
    private final int numOfRows;
    private final int numOfColumns;

    private final int[][] board;

    private Random rand = new Random();

    /*
     * Constructor for the Game class
     * @param rows, the number of rows in the board
     * @param columns, the number of columns in the board
     */
    public Game(int rows, int columns) {
        numOfRows = rows;
        numOfColumns = columns;

        board = new int[rows][columns];

        for(int i = 0; i < rows; i ++) {
            for(int k = 0; k < columns; k ++) {
                board[i][k] = 0;
            }
        }
        spawnSquare();
        spawnSquare();
    }

    /*
     * Start the game
     */
    public void start() {
        spawnSquare();
        printBoard();
        spawnSquare();
        printBoard();
        spawnSquare();
        printBoard();
        moveUp();
        printBoard();
    }

    /*
     * Spawn a square on the board
     */
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

    // Set the value of a square on the board
    public void setSquare(int row, int col, int val) {
        board[row][col] = val;
    }

    public int getBoardValue(int row, int col) {
        return board[row][col];
    }

    /*
     * Move all squares up
     * @return true if the move was successful
     */
    public boolean moveUp() {
        boolean moveFlag = false;
        boolean[][] merged = new boolean[numOfRows][numOfColumns];

        // Loop through board
        for (int j = 0; j < numOfColumns; j++) {
            for (int i = 1; i < numOfRows; i++) {
                // If square is not empty
                if (board[i][j] != 0) {
                    int temp = i;
                    // Move the number up until condition met
                    while (temp > 0) {
                        if (board[temp - 1][j] == 0) {
                            board[temp - 1][j] = board[temp][j];
                            board[temp][j] = 0;
                            temp--;
                            moveFlag = true;
                        } else if (board[temp - 1][j] == board[temp][j] && !merged[temp - 1][j]) {
                            board[temp - 1][j]++;
                            board[temp][j] = 0;
                            merged[temp - 1][j] = true;
                            moveFlag = true;
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        return moveFlag;
    }


    /*
     * Move all squares down
     * @return true if the move was successful
     */
    public boolean moveDown() {
        boolean moveFlag = false;
        boolean[][] merged = new boolean[numOfRows][numOfColumns];

        // Loop through board
        for (int j = 0; j < numOfColumns; j++) {
            for (int i = numOfRows - 2; i >= 0; i--) {
                // If square is not empty
                if (board[i][j] != 0) {
                    int temp = i;
                    while (temp < numOfRows - 1) {
                        if (board[temp + 1][j] == 0) {
                            board[temp + 1][j] = board[temp][j];
                            board[temp][j] = 0;
                            temp++;
                            moveFlag = true;
                        } else if (board[temp + 1][j] == board[temp][j] && !merged[temp + 1][j]) {
                            board[temp + 1][j]++;
                            board[temp][j] = 0;
                            merged[temp + 1][j] = true;
                            moveFlag = true;
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        return moveFlag;
    }


    /*
     * Move all squares left
     * @return true if the move was successful
     */
    public boolean moveLeft() {
        boolean moveFlag = false;
        boolean[][] merged = new boolean[numOfRows][numOfColumns];

        // Loop through board
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 1; j < numOfColumns; j++) {
                // If square is not empty
                if (board[i][j] != 0) {
                    int temp = j;
                    while (temp > 0) {
                        if (board[i][temp - 1] == 0) {
                            board[i][temp - 1] = board[i][temp];
                            board[i][temp] = 0;
                            temp--;
                            moveFlag = true;
                        } else if (board[i][temp - 1] == board[i][temp] && !merged[i][temp - 1]) {
                            board[i][temp - 1]++;
                            board[i][temp] = 0;
                            merged[i][temp - 1] = true;
                            moveFlag = true;
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        return moveFlag;
    }

    public boolean moveRight() {
        boolean moveFlag = false;
        boolean[][] merged = new boolean[numOfRows][numOfColumns];

        for (int i = 0; i < numOfRows; i++) {
            for (int j = numOfColumns - 2; j >= 0; j--) {
                if (board[i][j] != 0) {
                    int temp = j;
                    while (temp < numOfColumns - 1) {
                        if (board[i][temp + 1] == 0) {
                            board[i][temp + 1] = board[i][temp];
                            board[i][temp] = 0;
                            temp++;
                            moveFlag = true;
                        } else if (board[i][temp + 1] == board[i][temp] && !merged[i][temp + 1]) {
                            board[i][temp + 1]++;
                            board[i][temp] = 0;
                            merged[i][temp + 1] = true;
                            moveFlag = true;
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        return moveFlag;
    }

    // Print the board to the console
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
