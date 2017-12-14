package DSP;

/**
 * @author Phil Smith
 */

public class ComplexNumber {

	private double real;
	private double imaginary;

	/**
	 * This constructor creates a new complex number of the form (a + bi) where 'a' is the real portion
	 * and 'b' is the imaginary portion.
	 * @param a The real portion of the complex number.
	 * @param b The imaginary portion of the complex number.
	 */
	public ComplexNumber(double a, double b) {
		real = a;
		imaginary = b;
	}

	/**
	 * Adds two complex numbers.
	 * @param x The other complex number to add to this number.
	 * @return A new complex number containing the sum.
	 */
	public ComplexNumber add(ComplexNumber x) {
		double real = this.real + x.real;
		double imaginary = this.imaginary + x.imaginary;
		
		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Subtracts two complex numbers.
	 * @param x The other complex number to subtract from this number.
	 * @return A new complex number containing the sum.
	 */
	public ComplexNumber subtract(ComplexNumber x) {
		double real = this.real - x.real;
		double imaginary = this.imaginary - x.imaginary;
		
		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Multiplies two complex numbers.
	 * @param x The other complex number to be multiplied by this number.
	 * @return A new complex number containing the product.
	 */
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

	/**
	 * Divides this complex number by another complex number.
	 * @param x The other complex number by which this number should be divided.
	 * @return A new complex number containing the quotient.
	 */
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

	/**
	 * Calculates the magnitude of this complex number.
	 * @return How long this vector is in the imaginary plane.
	 */
	public double magnitude() {
		return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
	}

	/**
	 * Produces the conjugate of this complex number. A complex number added to its conjugate should
	 * produce a real number. A complex number multiplied by its conjugate should produce the squared
	 * magnitude of the complex number.
	 * @return The conjugate of this complex number.
	 */
	public ComplexNumber conjugate() {
		return new ComplexNumber(real, -1 * imaginary);
	}

	public double getReal() {
		return real;
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
