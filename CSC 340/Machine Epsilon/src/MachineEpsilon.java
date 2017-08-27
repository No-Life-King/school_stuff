
public class MachineEpsilon {

	public static void main(String[] args) {
		
		float floatMachineEpsilon = 1;
		float x = 2;
		byte exponent = 0;
		
		while (x > 1) {
			floatMachineEpsilon = floatMachineEpsilon/2;
			x = floatMachineEpsilon + 1;
			exponent--;
		}
		
		exponent++;
		floatMachineEpsilon *= 2;
		
		System.out.println("The smallest representable value by the float data type in java is " + floatMachineEpsilon + ".");
		System.out.println("This is equal to 2^" + exponent + " because the mantissa portion of the standard is " + 
							-1 * exponent + " bits long.\n");
		
		double doubleMachineEpsilon = 1;
		double y = 2;
		byte doubleExponent = 0;
		
		while (y > 1) {
			doubleMachineEpsilon = doubleMachineEpsilon/2;
			y = doubleMachineEpsilon + 1;
			doubleExponent--;
		}
		
		doubleExponent++;
		doubleMachineEpsilon *= 2;
		
		System.out.println("The smallest representable value by the double data type in java is " + doubleMachineEpsilon + ".");
		System.out.println("This is equal to 2^" + doubleExponent + " because the mantissa portion of the standard is " + 
							-1 * doubleExponent + " bits long.\n");
		
	}

}
