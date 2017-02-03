#!/usr/bin/env python3

# open 'numbers.txt' in read mode
numbers_file = open("numbers.txt", 'r')

# read each line into a list of integers
integers = [int(x.strip()) for x in numbers_file]

# get the size and print it
size_of_integers = len(integers)
print("The number of integers is", size_of_integers)

# sort the numbers so that the min, max, and median can be found easily
integers.sort()
total = 0
for number in integers:
    total += number
  
print("The overall average is", total/size_of_integers)
print("The minimum number is", integers[0])
print("The maximum number is", integers[size_of_integers - 1])

# find the median
median = 0
# determine whether there are 1 or 2 middle values and perform the appropriate calculations to get the median
if size_of_integers % 2 == 1:
    median = integers[size_of_integers//2]
else:
    median = (integers[size_of_integers//2] + integers[size_of_integers//2 - 1]) / 2
    
print("The median is", median)

