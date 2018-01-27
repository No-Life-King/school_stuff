//
// Created by ps1994 on 1/23/2018.
//

#ifndef LINKEDLIST_LINKEDLIST_H
#define LINKEDLIST_LINKEDLIST_H

#endif //LINKEDLIST_LINKEDLIST_H

struct linkedList {
    int numElements;
    struct node *head;
    struct node *tail;
};

struct node {
    char *text;
    struct node *next;
};

void add(struct linkedList *ll, char *string);
void print_list(struct linkedList *ll);
void clear_list(struct linkedList *ll);
void print_node(struct node);

