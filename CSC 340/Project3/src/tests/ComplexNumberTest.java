package tests;

import DSP.ComplexNumber;

public class ComplexNumberTest {
	
	public ComplexNumberTest() {
		additionTest();
		subtractionTest();
		multiplicationTest();
	}

	private void additionTest() {
		ComplexNumber x = new ComplexNumber(1, 1);
		ComplexNumber y = new ComplexNumber(6, -5);
		
		ComplexNumber z = x.add(y);
		
		ComplexNumber answer = new ComplexNumber(7, -4);
		
		validate("Addition Test...\t", z, answer);
	}
	
	private void subtractionTest() {
		ComplexNumber x = new ComplexNumber(-.5, 0);
		ComplexNumber y = new ComplexNumber(6, -5);
		
		ComplexNumber z = x.subtract(y);
		
		ComplexNumber answer = new ComplexNumber(-6.5, 5);
		
		validate("Subtraction Test...\t", z, answer);
	}
	
	private void multiplicationTest() {
		ComplexNumber x = new ComplexNumber(-.5, 0);
		ComplexNumber y = new ComplexNumber(6, -5);
		
		ComplexNumber z = x.multiply(y);
		
		ComplexNumber answer = new ComplexNumber(-3, 2.5);
		
		validate("Product Test 1...\t", z, answer);
		
		x = new ComplexNumber(10, -5);
		y = new ComplexNumber(-5, 5);
		z = x.multiply(y);
		answer = new ComplexNumber(-25, 75);
		
		validate("Product Test 2...\t", z, answer);
	}

	private void validate(String name, ComplexNumber solution, ComplexNumber answer) {
		System.out.print(name);
		
		if (solution.equals(answer)) {
			System.out.println("[PASS]");
		} else {
			System.out.println("[FAIL]");
			System.out.println("Expected: " + answer + ". Got: " + solution);
		}
	}

}
