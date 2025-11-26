// Asfi Hassan 110145259
// Improved Calculator
// Suggested filename: Asfi_1.2.java (class name must be Asfi_1_2 to compile)

import java.util.Scanner;

public class Asfi_1_2 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("===== Advanced Calculator =====");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt(input, "Enter choice (1-7): ");

            if (choice == 7) {
                System.out.println("Exiting calculator. Bye!");
                running = false;
                break;
            }

            if (choice < 1 || choice > 7) {
                System.out.println("Invalid choice. Please select 1-7.");
                continue;
            }

            double a = readDouble(input, "Enter first number: ");
            double b = 0;

            // For sqrt we will only use 'a', for others we need 'b'
            if (choice != 6) {
                b = readDouble(input, "Enter second number: ");
            }

            double result;
            switch (choice) {
                case 1:
                    result = add(a, b);
                    System.out.println("Result: " + result);
                    break;
                case 2:
                    result = subtract(a, b);
                    System.out.println("Result: " + result);
                    break;
                case 3:
                    result = multiply(a, b);
                    System.out.println("Result: " + result);
                    break;
                case 4:
                    if (b == 0) {
                        System.out.println("Error: division by zero.");
                    } else {
                        result = divide(a, b);
                        System.out.println("Result: " + result);
                    }
                    break;
                case 5:
                    if (b == 0) {
                        System.out.println("Error: modulo by zero.");
                    } else {
                        result = modulo(a, b);
                        System.out.println("Result: " + result);
                    }
                    break;
                case 6:
                    if (a < 0) {
                        System.out.println("Error: cannot take square root of a negative number.");
                    } else {
                        result = sqrt(a);
                        System.out.println("Result: " + result);
                    }
                    break;
                default:
                    System.out.println("Unknown option.");
            }
        }

        input.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("Choose an operation:");
        System.out.println("1) Addition (+)");
        System.out.println("2) Subtraction (-)");
        System.out.println("3) Multiplication (*)");
        System.out.println("4) Division (/)");
        System.out.println("5) Modulo (%)");
        System.out.println("6) Square Root (first number only)");
        System.out.println("7) Exit");
    }

    private static int readInt(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (input.hasNextInt()) {
                return input.nextInt();
            } else {
                System.out.println("Invalid integer. Try again.");
                input.next();
            }
        }
    }

    private static double readDouble(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (input.hasNextDouble()) {
                return input.nextDouble();
            } else {
                System.out.println("Invalid number. Try again.");
                input.next();
            }
        }
    }

    // Calculator operations
    private static double add(double a, double b) {
        return a + b;
    }

    private static double subtract(double a, double b) {
        return a - b;
    }

    private static double multiply(double a, double b) {
        return a * b;
    }

    private static double divide(double a, double b) {
        return a / b;
    }

    private static double modulo(double a, double b) {
        return a % b;
    }

    private static double sqrt(double a) {
        return Math.sqrt(a);
    }
}
