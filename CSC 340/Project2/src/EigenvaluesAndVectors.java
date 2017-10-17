/**  @author Philip Smith */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Stack;

public class EigenvaluesAndVectors {

	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new FileReader("src/data.txt"));
			String line;
			
			// skip the first two lines of the data file
			for (int a = 0; a < 2; a++) {
				in.readLine();
			}
			
			ArrayList<Vector> eigenData = new ArrayList<Vector>();
			
			// read all the vector values from the data file 
			while((line = in.readLine()) != null) {
				String[] vectorValues = line.split("\t");
				
				if (vectorValues.length != 2) {
					System.out.println("Error: incorrect number of values found on line.");
					System.out.print("Values found: ");
					for (String value: vectorValues) {
						System.out.println(value);
					}
					break;
				}
				
				// add the vectors to a list
				Vector eigenVector = new Vector(Double.parseDouble(vectorValues[0]), Double.parseDouble(vectorValues[1]));
				eigenData.add(eigenVector);
				
			}
			
			// calculate the mean of the vectors
			double numVectors = eigenData.size();
			Vector meanVector = new Vector(0, 0);
			
			for (Vector v: eigenData) {
				meanVector = meanVector.add(v);
			}
			
			meanVector = meanVector.scalarMultiply(1/numVectors);
			System.out.println("The mean of the vectors: \n" + meanVector);
			
			// generate the covariance matrix
			Matrix covarianceMatrix = calculateCovarianceMatrix(eigenData, meanVector);

			System.out.println("\nVector Covariance Matrix:");
			System.out.println(covarianceMatrix + "\n");
			
			// print the trace of the covariance matrix
			System.out.println("Covariance Matrix Trace: ");
			System.out.println(covarianceMatrix.trace() + "\n");
			
			// find the covariance matrix determinants
			double covarianceMatrixDeterminant = covarianceMatrix.determinant();
			System.out.println("Covariance Matrix Determinant:");
			System.out.println(covarianceMatrixDeterminant + "\n");	
			
			// apply the quadratic formula to solve the characteristic polynomial and yield the eigenvalues
			double b = (covarianceMatrix.getCell(1, 1) + covarianceMatrix.getCell(2, 2)) * -1;		// b = -(a + d)
			double c = covarianceMatrixDeterminant;													// c = (ad - bc) aka the determinant
			double[] eigenvalues = quadraticSolver(1, b, c);
			System.out.println("Eigenvalues of Covariance Matrix:");
			System.out.println(eigenvalues[0] + " " + eigenvalues[1] + "\n");
			
			// lambda(I) for both eigenvalues
			Matrix identityMatrix = Matrix.identityMatrix(2);
			Matrix eigenvalueMatrix1 = identityMatrix.scalarMultiply(eigenvalues[0]);
			Matrix eigenvalueMatrix2 = identityMatrix.scalarMultiply(eigenvalues[1]);
			
			// A - lambda(I) for first eigenvalue
			Matrix difference1 = covarianceMatrix.subtract(eigenvalueMatrix1);
			double x1ratio = difference1.getCell(2, 1) / difference1.getCell(1, 1)  * -1;	
			Vector eigenvector1 = new Vector(x1ratio, 1);
			
			// A - lambda(I) for second eigenvalue
			Matrix difference2 = covarianceMatrix.subtract(eigenvalueMatrix2);
			double x2ratio = difference2.getCell(2, 2) / difference2.getCell(2, 1)  * -1;	
			Vector eigenvector2 = new Vector(x2ratio, 1);
			
			// P is a matrix of the eigenvectors in order
			Matrix p = new Matrix(eigenvector1);
			p.addColumn(eigenvector2.toDouble());
			
			// B is a matrix of the eigenvalues on the diagonal
			double[][] bArray = {{eigenvalues[0], 0}, {0, eigenvalues[1]}};
			Matrix bMatrix = new Matrix(bArray);
			
			// P^-1 is the inverse matrix of P
			Matrix pInverse = p.inverse();
			
			// PBP^-1 yields the original covariance matrix
			System.out.println("A = PBP^-1:");
			Matrix PxB = p.matrixMultiply(bMatrix);
			Matrix aMatrix = PxB.matrixMultiply(pInverse);
			System.out.println(aMatrix);
			
			// display the discovered eigenvectors
			System.out.println("\nEigenvectors of Covariance Matrix:");
			System.out.println(eigenvector1);
			System.out.println(eigenvector2 + "\n");
			
			// convert the eigenvectors to unit vectors, they will be orthogonal
			Vector unitEigenVector1 = eigenvector1.scalarMultiply(1/eigenvector1.magnitude());
			Vector unitEigenVector2 = eigenvector2.scalarMultiply(1/eigenvector2.magnitude());
			System.out.println("Unit Eigenvectors: ");
			System.out.println(unitEigenVector1);
			System.out.println(unitEigenVector2 + "\n");
			
			// add the mean to the eigenvectors to get coordinates of the eigenvectors drawn from the center of the data
			System.out.println("Mean Vector Added to Eigenvectors:");
			System.out.println(eigenvector1.add(meanVector));
			System.out.println(eigenvector2.add(meanVector) + "\n");
			
			// output the characteristic equation coefficients of the companion matrix
			double[] polynomialCoefficients = {-2.6, -55.25, 90.55, 47.5, -5.6};
			Matrix companion = Matrix.companionMatrix(polynomialCoefficients);
			double[] coefficients = companion.characteristicEquationCoefficients();
			System.out.println("Characteristic Equation Coefficients:");
			// surprise, it's the opposite of the first row of the companion matrix
			for (double coefficient: coefficients) {
				System.out.print(coefficient + "\t");
			}
			System.out.println("\n");
			
			// get the largest eigenvalue of the matrix using an implementation of the power method
			double largestEigenvalue = companion.estimateLargestEigenvalue(.000000000001, 1000);
			System.out.println(largestEigenvalue);
			
			// get all of the eigenvalues 
			double[] fourthDegreeCoefficients = {5.4, -12.05, -5.85, .7};
			companion = Matrix.companionMatrix(fourthDegreeCoefficients);
			System.out.println(companion.estimateLargestEigenvalue(.000000000001, 1000));
			
			double[] thirdDegreeCoefficients = {-1.6, -.85, .1};
			companion = Matrix.companionMatrix(thirdDegreeCoefficients);
			System.out.println(companion.estimateLargestEigenvalue(.000000000001, 1000));
			
			double[] quadraticCoefficients = {.4, -.05};
			companion = Matrix.companionMatrix(quadraticCoefficients);
			System.out.println(companion.estimateLargestEigenvalue(.000000000001, 1000));
			
			
		} catch (Exception e) {
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
			Matrix transposedDifferenceMatrix = differenceMatrix.clone();
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
	
	public static double[] quadraticSolver(double a, double b, double c) {
		double negativeB = b * -1;
		double radicand = Math.sqrt(Math.pow(b, 2) - 4 * a * c);
		double twoA = 2 * a;
		
		double solutionOne = (negativeB + radicand) / twoA;
		double solutionTwo = (negativeB - radicand) / twoA;
		double[] solutions = {solutionOne, solutionTwo};
		return solutions;
	}

}
