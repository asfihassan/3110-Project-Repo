

#include <stdio.h>
#include <stdlib.h>

#define INSERTION_THRESHOLD 16

long long comparison_count = 0;
long long move_count = 0;

int less_equal(int a, int b, int ascending) {
    comparison_count++;
    return ascending ? (a <= b) : (a >= b);
}

int greater(int a, int b, int ascending) {
    comparison_count++;
    return ascending ? (a > b) : (a < b);
}

void insertion_sort(int *arr, int left, int right, int ascending) {
    for (int i = left + 1; i <= right; i++) {
        int key = arr[i];
        move_count++;
        int j = i - 1;
        while (j >= left && greater(arr[j], key, ascending)) {
            arr[j + 1] = arr[j];
            move_count++;
            j--;
        }
        arr[j + 1] = key;
        move_count++;
    }
}

void merge(int *arr, int *tmp, int left, int mid, int right, int ascending) {
    int i = left;
    int j = mid + 1;
    int k = left;

    while (i <= mid && j <= right) {
        if (less_equal(arr[i], arr[j], ascending)) {
            tmp[k++] = arr[i++];
        } else {
            tmp[k++] = arr[j++];
        }
        move_count++;
    }

    while (i <= mid) {
        tmp[k++] = arr[i++];
        move_count++;
    }
    while (j <= right) {
        tmp[k++] = arr[j++];
        move_count++;
    }

    for (int t = left; t <= right; t++) {
        arr[t] = tmp[t];
        move_count++;
    }
}

void hybrid_merge_sort(int *arr, int *tmp, int left, int right, int ascending) {
    if (right - left <= INSERTION_THRESHOLD) {
        insertion_sort(arr, left, right, ascending);
        return;
    }

    int mid = left + (right - left) / 2;
    hybrid_merge_sort(arr, tmp, left, mid, ascending);
    hybrid_merge_sort(arr, tmp, mid + 1, right, ascending);

    // already ordered check
    comparison_count++;
    if (ascending ? (arr[mid] <= arr[mid + 1]) : (arr[mid] >= arr[mid + 1])) {
        return;
    }

    merge(arr, tmp, left, mid, right, ascending);
}

int main(void) {
    int n;
    printf("Enter number of elements: ");
    if (scanf("%d", &n) != 1 || n <= 0) {
        printf("Invalid size.\n");
        return 1;
    }

    int order;
    printf("Enter 1 for ascending, 2 for descending: ");
    if (scanf("%d", &order) != 1 || (order != 1 && order != 2)) {
        printf("Invalid sort order.\n");
        return 1;
    }
    int ascending = (order == 1);

    int *arr = malloc(sizeof(int) * n);
    int *tmp = malloc(sizeof(int) * n);
    if (!arr || !tmp) {
        printf("Memory allocation failed.\n");
        free(arr);
        free(tmp);
        return 1;
    }

    printf("Enter %d integers:\n", n);
    for (int i = 0; i < n; i++) {
        if (scanf("%d", &arr[i]) != 1) {
            printf("Invalid input.\n");
            free(arr);
            free(tmp);
            return 1;
        }
    }

    comparison_count = 0;
    move_count = 0;

    hybrid_merge_sort(arr, tmp, 0, n - 1, ascending);

    printf("Sorted (%s):\n", ascending ? "ascending" : "descending");
    for (int i = 0; i < n; i++) {
