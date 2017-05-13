import sys
import random
import quickSort1
import time

sys.setrecursionlimit(10001)


def quickSort(alist):
    first = alist[0]
    last = alist[len(alist)-1]
    middle = alist[len(alist)//2]
    if (last > first and last < middle) or (last < first and last > middle):
        alist[0] = last
        alist[len(alist)-1] = first
    elif (middle > first and middle < last) or (middle < first and middle > last):
        alist[0] = middle
        alist[len(alist)//2] = first

    quickSortHelper(alist,0,len(alist)-1)

def quickSortHelper(alist,first,last):
   if first<last:

       splitpoint = partition(alist,first,last)

       quickSortHelper(alist,first,splitpoint-1)
       quickSortHelper(alist,splitpoint+1,last)


def partition(alist,first,last):
   pivotvalue = alist[first]

   leftmark = first+1
   rightmark = last

   done = False
   while not done:

       while leftmark <= rightmark and alist[leftmark] <= pivotvalue:
           leftmark = leftmark + 1

       while alist[rightmark] >= pivotvalue and rightmark >= leftmark:
           rightmark = rightmark -1

       if rightmark < leftmark:
           done = True
       else:
           temp = alist[leftmark]
           alist[leftmark] = alist[rightmark]
           alist[rightmark] = temp

   temp = alist[first]
   alist[first] = alist[rightmark]
   alist[rightmark] = temp


   return rightmark


print("n\tModifiedQuicksort\tQuicksort")
for n in range(100, 10001, 100):
    last10percent = [random.randint(0, n*2) for x in range(n//10)]

    unsorted = [x for x in range(n - n//10)] + last10percent
    unsorted2 = unsorted.copy()

    start = time.time()
    quickSort(unsorted)
    modifiedQuicksort = time.time() - start

    start = time.time()
    quickSort1.quickSort(unsorted2)
    quicksort = time.time() - start

    print(n, '\t', modifiedQuicksort, '\t', quicksort)