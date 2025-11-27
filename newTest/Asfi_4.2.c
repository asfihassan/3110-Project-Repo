/* Asfi_4.2.c
   Slightly modified calculator:
   - Allows multiple operations in a loop until user quits.
*/

#include <stdio.h>

int main(void) {
    double a, b, result;
    char op;
    char again;

    printf("=== Asfi Calculator 4.2 ===\n");

    do {
        printf("\nEnter expression (e.g., 5 + 3): ");

        if (scanf("%lf %c %lf", &a, &op, &b) != 3) {
            printf("Invalid input.\n");
            return 1;
        }

        switch (op) {
            case '+':
                result = a + b;
                break;
            case '-':
                result = a - b;
                break;
            case '*':
            case 'x':
            case 'X':
                result = a * b;
                break;
            case '/':
                if (b == 0) {
                    printf("Error: division by zero.\n");
                    // flush leftover input
                    result = 0;
                    break;
                }
                result = a / b;
                break;
            default:
                printf("Unsupported operator: %c\n", op);
                // flush leftover input
                result = 0;
        }

        printf("Result: %.2f\n", result);

        printf("Do another calculation? (y/n): ");
        // consume any leftover whitespace/newline
        do {
            again = getchar();
        } while (again == '\n' || again == ' ' || again == '\t');

    } while (again == 'y' || again == 'Y');

    printf("Goodbye.\n");
    return 0;
}
