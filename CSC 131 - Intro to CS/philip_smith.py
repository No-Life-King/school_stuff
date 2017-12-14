def mult_tables_one(num):
    """
    Prints a row of the multiplication table of the number 'num'
    from num*1 to num*10.
    """
    print(num, end="\t")
    for x in range(1, 11):
        print(x*num, end='\t')
    
    print('\n')


def mult_tables_two(start, end):
    """
    Prints the rows of the multiplication table from the specified
    starting point to the specified ending point. The columns go
    from 1 to 10.
    """
    print("\t", end='')
    for x in range(1, 11):
        print(x, end='\t')
        
    print('\n')
    
    for y in range(start, end+1):
        mult_tables_one(y)
        
        
def is_prime(x):
    """
    Returns true if 'x' is prime, otherwise false.
    """
    if (x == 2):
        return True
    for a in range(2, x//2+2):
        if (x%a==0):
            return False
    
    return True
    

def prime_split(num):
    """
    Accepts an integer input and returns the integer as the sum of
    two primes. 
    """
    if (num <= 2 or num%2==1):
        return "Improper Number"
    
    for x in range(2, num):
        if (is_prime(x) and is_prime(num-x)):
            return str(x) + "+" + str(num-x)
        
        
        
#mult_tables_one(8)
#mult_tables_two(1, 10)
#print(is_prime(34))
#print(prime_split(8490))


