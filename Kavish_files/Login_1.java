import java.util.Scanner;

public class Login_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Username: ");
        String u = sc.nextLine();
        System.out.print("Password: ");
        String p = sc.nextLine();

        if (u.equals("admin") && p.equals("1234")) {
            System.out.println("Access granted");
        } else {
            System.out.println("Access denied");
        }
        sc.close();
    }
}
