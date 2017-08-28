import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Data_for_two_classes {

	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new FileReader("src/datafile.txt"));
			String line;
			
			// skip the first four lines of the data file
			for (int a = 0; a < 4; a++) {
				in.readLine();
			}
			
			ArrayList<Vector> class1vectors = new ArrayList<Vector>();
			ArrayList<Vector> class2vectors = new ArrayList<Vector>();
			
			// read all the vector values from the data file 
			while((line = in.readLine()) != null) {
				String[] vectorValues = line.split("\t");
				
				if (vectorValues.length != 4) {
					System.out.println("Error: incorrect number of values found on line.");
					System.out.print("Values found: ");
					for (String value: vectorValues) {
						System.out.println(value);
					}
					break;
				}
				
				// parse the values and create the vectors for class 1 and class 2 and add them to the appropriate array lists
				Vector class1 = new Vector(Double.parseDouble(vectorValues[0]), Double.parseDouble(vectorValues[1]));
				class1vectors.add(class1);
				
				Vector class2 = new Vector(Double.parseDouble(vectorValues[2]), Double.parseDouble(vectorValues[3]));
				class2vectors.add(class2);
			}
			
			// calculate the mean vectors for both classes
			double numC1vectors = class1vectors.size();
			double numC2vectors = class2vectors.size();
			Vector m1 = new Vector(0, 0);
			Vector m2 = new Vector(0, 0);
			
			for (Vector v: class1vectors) {
				m1.add(v);
			}
			for (Vector v: class2vectors) {
				m2.add(v);
			}
			
			m1.scalarMultiply(1/numC1vectors);
			m2.scalarMultiply(1/numC2vectors);
			
			System.out.println("Mean Vector M1: " + m1);
			System.out.println("Mean Vector M2: " + m2);
			
			Matrix m = new Matrix(2, 3);
			System.out.println(m);
			
			double[] row = {1, 3, 2};
			m.setRow(1, row);
			double[] row2 = {3, 4, 1};
			m.setRow(2, row2);
			System.out.println(m);
			
			Matrix n = new Matrix(2, 3);
			double[] col1 = {3, 3};
			n.setColumn(1, col1);
			double[] col2 = {5, 5};
			n.setColumn(2, col2);
			double[] col3 = {7, 7};
			n.setColumn(3, col3);
			System.out.println(n);
			
			Matrix sum = m.add(n);
			System.out.println(sum);
			
			m.scalarMultiply(2);
			System.out.println(m);
			
			System.out.println(m.subtract(n));
			
			m.rowSwap(1, 2);
			System.out.println(m);
			
			m.rowMultiply(2, 2);
			System.out.println(m);
			
			m.rowAddAndMultiply(2, 1, .5);
			System.out.println(m);
			
			m.transpose();
			System.out.println(m);
			
			Matrix o = new Matrix(2, 2);
			double[] orow1 = {11, 12};
			double[] orow2 = {21, 22};
			o.setRow(1, orow1);
			o.setRow(2, orow2);
			System.out.println(m.isSquare());
			System.out.println(o.isSquare());
			
			System.out.println(n);
			System.out.println(m);
			Matrix multiplied = n.matrixMultiply(m);
			System.out.println(multiplied);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
