#include <stdio.h>
#include <time.h>
#include <math.h>
#include <float.h>

#define SIZE 33

double power(int x, int y);

int main() {

    double answers[SIZE];

    for (int x=0; x<=SIZE; x++) {
        answers[x] = power(2, x);
    }

    printf("exp answer\n");
    for (int x=0; x<SIZE; x++) {
        printf("%3d %6.0f\n", x, answers[x]);
    }

    printf("Double Max Value: %f x 10^308", DBL_MAX / (pow(10, 308)));

    return 0;
}

double power(int base, int exponent) {
    double power = base;

    if (exponent == 0) {
        return 1;
    }

    while (exponent > 1) {
        power *= base;
        exponent--;
    }

    return power;
}