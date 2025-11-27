import java.util.Scanner;

public class Snake_2 {

    private static void printBoard(int width, int height, int snakeX, int snakeY) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
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

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int width = 8;
        int height = 4;
        int x = width / 2;
        int y = height / 2;
        boolean keepPlaying = true;

        System.out.println("Snake v2 (same board, small tweaks)");
        printBoard(width, height, x, y);

        while (keepPlaying) {
            System.out.print("Move (w/a/s/d, q to quit): ");
            String input = in.nextLine().trim();
            if (input.isEmpty()) {
                continue;
            }
            char c = input.charAt(0);

            if (c == 'q') {
                keepPlaying = false;
            } else if (c == 'w') {
                y--;
            } else if (c == 's') {
                y++;
            } else if (c == 'a') {
                x--;
            } else if (c == 'd') {
                x++;
            }

           
            if (x < 0) {
                x = 0;
            } else if (x >= width) {
                x = width - 1;
            }
            if (y < 0) {
                y = 0;
            } else if (y >= height) {
                y = height - 1;
            }

            printBoard(width, height, x, y);
        }

        System.out.println("Game ended.");
        in.close();
    }
}
