#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>

/*
 * This program can convert a temperature from fahrenheit to celsius or vice versa.
 * You can tell it to print tables of temperature conversions using the '-p' switch
 * and either '-f' or '-c' to go from fahrenheit to celsius or celsius to fahrenheit
 * respectively. Otherwise a temperature can follow a '-f' or '-c' switch and will be
 * converted to the other measurement system.
 */

#define LOWER   0
#define UPPER   300
#define STEP    20

void printFahrenheitTable();
void printCelsiusTable();
double convertToCels(int fahren);
double convertToFahren(int cels);

int main(int argc, char *argv[]) {

    if (argc > 3) {
        printf("Error: Too many arguments.\n");
        return false;
    }

    bool isFahren = false;
    bool print = false;

    for (int x=1; x<argc; x++) {

        if (argv[x][1] == 'p') {
            print = true;
        }

        if (argv[x][1] == 'f') {
            isFahren = true;
        }
    }

    int degrees;

    if (!print) {
        degrees = atoi(argv[2]);
    }

    if (print && isFahren) {
        printFahrenheitTable();
    } else if (print && !isFahren) {
        printCelsiusTable();
    } else if (!print && isFahren) {
        printf("%f\n", convertToCels(degrees));
    } else if (!print && !isFahren) {
        printf("%f\n", convertToFahren(degrees));
    }

}

void printFahrenheitTable() {
    double fahrenheit, celsius;

    fahrenheit = LOWER;
    printf("%s %s\n", "Fahrenheit", "Celsius");

    while (fahrenheit <= UPPER) {
        celsius = convertToCels(fahrenheit);
        printf("%10.0f %7.1f\n", fahrenheit, celsius);
        fahrenheit = fahrenheit + STEP;
    }
}

void printCelsiusTable() {
    printf("Celsius Fahrenheit\n");

    for (int cels=-40; cels <= 120; cels = cels + 10) {
        printf("%7d %10.1f\n", cels, convertToFahren(cels));
    }
}

double convertToCels(int fahren) {
    return 5.0/9.0 * (fahren - 32);
}

double convertToFahren(int cels) {
    return 9.0/5.0  * cels + 32;
}