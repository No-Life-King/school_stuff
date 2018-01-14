#include <stdio.h>
#include <memory.h>

void fibonacciWord(char a[], char b[], int terms);
int len(const char s[]);
void binstringToText(const char word[]);
int power(int base, int exponent);

int main() {

    char a[10000];
    a[0] = '0';

    char b[10000];
    b[0] = '0';
    b[1] = '1';

    fibonacciWord(a, b, 12);

    printf("%s\n", b);

    binstringToText(b);
    printf("\n");
}

void fibonacciWord(char a[], char b[], int terms) {
    int aLength = len(a);
    int bLength = len(b);

    int x=0;

    while (x < aLength) {
        b[x + bLength] = a[x];
        x++;
    }

    int y=0;
    for (y; y<bLength; y++) {
        a[y] = b[y];
    }

    if (terms == 1) {
        return;
    } else {
        fibonacciWord(a, b, terms-1);
    }
}

int len(const char s[]) {
    int stringLength = 0;

    while (s[stringLength] != '\0') {
        stringLength++;
    }

    return stringLength;
}

void binstringToText(const char word[]) {
    int x = 0;
    char c = 0;

    while (word[x] != '\0') {

        if (x % 8 == 7) {
            printf("%c", c);
            if ((x+1) % 1024 == 0) {
                printf("\n");
            }
            c = 0;
        }

        if (word[x] == '1') {
            c += power(2, 7 - x % 8);
        }

        x++;
    }
}

int power(int base, int exponent) {
    int power = base;

    if (exponent == 0) {
        return 1;
    }

    while (exponent > 1) {
        power *= base;
        exponent--;
    }

    return power;
}
