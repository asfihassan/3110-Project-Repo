import java.util.Scanner;

public class Fibonacci_1 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("n? ");
        int n = in.nextInt();

        if (n <= 0) {
            System.out.println("Nothing to print.");
            in.close();
            return;
        }

        long a = 0;
        long b = 1;

        for (int i = 0; i < n; i++) {
            System.out.print(a + " ");
            long next = a + b;
            a = b;
            b = next;
        }
        System.out.println();
        in.close();
    }
}
