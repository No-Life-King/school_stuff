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
    number = 2
    
    # This loop adds all of the factors. A factor cannot be greater than 1/2
    # of the number itself, therefore the loop ends at the half-way mark.
    while (number <= n//2):
        if (n%number == 0):
            sum_of_factors += number
        number += 1
          
    return n == sum_of_factors


def sum_of_digits(n):
    """
    Returns the sum of the digits of a number.
    """
    total = 0
    while (n > 0):
        total += n%10
        n = n//10
    return total

def square_root(n, threshold):
    """
    This function uses Newton's method to approximate the square root
    of the number n with the accuracy of the defined threshold.
    """
    current_approximation = n/2
    last_approximation = n
    count = 1
    while (last_approximation - current_approximation > threshold):
        last_approximation = current_approximation
        current_approximation = .5*(current_approximation + n/current_approximation)
        count += 1
    return current_approximation, count

def pi_series(threshold):
    """
    This function uses a series to estimate pi to the precision of the specified threshold.
    """
    denominator = 3
    add = False
    pi = 4
    previous_pi = 0
    difference = 4
    count = 1
    
    while (difference > threshold):
        # add or subtract the fraction from the running total
        previous_pi = pi
        if (add == True):
            pi += 4/denominator
            add = False
        else:
            pi -= 4/denominator
            add = True
        
        denominator += 2
        count += 1
        difference = pi - previous_pi
        
        # take the absolute value of 'difference' so that it can be compared to the threshold
        if (difference < 0):
            difference *= -1
    
    return pi, count

















