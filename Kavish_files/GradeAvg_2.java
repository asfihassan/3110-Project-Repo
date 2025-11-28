import java.util.Scanner;

public class GradeAvg_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double[] grades = new double[3];

        for (int i = 0; i < grades.length; i++) {
            System.out.print("Enter grade " + (i+1) + ": ");
            grades[i] = sc.nextDouble();
        }

        System.out.println("Average = " + average(grades));
        sc.close();
    }

    private static double average(double[] arr) {
        double sum = 0;
        for (double x : arr) sum += x;
        return sum / arr.length;
    }
}
