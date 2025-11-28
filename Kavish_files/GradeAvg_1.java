import java.util.Scanner;

public class GradeAvg_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter 3 grades: ");
        double g1 = sc.nextDouble();
        double g2 = sc.nextDouble();
        double g3 = sc.nextDouble();

        double avg = (g1 + g2 + g3) / 3;
        System.out.println("Average = " + avg);
        sc.close();
    }
}
