import java.util.Scanner;

public class StringTools_2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter text: ");
        String original = scanner.nextLine();

        String normalized = original.trim();
        int vowels = countVowels(normalized);
        String reversed = reverse(normalized);

        System.out.println("Vowels: " + vowels);
        System.out.println("Reversed: " + reversed);
        scanner.close();
    }

    private static String reverse(String s) {
        StringBuilder builder = new StringBuilder();
        for (int i = s.length() - 1; i >= 0; i--) {
            builder.append(s.charAt(i));
        }
        return builder.toString();
    }

    private static int countVowels(String s) {
        int count = 0;
        String vowels = "aeiou";
        String lower = s.toLowerCase();
        for (int i = 0; i < lower.length(); i++) {
            if (vowels.indexOf(lower.charAt(i)) >= 0) {
                count++;
            }
        }
        return count;
    }
}
