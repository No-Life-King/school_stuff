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
	
	// Creates a new fraction object by converting a double to a fraction
	public Fraction(double x) {
		// go by powers of 10 to 
        int y = 10;
        Double multipleOfX = new Double(x);
        Double powerOf10 = new Double(10);
        
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
        numerator = y;
        denominator = powerOf10.intValue();
        reduce();
	}
	
	public Fraction(int a, int b) {
		numerator = a;
		denominator = b;
                reduce();
        }
	
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
	
	public Fraction add(Fraction other) {
		Fraction sum = new Fraction();
		
		int a = this.numerator * other.getDenominator();
		int b = other.getNumerator() * this.denominator;
		int c = this.denominator * other.getDenominator();
		
		sum.setNumerator(a + b);
		sum.setDenominator(c);
		
		return sum;
	}
	
	public Fraction sub(Fraction other) {
		Fraction opposite = new Fraction(other.getNumerator() * -1, other.getDenominator());
		
		return add(opposite);
	}
	
	public Fraction mult(Fraction other) {
		Fraction multiplied = new Fraction(numerator * other.getNumerator(), denominator * other.getDenominator());
		return multiplied;
	}
	
	public Fraction div(Fraction other) {
		Fraction divided = new Fraction(numerator * other.getDenominator(), denominator * other.getNumerator());
		return divided;
	}
	
	public int compareTo(Fraction other) {
		if (this.toDecimal() > other.toDecimal()) {
			return 1;
		} else if (this.toDecimal() < other.toDecimal()) {
			return -1;
		} else {
			return 0;
		}
	}
	
	public double toDecimal() {
            double a = this.numerator;
            double b = this.denominator;
            return a/b;
	}
	
	@Override
	public String toString() {
		return numerator + "/" + denominator;
	}
	
}
