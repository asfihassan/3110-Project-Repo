import java.util.Scanner;

public class Stats_1 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("How many numbers? ");
        int n = in.nextInt();

        if (n <= 0) {
            System.out.println("No data.");
            in.close();
            return;
        }

        int count = 0;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int sum = 0;

        while (count < n) {
            System.out.print("Value: ");
            int v = in.nextInt();
            sum += v;
            if (v < min) min = v;
            if (v > max) max = v;
            count++;
        }

        double avg = sum / (double) n;
        System.out.println("Min = " + min);
        System.out.println("Max = " + max);
        System.out.println("Avg = " + avg);
        in.close();
    }
}
