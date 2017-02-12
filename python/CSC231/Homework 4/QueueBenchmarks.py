#!/usr/bin/env python3
from time import clock
from QueueWithList import QueueWithList
from QueueWithLinkedList import QueueWithLinkedList

print("N\tListQueue\tLinkedListQueue")
for n in range(10000, 100001, 10000):
    
    # instantiate the queues
    listQueue = QueueWithList()
    linkedListQueue = QueueWithLinkedList()
    
    # time how long it takes to enqueue and dequeue items with a list queue
    start = clock()
    for x in range(n):
        listQueue.enqueue(x)
    
    for x in range(n):
        listQueue.dequeue()
        
    listQueueTime = clock() - start
    
    
    # time how long it takes to enqueue and dequeue items with a linked list queue
    start = clock()
    for x in range(n):
        linkedListQueue.enqueue(x)
    
    for x in range(n):
        linkedListQueue.dequeue()
        
    linkedListQueueTime = clock() - start
    
    # print a line of results for each 'n'
    print(str(n) + '\t' + str(listQueueTime * 1000) + '\t' + str(linkedListQueueTime * 1000))
