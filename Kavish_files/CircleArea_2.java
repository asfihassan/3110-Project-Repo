import java.util.Scanner;

public class CircleArea_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double radius = readRadius(sc);
        System.out.println("Area = " + area(radius));
        sc.close();
    }

    private static double readRadius(Scanner sc) {
        System.out.print("Radius: ");
        return sc.nextDouble();
    }

    private static double area(double r) {
        return Math.PI * r * r;
    }
}
