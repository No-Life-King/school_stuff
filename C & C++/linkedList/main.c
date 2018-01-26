#include <stdio.h>

#include "linkedList.h"

int main() {
    struct linkedList ll;

    ll.numElements = 0;

    add(&ll, "hello");

    printf("%i", ll.numElements);

    struct node current = *ll.head;


    for (int x=0; x<ll.numElements; x++) {
        printf("%c", current.text[0]);
    }




}


