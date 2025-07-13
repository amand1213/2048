public class main {
    public static void main(String[] args) {
        Game game = new Game(4, 4);

        game.setSquare(0, 0, 2);
        game.setSquare(3, 0, 1);
        game.setSquare(2, 0, 1);

        game.printBoard();
        game.moveUp();
        game.printBoard();
        game.moveUp();
        game.printBoard();
    }
}
