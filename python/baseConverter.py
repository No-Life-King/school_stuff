#!/usr/bin/env python3
from Stack import Stack
"""
This script works the same as 'decimalToBinary.py' except that it has been
modified slightly to support conversion from decimal to any base 2-16.
"""

def baseConverter(decimal_number, base):
    if decimal_number == 0:
        return '0'
    
    digits = "0123456789ABCDEF"
    converted_stack = Stack()
    
    while decimal_number > 0:
        remainder = decimal_number % base
        converted_stack.push(remainder)
        decimal_number = decimal_number // base
        
    converted_string = ''
    while not converted_stack.isEmpty():
        converted_string += digits[converted_stack.pop()]
        
    return converted_string


print("27 in binary is:", baseConverter(27, 2))     # should print 11011
print("27 in hex is:", baseConverter(27, 16))       # should print 1B
print("27 in octal is:", baseConverter(27, 8))      # should print 33


# prints a table of the first 100 integers converted to the 3 most common bases
# used in computing just to make sure things are working out right
print("\nHEX\tOCT\tBIN")
for x in range(101):
    print(baseConverter(x, 16) + '\t' + baseConverter(x, 8) + '\t' + baseConverter(x, 2))
    
