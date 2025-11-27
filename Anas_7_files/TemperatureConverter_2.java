import java.util.Scanner;

public class TemperatureConverter_2 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Temperature converter v2");
        System.out.println("1) C -> F");
        System.out.println("2) F -> C");
        System.out.print("Choice: ");
        int choice = in.nextInt();

        if (choice == 1) {
            System.out.print("Celsius: ");
            double c = in.nextDouble();
            double f = c * 9.0 / 5.0 + 32.0;
            System.out.println("Fahrenheit = " + f);
        } else if (choice == 2) {
            System.out.print("Fahrenheit: ");
            double f = in.nextDouble();
            double c = (f - 32.0) * 5.0 / 9.0;
            System.out.println("Celsius = " + c);
        } else {
            System.out.println("Unknown option.");
        }

        in.close();
    }
}
