package DSP;

/**
 * @author Phil Smith
 */

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
		// their product will be equal to (ac - bd) + (ad + bc)
		return new ComplexNumber(real * x.real - imaginary * x.imaginary, real * x.imaginary + imaginary * x.real);
	}

	/**
	 * Allows a complex number to be scaled, multiplying the complex number by 'x'.
	 * @param x The number by which to scale the complex number.
	 * @return The scaled complex number.
	 */
	public ComplexNumber multiply(double x) {
		return new ComplexNumber(x * real, x * imaginary);
	}

	public ComplexNumber divide(ComplexNumber x) {
		double realPortion, imaginaryPortion, denominator;

		/*
		 * In complex number division, the denominator must be converted to the real plane
		 * by multiplying by its conjugate. The real portion of the resulting complex number
		 * is equal to (ac + bd)/(c^2 + d^2) and the imaginary portion is equal to
		 * (bc - ad)/(c^2 + d^2) i.
		 */

		if (x.imaginary != 0) {
			denominator = Math.pow(x.real, 2) + Math.pow(x.imaginary, 2);

			realPortion = (real * x.real + imaginary * x.imaginary) / denominator;

			imaginaryPortion = (imaginary * x.real - real * x.imaginary) / denominator;
		} else {
			realPortion = real / x.real;
			imaginaryPortion = imaginary / x.real;
		}

		return new ComplexNumber(realPortion, imaginaryPortion);
	}

	public double magnitude() {
		return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
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
		String operator = " + ";
		String formattedImaginary = Double.toString(imaginary);

		if (imaginary < 0) {
			operator = " - ";
			formattedImaginary = Double.toString(imaginary*-1);
		}

		return real + operator + formattedImaginary + "i";
	}
}
