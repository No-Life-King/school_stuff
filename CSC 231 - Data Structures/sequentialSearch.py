from time import clock


def sequentialSearch(collection, element):
    '''
    Iterate through each element of a collection to determine if 'element' exists in the
    collection.
    '''
    for x in collection:
        if x == element:
            return True
    return False


print("N\tMicroseconds")
for n in range(500000, 5000001, 500000):
    # prepopulate a big list of numbers so that the time spent generating the numbers isn't
    # factored into the search benchmarks
    bigListOfNumbers = [x for x in range(n)]
    
    start = clock()
    sequentialSearch(bigListOfNumbers, -1)
    sequentialSearchTime = clock() - start
    
    
    print(str(n) + '\t' + str(sequentialSearchTime * 1000000))