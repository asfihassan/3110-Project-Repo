import java.util.Scanner;

public class Reverse_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = read(sc);
        System.out.println("Reversed: " + reverse(input));
        sc.close();
    }

    private static String read(Scanner sc) {
        System.out.print("Enter text: ");
        return sc.nextLine();
    }

    private static String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }
}
