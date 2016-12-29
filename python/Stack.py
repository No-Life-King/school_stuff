class Stack:
    """
    A python implementation of a simple stack.
    """
    
    def __init__(self):
        # create an empty list to hold stack objects
        self.items = []
        
    
    def isEmpty(self):
        # return true if the stack is empty, otherwise false
        return len(self.items) == 0
    
    
    def push(self, item):
        # add an item to the top of the stack
        self.items.append(item)
        
        
    def pop(self):
        # remove and item from the top of the stack and return it
        return self.items.pop()
    
    
    def peek(self):
        # return the item from the top of the stack without removing it from the stack
        return self.items[len(self.items)-1]
    
    
    def size(self):
        # the number of objects in the stack
        return len(self.items)