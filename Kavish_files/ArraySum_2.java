import java.util.Scanner;

public class ArraySum_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] nums = read(sc, 5);
        System.out.println("Sum = " + sum(nums));
        sc.close();
    }

    private static int[] read(Scanner sc, int size) {
        int[] arr = new int[size];
        System.out.println("Enter " + size + " integers:");
        for (int i = 0; i < size; i++) arr[i] = sc.nextInt();
        return arr;
    }

    private static int sum(int[] arr) {
        int s = 0;
        for (int x : arr) s += x;
        return s;
    }
}
