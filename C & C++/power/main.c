#include <stdio.h>
#include <ctype.h>
#include <math.h>
#include <float.h>
#include <zconf.h>
#include <values.h>

#include "functions.h"

int main() {

    double answers[SIZE];

    for (int x=0; x<SIZE; x++) {
        answers[x] = power(2, x);
    }

    // print the first 33 powers of 2
    printf("exp answer\n");
    for (int x=0; x<SIZE; x++) {
        printf("%3d %6.0f\n", x, answers[x]);
    }

    // print the minimum and maximum values for a few different data types
    printMinsMaxes();

    // convert a string of hex digits to their corresponding integer value
    char hexString[] = "f45";
    printf("%d\n", hexToDecimal(hexString));

    // convert a character (A-Z) to lowercase
    char c = lower('C');
    printf("%c\n", c);

    // perform a binary search for an integer on an array of integers
    int v[10000];

    // populate an array with the digits 0-9999
    for (int x=0; x<10000; x++) {
        v[x] = x;
    }

    // if the integer is found in the array, return it
    printf("%d\n", binsearch(500, v, 10000));
    // otherwise return -1
    printf("%d\n", binsearch(10000, v, 10000));

    // print an integer in decimal
    printd(1337);
    printf("\n");

    // perform a quicksort on an array of integers
    int numbers[] = {1, 6, 3, 7, 8, 5, 10, 5, 2, 4, 5};
    quicksort(numbers, 0, sizeof(numbers)/4 - 1);

    for (int x=0; x<sizeof(numbers)/4; x++) {
        printf("%i ", numbers[x]);
    }

    // reverse a string recursively
    // this palindrome translates to: "we go into the circle by night, we are consumed by fire"
    // or rougly: "we go wandering at night and are consumed by fire"
    char string[] = "in girum imus nocte et consumimur igni";
    printf("\n%s\n", string);
    reverse(string, 0, sizeof(string)-2);
    printf("%s", string);

    return 0;
}

void printMinsMaxes() {
    // print the char min and max values
    printf("Signed char\t\t-\tMin: %i\t\t\t\t\tMax: %i \n", CHAR_MIN, CHAR_MAX);
    printf("Unsigned char\t-\tMin: %i\t\t\t\t\t\tMax: %i \n", 0, UCHAR_MAX);

    // print the short min and max values
    printf("Signed short\t-\tMin: %i\t\t\t\t\tMax: %i \n", MINSHORT, MAXSHORT);
    printf("Unsigned short\t-\tMin: %i\t\t\t\t\t\tMax: %i \n", 0, USHRT_MAX);

    // print the int min and max values
    printf("Signed int\t\t-\tMin: %i\t\t\tMax: %i \n", INT_MIN, INT_MAX);
    printf("Unsigned int\t-\tMin: %i\t\t\t\t\t\tMax: %ld \n", 0, UINT_MAX);

    // print the long min and max values
    printf("Signed long\t\t-\tMin: %ld\tMax: %ld \n", LONG_MIN, LONG_MAX);
    printf("Unsigned long\t-\tMin: %ld\t\t\t\t\t\tMax: %lu \n", 0, ULONG_MAX);

    // print the double min and max values
    printf("double\t\t\t-\tMin: %f x 10^%i\t\tMax: %f x 10^%i \n", DBL_MIN / (pow(10, DBL_MIN_10_EXP)),
           DBL_MIN_10_EXP, DBL_MAX / (pow(10, DBL_MAX_10_EXP)), DBL_MAX_10_EXP);
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

int hexToDecimal(char hexString[]) {
    int decimal = 0;
    int nullCharIndex = 0;

    while (hexString[++nullCharIndex] != '\0');

    int i = nullCharIndex;
    for (int x=0; x<=nullCharIndex; x++) {
        decimal += hexDigitValue(hexString[x]) * power(16, --i);
    }

    return decimal;
}

int hexDigitValue(char digit) {
    if (isdigit(digit)) {
        return (int) digit - 48;
    } else if (digit >= 65 && digit <=70) {
        return (int) digit - 55;
    } else if (digit >= 97 && digit <=102) {
        return digit - 87;
    }
}

/**
 * Converts an uppercase character to lowercase.
 * @param c The character to convert to lowercase.
 * @return The lowercase version of the character.
 */
char lower(char c) {
    return c >= 'A' && c <= 'Z' ? (char) (c + 32) : c;
}

void printd(int n) {

    if (n < 0) {
        putchar('-');
        n = -n;
    }

    if (n / 10) {
        printd(n / 10);
    }

    putchar(n % 10 + '0');
}

void reverse(char s[], int left, int right) {
    if (!(left < right)) {
        return;
    }

    char temp = s[left];
    s[left] = s[right];
    s[right] = temp;

    reverse(s, left+1, right-1);
}