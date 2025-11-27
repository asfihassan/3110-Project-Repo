import java.util.Scanner;

public class Stats_2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("How many numbers? ");
        int n = scanner.nextInt();

        if (n <= 0) {
            System.out.println("No data.");
            scanner.close();
            return;
        }

        int min = Integer.MAX_VALUE;
        int sum = 0;

        for (int i = 0; i < n; i++) {
            System.out.print("Value: ");
            int value = scanner.nextInt();
            sum += value;
            if (value < min) {
                min = value;
            }
        }

        double average = sum / (double) n;
        System.out.println("Count = " + n);
        System.out.println("Min = " + min);
        System.out.println("Avg = " + average);
        scanner.close();
    }
}
