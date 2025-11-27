import java.util.Scanner;

public class StringTools_1 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter text: ");
        String s = in.nextLine();

        int vowels = countVowels(s);
        String reversed = reverse(s);

        System.out.println("Vowels: " + vowels);
        System.out.println("Reversed: " + reversed);
        in.close();
    }

    private static int countVowels(String s) {
        int count = 0;
        String vowels = "aeiouAEIOU";
        for (int i = 0; i < s.length(); i++) {
            if (vowels.indexOf(s.charAt(i)) >= 0) {
                count++;
            }
        }
        return count;
    }

    private static String reverse(String s) {
        String result = "";
        for (int i = s.length() - 1; i >= 0; i--) {
            result += s.charAt(i);
        }
        return result;
    }
}
