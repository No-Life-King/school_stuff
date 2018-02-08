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
#include "osp.h"
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
 * Add a key-value pair to the end of the deque.
 * @param self The deque to which the data should be added.
 * @param data A key-value pair of integers.
 */
void enQueue(Queue *self, data_t *data)
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
 * Returns a pointer to the highest-priority node in the deque.
 * @param self The deque.
 * @return The first node in the deque.
 */
QueueNode *frontNode(Queue *self)
{
    return self->head;
}

/**
 * Returns a pointer to the data in the first node.
 * @param self The deque.
 * @return The key-value pair in the first node.
 */
data_t *frontValue(Queue *self)
{
    if (self->head == NULL) {
        return NULL;
    }

    return self->head->data;
}

/**
 * Pops the element at the front of the deque.
 * @param self The deque.
 * @return The key-value pair of the first node in the deque.
 */
data_t *deQueue(Queue *self)
{
    // if the deque is empty, return null
    if (self->head == NULL) {
        return NULL;
    }

    data_t *data = self->head->data;

    QueueNode *next = self->head->next;
    free(self->head);
    self->head = next;

    return data;
}

/**
 * Find a node in the deque by key.
 * @param self The deque.
 * @param data A data_t struct containing a key to be searched for.
 * @return A pointer to the node containing the key if it exists in the deque.
 */
QueueNode *findNode(Queue *self, data_t *data)
{
    if (self->head == NULL) {
        return NULL;
    } else {
        QueueNode *current = self->head;

        for (;;) {
            if (current->data->key == data->key) {
                return current;
            }

            if (current->next == NULL) {
                return NULL;
            }

            current = current->next;
        }
    }
}

/**
 * Delete a node from the deque.
 * @param self The deque.
 * @param p A pointer to the node that should be deleted.
 */
void removeNode(Queue *self, QueueNode *p)
{
    // if the value to remove is at the front of the deque, or there is only item in the deque,
    // simply dequeue the head and discard the pointer to it
    if (p == self->head) {
        deQueue(self);
    }

    // if the value to remove is at the back of the deque, set the previous node's pointer to
    // null, free the node, and then point the tail to the new final node
    else if (p == self->tail) {
        QueueNode *newTail = self->tail->prev;
        self->tail->prev->next = NULL;
        free(self->tail);
        self->tail = newTail;
    }

    // if the deque is empty, print an error
    else if (self->head == NULL) {
        fprintf(stderr, "Error: Deque is empty. Node cannot be removed.");
    }

    // omit the node and free it
    else {
        p->prev->next = p->next;
        p->next->prev = p->prev;
        free(p);
    }
}

/**
 * Look for a key in the deque and return its corresponding key-value pair.
 * @param self The deque.
 * @return The data to which the key corresponds.
 */
data_t *findValue(Queue *self, data_t *data)
{
    if (findNode(self, data) == NULL) {
        return NULL;
    }

    return findNode(self, data)->data;
}

/**
 * Implementation not required.
 * @param self
 * @param data
 */
void purge(Queue *self, data_t *data)
{
}

/**
 * Print all the key-value pairs in the deque.
 * @param self The deque.
 * @param label A label to be printed on top of the deque.
 */
void printQ(Queue *self, char *label)
{
    // print the user's label
    puts(label);

    // if the deque is empty, just print "Empty"
    if (self->head == NULL) {
        puts("Empty");
    }

    // otherwise print all of the deque key and value pairs
    else {
        QueueNode current = *self->head;

        for (;;) {
            printf("%s\n", toString(current.data));

            // quit printing after the final node has been printed out
            if (current.next == NULL) {
                break;
            }

            current = *current.next;
        }
    }
}

/**
 * Converts a key-value pair to an easily readable string.
 * @param d The data to be converted.
 * @return A pointer to the converted string.
 */
char *toString(data_t *d)
{
    static char result[BUFSIZ];

    if (d == NULL)
        sprintf (result, "NULL");
    else
        sprintf (result, "key=%d(data=%d) ", d->key, d->value);
    return result;
}

/*
int main ()
{
    Queue myQueue;
    QueueNode *p;
    data_t data[10], d2;
    int i;

    initQueue (&myQueue);

    for (i = 0; i < 10; i++) {

        data[i].key = i;
        data[i].value = 10*i;

        enQueue (&myQueue, &data[i]);
    }

    printQ (&myQueue, "MyQueue:" );

    QueueNode head = *frontNode(&myQueue);
    printf("%s\n", toString(head.data));

    printf("%s\n", toString(frontValue(&myQueue)));

    printf("\nHead Dequeued: %s\n\n", toString(deQueue(&myQueue)));
    deQueue(&myQueue);
    deQueue(&myQueue);
    deQueue(&myQueue);

    printQ(&myQueue, "Queue after some more values have been popped:");

    data_t fourKey = {4, 0};
    QueueNode *four = findNode(&myQueue, &fourKey);
    removeNode(&myQueue, four);
    printQ(&myQueue, "\nRemove head by pointer:");

    data_t sevenKey = {7, 0};
    QueueNode *seven = findNode(&myQueue, &sevenKey);
    removeNode(&myQueue, seven);
    printQ(&myQueue, "\nPop a found value:");

    removeNode(&myQueue, myQueue.tail);
    printQ(&myQueue, "\nPop tail:");

    data_t ten = {10, 100};
    enQueue(&myQueue, &ten);
    data_t elf = {11, 110};
    enQueue(&myQueue, &elf);

    printQ(&myQueue, "\nEnqueue more:");

    data_t *tenValue = findValue(&myQueue, &ten);
    printf("Value of key 10: %i\n", tenValue->value);

}
*/

