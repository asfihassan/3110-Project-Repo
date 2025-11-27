import java.util.Scanner;

public class Calculator_2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Calculator v2 (refactored)");

        System.out.print("Enter a: ");
        double first = scanner.nextDouble();

        System.out.print("Enter op (+ - * /): ");
        char op = scanner.next().charAt(0);

        System.out.print("Enter b: ");
        double second = scanner.nextDouble();

        Double answer = compute(first, second, op);
        if (answer == null) {
            System.out.println("Invalid operation.");
        } else {
            System.out.println("Result = " + answer);
        }
        scanner.close();
    }

    private static Double compute(double x, double y, char op) {
        switch (op) {
            case '+':
                return x + y;
            case '-':
                return x - y;
            case '*':
                return x * y;
            case '/':
                if (y == 0) {
                    return null;
                }
                return x / y;
            default:
                return null;
        }
    }
}
