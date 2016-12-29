#!/usr/bin/env python3
from Stack import Stack

cats = Stack()
dogs = Stack()

print(dogs.isEmpty())
cats.push('neko')
cats.push('mia')
cats.push('callie')
print(cats.items)
dogs.push('snoopy')
dogs.push('pluto')
dogs.push('hunter')
print(dogs.items)
print(dogs.pop())
print(dogs.items)
print(dogs.peek())
print(dogs.size())

string = "reverse this string"
string_stack = Stack()
for letter in string:
    string_stack.push(letter)

backwards = '' 
for char in range(string_stack.size()):
    backwards += string_stack.pop()
    
print(backwards)