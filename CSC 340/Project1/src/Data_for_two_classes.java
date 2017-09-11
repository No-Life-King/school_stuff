/**  @author Philip Smith */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Data_for_two_classes {

	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new FileReader("src/datafile.txt"));
			String line;
			
			// skip the first line of the data file
			for (int a = 0; a < 1; a++) {
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
				v1 = v1.add(v);
			}
			for (Vector v: class2vectors) {
				v2 = v2.add(v);
			}
			
			v1 = v1.scalarMultiply(1/numC1vectors);
			v2 = v2.scalarMultiply(1/numC2vectors);
			
			System.out.println("Mean Vector M1: " + v1);
			System.out.println("Mean Vector M2: " + v2);
			
			// generate the covariance matrices for each class
			Matrix covarianceMatrixC1 = calculateCovarianceMatrix(class1vectors, v1);
			Matrix covarianceMatrixC2 = calculateCovarianceMatrix(class2vectors, v2);
			
			System.out.println("\nClass 1 Covariance Matrix:");
			System.out.println(covarianceMatrixC1 + "\n");
			System.out.println("Class 2 Covariance Matrix:");
			System.out.println(covarianceMatrixC2 + "\n");
			
			// find the covariance matrix determinants
			double c1covarianceMatrixDeterminant = covarianceMatrixC1.determinant();
			double c2covarianceMatrixDeterminant = covarianceMatrixC2.determinant();
			System.out.println("Class 1 Covariance Matrix Determinant:");
			System.out.println(c1covarianceMatrixDeterminant + "\n");			
			System.out.println("Class 2 Covariance Matrix Determinant:");
			System.out.println(c2covarianceMatrixDeterminant + "\n");
			
			// find the covariance matrix inverses
			Matrix covarianceInverseMatrixC1 = covarianceMatrixC1.inverse();
			Matrix covarianceInverseMatrixC2 = covarianceMatrixC2.inverse();
			
			System.out.println("Class 1 Inverse Covariance Matrix: \n " + covarianceInverseMatrixC1);
			System.out.println("\nClass 2 Inverse Covariance Matrix: \n " + covarianceInverseMatrixC2);
			
			// solve a large linear system computationally
			System.out.println("\n8x9 system of equations solution:");
			double[][] coefficients = {{3, 1, -1, 4, 1, 1, -1, -1, 2},
									   {1, 2, 2, 0, -1, -2, 2, 2, -3},
									   {0, -2, 5, 4, -1, 0, 3, 1, 4},
									   {1, 1, -7, 3, 2, 2, -9, 0, -1},
									   {1, 1, 2, 3, -2, 2, 2, 9, 1},
									   {0, -3, -2, 2, 0, 2, 4, -5, -2},
									   {-2, 1, -1, 1, 1, -5, 0, -2, 3},
									   {1, 0, 1, 1, 0, 2, 1, 1, -4}};
			
			// print the Gauss-Jordan solution
			Matrix solution = new Matrix(coefficients);
			Matrix determinant = solution.duplicate();
			System.out.println(solution.gaussJordanElimination());
			System.out.println("(Gauss-Jordan)\n");
			
			// just for kicks, print the Gaussian solution as well
			System.out.println(new Matrix(coefficients).gaussianElimination(false));
			System.out.println("(Gaussian Elimination)\n");
			
			// print the determinant
			System.out.println("Determinant:");
			System.out.println(determinant.determinant() + "\n");
			
			// grades of the mean vectors by each discriminant function
			System.out.println("Classification of m1 by g1(x):");
			System.out.println(grade(v1, v1, covarianceInverseMatrixC1, c1covarianceMatrixDeterminant));
			System.out.println("\nClassification of m2 by g1(x):");
			System.out.println(grade(v2, v1, covarianceInverseMatrixC1, c1covarianceMatrixDeterminant));
			System.out.println("\nClassification of m2 by g2(x):");
			System.out.println(grade(v2, v2, covarianceInverseMatrixC2, c2covarianceMatrixDeterminant));
			System.out.println("\nClassification of m1 by g2(x):");
			System.out.println(grade(v1, v2, covarianceInverseMatrixC2, c2covarianceMatrixDeterminant));
			
			// grade every data point using both discriminant functions
			ArrayList<Vector> misclassifiedC1 = new ArrayList<Vector>();
			ArrayList<Vector> misclassifiedC2 = new ArrayList<Vector>();
			ArrayList<Double> gradesC1 = new ArrayList<Double>();
			ArrayList<Double> gradesC2 = new ArrayList<Double>();
			
			for (Vector c1vector: class1vectors) {
				double class1grade = grade(c1vector, v1, covarianceInverseMatrixC1, c1covarianceMatrixDeterminant);
				double class2grade = grade(c1vector, v2, covarianceInverseMatrixC2, c2covarianceMatrixDeterminant);
				if (class1grade < class2grade) {
					misclassifiedC1.add(c1vector);
					gradesC1.add(class1grade);
					gradesC1.add(class2grade);
				}
			}
			for (Vector c2vector: class2vectors) {
				double class1grade = grade(c2vector, v1, covarianceInverseMatrixC1, c1covarianceMatrixDeterminant);
				double class2grade = grade(c2vector, v2, covarianceInverseMatrixC2, c2covarianceMatrixDeterminant);
				if (class1grade > class2grade) {
					misclassifiedC2.add(c2vector);
					gradesC2.add(class1grade);
					gradesC2.add(class2grade);
				}
			}
			
			// display the points that were misclassified
			System.out.println("\nClass 1 Misclassified Points\tg1 Grade\t\tg2 Grade");
			for (Vector misclassified: misclassifiedC1) {
				int index = misclassifiedC1.indexOf(misclassified);
				double g1 = gradesC1.get(index*2);
				double g2 = gradesC1.get(index*2 + 1);
				System.out.println(misclassified + "\t" + g1 + "\t" + g2);
			}
			System.out.println("\nClass 2 Misclassified Points:\tg1 Grade\t\tg2 Grade");
			for (Vector misclassified: misclassifiedC2) {
				int index = misclassifiedC2.indexOf(misclassified);
				double g1 = gradesC2.get(index*2);
				double g2 = gradesC2.get(index*2 + 1);
				System.out.println(misclassified + "\t" + g1 + "\t" + g2);
			}
			
			// estimate the boundary between the two discriminant functions and output points
			// along that boundary
			System.out.println();
			double i = -3;
			double j = -6;
			while (i <= 3) {
				Vector testPoint = new Vector(i, j);
				double g1Grade = grade(testPoint, v1, covarianceInverseMatrixC1, c1covarianceMatrixDeterminant);
				double g2Grade = grade(testPoint, v2, covarianceInverseMatrixC2, c2covarianceMatrixDeterminant);
				double modifier = .0001;
				double gradeDifference = g1Grade - g2Grade;
				boolean found = false;
				while (Math.abs(gradeDifference) > .001 && j < 4) {
					testPoint = new Vector(i, j);
					g1Grade = grade(testPoint, v1, covarianceInverseMatrixC1, c1covarianceMatrixDeterminant);
					g2Grade = grade(testPoint, v2, covarianceInverseMatrixC2, c2covarianceMatrixDeterminant);
					j += modifier;
					gradeDifference = g1Grade - g2Grade;
					if (gradeDifference > .001) {
						found = true;
					}
				}
				
				if (found) {
					System.out.println(testPoint.getI() + "\t" +  testPoint.getJ());
				}
				found = false;
				i += .125;
				j = -6;
			}
			
			// set to true if you want to output the matrix operations test
			testMatrixOperations.runTest(false);
		} catch (Exception e) {
			// don't handle exceptions, just give me the stack trace
			e.printStackTrace();
		}

	}
	
	// reuse this code to calculate the covariance matrix for each class
	public static Matrix calculateCovarianceMatrix(ArrayList<Vector> vectors, Vector meanVector) {
		Matrix covarianceMatrix = new Matrix(2, 2);
		
		for (Vector v: vectors) {
			// find the difference vector
			v = v.subtract(meanVector);
			double[] differenceVector = {v.getI(), v.getJ()};
			Matrix differenceMatrix = new Matrix(2, 1);
			differenceMatrix.setColumn(1, differenceVector);
			
			// transpose it to multiply it by itself (square it)
			Matrix transposedDifferenceMatrix = differenceMatrix.duplicate();
			transposedDifferenceMatrix.transpose();
			Matrix multiplied = differenceMatrix.matrixMultiply(transposedDifferenceMatrix);
			
			// keep a running sum of the variance
			covarianceMatrix = covarianceMatrix.add(multiplied);
		}
		
		// find the average variance and return it
		double k = vectors.size();
		covarianceMatrix = covarianceMatrix.scalarMultiply(1/k);
		return covarianceMatrix;
	}
	
	// This is the grading function.
	public static double grade(Vector x, Vector mean, Matrix inverseCovarianceMatrix, double covarianceMatrixDeterminant) {
		// find the difference between the measurement vector and the mean
		Vector difference = x.subtract(mean);
		Matrix meanDifference = new Matrix(difference);
		
		// create another matrix of the mean vector that is transposed
		Matrix meanDifferenceTransposed = meanDifference.duplicate();
		meanDifferenceTransposed.transpose();
		
		// multiply all the matrices
		Matrix firstMultiply = meanDifferenceTransposed.matrixMultiply(inverseCovarianceMatrix);
		Matrix secondMultiply = firstMultiply.matrixMultiply(meanDifference);
		
		// finally, multiply by the scalar
		Matrix scalarMultiply = secondMultiply.scalarMultiply(-.5);
		
		// return the result minus 1/2 the natural log of the determinant of the covariance 
		// matrix and add the natural log of the frequency of the class 
		return scalarMultiply.getCell(1, 1) - .5 * Math.log(covarianceMatrixDeterminant) + Math.log(.5);
	}

}
