def i_power(x, y):
    """
    Calculates the value of x to the power of y using a 'for' loop.

    Args:
        x (number): the base number
        y (int): the exponent

    Returns:
        a number
    """
    
    total = 1
    for count in range(y):
        total *= x
    return total

def w_power(x, y):
    """
    Calculates the value of x to the power of y using a 'while' loop.

    Args:
        x (number): the base number
        y (int): the exponent

    Returns:
        a number
    """
    
    total = 1
    count = 0
    while (count < y):
        total *= x
        count += 1
    return total

def r_power(x, y):
    """
    Calculates the value of x to the power of y using recursion.

    Args:
        x (number): the base number
        y (int): the exponent

    Returns:
        a number
    """
    if (y == 0):
        return 1
    
    y -= 1
    return x * r_power(x, y)
    
def i_sum_to(n):
    """
    Calculates the sum of the first n reciprocals using a 'for' loop.

    Args:
        n (int): n times the final reciprocal is equal to 1

    Returns:
        a number
    """
    total = 0
    for x in range(1,n+1):
        total += 1/x
    return total

def w_sum_to(n):
    """
    Calculates the sum of the first n reciprocals using a 'while' loop.

    Args:
        n (int): n times the final reciprocal is equal to 1

    Returns:
        a number
    """
    total = 0
    count = 0
    while (count < n):
        count += 1
        total += 1/count
    return total
    
def r_sum_to(n):
    """
    Calculates the sum of the first n reciprocals using recursion.

    Args:
        n (int): n times the final reciprocal is equal to 1

    Returns:
        a number
    """
    if (n == 0):
        return 0
    n-=1
    return 1/(n+1)+r_sum_to(n)

def i_repeat(text, x):
    """
    Creates a string of text that contains the parameter text repeated x number of times using a 'for' loop.

    Args:
        text (string): the text that is to be repeated
        x (int): the number of times to repeat the text

    Returns:
        a string of text
    """
    string_builder = ""
    for i in range(x):
        string_builder += text
    return string_builder

def w_repeat(text, x):
    """
    Creates a string of text that contains the parameter text repeated x number of times using a 'while' loop.

    Args:
        text (string): the text that is to be repeated
        x (int): the number of times to repeat the text

    Returns:
        a string of text
    """
    string_builder = ""
    while (x > 0):
        string_builder += text
        x -= 1
    return string_builder

def r_repeat(text, x):
    """
    Creates a string of text that contains the parameter text repeated x number of times using recursion.

    Args:
        text (string): the text that is to be repeated
        x (int): the number of times to repeat the text

    Returns:
        a string of text
    """
    if (x == 0):
        return ""
    
    x -= 1
    return text + r_repeat(text, x)

def smallest(aList):
    """
    Returns the smallest number in a list of numbers using a 'while' loop.

    Args:
        aList (list): the list of numbers

    Returns:
        a number
    """
    smallest = 0
    i = 0
    while (i < len(aList)):
        if (aList[i] < smallest):
            smallest = aList[i]
        i += 1
    return smallest

def has_repeats(aList):
    """
    Determines if a list contains any repeated numbers.

    Args:
        aList (list): the list of numbers

    Returns:
        True or False
    """
    previous = ""
    for x in aList:
        if (x == previous):
            return True
        previous = x
    return False

def reverse(text):
    """
    Reverses the characters in a string.

    Args:
        text (String): the text to reverse

    Returns:
        A String
    """
    index = len(text) - 1
    reversed_text = ""
    while (index >= 0):
        reversed_text += text[index]
        index -= 1
    return reversed_text
    
def binary(n):
    """
    Returns the base 2 representation of a base 10 number

    Args:
        n (int): the base 10 number to be converted

    Returns:
        A String of 1s and 0s
    """
    x = 0
    while (r_power(2, x) <= n):
        x += 1
    x -= 1
    binary = ""
    while (x >= 0):
        if (n - r_power(2, x) >= 0):
            binary += "1"
            n -= r_power(2, x)
        else:
            binary += "0"
        x -= 1
    return binary
    







