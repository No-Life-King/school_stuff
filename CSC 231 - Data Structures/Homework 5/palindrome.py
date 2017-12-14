#!/usr/bin/env python3
from deque import Deque

def isPalindrome(word):
    wordDeque = Deque()
    for char in word:
        char = char.lower()
        if char in "abcdefghijklmnopqrstuvwxyz":
            wordDeque.addFront(char)
        
    while len(wordDeque.items) > 1:
        firstLetter = wordDeque.removeFront()
        lastLetter = wordDeque.removeRear()
        
        #print('comparing:', firstLetter + ',', lastLetter)  # a useful bit of debugging code
        
        if firstLetter != lastLetter:
            return False
        
    return True


print(isPalindrome('racecar'))
print(isPalindrome('palindrome'))
print(isPalindrome('A man, a plan, a canal, Panama!'))
print(isPalindrome('In girum imus nocte et consumimur igni'))