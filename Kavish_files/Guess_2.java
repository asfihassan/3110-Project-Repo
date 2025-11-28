import java.util.Random;
import java.util.Scanner;

public class Guess_2 {
    public static void main(String[] args) {
        int secret = generate();
        int guess = input();

        System.out.println(guess == secret ?
                "Correct!" : "Wrong! Number was " + secret);
    }

    private static int generate() {
        return new Random().nextInt(10) + 1;
    }

    private static int input() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Guess (1-10): ");
        int g = sc.nextInt();
        sc.close();
        return g;
    }
}
