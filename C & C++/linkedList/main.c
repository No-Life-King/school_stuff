#include <stdio.h>

#include "linkedList.h"

int main() {
    struct linkedList ll;

    ll.numElements = 0;

    add(&ll, "hello");
    add(&ll, "world");
    add(&ll, "i'm a");
    add(&ll, "linked list");

    print_list(&ll);

    printf("%i\n", ll.numElements);

    print_list(&ll);
}


