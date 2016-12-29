#!/usr/bin/env python3
from Stack import Stack
"""
This script uses a stack to implement a method of converting a decimal number
to a binary string. The method entails dividing the decimal number by 2
repeatedly. If there is a remainder, then a binary '1' is pushed to the stack,
otherwise a zero. A stack makes sense in this senario because it can be used
to efficiently reverse the binary number at the end of the process.
"""

def decimal_to_binary(decimal_number):
    """
    Converts a decimal number to binary using the 'divide-by-2' method.
    """
    binary_stack = Stack()
    while decimal_number > 0:
        binary_digit = decimal_number % 2
        binary_stack.push(binary_digit)
        decimal_number = decimal_number // 2
    
    binary_string = ''
    while not binary_stack.isEmpty():
        binary_string += str(binary_stack.pop())
        
    return binary_string


# a couple of tests for this function
print(decimal_to_binary(233))       # should return 11101001
print(decimal_to_binary(1024))      # should return 10000000000