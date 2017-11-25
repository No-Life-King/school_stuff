package DSP;

/**
 * @author Phil Smith
 */

import tests.ComplexNumberTest;

public class DigitalSignalProcessing {

	public static void main(String[] args) {
		runTests(true);

		double[] testData = {26160, 19011, 18757, 18405, 17888, 14720, 14285, 17018, 18014, 17119, 16400, 17497, 17846, 15700, 17636, 17181};

		fastFourierTransform(convertToComplex(testData), false);

	}

	private static void fastFourierTransform(ComplexNumber[] vector, boolean inverseFFT) {
		double direction = 1;
		int n = vector.length;

		// if 'direction' is set to -1, an inverse Fast Fourier Transform will be performed on the vector
		if (inverseFFT) {
			direction = -1;
		}

		double theta = -2 * Math.PI * direction / n;
		int r = n / 2;

		for (int i=1; i<n; i=2*i) {
			ComplexNumber w = new ComplexNumber(Math.cos((double) i * theta), Math.sin(i * theta));

			for (int k=0; k<n; k = k + 2*r) {
				ComplexNumber u = new ComplexNumber(1, 0);
				for (int m=0; m<r; m++) {
					ComplexNumber t = vector[k + m].subtract(vector[k + m + r]);
					vector[k + m] = vector[k + m].add(vector[k + m + r]);
					vector[k + m + r] = u.multiply(t);
					u = w.multiply(u);
				}
			}

			r = r/2;
		}

		for (int i=0; i<n; i++) {
			r = i; int k = 0;

			for (int m=1; m<n; m++) {
				k = 2*k + (r % 2);
				r = r/2;
				m =2*m;
			}

			if (k > i) {
				ComplexNumber t = vector[i];
				vector[i] = vector[k];
				vector[k] = t;
			}
		}

		if (direction < 0) {
			for (int i=0; i<n; i++) {
				vector[i] = vector[i].divide(new ComplexNumber(n, 0));
			}
		}

		for (ComplexNumber i: vector) {
			System.out.println(i);
		}

	}

	private static ComplexNumber[] convertToComplex(double[] data) {
		ComplexNumber[] converted = new ComplexNumber[data.length];

		for (int x=0; x<data.length; x++) {
			converted[x] = new ComplexNumber(data[x], 0);
		}

		return converted;
	}

	private static void runTests(boolean enabled) {
		if (enabled) {
			new ComplexNumberTest();
		}
		
	}

}
