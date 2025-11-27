public class LoopDemo_2 {

    public static void main(String[] args) {
        System.out.println("LoopDemo v2");
        printOdd(1, 9);
        printEven(1, 9);
    }

    private static void printOdd(int start, int end) {
        System.out.print("Odd: ");
        int i = start;
        while (i <= end) {
            if ((i & 1) == 1) {
                System.out.print(i + " ");
            }
            i++;
        }
        System.out.println();
    }

    private static void printEven(int start, int end) {
        System.out.print("Even: ");
        int i = start;
        while (i <= end) {
            if ((i % 2) == 0) {
                System.out.print(i + " ");
            }
            i++;
        }
        System.out.println();
    }
}
