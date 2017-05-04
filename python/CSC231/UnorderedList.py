#!/usr/bin/env python3
from Node import Node

class UnorderedList:
    
    def __init__(self, initdata):
        self.head = None
        self.size = 0
        
        if isinstance(initdata, (str, float, int)):
            self.add(initdata)
        else:
            for item in initdata:
                self.add(item)
            
        
    def isEmpty(self):
        return self.head == None
    
    def add(self, item):
        temp = Node(item)
        temp.setNext(self.head)
        self.head = temp
        self.size += 1
        
    def size(self):
        return self.size
    
    def search(self, item):
        current = self.head
        while current != None:
            if current.getData() == item:
                return True
            else:
                current = current.getNext()
                
        return False
    
    def remove(self, item):
        current = self.head
        previous = None
        found = False
        while not found:
            if current.getData() == item:
                found = True
            else:
                previous = current
                current = current.getNext()
                
        if previous == None:
            self.head = current.getNext()
        else:
            previous.setNext(current.getNext())
            

            
            
    
    
    
    
    
    
    
    
    
    
    
    
