"""
This file contains two different implementations of a queue. The 'ListQueue'
class was copied out of my textbook. I noticed an inherent design flaw in
using a List to implement a queue so I also wrote dictionary-based queue.
Inserting an item at the beginning of a list causes the list to have to
increment all other indices which means the time it takes to enqueue an item
grows exponentially as the queue increases in size. My dictionary-based queue
cuts that time out of the equation.
"""

class ListQueue:
    """
    A list-based implementation of a queue.
    """
    
    def __init__(self):
        self.items = []
        
    def isEmpty(self):
        return len(self.items) == 0
    
    def enqueue(self, item):
        self.items.insert(0, item)
        
    def dequeue(self):
        return self.items.pop()
    
    def size(self):
        return len(self.items)
    
    
class DictQueue:
    """
    A dictionary-based implementation of a queue.
    """
    
    def __init__(self):
        self.items = {}
        self.front = 0
        self.rear = 0
        
    def isEmpty(self):
        return len(self.items) == 0
    
    def enqueue(self, item):
        self.items[self.rear] = item
        self.rear += 1
        
    def dequeue(self):
        if self.front != self.rear:
            item = self.items.pop(self.front)
            self.front += 1
            return item
        else:
            raise RuntimeError("Empty Queue")
        
    def size(self):
        return len(self.items)
        
    