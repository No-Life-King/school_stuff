package DSP;

public class ComplexNumber {
	private double real;
	private double imaginary;
	
	public ComplexNumber(double a, double b) {
		real = a;
		imaginary = b;
	}
	
	public ComplexNumber add(ComplexNumber x) {
		double real = this.real + x.real;
		double imaginary = this.imaginary + x.imaginary;
		
		return new ComplexNumber(real, imaginary);
	}
	
	public ComplexNumber subtract(ComplexNumber x) {
		double real = this.real - x.real;
		double imaginary = this.imaginary - x.imaginary;
		
		return new ComplexNumber(real, imaginary);
	}
	
	public ComplexNumber multiply(ComplexNumber x) {
		// for two imaginary numbers (a + bi) and (c + di),  
		// their product will be equal to (ac - bd) + (ad + bc)i
		
		return new ComplexNumber(real * x.real - imaginary * x.imaginary, real * x.imaginary + imaginary * x.real);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof ComplexNumber) {
			
			ComplexNumber other = (ComplexNumber) o;
			
			if (real == other.real && imaginary == other.imaginary) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override 
	public String toString() {
		return real + " " + imaginary + "i";
	}
}
