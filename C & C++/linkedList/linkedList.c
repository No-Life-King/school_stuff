//
// Created by Phil on 1/23/2018.
//

#include <stdio.h>
#include <malloc.h>
#include <stdbool.h>
#include "linkedList.h"

void add(struct linkedList *ll, char *string) {
    struct node *newNode = malloc(sizeof(struct node));
    newNode->text = string;
    newNode->next = NULL;

    if (ll->numElements == 0) {
        ll->head = newNode;
        ll->tail = newNode;
    } else {
        ll->tail->next = newNode;
        ll->tail = newNode;
    }

    ll->numElements++;
}

void print_list(struct linkedList *ll) {
    struct node current = *ll->head;

    while (ll->numElements > 0) {
        print_node(current);

        if (current.next == NULL) {
            break;
        }

        current = *current.next;
    }
}

void clear_list(struct linkedList *ll) {
    struct node *current = ll->head;
    struct node *next;
    ll->numElements = 0;
    ll->head = ll->tail = NULL;

    while (true) {
        if (current->next == NULL) {
            free(&current);
            break;
        } else {
            next = current->next;
            free(&current);
            current = next;
        }
    }
}

void print_node(struct node node) {
    int index = 0;
    char letter = node.text[0];

    while (letter != '\0') {
        printf("%c", letter);
        letter = node.text[++index];
    }

    puts("");
}