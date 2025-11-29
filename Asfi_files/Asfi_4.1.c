

#include <stdio.h>

int main(void) {
    double a, b, result;
    char op;

    printf("=== Asfi Calculator 4.1 ===\n");
    printf("Enter expression (e.g., 5 + 3): ");

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
                return 1;
            }
            result = a / b;
            break;
        default:
            printf("Unsupported operator: %c\n", op);
            return 1;
    }

    printf("Result: %.2f\n", result);
    return 0;
}
