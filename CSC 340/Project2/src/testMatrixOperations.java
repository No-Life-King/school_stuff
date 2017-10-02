/**  @author Philip Smith */

import java.util.ArrayList;
import java.util.Stack;

public class testMatrixOperations {
	
	public static void runTest(boolean test) {
		
		if (test) {
			
			// test empty new matrix
			Matrix m = new Matrix(2, 3);
			System.out.println("\nNew Empty Matrix:");
			System.out.println(m + "\n");
			
			// test row setting
			double[] row = {1, 3, 2};
			m.setRow(1, row);
			double[] row2 = {3, 4, 1};
			m.setRow(2, row2);
			System.out.println("Set row 1 equal to 1, 3, 2. Set row 2 equal to 3, 4, 1:");
			System.out.println(m);
			System.out.println("(M1)\n");
			
			// test column setting
			Matrix n = new Matrix(2, 3);
			double[] col1 = {3, 3};
			n.setColumn(1, col1);
			double[] col2 = {5, 5};
			n.setColumn(2, col2);
			double[] col3 = {7, 7};
			n.setColumn(3, col3);
			System.out.println("Put 3s in column 1, 5s in column 2, and 7s in column 3:");
			System.out.println(n);
			System.out.println("(M2)\n");
			
			// test matrix addition
			Matrix sum = m.add(n);
			System.out.println("Add M1 and M2:");
			System.out.println(sum + "\n");
			
			// test scalar multiplication
			m = m.scalarMultiply(2);
			System.out.println("Multiply M1 by a single scalar quantity:");
			System.out.println(m + "\n");
			
			// test matrix subtraction
			System.out.println("Subtract M2 from M1:");
			m = m.subtract(n);
			System.out.println(m + "\n");
			
			// test row swapping operation
			System.out.println("Swap rows 1 & 2 in M1:");
			m.rowSwap(1, 2);
			System.out.println(m + "\n");
			
			// test row multiplication operation
			System.out.println("Multiply row 2 by 2 in M1:");
			m.rowMultiply(2, 2);
			System.out.println(m + "\n");
			
			// test the addition of a scaled row to another row
			System.out.println("Add 1/2 of row 1 to row 2 in M1:");
			m.rowMultiplyAndAdd(2, 1, .5);
			System.out.println(m + "\n");
			
			// test matrix transposition
			System.out.println("Transpose matrix M1:");
			m.transpose();
			System.out.println(m);
			System.out.println(m.getRows() + "x" + m.getColumns() + "\n");
			
			// test if the matrix is square
			Matrix o = new Matrix(2, 2);
			double[] orow1 = {11, 12};
			double[] orow2 = {21, 22};
			o.setRow(1, orow1);
			o.setRow(2, orow2);
			System.out.println("Is M1 a square matrix?");
			System.out.println(m.isSquare() + "\n");
			System.out.println(o + "\n(M3)\n");
			System.out.println("Is M3 a square matrix?");
			System.out.println(o.isSquare() + "\n");
			
			
			// test matrix multiplication
			System.out.println("M2 * M1:");
			Matrix multiplied = n.matrixMultiply(m);
			System.out.println(multiplied + "\n");
			
			System.out.println("M1 * M2:");
			multiplied = m.matrixMultiply(n);
			System.out.println(multiplied + "\n");
			
			// test Gauss-Jordan elimination method
			Matrix systemOfEquations = new Matrix(3, 4);
			double[] firstRow = 	{1, -1, 0, 2};
			double[] secondRow =   {-2, 2, -1, -1};
			double[] thirdRow = 	{0, 1, -2, 6};
			systemOfEquations.setRow(1, firstRow);
			systemOfEquations.setRow(2, secondRow);
			systemOfEquations.setRow(3, thirdRow);
			Matrix reduced = systemOfEquations.gaussJordanElimination();
			System.out.println("Solve a system of equations by using the Gauss-Jordan elimination method:");
			System.out.println(reduced + "\n");
			
			// test again with a smaller matrix
			double[][] twoByThree = {{2, 3, 2}, {5, -2, 24}};
			Matrix smallerSystem = new Matrix(twoByThree);
			Matrix solution = smallerSystem.gaussJordanElimination();
			System.out.println("A smaller system of equations using the same method:");
			System.out.println(solution + "\n");
			
			// test a system that has no unique solution
			double[][] noSolution = {{2, 4, -8, 6}, {0, 1, -2, 2}, {1, 3, -6, 1}};
			Matrix noSol = new Matrix(noSolution);
			Matrix NaN = noSol.gaussJordanElimination();
			System.out.println("A problem that can't be solved:");
			System.out.println(NaN + "\n");
			
			// test the Gauss-Jordan method for creating an inverse matrix
			double[][] inverseMe = {{1, -1, 0}, {2, 0, 4}, {0, 2, -1}};
			Matrix inverse = new Matrix(inverseMe).inverse();
			System.out.println("An inverse matrix:");
			System.out.println(inverse + "\n");
			
			
			// test the Gaussian Elimination method of reducing a matrix
			double[][] gaussEliminate = {{1, 0, 2, 1}, {2, -1, 3, -1}, {4, 1, 8, 2}};
			Matrix gauss = new Matrix(gaussEliminate).gaussianElimination(false);
			System.out.println("Solve a system of equations using Gaussian Elimination:");
			System.out.println(gauss + "\n");
			
			// test the back substitution method of solving a system of equations after using Gaussian elimination on a matrix
			System.out.println("Answers to the matrix above:");
			Stack<Double> solutionStack = gauss.backSubstitute();
			while (!solutionStack.isEmpty()) {
				System.out.print(solutionStack.pop() + " ");
			}
			System.out.println("\n");
			
			// test the determinant finding method
			double[][] determineMe = {{1, 4, 0}, {0, 2, 6}, {-1, 0, 1}};
			double determinant = new Matrix(determineMe).determinant();
			System.out.println("The determinant of a 3x3 matrix: ");
			System.out.println(determinant + "\n");
			
			// create a matrix out of a vector
			Vector a = new Vector(3.5, 7.77);
			Matrix b = new Matrix(a);
			System.out.println("Create a matrix out of a vector:");
			System.out.println(a + "\n");
			System.out.println(b + "\n");
			
		}
	}

}
