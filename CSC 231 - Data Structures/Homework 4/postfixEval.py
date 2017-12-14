#!/usr/bin/env python3
from Stack import Stack

def postfixEval(postfixExpr):
    operandStack = Stack()
    tokenList = postfixExpr.split()
    
    try:
        for token in tokenList:
            if token in "*/+-":
                try:
                    operand2 = operandStack.pop()
                    operand1 = operandStack.pop()
                except IndexError:
                    return "Too many operators!"
                result = doMath(token,operand1,operand2)
                operandStack.push(result)
            elif isinstance(int(token), int):
                operandStack.push(int(token))
    except ValueError:
        return "Invalid token in postfix expression."
        
    if len(operandStack.items) > 1:
        return "Too few operators!"
               
    return operandStack.pop()


def doMath(op, op1, op2):
    if op == "*":
        return op1 * op2
    elif op == "/":
        return op1 / op2
    elif op == "+":
        return op1 + op2
    else:
        return op1 - op2


print(postfixEval('3    3    7   *   +    4    +   3   +'))
