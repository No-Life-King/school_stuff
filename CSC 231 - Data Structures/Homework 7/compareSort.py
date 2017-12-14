import random, time
import bubbleSort, insertionSort, mergeSort, quickSort, selectionSort, shellSort
import sys


sys.setrecursionlimit(100000)
# print("N \t Bubble \t Select \t Insert \t Shell \t Merge \t Quick")
print("N \t Shell \t Merge \t Quick")

for n in range(10000, 100001, 10000):
    
    mylist = []
    for i in range(n):
        r = random.randint(0, 2*n)
        mylist.append(r)

    #mylist2 = mylist.copy()
    #mylist3 = mylist.copy()
    mylist4 = mylist.copy()
    mylist5 = mylist.copy()
    mylist6 = mylist.copy()

    """
    start = time.clock()
    bubbleSort.bubbleSort(mylist)
    end = time.clock()
    bubble = end - start

    start = time.clock()
    selectionSort.selectionSort(mylist2)
    end = time.clock()
    select = end - start

    start = time.clock()
    insertionSort.insertionSort(mylist3)
    end = time.clock()
    insert = end - start
    """
    
    start = time.clock()
    shellSort.shellSort(mylist4)
    end = time.clock()
    shell = end - start
    
    start = time.clock()
    mergeSort.mergeSort(mylist5)
    end = time.clock()
    merge = end - start
    
    start = time.clock()
    quickSort.quickSort(mylist6)
    end = time.clock()
    quick = end - start

    # print(n, '\t', bubble, '\t', select, '\t', insert, '\t', shell, '\t', merge, '\t', quick)
    print(n, '\t', shell, '\t', merge, '\t', quick)

