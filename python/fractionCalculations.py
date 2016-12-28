#!/usr/bin/env python3
from fraction import Fraction

# declare some new instances of the fraction class
a = Fraction(1, 8)
b = Fraction(1, 6)
c = Fraction(2, 12)

# print the fractions using the 'display' method and the '__str__' method
print("a = ", end="")
a.display_fraction()
print("b = ", end="")
print(b)
print("c = " + str(c))

# add the fractions using the defined '__add__' behavior
total = a + b
print("a + b = " + str(total))

def check_equality(a, b):
    if a == b:
        print("%s is equal to %s" % (a, b))
    else:
        print("%s is not equal to %s" % (a, b))
        
check_equality(a, b)
check_equality(b, c)

difference = b - a
print("b - a =", difference)
difference = a - b
print("a - b =", difference)

