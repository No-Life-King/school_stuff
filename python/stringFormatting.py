#!/usr/bin/env python3
import math

name = "Phil"
age = 26

# this shows how a string and int can be dropped into a sentence
print("%s was %d when this was written." % (name, age))

circumference = 2*math.pi

# this demonstrates how a number is formatted as an int with %d
# or a float with %f
print("The circumference of a unit circle is %d. Or more precisely it is %f." % (circumference, circumference))

# shows how to specify the number of decimal places to round to
print("The most common approximation of pi is %.2f." % math.pi)

# floats will be rounded
decimalNumber = 5.69
print("Is %.1f rounded or truncated? It's rounded." % decimalNumber)

# using a dictionary value
smallDict = {"Apple": 1.50, "Banana": .99, "Carrot": .89}
discount = smallDict["Banana"] - smallDict["Banana"]*.1
print("Bananas cost $%(Banana).2f per pound " % smallDict, end="")
print("or $%.2f with a discount." % discount)