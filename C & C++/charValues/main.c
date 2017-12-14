#include <stdio.h>
#include <stdlib.h>

int main() {
    int c = 32;

    printf("The value of EOF: %d.\n", EOF);

    while (c < 128) {
        printf("%c: %d\n", (char) c, c);
        c++;
    }

    system("cmd");
}