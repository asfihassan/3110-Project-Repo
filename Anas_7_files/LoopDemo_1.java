public class LoopDemo_1 {

    public static void main(String[] args) {
        System.out.println("LoopDemo v1");
        printEven(0, 10);
        printOdd(0, 10);
    }

    private static void printEven(int start, int end) {
        System.out.print("Even: ");
        for (int i = start; i <= end; i++) {
            if (i % 2 == 0) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }

    private static void printOdd(int start, int end) {
        System.out.print("Odd: ");
        for (int i = start; i <= end; i++) {
            if (i % 2 != 0) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }
}
