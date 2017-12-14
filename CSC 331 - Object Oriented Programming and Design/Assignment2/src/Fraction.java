/**  @author Philip Smith */

public class Fraction {
	/* 
	 * This class simply keeps track of an integer numerator and denominator
	 * of a fraction and provides a few methods for working with fractions
	 * including addition and subtraction, multiplication and division, 
	 * and simplification. 
	 */
	
	private int numerator;
	private int denominator;
	
	// Creates an empty fraction object whose numerator and denominator can be set later.
	public Fraction() {}
	
	// Creates a new fraction object by converting a double to a fraction.
	public Fraction(double x) {
		// multiply by "well chosen one" powers of 10 until a whole number numerator is reached 
        int y = 10;
        Double multipleOfX = new Double(x);
        Double powerOf10 = new Double(10);
        
        // stored as 2 integers, the fraction values cannot exceed the maximum integer value
        while (powerOf10 < Integer.MAX_VALUE) {
            multipleOfX = x * powerOf10;
            y = multipleOfX.intValue();
            
            if (y - multipleOfX == 0) {
                break;
            }
            
            if (powerOf10 == 1_000_000_000) {
            	powerOf10 *= 2;
            } else {
            	powerOf10 *= 10;
            }
        }
        
        // once a fitting multiple is found, set the numerator and denominator and reduce the fraction
        numerator = y;
        denominator = powerOf10.intValue();
        reduce();
	}
	
	// Sets the numerator and denominator and then reduces the fraction.
	public Fraction(int a, int b) {
		numerator = a;
		denominator = b;
        reduce();
	}
	
	// Employs Euclid's algorithm to find the greatest common divisor of the 
	// numerator and denominator and then factors that divisor out. 
	public void reduce() {
		int temp;
		int a = numerator;
		int b = denominator;
		while (b != 0) {
			temp = b;
			b = a % b;
			a = temp;
		}
                
        if (a != 0 || a != 1) {
            numerator /= a;
            denominator/= a;
        }
	}
	
	public int getNumerator() {
		return numerator;
	}
	
	public int getDenominator() {
		return denominator;
	}
	
	public void setNumerator(int a) {
		numerator = a;
	}
	
	public void setDenominator(int b) {
		denominator = b;
	}
	
	// Adds this fraction to another fraction, returning a new fraction that is the sum of the two.
	public Fraction add(Fraction other) {
		Fraction sum = new Fraction();
		
		int a = this.numerator * other.getDenominator();
		int b = other.getNumerator() * this.denominator;
		int c = this.denominator * other.getDenominator();
		
		sum.setNumerator(a + b);
		sum.setDenominator(c);
		
		return sum;
	}
	
	// Subtracts another fraction from this fraction, returning a new fraction that is the difference of the two.
	public Fraction sub(Fraction other) {
		// add the opposite of the other fraction
		Fraction opposite = new Fraction(other.getNumerator() * -1, other.getDenominator());
		
		return add(opposite);
	}
	
	// Multiplies this fraction by another fraction, returning a new fraction that is the product of the two.
	public Fraction mult(Fraction other) {
		Fraction multiplied = new Fraction(numerator * other.getNumerator(), denominator * other.getDenominator());
		return multiplied;
	}
	
	// Divides this fraction by another fraction, returning a new fraction.
	public Fraction div(Fraction other) {
		// simply multiply by the reciprocal 
		Fraction divided = new Fraction(numerator * other.getDenominator(), denominator * other.getNumerator());
		return divided;
	}
	
	// Compares this fraction to another fraction, returning 1 if this fraction is greater, -1
	// if this fraction is lower, and 0 if the two fractions are equal.
	public int compareTo(Fraction other) {
		if (this.toDecimal() > other.toDecimal()) {
			return 1;
		} else if (this.toDecimal() < other.toDecimal()) {
			return -1;
		} else {
			return 0;
		}
	}
	
	// Returns the double approximation of this fraction.
	public double toDecimal() {
            double a = this.numerator;
            double b = this.denominator;
            return a/b;
	}
	
	// Allow the fraction to be printed in a nice way.
	@Override
	public String toString() {
		return numerator + "/" + denominator;
	}
	
}
