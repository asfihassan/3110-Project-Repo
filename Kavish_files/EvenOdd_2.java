import java.util.Scanner;

public class EvenOdd_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int value = readNumber(sc);

        System.out.println(isEven(value) ? "Even" : "Odd");

        sc.close();
    }

    private static int readNumber(Scanner sc) {
        System.out.print("Enter number: ");
        return sc.nextInt();
    }

    private static boolean isEven(int x) {
        return x % 2 == 0;
    }
}
