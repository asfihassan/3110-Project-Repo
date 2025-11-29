#include <iostream>
using namespace std;

void bubble(int arr[], int n) {
    for (int i = 0; i < n-1; i++)
        for (int j = 0; j < n-i-1; j++)
            if (arr[j] > arr[j+1])
                swap(arr[j], arr[j+1]);
}

int main() {
    int arr[] = {3, 1, 2};
    bubble(arr, 3);            // new functionality

    for (int i = 0; i < 3; i++)
        cout << arr[i] << " ";

    return 0;
}
