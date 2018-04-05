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
 * Add a key-value pair to the end of the deque.
 * @param self The deque to which the data should be added.
 * @param data A key-value pair of integers.
 */
void enQueue(Queue *self, int frame_id)
{
    QueueNode *node = malloc(sizeof(QueueNode));
    node->frame_id = frame_id;

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
 * Dequeues the element at the front of the deque.
 * @param self The deque.
 * @return The key-value pair of the first node in the deque.
 */
int deQueue(Queue *self)
{
    // if the deque is empty, return null
    if (self->head == NULL) {
        return NULL;
    }

    int frame_id = self->head->frame_id;

    QueueNode *next = self->head->next;
    free(self->head);
    self->head = next;

    return frame_id;
}

/**
 * Find a node in the deque by key.
 * @param self The deque.
 * @param data A data_t struct containing a key to be searched for.
 * @return A pointer to the node containing the key if it exists in the deque.
 */
QueueNode *findNode(Queue *self, int frame_id)
{

	QueueNode *current = self->head;

	while (current != NULL) {
		if (current->frame_id == frame_id) {
			return current;
		}

		current = current->next;
	}

	return NULL;

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
int *findValue(Queue *self, int frame_id)
{
	QueueNode *node = findNode(self, frame_id);

    if (node == NULL) {
        return NULL;
    }

    return node->frame_id;
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
            printf("%i\n", current.frame_id);

            // quit printing after the final node has been printed out
            if (current.next == NULL) {
                break;
            }

            current = *current.next;
        }
    }
}

