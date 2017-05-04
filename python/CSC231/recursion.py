#!/usr/bin/env python3

def reverseList(aList):
    """
    Uses recursion to return a list in reverse order.
    """
    if len(aList) == 1:
        return aList
    
    return [aList[-1]] + reverseList(aList[:-1])

def countVowels(s):
    """
    Uses recursion to count the number of vowels in a string (excluding 'y').
    """
    if len(s) == 0:
        return 0
    
    if s[0].lower() in 'aeiou':
        return 1 + countVowels(s[1:])
    else:
        return 0 + countVowels(s[1:])


print(reverseList([0,1,2,3,4,5]))
print(countVowels('Able was I ere I saw Elba'))