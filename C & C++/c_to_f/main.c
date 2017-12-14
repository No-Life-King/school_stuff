#include <stdio.h>

/*
 * Print tables of Fahrenheit and Celsius conversions.
 */

#define LOWER   0
#define UPPER   300
#define STEP    20

main() {
    float fahrenheit, celsius;

    fahrenheit = LOWER;
    printf("%s %s\n", "Fahrenheit", "Celsius");

    while (fahrenheit <= UPPER) {
        celsius = 5.0/9.0 * (fahrenheit - 32);
        printf("%10.0f %7.1f\n", fahrenheit, celsius);
        fahrenheit = fahrenheit + STEP;
    }

    printf("\nCelsius Fahrenheit\n");

    for (int cels=-40; cels <= 120; cels = cels + 10) {
        printf("%7d %10.1f\n", cels, 9.0/5.0 * cels + 32);
    }
}