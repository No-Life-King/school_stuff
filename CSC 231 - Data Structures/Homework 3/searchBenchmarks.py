from time import time, clock


def sequentialSearch(collection, element):
    '''
    Iterate through each element of a collection to determine if 'element' exists in the
    collection.
    '''
    for x in collection:
        if x == element:
            return True
    return False


def betterFind(aList, element):
    '''
    A recursive binary search. 
    '''
    return betterFindHelper(aList, element, 0, len(aList) - 1)
 

def betterFindHelper(aList, element, start, end):
    '''
    A helper function for the recursive binary search.
    '''
    if start > end:
        return False
    middle = (start + end) // 2
    if aList[middle] == element:
        return True
    elif aList[middle] > element:
        return betterFindHelper(aList, element, start, middle - 1)
    else:
        return betterFindHelper(aList, element, middle + 1, end)
    
    
def binarySearchIteratively(aList, element):
    '''
    An iterative binary search.
    '''
    left = 0
    right = len(aList) - 1

    while left <= right:
        mid = (left + right) // 2
        if aList[mid] == element:
            return True
        elif aList[mid] > element:
            right = mid - 1
        else:
            left = mid + 1
    return False


print("N\tSequential\tBinary")
for n in range(500000, 5000001, 500000):
    # prepopulate a big list of numbers so that the time spent generating the numbers isn't
    # factored into the search benchmarks
    bigListOfNumbers = [x for x in range(n)]
    
    start = clock()
    sequentialSearch(bigListOfNumbers, -1)
    sequentialSearchTime = clock() - start
    
    start = clock()
    betterFind(bigListOfNumbers, -1)
    binarySearchTime = clock() - start
    
    print(str(n) + '\t' + str(sequentialSearchTime * 1000) + '\t' + str(binarySearchTime * 1000))
    

print("\nN\tRecursive Binary\tIterative Binary")
for n in range(500000, 5000001, 500000):
    # prepopulate a big list of numbers so that the time spent generating the numbers isn't
    # factored into the search benchmarks
    bigListOfNumbers = [x for x in range(n)]
    
    start = clock()
    binarySearchIteratively(bigListOfNumbers, -1)
    binarySearchIterTime = clock() - start
    
    start = clock()
    betterFind(bigListOfNumbers, -1)
    binarySearchTime = clock() - start
    
    print(str(n) + '\t' + str(binarySearchTime * 1000) + '\t' + str(binarySearchIterTime * 1000))



'''
Some test code for the 'sequentialSearch' function. On my machine, it takes 1.5 ms to find
a value half-way through the list, and 3.0 ms to return 'False' after having searched the
entire list. This is approximately linear.
'''
'''
collection = [x for x in range(1, 100000)]
start = time()
found = sequentialSearch(collection, -1)
timeTaken = time() - start
print(found, timeTaken * 1000, 'ms')
'''


