import java.util.Scanner;

public class Snake_1 {

    private static final int WIDTH = 8;
    private static final int HEIGHT = 4;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int x = WIDTH / 2;
        int y = HEIGHT / 2;
        boolean running = true;

        System.out.println("Snake v1");
        printBoard(x, y);

        while (running) {
            System.out.print("Move (w/a/s/d, q to quit): ");
            String input = in.nextLine().trim();
            if (input.isEmpty()) {
                continue;
            }
            char c = input.charAt(0);

            if (c == 'q') {
                running = false;
            } else if (c == 'w') {
                y--;
            } else if (c == 's') {
                y++;
            } else if (c == 'a') {
                x--;
            } else if (c == 'd') {
                x++;
            }

            if (x < 0) x = 0;
            if (x >= WIDTH) x = WIDTH - 1;
            if (y < 0) y = 0;
            if (y >= HEIGHT) y = HEIGHT - 1;

            printBoard(x, y);
        }

        System.out.println("Bye.");
        in.close();
    }

    private static void printBoard(int snakeX, int snakeY) {
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                if (row == snakeY && col == snakeX) {
                    System.out.print("S");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
