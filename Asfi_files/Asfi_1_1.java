

import java.util.Scanner;

public class Asfi_1_1 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("=== Simple Calculator ===");

        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("Choose an operation:");
            System.out.println("1) Addition (+)");
            System.out.println("2) Subtraction (-)");
            System.out.println("3) Multiplication (*)");
            System.out.println("4) Division (/)");
            System.out.println("5) Exit");
            System.out.print("Enter choice (1-5): ");

            int choice;
            if (input.hasNextInt()) {
                choice = input.nextInt();
            } else {
                System.out.println("Invalid input. Please enter a number 1-5.");
                input.next(); // clear invalid
                continue;
            }

            if (choice == 5) {
                running = false;
                System.out.println("Goodbye!");
                break;
            }

            System.out.print("Enter first number: ");
            if (!input.hasNextDouble()) {
                System.out.println("Invalid number. Try again.");
                input.next();
                continue;
            }
            double a = input.nextDouble();

            System.out.print("Enter second number: ");
            if (!input.hasNextDouble()) {
                System.out.println("Invalid number. Try again.");
                input.next();
                continue;
            }
            double b = input.nextDouble();

            double result;
            switch (choice) {
                case 1:
                    result = a + b;
                    System.out.println("Result: " + result);
                    break;
                case 2:
                    result = a - b;
                    System.out.println("Result: " + result);
                    break;
                case 3:
                    result = a * b;
                    System.out.println("Result: " + result);
                    break;
                case 4:
                    if (b == 0) {
                        System.out.println("Error: division by zero is not allowed.");
                    } else {
                        result = a / b;
                        System.out.println("Result: " + result);
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-5.");
            }
        }

        input.close();
    }
}
