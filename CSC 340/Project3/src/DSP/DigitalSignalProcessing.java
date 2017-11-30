package DSP;

/**
 * @author Phil Smith
 */

import tests.ComplexNumberTest;
import helpers.Picture;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class DigitalSignalProcessing {

	public static void main(String[] args) {

		// test classes and methods if set to 'true'
		runTests(false);

		// phaseShift(0);

		double[] evenSamples = sineFunction(100,512, false);

		for (double sample: evenSamples) {
			//System.out.println(sample);
		}

		for (ComplexNumber c: fastFourierTransform(convertToComplex(evenSamples), false)) {
			// System.out.println(c);
		}

		// DTMFtones();

		twoDimensionalFFT();

		/*
		 * The following code uses the estimated piecewise function to generate samples for a Fast Fourier
		 * Transform, which can then be used to print out the power spectral density of the frequencies.
		 * This code can be quickly modified to use the 'sineFunction' defined in its own method below for
		 * generating an aribtrary number of even or odd frequencies and samples.
		 */

		/*
		double[] oddSamples = piecewiseFunction(512, true);
		double[] evenSamples = piecewiseFunction(512, false);

		ComplexNumber[] oddTransform = fastFourierTransform(convertToComplex(oddSamples), false);
		ComplexNumber[] evenTransform = fastFourierTransform(convertToComplex(evenSamples), false);

		for (int x=0; x<50; x++) {
			 System.out.println(Math.pow(evenTransform[x].magnitude(), 2));
		}
		*/

		/*
		 * The following code examines two sine functions with no amplitude or phase shift changes. The functions
		 * are sin(2pi(11)t) and sin(2pi(23)t). The are both added and multiplied for the purpose of examining the
		 * effects on the power spectral density.
		 */

		/*
		double[] sumOfSines = new double[1024];
		for (int x=0; x<1024; x++) {
			sumOfSines[x] = Math.sin(2 * Math.PI * 11 * x/1024.0) + Math.sin(2 * Math.PI * 23 * x/1024.0);
		}

		double[] productOfSines = new double[1024];
		for (int x=0; x<1024; x++) {
			productOfSines[x] = Math.sin(2 * Math.PI * 11 * x/1024.0) * Math.sin(2 * Math.PI * 23 * x/1024.0);
		}

		ComplexNumber[] sumTransform = fastFourierTransform(convertToComplex(sumOfSines), false);
		ComplexNumber[] productTransform = fastFourierTransform(convertToComplex(productOfSines), false);

		for (int x=0; x<1024; x++) {
			// System.out.println(Math.pow(productTransform[x].magnitude(), 2));
		}
		*/

	}

	private static void twoDimensionalFFT() {
		Picture image = new Picture(512, 512);

		for (int i=0; i<512; i++) {
			for (int j=0; j<512; j++) {
				image.set(i, j, Color.BLACK);
			}
		}

		for (int i=220; i<330; i++) {
			for (int j=180; j<320; j++) {
				image.set(i, j, Color.WHITE);
			}
		}

		for (int i=260; i<290; i++) {
			for (int j=205; j<295; j++) {
				image.set(i, j, Color.BLACK);
			}
		}

		image.show();

	}

	private static void DTMFtones() {
		ArrayList<Double> samples = new ArrayList<Double>();

		try {
			BufferedReader in = new BufferedReader(new FileReader("tonedataF20173.txt"));
			String line;

			while((line = in.readLine()) != null) {
				samples.add(Double.parseDouble(line));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		double[] sampleArray = new double[samples.size()];

		for (int x=0; x<samples.size(); x++) {
			sampleArray[x] = samples.get(x);
		}

		for (ComplexNumber c: fastFourierTransform(convertToComplex(sampleArray), false)) {
			System.out.println(Math.pow(c.magnitude(), 2));
		}
	}

	/**
	 * Performs a Fast Fourier Transform (FFT) or inverse FFT on the specified complex vector. The vector length
	 * will be equal to the number of samples that should be transformed. An FFT can be performed in O(.5n(log(n))
	 * time as opposed to the O(n^2) time of a Discrete Fourier Transform.
	 * @param vector The vector to transform.
	 * @param inverseFFT Set to 'true' if an inverse FFT should be performed on the vector to get the original
	 *                   samples back.
	 * @return The transformed vector.
	 */
	private static ComplexNumber[] fastFourierTransform(ComplexNumber[] vector, boolean inverseFFT) {
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

		return vector;
	}

	private static double[] sineFunction(int terms, double samples, boolean odd) {
		double time = 0;
		double interval = 1/samples;
		double[] results = new double[(int) samples];

		while (time < 1) {
			double sum = 0;

			for (int k=1; k<=terms; k++) {

				double frequency = 2*k;

				if (odd) {
					frequency-= 1;
				}

				double result = Math.sin((2*Math.PI * frequency * time)) / frequency;

				sum += result;
			}

			results[(int) (time * samples)] = sum;

			time += interval;
		}

		return results;
	}

	private static double[] piecewiseFunction(double samples, boolean odd) {
		double time = 0;
		double interval = 1/samples;
		double[] results = new double[(int) samples];

		while (time < 1) {

			double sum = 0;

			if (odd) {
				if (time > 0 && time < .5) {
					sum = Math.PI / 4;
				}

				if (time > .5 && time < 1) {
					sum = Math.PI / 4 * -1;
				}
			} else {
				if (time > 0 && time < .5) {
					sum = -Math.PI*time + Math.PI / 4;
				}

				if (time > .5 && time < 1) {
					sum = -Math.PI * time + 3 * Math.PI / 4;
				}
			}

			results[(int) (time * samples)] = sum;
			time += interval;
		}

		return results;
	}

	private static void phaseShift(int pulseIndex) {
		double[] pulseArray = new double[1024];

		for (int x=0; x<1024; x++) {
			if (x == pulseIndex) {
				pulseArray[x] = 1;
			} else {
				pulseArray[x] = 0;
			}
		}

		//printPowerSpectralDensity(fastFourierTransform(convertToComplex(pulseArray), false), 1024);

		double[] sineWave = new double[1024];
		double[] shiftedSineWave1 = new double[1024];
		double[] shiftedSineWave2 = new double[1024];
		double[] shiftedSineWave3 = new double[1024];

		for (int x=0; x<1024; x++) {
			sineWave[x] = Math.sin(18 * Math.PI * x/1024.0);
			shiftedSineWave1[x] = Math.sin(18 * Math.PI * (x/1024.0 - .25));
			shiftedSineWave2[x] = Math.sin(18 * Math.PI * (x/1024.0 - .5));
			shiftedSineWave3[x] = Math.sin(18 * Math.PI * (x/1024.0 - .75));
		}

		ComplexNumber[] transformedWave = fastFourierTransform(convertToComplex(sineWave), false);

		for (ComplexNumber c: transformedWave) {
			System.out.println(c);
		}

		// printPowerSpectralDensity(fastFourierTransform(convertToComplex(sineWave), false), 50);

	}

	private static void printPowerSpectralDensity(ComplexNumber[] transform, int values) {
		for (int x=0; x<values; x++) {
			System.out.println(Math.pow(transform[x].magnitude(), 2));
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
			System.out.println("\tComplex Number Tests");
			new ComplexNumberTest();

			System.out.println("\n\tFast Fourier Transform Test");
			FFTtest();
		}
		
	}

	private static void FFTtest() {
		double[] testData = {26160, 19011, 18757, 18405, 17888, 14720, 14285, 17018,
				 			 18014, 17119, 16400, 17497, 17846, 15700, 17636, 17181};
		ComplexNumber[] complexTestData = convertToComplex(testData);

		ComplexNumber[] transformed = fastFourierTransform(complexTestData, false);

		for (ComplexNumber c: transformed) {
			// System.out.println(c);
		}

		ComplexNumber[] inverse = fastFourierTransform(transformed, true);

		if (FFTresultsCheck(complexTestData, inverse)) {
			System.out.println("Inverse Test...\t\t[PASS]");
		} else {
			System.out.println("Inverse Test...\t[FAIL]");
		}
	}

	private static boolean FFTresultsCheck(ComplexNumber[] complexTestData, ComplexNumber[] inverse) {

		for (int i=0; i<complexTestData.length; i++) {
			//System.out.println("Comparing: " + complexTestData[i] + " and " + inverse[i]);

			if (!complexTestData[i].equals(inverse[i])) {
				return false;
			}
		}

		return true;
	}

}
