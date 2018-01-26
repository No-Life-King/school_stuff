#include <stdio.h>

void swap(int *x, int *y);

void main() {
    int x = 37, y = 13;
    swap(&x, &y);
    printf("x=%i, y=%i", x, y);
}

void swap(int *x, int *y) {
    int temp = *x;
    *x = *y;
    *y = temp;
}