# This function uses Newton's method to estimate the square root of a number.
def square_root(number, num_terms):
    approximation = number/2
    for x in range(num_terms-1):
        approximation = .5*(approximation + number/approximation)
    return approximation

# Estimating the square root of 100.
# print(square_root(100, 7))