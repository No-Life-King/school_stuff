// Author: Philip Smith

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
			Vector v1 = new Vector(0, 0);
			Vector v2 = new Vector(0, 0);
			
			for (Vector v: class1vectors) {
				v1.add(v);
			}
			for (Vector v: class2vectors) {
				v2.add(v);
			}
			
			v1.scalarMultiply(1/numC1vectors);
			v2.scalarMultiply(1/numC2vectors);
			
			System.out.println("Mean Vector M1: " + v1);
			System.out.println("Mean Vector M2: " + v2);
			
			Matrix covarianceMatrixC1 = calculateCovarianceMatrix(class1vectors, v1);
			Matrix covarianceMatrixC2 = calculateCovarianceMatrix(class2vectors, v2);
			
			System.out.println("\nClass 1 Covariance Matrix:");
			System.out.println(covarianceMatrixC1 + "\n");
			System.out.println("Class 2 Covariance Matrix:");
			System.out.println(covarianceMatrixC2 + "\n");
			
			Matrix covarianceInverseMatrixC1 = covarianceMatrixC1.inverse();
			Matrix covarianceInverseMatrixC2 = covarianceMatrixC2.inverse();
			
			System.out.println("Class 1 Covariance Inverse Matrix: \n " + covarianceInverseMatrixC1);
			System.out.println("\nClass 2 Covariance Inverse Matrix: \n " + covarianceInverseMatrixC2);
			
			testMatrixOperations.runTest(false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static Matrix calculateCovarianceMatrix(ArrayList<Vector> vectors, Vector meanVector) {
		Matrix covarianceMatrix = new Matrix(2, 2);
		
		for (Vector v: vectors) {
			v.subtract(meanVector);
			double[] differenceVector = {v.getI(), v.getJ()};
			Matrix differenceMatrix = new Matrix(2, 1);
			differenceMatrix.setColumn(1, differenceVector);
			Matrix transposedDifferenceMatrix = differenceMatrix.duplicate();
			transposedDifferenceMatrix.transpose();
			Matrix multiplied = differenceMatrix.matrixMultiply(transposedDifferenceMatrix);
			covarianceMatrix = covarianceMatrix.add(multiplied);
		}
		
		double k = vectors.size();
		covarianceMatrix = covarianceMatrix.scalarMultiply(1/k);
		return covarianceMatrix;
	}

}
