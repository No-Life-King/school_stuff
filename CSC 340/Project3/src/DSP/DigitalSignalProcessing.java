package DSP;

import tests.ComplexNumberTest;

public class DigitalSignalProcessing {

	public static void main(String[] args) {
		runTests(true);

	}

	private static void runTests(boolean enabled) {
		if (enabled) {
			new ComplexNumberTest();
		}
		
	}

}
