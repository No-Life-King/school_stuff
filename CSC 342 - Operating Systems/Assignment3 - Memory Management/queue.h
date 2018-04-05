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


typedef struct {
    int key;
    int value;
} data_t;

typedef struct queueNode {
    struct queueNode *next, *prev;
    int frame_id;
} QueueNode;

typedef struct queueType {
    QueueNode *head;
    QueueNode *tail;
} Queue;




/****************************************************************************/
/* Function Prototypes/Declarations                                         */
/****************************************************************************/
void initQueue(Queue *self);
void enQueue(Queue *self, int frame_id);
int deQueue(Queue *self);
void removeNode(Queue *self, QueueNode *p);
QueueNode *findNode(Queue *self, int frame_id);
int *findValue(Queue *self, int frame_id);
void printQ(Queue *self, char *label);

