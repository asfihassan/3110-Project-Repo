import java.util.Scanner;

public class CircleArea_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Radius: ");
        double r = sc.nextDouble();
        double area = 3.14159 * r * r;
        System.out.println("Area = " + area);
        sc.close();
    }
}
