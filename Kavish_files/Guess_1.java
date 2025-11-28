import java.util.Random;
import java.util.Scanner;

public class Guess_1 {
    public static void main(String[] args) {
        Random r = new Random();
        int secret = r.nextInt(10) + 1;

        Scanner sc = new Scanner(System.in);
        System.out.print("Guess (1-10): ");
        int g = sc.nextInt();

        if (g == secret) {
            System.out.println("Correct!");
        } else {
            System.out.println("Wrong! Number was " + secret);
        }
        sc.close();
    }
}
