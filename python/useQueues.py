#!/usr/bin/env python3
from queues import *
from queue import Queue
from time import time

"""
This script tests and benchmarks 3 different types of queues. I ran this script
with several queue sizes. At a queue size of 1000, the 'ListQueue' actually
outperformed the 'DictQueue' and the python queue with times of 1.81 ms, 2.21 ms,
and 11.45 ms respectively. At a queue size of 10000, the design flaw of the
'ListQueue' begins to manifest with times of 53 ms, 17 ms, and 78 ms. At a queue
size of 100000, we can see the 'ListQueue' flaw impacting performance exponentially
with recorded times of 2804 ms, 114 ms, and 604 ms. The dictionary and python
queues seem to increase at an O(log(n)) rate with the average time per enqueue and
dequeue decreasing as the queue grows larger. Surprisingly my dictionary queue
seems to perform the best.
"""

# instantiate all 3 types of queues, my two implementations and the python implementation
list_queue = ListQueue()
dict_queue = DictQueue()
python_queue = Queue()

# fill a list-based queue with the integers 0-9999 and then remove all of them
start = time()
for x in range(10000):
    list_queue.enqueue(x)
    
for x in range(10000):
    list_queue.dequeue()
    
finish = (time() - start) * 1000

print("List queue took %.2f ms." % finish)

# fill a dictionary-based queue with the integers 0-9999 and then remove all of them
start = time()
for x in range(10000):
    dict_queue.enqueue(x)

for x in range(10000):
    dict_queue.dequeue()
    
finish = (time() - start) * 1000

print("Dictionary queue took %.2f ms." % finish)

# fill a python queue with the integers 0-9999 and then remove all of them
start = time()
for x in range(10000):
    python_queue.put(x)
    
for x in range(10000):
    python_queue.get()
    
finish = (time() - start) * 1000

print("Python queue took %.2f ms." % finish)

# test the 'empty' methods and make sure all queues have been fully emptied
if list_queue.isEmpty() and dict_queue.isEmpty() and python_queue.empty():
    print("All 3 queues are empty.\n")
    
    
