import java.util.Scanner;

public class Login_2 {

    private static final String USER = "admin";
    private static final String PASS = "1234";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String username = prompt(sc, "Username: ");
        String password = prompt(sc, "Password: ");

        System.out.println(auth(username, password) ?
                "Access granted" : "Access denied");

        sc.close();
    }

    private static String prompt(Scanner sc, String text) {
        System.out.print(text);
        return sc.nextLine();
    }

    private static boolean auth(String u, String p) {
        return USER.equals(u) && PASS.equals(p);
    }
}
