import java.util.Scanner;

public class Calculator_1 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Calculator v1");
        System.out.print("Enter a: ");
        double a = in.nextDouble();
        System.out.print("Enter op (+ - * /): ");
        char op = in.next().charAt(0);
        System.out.print("Enter b: ");
        double b = in.nextDouble();

        double result;
        if (op == '+') {
            result = a + b;
        } else if (op == '-') {
            result = a - b;
        } else if (op == '*') {
            result = a * b;
        } else if (op == '/') {
            if (b == 0) {
                System.out.println("Cannot divide by zero.");
                in.close();
                return;
            }
            result = a / b;
        } else {
            System.out.println("Unknown operator.");
            in.close();
            return;
        }

        System.out.println("Result = " + result);
        in.close();
    }
}
