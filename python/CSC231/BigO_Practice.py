__author__ = 'guinnc'
'''
For use in Lab 3.
'''

import time, random, math

def mystery1(n):
    '''What is it's big O?'''
    sum = 0
    for i in range(0, 10):
        sum += n
    return sum

def mystery2(n):
    '''What is it's big O?'''
    sum = 0
    for i in range(0, n):
        sum += i
    return sum


def mystery3(n):
    '''What is it's big O?'''
    sum = 0
    for i in range(0, n):
        for j in range(0, n):
            sum += i + j
    return sum

def mystery4(n):
    '''What is it's big O?'''
    sum = 0
    for i in range(0, n):
        for j in range(0, 25):
            sum += i + j
    return sum

def mystery5(n):
    '''What is it's big O?'''
    sum = 0
    while n >  0:
        sum += n
        n = n // 2     
    return sum

def mystery6(n):
    '''What is it's big O?'''
    sum = 0
    for i in range(0, n):
        while n >  0:
            sum += n
            n = n // 2     
    return sum

def mystery7(n):
    '''What is it's big O?'''
    if n == 2:
        return True
    if n % 2 == 0:
        return False
    div = 3
    while div < math.sqrt(n):
        if n % div == 0:
            return False
        div += 2   
    return True

def mystery8(n):
    '''What is it's big O?'''
    sum = 0
    for i in range(0, n):
        sum += mystery2(i)
    return sum

# let's generate some experimental times
print("N\tM1\tM2\tM3\tM4\tM5\tM6\tM7\tM8")
for n in range(100, 1001, 25):
    start = time.clock()
    result = mystery1(n)
    time1 = time.clock() - start

    start = time.clock()
    result = mystery2(n)
    time2 = time.clock() - start

    start = time.clock()
    result = mystery3(n)
    time3 = time.clock() - start

    start = time.clock()
    result = mystery4(n)
    time4 = time.clock() - start

    start = time.clock()
    result = mystery5(n)
    time5 = time.clock() - start

    start = time.clock()
    result = mystery6(n)
    time6 = time.clock() - start

    start = time.clock()
    result = mystery7(n)
    time7 = time.clock() - start

    start = time.clock()
    result = mystery8(n)
    time8 = time.clock() - start

    print(n, "\t", time1, "\t", time2, "\t", time3, "\t", time4, "\t", time5, "\t", time6, "\t", time7, "\t", time8)
