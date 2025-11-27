import java.util.Scanner;

public class Fibonacci_2 {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("n? ");
        int n = scan.nextInt();

        if (n <= 0) {
            System.out.println("Nothing to print.");
            scan.close();
            return;
        }

        long prev = 1;
        long curr = 1;

        for (int i = 0; i < n; i++) {
            long value;
            if (i < 2) {
                value = 1;
            } else {
                value = prev + curr;
                prev = curr;
                curr = value;
            }
            System.out.print(value + " ");
        }
        System.out.println();
        scan.close();
    }
}
