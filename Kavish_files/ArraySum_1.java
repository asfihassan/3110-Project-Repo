import java.util.Scanner;

public class ArraySum_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] nums = new int[5];

        System.out.println("Enter 5 integers:");
        for (int i = 0; i < 5; i++) {
            nums[i] = sc.nextInt();
        }

        int sum = 0;
        for (int n : nums) sum += n;

        System.out.println("Sum = " + sum);
        sc.close();
    }
}
