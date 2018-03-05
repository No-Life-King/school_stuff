/****************************************************************************/
/* File: queue.h
 */
/****************************************************************************/

/****************************************************************************/
/*                                                                          */
/*              Simple Queue ADT                                            */
/*                                                                          */
/*              Declaration                                                 */
/*                                                                          */
/****************************************************************************/


typedef struct queueNode {
    struct queueNode *next, *prev;
    void *data;
} QueueNode;

typedef struct queueType {
    QueueNode *head;
    QueueNode *tail;
} Queue;


/****************************************************************************/
/* Function Prototypes/Declarations                                         */
/****************************************************************************/
void initQueue(Queue *self);
void enQueue(Queue *self, void *data);
void *deQueue(Queue *self);


