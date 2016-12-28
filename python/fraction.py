def gcf(m, n):
    while m%n != 0:
        old_m = m
        old_n = n
        m = old_n
        n = old_m % old_n
    return n

    
class Fraction:
    
    
    def __init__(self, top, bottom):
        self.numerator = top
        self.denominator = bottom
        
        
    def __str__(self):
        return "%d/%d" % (self.numerator, self.denominator)
    
    
    def __add__(self, f2):
        numerator = self.numerator * f2.denominator + self.denominator * f2.numerator
        denominator = self.denominator * f2.denominator
        greatest_common_factor = gcf(numerator, denominator)
        return Fraction(numerator//greatest_common_factor, denominator//greatest_common_factor)        
    
    
    def __sub__(self, f2):
        numerator = self.numerator * f2.denominator - self.denominator * f2.numerator
        denominator = self.denominator * f2.denominator
        greatest_common_factor = gcf(numerator, denominator)
        return Fraction(numerator//greatest_common_factor, denominator//greatest_common_factor)        
    
    
    def __eq__(self, other):
        firstnum = self.numerator * other.denominator
        secondnum = other.numerator * self.denominator
        return firstnum == secondnum
    
    
    def display_fraction(self):
        print("%d/%d" % (self.numerator, self.denominator))
    


