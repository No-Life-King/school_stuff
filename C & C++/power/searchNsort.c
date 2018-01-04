//
// Created by phil on 12/21/17.
//

int binsearch(int x, int v[], int n) {
    int low, high, mid;

    low = 0;
    high = n - 1;
    while (low <= high) {
        mid = (low + high) / 2;

        if (mid == x) return mid;

        x < v[mid] ? (high = mid - 1) : (low = mid + 1);
    }

    return -1;
}

void quicksort(int v[], int left, int right) {

    if (left >= right) {
        return;
    }

    swap(v, left, (left + right)/2);

    int last = left;

    for (int i=left+1; i<=right; i++) {
        if (v[i] < v[left]) {
            swap(v, ++last, i);
        }
    }

    swap(v, left, last);

    quicksort(v, left, last-1);
    quicksort(v, last+1, right);
}

void swap(int v[], int i, int j) {
    int temp = v[i];
    v[i] = v[j];
    v[j] = temp;
}
