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
		// runTests(true);

		// filters();

		// phaseShift(0);

		// correlation();

		// printPowerSpectralDensity(evenFFTinverse, 512);

		// DTMFtones();

		twoDimensionalSignalCorrelation();

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

	private static void correlation() {
		ArrayList<Double> pulse = new ArrayList<Double>();
		ArrayList<Double> signal = new ArrayList<Double>();

		try {
			BufferedReader in = new BufferedReader(new FileReader("rangeTestDataF2017.txt"));
			String line;

			in.readLine();
			in.readLine();

			while(!(line = in.readLine()).startsWith("s")) {
				pulse.add(Double.parseDouble(line));
			}

			in.readLine();

			while((line = in.readLine()) != null) {
				signal.add(Double.parseDouble(line));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		double[] pulseArray = new double[signal.size()];
		double[] signalArray = new double[signal.size()];

		for (int x=0; x<signal.size(); x++) {
			signalArray[x] = signal.get(x);
		}

		for (int x=0; x<pulse.size(); x++) {
			pulseArray[x] = pulse.get(x);
		}

		ComplexNumber[] results = correlateSignals(pulseArray, signalArray);

		for (ComplexNumber result: results) {
			//System.out.println(result.getReal());
		}

		double[] avgFilter = new double[1024];
		double oneTenth = 1.0/10;

		for (int x=0; x<10; x++) {
			avgFilter[x] = oneTenth;
			avgFilter[avgFilter.length - x - 1] = oneTenth;
		}

		ComplexNumber[] signalFFT = fastFourierTransform(convertToComplex(signalArray), false);
		ComplexNumber[] filterFFT = fastFourierTransform(convertToComplex(avgFilter), false);
		ComplexNumber[] multiplied = new ComplexNumber[1024];

		for (int x=0; x<1024; x++) {
			multiplied[x] = signalFFT[x].multiply(filterFFT[x]);
		}

		ComplexNumber[] convoluted = fastFourierTransform(multiplied, true);

		for (int x=0; x<256; x++) {
			System.out.println(signalArray[x]);
		}

	}

	private static ComplexNumber[] correlateSignals(double[] pulseArray, double[] signalArray) {
		ComplexNumber[] signalFFT = fastFourierTransform(convertToComplex(signalArray), false);
		ComplexNumber[] pulseFFT = fastFourierTransform(convertToComplex(pulseArray), false);

		for (int x=0; x<pulseFFT.length; x++) {
			pulseFFT[x] = pulseFFT[x].conjugate();
		}

		ComplexNumber[] correlation = new ComplexNumber[signalFFT.length];

		for (int x=0; x<signalFFT.length; x++) {
			correlation[x] = signalFFT[x].multiply(pulseFFT[x]);
		}

		return fastFourierTransform(correlation, true);
	}

	private static void twoDimensionalSignalCorrelation() {
		Picture testSignal = new Picture(512, 512, "Test Signal");

		double[][] testSignalPixels = new double[512][512];

		for (int i=0; i<512; i++) {
			for (int j=0; j<512; j++) {
				testSignalPixels[i][j] = 0;
			}
		}

		for (int i=220; i<330; i++) {
			for (int j=180; j<320; j++) {
				testSignalPixels[i][j] = 255;
			}
		}

		for (int i=260; i<290; i++) {
			for (int j=205; j<295; j++) {
				testSignalPixels[i][j] = 0;
			}
		}

		for (int x=0; x<512; x++) {
			for (int y=0; y<512; y++) {
				testSignal.set(x, y, (int) testSignalPixels[x][y]);
			}
		}

		testSignal.show();

		Picture testPulse = new Picture(512, 512, "Test Pulse");

		double[][] testPulsePixels = new double[512][512];

		for (int i=0; i<50; i++) {
			for (int j=0; j<110; j++) {
				testPulsePixels[i][j] = 255;
			}
		}

		for (int i=10; i<40; i++) {
			for (int j=10; j<100; j++) {
				testPulsePixels[i][j] = 0;
			}
		}

		for (int x=0; x<512; x++) {
			for (int y=0; y<512; y++) {
				testPulse.set(x, y, (int) testPulsePixels[x][y]);
			}
		}

		testPulse.show();

		ComplexNumber[][] complexSignal = new ComplexNumber[512][512];
		ComplexNumber[][] complexPulse = new ComplexNumber[512][512];

		for (int x=0; x<512; x++) {
			for (int y=0; y<512; y++) {
				complexSignal[x][y] = new ComplexNumber(testSignalPixels[x][y], 0);
			}
		}

		for (int x=0; x<512; x++) {
			for (int y=0; y<512; y++) {
				complexPulse[x][y] = new ComplexNumber(testPulsePixels[x][y], 0);
			}
		}

		ComplexNumber[][] signalFFT = twoDimensionalFFT(complexSignal, false);
		ComplexNumber[][] pulseFFT = twoDimensionalFFT(complexPulse, false);
		ComplexNumber[][] multiplied = new ComplexNumber[512][512];

		for (int i=0; i<512; i++) {
			for (int j=0; j<512; j++) {
				multiplied[i][j] = pulseFFT[i][j].conjugate().multiply(signalFFT[i][j]);
			}
		}

		ComplexNumber[][] correlated = twoDimensionalFFT(multiplied, true);

		double maxMagnitude = 0;

		for (int i=0; i<512; i++) {
			for (int j=0; j<512; j++) {
				double correlationMagnitude = correlated[i][j].magnitude();
				if (correlationMagnitude > maxMagnitude) {
					maxMagnitude = correlationMagnitude;
				}
			}
		}

		double[][] redmask = new double[512][512];
		double threshold = 0.9 * maxMagnitude;

		for (int i=0; i<512; i++) {
			for (int j=0; j<512; j++) {
				double correlationMagnitude = correlated[i][j].magnitude();
				if (correlationMagnitude > threshold) {
					redmask[i][j] = 1;
				}
			}
		}

		Picture red = new Picture(512, 512, "Red Mask");
		Picture correlationMagnitudes = new Picture(512, 512, "Correlation Magnitudes");

		for (int i=0; i<512; i++) {
			for (int j=0; j<512; j++) {
				correlationMagnitudes.set(i, j, (int) (Math.log(correlated[i][j].magnitude() + 1) / Math.log(1.08)));
				red.set(i, j, (int) (Math.log(correlated[i][j].magnitude() + 1) / Math.log(1.08)));
			}
		}

		correlationMagnitudes.show();

		for (int i=0; i<512; i++) {
			for (int j = 0; j < 512; j++) {
				if (redmask[i][j] == 1) {
					red.set(i, j, Color.RED);
				}
			}
		}

		correlationMagnitudes.show();
		red.show();

	}

	private static ComplexNumber[][] twoDimensionalFFT(ComplexNumber[][] matrix, boolean inverse) {
		int len = matrix.length;
		ComplexNumber[][] transformed = new ComplexNumber[len][len];

		for (int i=0; i<len; i++) {
			ComplexNumber[] complexRow = new ComplexNumber[len];

			for (int j = 0; j<len; j++) {
				complexRow[j] = matrix[i][j];
			}

			ComplexNumber[] rowFFT = fastFourierTransform(complexRow, inverse);

			for (int x=0; x<len; x++) {
				transformed[i][x] = rowFFT[x];
			}

		}

		for (int j=0; j<len; j++) {
			ComplexNumber[] complexColumn = new ComplexNumber[len];

			for (int i=0; i<len; i++) {
				complexColumn[i] = transformed[i][j];
			}

			ComplexNumber[] columnFFT = fastFourierTransform(complexColumn, inverse);

			for (int x=0; x<len; x++) {
				transformed[x][j] = columnFFT[x];
			}
		}


		return transformed;
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

	private static void filters() {
		double[] sineWave = sineFunction(100, 512, false);

		double[] lowPass = generateFilter(1, 5, 512);
		double[] highPass = generateFilter(6, 100, 512);
		double[] bandPass = generateFilter(4, 7, 512);
		double[] notch = generateFilter(1, 3, 512);

		for (int x=16; x<=496; x++) {
			notch[x] = 1;
		}

		ComplexNumber[] waveFFT = fastFourierTransform(convertToComplex(sineWave), false);

		for (int x=0; x<512; x++) {
			waveFFT[x] = waveFFT[x].multiply(notch[x]);
		}

		ComplexNumber[] filteredWave = fastFourierTransform(waveFFT, true);

		for (ComplexNumber value: filteredWave) {
			System.out.println(value.getReal());
		}

	}

	private static double[] generateFilter(int start, int end, int len) {
		double[] filter = new double[len];

		for (int x=start*2; x<=end*2; x+=2) {
			filter[x] = 1;
			filter[len -  x] = 1;
		}

		return filter;
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
			System.out.println("Inverse Test...\t\t[FAIL]");
		}

		double[][] twoDtest = {{26160, 19011, 18757, 18405},
							   {17888, 14720, 14285, 17018},
							   {18014, 17119, 16400, 17497},
							   {17846, 15700, 17636, 17181}};
		ComplexNumber[][] twoDdata = new ComplexNumber[4][4];

		for (int i=0; i<4; i++) {
			for (int j=0; j<4; j++) {
				twoDdata[i][j] = new ComplexNumber(twoDtest[i][j], 0);
			}
		}

		ComplexNumber[][] twoDfft = twoDimensionalFFT(twoDdata, false);
		ComplexNumber[][] twoDinverse = twoDimensionalFFT(twoDfft, true);

		boolean valid = true;
		for (int i=0; i<4; i++) {
			for (int j=0; j<4; j++) {
				if (twoDinverse[i][j].getReal() != twoDtest[i][j]) {
					valid = false;
				}
			}
		}

		if (valid) {
			System.out.println("2D FFT Inverse...\t[PASS]");
		} else {
			System.out.println("2D FFT Inverse...\t[FAIL]");
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
