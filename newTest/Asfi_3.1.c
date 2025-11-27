/* Asfi_3.1.c
 * Hybrid Merge + Insertion Sort (ascending)
 */

#include <stdio.h>
#include <stdlib.h>

#define INSERTION_THRESHOLD 16

void insertion_sort(int *arr, int left, int right) {
    for (int i = left + 1; i <= right; i++) {
        int key = arr[i];
        int j = i - 1;
        while (j >= left && arr[j] > key) {
            arr[j + 1] = arr[j];
            j--;
        }
        arr[j + 1] = key;
    }
}

void merge(int *arr, int *tmp, int left, int mid, int right) {
    int i = left;
    int j = mid + 1;
    int k = left;

    while (i <= mid && j <= right) {
        if (arr[i] <= arr[j]) {
            tmp[k++] = arr[i++];
        } else {
            tmp[k++] = arr[j++];
        }
    }

    while (i <= mid) tmp[k++] = arr[i++];
    while (j <= right) tmp[k++] = arr[j++];

    for (int t = left; t <= right; t++) {
        arr[t] = tmp[t];
    }
}

void hybrid_merge_sort(int *arr, int *tmp, int left, int right) {
    if (right - left <= INSERTION_THRESHOLD) {
        insertion_sort(arr, left, right);
        return;
    }

    int mid = left + (right - left) / 2;
    hybrid_merge_sort(arr, tmp, left, mid);
    hybrid_merge_sort(arr, tmp, mid + 1, right);

    // optimization: already sorted
    if (arr[mid] <= arr[mid + 1]) return;

    merge(arr, tmp, left, mid, right);
}

int main(void) {
    int n;
    printf("Enter number of elements: ");
    if (scanf("%d", &n) != 1 || n <= 0) {
        printf("Invalid size.\n");
        return 1;
    }

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

    hybrid_merge_sort(arr, tmp, 0, n - 1);

    printf("Sorted (ascending):\n");
    for (int i = 0; i < n; i++) {
        printf("%d ", arr[i]);
    }
    printf("\n");

    free(arr);
    free(tmp);
    return 0;
}
