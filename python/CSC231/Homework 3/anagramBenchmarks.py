import string
import random
from time import clock
from anagramSolutions import *


def randomString(size):
    '''
    Generates a random string of length 'size'.
    '''
    return ''.join(random.choice(string.ascii_lowercase) for _ in range(size))


def makeAnagram(s):
    '''
    Scrambles up the characters of a string.
    '''
    temp = s;
    t = ''
    while temp != '':
        i = random.randrange(0, len(temp))
        t += temp[i]
        temp = temp[:i] + temp[(i+1):]
    return t

print("\t\t~Test 1~")
print("N\tSolution1\tSolution2\tSolution4")
for n in range(100, 1001, 50):
    
    s = randomString(n)
    t = makeAnagram(s)
    
    start = clock()
    anagramSolution1(s, t)
    sol1 = (clock() - start) * 1000
    
    start = clock()
    anagramSolution2(s, t)
    sol2 = (clock() - start) * 1000
    
    start = clock()
    anagramSolution4(s, t)
    sol4 = (clock() - start) * 1000
    
    print(n, sol1, sol2, sol4, sep='\t')
    

print("\n\t\t~Test 2~")
print("N\tSolution2\tSolution4")
for n in range(10000, 100001, 5000):
    
    s = randomString(n)
    t = makeAnagram(s)
    
    start = clock()
    anagramSolution2(s, t)
    sol2 = (clock() - start) * 1000
    
    start = clock()
    anagramSolution4(s, t)
    sol4 = (clock() - start) * 1000
    
    print(n, sol2, sol4, sep='\t')
    
    
    
    
    
    
    