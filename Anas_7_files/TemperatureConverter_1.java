import java.util.Scanner;

public class TemperatureConverter_1 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Celsius: ");
        double c = in.nextDouble();
        double f = c * 9.0 / 5.0 + 32.0;
        System.out.println("Fahrenheit = " + f);
        in.close();
    }
}
