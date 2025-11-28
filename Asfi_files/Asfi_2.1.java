

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

class SnakeGameV1 extends JFrame {

    public SnakeGameV1() {
        setTitle("Snake Game - V1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(new GamePanel());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SnakeGameV1::new);
    }

    static class GamePanel extends JPanel implements ActionListener, KeyListener {

        private static final int SCREEN_WIDTH = 600;
        private static final int SCREEN_HEIGHT = 600;
        private static final int UNIT_SIZE = 25;
        private static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
        private static final int DELAY = 120;

        private final int[] x = new int[GAME_UNITS];
        private final int[] y = new int[GAME_UNITS];

        private int bodyParts = 6;
        private int applesEaten = 0;
        private int appleX;
        private int appleY;
        private char direction = 'R';
        private boolean running = false;
        private Timer timer;
        private Random random;

        public GamePanel() {
            random = new Random();
            setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
            setBackground(Color.black);
            setFocusable(true);
            addKeyListener(this);
            startGame();
        }

        private void startGame() {
            newApple();
            running = true;
            timer = new Timer(DELAY, this);
            timer.start();
        }

        private void newApple() {
            appleX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
            appleY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            draw(g);
        }

        private void draw(Graphics g) {
            if (running) {
                g.setColor(Color.red);
                g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

                for (int i = 0; i < bodyParts; i++) {
                    if (i == 0) {
                        g.setColor(Color.green);
                    } else {
                        g.setColor(new Color(45, 180, 0));
                    }
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }

                g.setColor(Color.white);
                g.setFont(new Font("SansSerif", Font.BOLD, 20));
                FontMetrics metrics = getFontMetrics(g.getFont());
                g.drawString("Score: " + applesEaten,
                        (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2,
                        g.getFont().getSize());
            } else {
                gameOver(g);
            }
        }

        private void move() {
            for (int i = bodyParts; i > 0; i--) {
                x[i] = x[i - 1];
                y[i] = y[i - 1];
            }

            switch (direction) {
                case 'U' -> y[0] -= UNIT_SIZE;
                case 'D' -> y[0] += UNIT_SIZE;
                case 'L' -> x[0] -= UNIT_SIZE;
                case 'R' -> x[0] += UNIT_SIZE;
            }
        }

        private void checkApple() {
            if (x[0] == appleX && y[0] == appleY) {
                bodyParts++;
                applesEaten++;
                newApple();
            }
        }

        private void checkCollisions() {
            // head collides with body
            for (int i = bodyParts; i > 0; i--) {
                if (x[0] == x[i] && y[0] == y[i]) {
                    running = false;
                }
            }

            // left border
            if (x[0] < 0) running = false;
            // right border
            if (x[0] >= SCREEN_WIDTH) running = false;
            // top border
            if (y[0] < 0) running = false;
            // bottom border
            if (y[0] >= SCREEN_HEIGHT) running = false;

            if (!running) {
                timer.stop();
            }
        }

        private void gameOver(Graphics g) {
            g.setColor(Color.red);
            g.setFont(new Font("SansSerif", Font.BOLD, 40));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Game Over",
                    (SCREEN_WIDTH - metrics1.stringWidth("Game Over")) / 2,
                    SCREEN_HEIGHT / 2 - 20);

            g.setColor(Color.white);
            g.setFont(new Font("SansSerif", Font.BOLD, 25));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten,
                    (SCREEN_WIDTH - metrics2.stringWidth("Score: " + applesEaten)) / 2,
                    SCREEN_HEIGHT / 2 + 20);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (running) {
                move();
                checkApple();
                checkCollisions();
            }
            repaint();
        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> {
                    if (direction != 'R') direction = 'L';
                }
                case KeyEvent.VK_RIGHT -> {
                    if (direction != 'L') direction = 'R';
                }
                case KeyEvent.VK_UP -> {
                    if (direction != 'D') direction = 'U';
                }
                case KeyEvent.VK_DOWN -> {
                    if (direction != 'U') direction = 'D';
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) { }

        @Override
        public void keyTyped(KeyEvent e) { }
    }
}
