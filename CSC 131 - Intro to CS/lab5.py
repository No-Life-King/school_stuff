def two_max(a, b):
    """
    Returns the greater of two numbers a and b.
    
    Args:
        a (number): the first number
        b (number): the second number
    Returns: a or b, whichever is larger.
    """
    if (a > b):
        return a
    else:
        return b

def two_min(a, b):
    """
    Returns the lesser of two numbers a and b.
    
    Args:
        a (number): the first number
        b (number): the second number
    Returns: a or b, whichever is smaller.
    """
    if (a < b):
        return a
    else:
        return b

def three_max_v1(a, b, c):
    """
    Returns the greater of three numbers a, b, and c. 
    
    Args:
        a (number): the first number
        b (number): the second number
        c (number): the third number
    Returns: a, b, or c, whichever is larger.
    """
    if (a > b and a > c):
        return a
    elif (b > a and b > c):
        return b
    else:
        return c
    
def three_max_v2(a, b, c):
    """
    Returns the greater of three numbers a, b, and c by
    making two calls to the "two_max" function.
    
    Args:
        a (number): the first number
        b (number): the second number
        c (number): the third number
    Returns: a, b, or c, whichever is larger.
    """
    return two_max(two_max(a, b), c)

def is_odd(a):
    """
    Returns "True" if the number is odd or "False" if the
    number is even.
    
    Args:
        a (number): the number whose oddity is to be determined
    Returns:
        True or False
    """
    if (a%2 == 0):
        return False
    return True
    
def length(s):
    """
    Returns the length of the string "s".
    
    Args:
        s (String): the string whose characters are to be counted
    Returns:
        the number of characters
    """
    counter = 0
    for x in s:
        counter += 1
    return counter
    
def sum_odd(n):
    """
    Returns the sum of the odd numbers between 1 and n.
    
    Args:
        n (number): the stopping point
    Returns:
        the sum of the odd numbers up to and excluding n
    """
    sum = 0
    for x in range(1, n, 2):
        sum += x
        
    return sum
    
def sum_series(m, n):
    """
    Returns the sum of the numbers up to m that are divisible by n.
    
    Args:
        m (integer): the stopping point
        n (integer): the number to divide by to see if there is a remainder
    Returns:
        the sum of the sum of the multiples of n
    """
    sum = 0
    for x in range(1, m):
            if (x%n == 0):
                sum += x
            
    return sum
    
def my_max(aList):
    """
    Returns the maximum value in a list of numbers.
    
    Args:
        aList (list): the list of numbers
    Returns:
        the maximum value
    """
    max = 0
    for x in aList:
        max = two_max(max, x)
        
    return max
    
def my_min(aList):
    """
    Returns the minimum value in a list of numbers.
    
    Args:
        aList (list): the list of numbers
    Returns:
        the minimum value
    """
    min = 0
    for x in aList:
        min = two_min(min, x)
        
    return min

def count_negative(aList):
    """
    Returns the number of negative numbers in a list of numbers.
    
    Args:
        aList (list): the list of numbers
    Returns:
        the number of negative numbers
    """
    count = 0
    for x in aList:
        if x < 0:
            count += 1
    
    return count

def count_passing(grade_list, passing_score):
    """
    Returns the minimum value in a list of numbers.
    
    Args:
        aList (list): the list of numbers
    Returns:
        the minimum value
    """
    count = 0
    for grade in grade_list:
        if grade >= passing_score:
            count += 1
            
    return count

def contains_char(aString, aChar):
    """
    Checks to see if a certain character is contained within the specified string.
    
    Args:
        aString (String): the string to be tested
        aChar (char): the character to be searched for
    Returns:
        True if the character is present or False if it is not.
    """
    # I wrote it this way first and then remembered Python's nice "if x in iterable" feature
    # for char in aString:
    #     if char == aChar:
    #         return True
    if aChar in aString:
        return True
    return False

def power(a, b):
    """
    Calculates the value a to the power of b. 
    
    Args:
        a (number): the base
        b (int): the exponent
    Returns:
        True if the character is present or False if it is not.
    """
    answer = a
    for x in range(b-1):
        answer *= a
        
    return answer

def contains(aList, aNumber):
    """
    Checks to see if a list contains a number. 
    
    Args:
        aList (list): a list of numbers
        aNumber (number): the number to be searched for
    Returns:
        True if the character is present or False if it is not.
    """
    if aNumber in aList:
        return True
    return False

def location(aList, aNumber):
    """
    Returns the position of a number in a list or -1 if the number is not in the list. 
    
    Args:
        aList (list): a list of numbers
        aNumber (number): the number to be searched for
    Returns:
        the position or -1
    """
    count = 0
    for number in aList:
        if (number == aNumber):
            return count
        count += 1
    return -1

def count_between(aList, low, high):
    """
    Counts the number of numbers that are between "low" and "high" in a list. 
    
    Args:
        aList (list): a list of numbers
        low (number): the lower bound of the search
        high (number): the upper bound of the search
    Returns:
        the number of numbers between "low" and "high"
    """
    count = 0
    for x in aList:
        if (low < x and high > x):
            count += 1
    
    return count

def count_vowels(aString):
    """
    Counts the number of vowels that are in a string (excluding "y"). 
    
    Args:
        aString (String): the string whose vowels should be counted
    Returns:
        the number of vowels
    """
    count = 0
    for char in aString:
        if char in ['a', 'e', 'i', 'o', 'u']:
            count += 1
    
    return count





