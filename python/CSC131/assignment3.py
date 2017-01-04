# Assignment 3 for CSC 131
# Author: Philip Smith

def is_perfect(n):
    """
    This function accepts an integer as input and performs the necessary
    calculations to determine if the sum of the factors of the number
    is equal to the number itself. In other words, it determines if the
    number is "Perfect".
    """
    # 1 is a factor of every number so the variable can be initialized with
    # this value and then it can be skipped in the process of finding factors
    sum_of_factors = 1
    
    # This loop adds all of the factors. A factor cannot be greater than 1/2
    # of the number itself, therefore the loop ends at the half-way mark.
    for x in range (2, n//2+1):
        if (n%x == 0):
            sum_of_factors += x
          
    if (n == sum_of_factors):
        return True
    else:
        return False
    
def print_perfect(n):
    """
    This function accepts an integer as input and will print all of the perfect
    numbers between 1 and n.
    """
    for x in range(n):
        if is_perfect(x):
            print(x)
            
def calc_pi(n):
    """
    This function uses a series to estimate pi.
    """
    denominator = 1
    add = True
    piFourths = 0
    count = 0
    while (count < n):
        # add or subtract the fraction from the running total
        if (add == True):
            piFourths += 1/denominator
            add = False
        else:
            piFourths -= 1/denominator
            add = True
        
        denominator += 2
        count += 1
        
    return 4*piFourths



