//
// Created by Phil on 1/23/2018.
//

#include <stdio.h>
#include "linkedList.h"

void add(struct linkedList *ll, char *string) {
    struct node newNode;
    newNode.text = string;

    if (ll->numElements == 0) {
        ll->head = &newNode;
        ll->tail = &newNode;
    } else {

    }

    ll->numElements++;
}