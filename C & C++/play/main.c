#include <stdio.h>
#include <sodium.h>

void main() {

    if (sodium_init() < 0) {
        puts("failed");
    }
}