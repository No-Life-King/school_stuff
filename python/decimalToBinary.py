#!/usr/bin/env python3
from Stack import Stack

def decimal_to_binary(decimal_number):
    binary_stack = Stack()
    while decimal_number > 0:
        binary_digit = decimal_number % 2
        binary_stack.push(binary_digit)
        decimal_number = decimal_number // 2
    
    binary_string = ''
    while not binary_stack.isEmpty():
        binary_string += str(binary_stack.pop())
        
    return binary_string

print(decimal_to_binary(233))
print(decimal_to_binary(1024))