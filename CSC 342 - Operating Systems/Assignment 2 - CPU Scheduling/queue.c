/****************************************************************************/
/* File: queue.c
 */
/****************************************************************************/

/****************************************************************************/
/*                                                                          */
/*              Simple Queue ADT                                            */
/*                                                                          */
/*              Implementation                                              */
/*                                                                          */
/****************************************************************************/


#include <stdio.h>
#include <stdlib.h>
#include "queue.h"


/**
 * Initialize a deque in a safe state.
 * @param self The deque to be initialized.
 */
void initQueue(Queue *self)
{
    self->head = NULL;
    self->tail = NULL;
}

/**
 * Add a node containing some data to the end of the deque.
 * @param self The deque to which the data should be added.
 * @param data Any arbitrary data payload.
 */
void enQueue(Queue *self, void *data)
{
    QueueNode *node = malloc(sizeof(QueueNode));
    node->data = data;

    if (self->head == NULL) {
        node->next = node->prev = NULL;
        self->head = self->tail = node;
    } else {
        self->tail->next = node;
        node->prev = self->tail;
        node->next = NULL;
        self->tail = node;
    }
}

/**
 * Pops the element at the front of the deque.
 * @param self The deque.
 * @return The data in the first node of the deque.
 */
void *deQueue(Queue *self)
{
    // if the deque is empty, return null
    if (self->head == NULL) {
        return NULL;
    }

    void *data = self->head->data;

    QueueNode *next = self->head->next;
    free(self->head);
    self->head = next;

    return data;
}

