// Save as: Asfi_2.2.java
// Small changes from V1:
//  - Slightly faster after each apple
//  - Added simple restart with 'R' key on Game Over
//  - Different colors/title to look like a modified version

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

class SnakeGameV2 extends JFrame {

    public SnakeGameV2() {
        setTitle("Snake Game - V2 (Restart + Speed Up)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(new GamePanel());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SnakeGameV2::new);
    }

    static class GamePanel extends JPanel implements ActionListener, KeyListener {

        private static final int SCREEN_WIDTH = 600;
        private static final int SCREEN_HEIGHT = 600;
        private static final int UNIT_SIZE = 25;
        private static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
        private static final int BASE_DELAY = 120;

        private final int[] x = new int[GAME_UNITS];
        private final int[] y = new int[GAME_UNITS];

        private int bodyParts;
        private int applesEaten;
        private int appleX;
        private int appleY;
        private char direction;
        private boolean running;
        private Timer timer;
        private Random random;

        public GamePanel() {
            random = new Random();
            setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
            setBackground(new Color(15, 15, 30));
            setFocusable(true);
            addKeyListener(this);
            startGame();
        }

        private void startGame() {
            bodyParts = 6;
            applesEaten = 0;
            direction = 'R';
            x[0] = 0;
            y[0] = 0;

            newApple();
            running = true;

            int delay = BASE_DELAY;
            if (timer == null) {
                timer = new Timer(delay, this);
            } else {
                timer.setDelay(delay);
            }
            timer.start();
        }

        private void increaseSpeed() {
            int currentDelay = timer.getDelay();
            int newDelay = Math.max(60, currentDelay - 5); // speed up but not too crazy
            timer.setDelay(newDelay);
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
                g.setColor(Color.orange);
                g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

                for (int i = 0; i < bodyParts; i++) {
                    if (i == 0) {
                        g.setColor(Color.cyan);
                    } else {
                        g.setColor(new Color(0, 120, 255));
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
                increaseSpeed();
            }
        }

        private void checkCollisions() {
            for (int i = bodyParts; i > 0; i--) {
                if (x[0] == x[i] && y[0] == y[i]) {
                    running = false;
                }
            }

            if (x[0] < 0 || x[0] >= SCREEN_WIDTH || y[0] < 0 || y[0] >= SCREEN_HEIGHT) {
                running = false;
            }

            if (!running && timer != null) {
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
            g.setFont(new Font("SansSerif", Font.BOLD, 22));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten,
                    (SCREEN_WIDTH - metrics2.stringWidth("Score: " + applesEaten)) / 2,
                    SCREEN_HEIGHT / 2 + 15);

            g.setFont(new Font("SansSerif", Font.PLAIN, 18));
            FontMetrics metrics3 = getFontMetrics(g.getFont());
            g.drawString("Press R to Restart",
                    (SCREEN_WIDTH - metrics3.stringWidth("Press R to Restart")) / 2,
                    SCREEN_HEIGHT / 2 + 45);
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
            int key = e.getKeyCode();

            if (running) {
                switch (key) {
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
            } else {
                if (key == KeyEvent.VK_R) {
                    startGame();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) { }

        @Override
        public void keyTyped(KeyEvent e) { }
    }
}
