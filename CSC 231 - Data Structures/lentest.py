#!/usr/bin/env python3
from time import clock

a = []

def howlong():
    if len(a) == 0:
        return True

def equals():
    if a == []:
        return True
        
def isNotm():
    if not a:
        return True
        

length = clock()
    howlong()
    
length = clock() - length

equal = clock()
    equals()
    
equal = clock() - equal

isNot = clock()
    isNotm()
    
isNot = clock() - isNot

print('Times:\t isnot - ' + str(isNot) + '\tequal' + str(equal) + '\tlength' + str(length))
    