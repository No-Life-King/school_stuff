#imports the math module
import math

#creates a list of numbers
numbers = [12, 10, 32, 3, 66, 17, 42, 99, 20]

#the "for loop" that prints each number in the list
for x in numbers:
    print(x)

#the "for loop" that prints each number and its square
for x in numbers:
    print(x, math.pow(x, 2))
    
