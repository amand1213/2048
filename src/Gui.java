import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Gui extends JFrame {

    private final int screenWidth;
    private final int screenHeight;
    private final int boardWidth;
    private final int boardHeight;
    private final int tileSize = 100;
    private final int spacing = 10;

    private Game game;

    private GamePanel gamePanel;

    public Gui(int sWidth, int sHeight, int bWidth, int bHeight) {
        screenWidth = sWidth;
        screenHeight = sHeight;
        boardWidth = bWidth;
        boardHeight = bHeight;

        game = new Game(boardWidth, boardHeight);

        setTitle("2048");
        setSize(screenWidth, screenHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        // Create and add drawing panel
        gamePanel = new GamePanel();
        add(gamePanel);

        // Add key listener for input
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                boolean moved = false;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> moved = game.moveUp();
                    case KeyEvent.VK_DOWN -> moved = game.moveDown();
                    case KeyEvent.VK_LEFT -> moved = game.moveLeft();
                    case KeyEvent.VK_RIGHT -> moved = game.moveRight();
                }
                if (moved) {
                    game.spawnSquare();  // spawn new tile after move
                    gamePanel.repaint();
                }
            }
        });

        setVisible(true);
    }

    // Inner class for drawing
    private class GamePanel extends JPanel {

        public GamePanel() {
            // Total width = tiles + spacing between them (one less than tiles)
            setPreferredSize(new Dimension(
                boardWidth * tileSize + (boardWidth) * spacing,
                boardHeight * tileSize + (boardHeight) * spacing));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            int panelWidth = getWidth();
            int panelHeight = getHeight();

            int gridWidth = boardWidth * tileSize + (boardWidth - 1) * spacing;
            int gridHeight = boardHeight * tileSize + (boardHeight - 1) * spacing;

            int marginX = (panelWidth - gridWidth) / 2;
            int marginY = (panelHeight - gridHeight) / 2;

            g2.setColor(new Color(0xBBADA0)); // background color
            g2.fillRect(0, 0, panelWidth, panelHeight);

            for (int row = 0; row < boardHeight; row++) {
                for (int col = 0; col < boardWidth; col++) {
                    int exponent = game.getBoardValue(row, col);
                    int value = exponent == 0 ? 0 : 1 << exponent;

                    int x = marginX + col * (tileSize + spacing);
                    int y = marginY + row * (tileSize + spacing);

                    Color tileColor = exponent == 0 ? new Color(0xCDC1B4) : new Color(255 - exponent * 20, 200, 150);
                    g2.setColor(tileColor);
                    g2.fillRoundRect(x, y, tileSize, tileSize, 15, 15);

                    if (value != 0) {
                        g2.setColor(Color.BLACK);
                        String text = String.valueOf(value);
                        Font font = new Font("Arial", Font.BOLD, 36);
                        g2.setFont(font);
                        FontMetrics fm = g2.getFontMetrics();
                        int textWidth = fm.stringWidth(text);
                        int textHeight = fm.getAscent();
                        int tx = x + (tileSize - textWidth) / 2;
                        int ty = y + (tileSize + textHeight) / 2 - 5;
                        g2.drawString(text, tx, ty);
                    }
                }
            }
        }
    }
    // You can create a main method here for quick testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Gui(1280, 720, 6, 6));
    }
}
