#!/usr/bin/env python3

class Node:
    
    def __init__(self, initdata):
        self._data = initdata
        self._next = None
        
    def getData(self):
        return self._data
    
    def setData(self, newdata):
        self._data = newdata
        
    def getNext(self):
        return self._next
    
    def setNext(self, newnext):
        self._next = newnext
        
        