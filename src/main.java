public class main {
    public static void main(String[] args) {
        Game game = new Game(4, 4);

        game.printBoard();
        game.moveRight();
        game.printBoard();
        game.moveRight();
        game.printBoard();
    }
}
