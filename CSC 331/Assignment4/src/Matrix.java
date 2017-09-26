/**  @author Philip Smith */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


/** 
 * This class contains the necessary functionality for working with matrices
 * and solving matrix-related problems.
 */
public class Matrix implements Cloneable {

	private int rows;
	private int columns;
	private double[][] matrix;
	
	/** Initializes an empty 2D array with the form Rows x Columns. 
	 * @param rows The number of rows the matrix needs.
	 * @param columns The number of columns the matrix needs. */
	public Matrix(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		
		matrix = new double[rows][columns];
	}
	
	/** Accepts an existing 2D array and converts it to the matrix type. 
	 * @param matrix A two-dimensional double array. */
	public Matrix(double[][] matrix) {
		rows = matrix.length;
		columns = matrix[0].length;
		
		this.matrix = matrix;
	}
	
	/** Reads a matrix in from a text file.
	 * @param filePath The string representation of a file path. */
	public Matrix(String filePath) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(filePath));
			String line;
			
			// read in each line of the text file
			while((line = in.readLine()) != null) {
				String[] rowData = line.split(" ");
				columns = rowData.length;
				
				// create a double array
				double[] row = new double[columns];
				for(int i = 0; i < columns; i++) {
					row[i] = Double.parseDouble(rowData[i]);
				}
				
				// add it to the matrix
				this.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/** Append a row to the end of this matrix. 
	 * @param row A double array containing the row values. */
	public void addRow(double[] row) {
		this.rows += 1;
		double[][] newMatrix = new double[rows][columns];

		if (rows > 1) {
			for (int i = 1; i < rows; i++) {
				double[] rowData = getRow(i);
				for (int j = 0; j < columns; j++) {
					newMatrix[i-1][j] = rowData[j];
				}
			}
		}
		
		this.matrix = newMatrix;
		setRow(rows, row);
	}
	
	/** Sets the values of the specified row to the values of the passed-in array. 
	 * @param rowNumber The number of the row to set. (starts at 1)
	 * @param values A double array of the values to be set, in order. */
	public void setRow(int rowNumber, double[] values) {
		rowNumber--;
		
		for (int j=0; j < columns; j++) {
			matrix[rowNumber][j] = values[j];
		}
	}
	
	/** Returns the values of the specified row in an array. 
	 * @param rowNumber The number of the row to get.
	 * @return A double array containing the row values. */
	public double[] getRow(int rowNumber) {
		double[] row = new double[columns];
		
		for (int j=0; j<columns; j++) { 
			row[j] = matrix[rowNumber-1][j];
		}
		
		return row;
	}
	
	/** Sets the values of the specified column to the values of the passed-in array. 
	 * @param colNumber The number of the column to be set. (starts at 1)
	 * @param values A double array containing the values to be set. */
	public void setColumn(int colNumber, double[] values) {
		colNumber--;
		
		for (int i=0; i < rows; i++) {
			matrix[i][colNumber] = values[i];
		}
	}
	
	/** Returns the values of the specified column in an array. 
	 * @param colNumber The number of the column to get.
	 * @return A double array containing the column values. */
	public double[] getColumn(int colNumber) {
		double[] column = new double[rows];
		
		for (int i=0; i<rows; i++) {
			column[i] = matrix[i][colNumber-1];
		}
		
		return column;
	}
	
	/** Sets the value of a single cell in the matrix. 
	 * @param rowNumber The row number of the cell to change.
	 * @param colNumber The column number of the cell to change.
	 * @param value The value to change the cell to. */
	public void setCell(int rowNumber, int colNumber, double value) {
		matrix[rowNumber - 1][colNumber - 1] = value;
	}
	
	/** Returns the value of a single cell in the matrix. 
	 * @param rowNumber The row number of the cell to get.
	 * @param colNumber The column number of the cell to get.
	 * @return The value at the specified cell. */
	public double getCell(int rowNumber, int colNumber) {
		return matrix[rowNumber - 1][colNumber - 1];
	}
	
	/** Returns the number of rows 'A' in an 'AxB' array. 
	 * @return The number of rows. */
	public int getRows() {
		return rows;
	}
	
	/** Returns the number of columns 'B' in an 'AxB' array. 
	 * @return The number of columns. */
	public int getColumns() {
		return columns;
	}
	
	/** Returns a new matrix containing values that are multiplied by the scalar. 
	 * @param scalar The scalar quantity by which to multiply the matrix.
	 * @return A new matrix whose values have been multiplied by the scalar. */
	public Matrix mult(double scalar) {
		Matrix m = new Matrix(rows, columns);
		
		for (int i=1; i<=rows; i++) {
			for (int j=1; j<=columns; j++) {
				m.setCell(i, j, matrix[i-1][j-1] * scalar);
			}
		}
		
		return m;
	}
	
	/** Returns a new matrix containing the result of adding this matrix to another matrix.
	 * @param other The other matrix that this matrix should be added to.
	 * @return A new matrix containing the added values. */
	public Matrix add(Matrix other) {
		Matrix m;
		
		// return a matrix containing only 'NaN' if the matrices are not the same size
		if (other.getRows() != rows || other.getColumns() != columns) {
			m = new Matrix(1, 1);
			m.setCell(1, 1, Double.NaN);
			return m;
		}
		
		m = new Matrix(other.getRows(), other.getColumns());
		
		// add the matrices
		for (int i=1; i<=rows; i++) {
			for (int j=1; j<=columns; j++) {
				double value = other.getCell(i, j) + matrix[i-1][j-1];
				m.setCell(i, j, value);
			}
		}
		
		return m;
	}
	
	/** Returns a new matrix containing the result of subtracting another matrix from this matrix.
	 * @param other The matrix to subtract from this matrix. 
	 * @return A new matrix containing the subtracted values. */
	public Matrix subtract(Matrix other) {
		Matrix m;
		
		// create another matrix containing the opposite quantities of the matrix to subtract
		Matrix oppositeMatrix = other.mult(-1);
		
		// add the opposite
		m = this.add(oppositeMatrix);
		
		return m;
	}
	
	/** A matrix operation that swaps the specified rows of this matrix. 
	 * @param row1 The first row to swap.
	 * @param row2 The second row to swap. */
	public void rowSwap(int row1, int row2) {
		double[] r1 = new double[columns];
		double[] r2 = new double[columns];
		
		// copy row1
		for (int j=0; j<columns; j++) {
			r1[j] = matrix[row1-1][j];
		}
		// copy row2
		for (int j=0; j<columns; j++) {
			r2[j] = matrix[row2-1][j];
		}
		
		// set the rows to their new values
		this.setRow(row1, r2);
		this.setRow(row2, r1);
	}
	
	/** Multiply a row in this matrix by the scalar quantity.
	 * @param row The row that is to be scaled.
	 * @param scalar The quantity by which to scale the row. */
	public void rowMultiply(int row, double scalar) {
		for (int j=0; j<columns; j++) {
			matrix[row-1][j] *= scalar;
		}
	}
	
	/** A matrix row operation that consists of multiplying row2 by the  
	 * scalar (nondestructively) and then adding the result to row1. 
	 * @param row1 The row that is to be modified.
	 * @param row2 The row whose values are to be scaled and added to row1.
	 * @param scalar The amount by which to scale row 2. */
	public void rowMultiplyAndAdd(int row1, int row2, double scalar) {
		double[] scaledRow = new double[columns];
		
		// calc the values of the scaled row
		for (int j=0; j<columns; j++) {
			scaledRow[j] = matrix[row2-1][j] * scalar;
		}
		
		// add them to row1
		for (int j=0; j<columns; j++) {
			matrix[row1-1][j] += scaledRow[j];
		}
	}
	
	/** Transpose the matrix. Coordinates become flipped so (1, 2) goes to (2, 1), 
	 * (1, 3) goes to (3, 1) etc. The resulting matrix goes from 'AxB' to 'BxA'.
	 * @return A new matrix containing the transposed values. */
	public Matrix transpose() {
		double[][] transposedMatrix = new double[columns][rows];
		
		// move the values to their new positions
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				transposedMatrix[j][i] = matrix[i][j];
			}
		}
		
		return new Matrix(transposedMatrix);
	}
	
	/** Create a copy of this matrix in a new matrix object.
	 * @return A duplicate of this matrix. */
	@Override
	public Matrix clone() {
		Matrix m = new Matrix(rows, columns);
		
		for (int i=1; i<=rows; i++) {
			for (int j=1; j<=columns; j++) {
				double value = getCell(i, j);
				m.setCell(i, j, value);
			}
		}
		
		return m;
	}
	
	/** Multiply this matrix by another matrix. This operation is NOT commutative.
	 * @param other The other matrix by which to multiply this matrix.
	 * @return A new matrix containing the multiplied values. */
	public Matrix mult(Matrix other) {
		/* 
		 * Return a matrix containing only 'NaN' if the matrices are not of compatible sizes.
		 * To be compatible for multiplication, the column length of the first array must equal
		 * the row length of the second array. In other words, they must take the form 'AxB' and
		 * 'BxC' in respective order.
		 */
		if (other.getRows() != columns) {
			return invalidMatrix();
		}
		
		int mRows = rows;
		int mColumns = other.getColumns();
		Matrix m = new Matrix(mRows, mColumns);
		
		// calculate the dot product for each cell of the result matrix
		for (int i=1; i<=mRows; i++) {
			for (int j=1; j<=mColumns; j++) {
				double dotProduct = 0;
				double[] row = this.getRow(i);
				double[] column = other.getColumn(j);
				
				// dot multiply the row by the column
				int n = other.getRows();
				for (int x=0; x<n; x++) {
					dotProduct += row[x] * column[x];
				}
				
				m.setCell(i, j, dotProduct);
			}
		}
		
		return m;
	}
	
	/** Return 'true' if the matrix is square. 'false' otherwise.
	 * @return True or false. */
	public boolean isSquare() {
		return rows == columns;
	}
	
	/** Employ the Gauss-Jordan Elimination algorithm for reducing a matrix to a partitioned identity matrix.
	 * @return The reduced matrix if possible. */
	public Matrix gaussJordanElimination() {
		
		// matrix must be Ax(A+1) or Ax2A if calculating an inverse matrix
		if (rows != columns-1 && columns != rows*2) {
			return invalidMatrix();
		}
		
		Matrix m = this.clone();
		
		for (int j=1; j<=rows; j++) {
			// calculate the pivot index
			// pivot is the largest value in the column
			double pivot = 0;
			double max = 0;
			int pivotRow = 0;
			for (int i=j; i<=rows; i++) {
				double c = m.getCell(i, j);
				double absC = Math.abs(c);
				if (absC > max) {
					max = absC;
					pivot = c;
					pivotRow = i;
				}
			}
			
			// if pivot is 0, the system of equations cannot be solved
			if (pivot == 0) {
				return invalidMatrix();
			}
			
			// swap the rows if pivot is in a lower row
			if (pivotRow > j) {
				m.rowSwap(pivotRow, j);
			}
			
			// divide the row by the pivot value to get a 1 in the proper diagonal
			m.rowMultiply(j, 1/pivot);
			
			// reduce the rest of the row cells to 0
			for (int i=1; i<=rows; i++) {
				if (i != j) {
					double scalar = m.getCell(i, j) * -1;
					m.rowMultiplyAndAdd(i, j, scalar);
				}
			}
			
		}
		
		return m;
	}
	
	/** If any errors are encountered during matrix operations, return this 
	 * matrix to signify that the operation couldn't be completed.  
	 * @return A 1x1 matrix containing NaN. */
	private Matrix invalidMatrix() {
		Matrix m = new Matrix(1, 1);
		m.setCell(1, 1, Double.NaN);
		return m;
	}
	
	/** Returns the inverse matrix of this matrix using Gauss-Jordan elimination.
	 * @return A new matrix containing the inverse of this matrix. */
	public Matrix inverse() {
		Matrix m;
		
		if (rows != columns) {
			m = new Matrix(1, 1);
			m.setCell(1, 1, Double.NaN);
			return m;
		}
		
		m = new Matrix(rows, columns*2);
		for (int i=1; i<=rows; i++) {
			for (int j=1; j<=columns; j++) {
				m.setCell(i, j, getCell(i, j));
			}
		}
		
		for (int i=1; i<=columns; i++) {
			m.setCell(i, i+columns, 1);
		}
		
		m = m.gaussJordanElimination();
		
		Matrix n = new Matrix(rows, columns);
		for (int i=1; i<=rows; i++) {
			for (int j=1; j<=columns; j++) {
				n.setCell(i, j, m.getCell(i, j+rows));
			}
		}
		
		return n;
	}
	
	/** Employs the Gaussian Elimination algorithm to reduce a matrix. 
	 * @param returnR Return the reduced matrix with the lower lefthand cell set to
	 * the R value of the matrix. This can then be used to calculate the determinant of the matrix.
	 * @return An upper-triangular reduced matrix if possible. */
	public Matrix gaussianElimination(boolean returnR) {

		if (rows != columns-1 && columns != rows) {
			return invalidMatrix();
		}
		
		Matrix m = this.clone();
		
		int r = 0;
		
		for (int j=1; j<=rows; j++) {
			// calculate the pivot index
			// pivot is the largest value in the column
			double pivot = 0;
			double max = 0;
			int pivotRow = 0;
			for (int i=j; i<=rows; i++) {
				double c = m.getCell(i, j);
				double absC = Math.abs(c);
				if (absC > max) {
					max = absC;
					pivot = c;
					pivotRow = i;
				}
			}
			
			// if pivot is 0, the system of equations cannot be solved
			if (pivot == 0) {
				return invalidMatrix();
			}
			
			// swap the rows if pivot is in a lower row
			if (pivotRow > j) {
				m.rowSwap(pivotRow, j);
				r++;
			}
			
			for (int i=1; i<=rows; i++) {
				if (i > j) {
					double scalar = m.getCell(i, j)/m.getCell(j, j) * -1;
					m.rowMultiplyAndAdd(i, j, scalar);
				}
			}
		}
		
		// Set the lower left-hand cell to the "r" value if "returnR" is true.
		// "r" can then be used as the exponent when finding a determinant.
		if (returnR) {
		m.setCell(rows, 1, r);
		}
		
		return m;
	}
	
	/** Uses back substitution to get the solutions to a system of equations 
	 * from a Gaussian Elimination reduced matrix. This operation will 
	 * succeed without throwing and error even if the matrix has not been
	 * properly reduced, however, the solution will not be the correct answer
	 * to the system of equations. It only makes sense to  call this method 
	 * on an upper-triangular Ax(A+1) matrix.
	 * @return An ArrayList containing the solutions in reverse order. */
	public ArrayList<Double> backSubstitute() {
		ArrayList<Double> solutionSet = new ArrayList<Double>(rows);
		
		for (int j=rows; j>0; j--) {
			double answer = 1/getCell(j, j);
			double constant = getCell(j, columns);
			if (solutionSet.size() > 0) {
				double numerator = constant;
				for (int a=0; a<solutionSet.size(); a++) {
					numerator -= solutionSet.get(a) * getCell(j, rows-a); 
				}
				answer = numerator * answer;
			} else {
				answer *= constant;
			}
			solutionSet.add(answer);
		}
		
		return solutionSet;
	}
	
	/** Returns the determinant of the matrix using Gaussian Elimination.
	 * @return The determinant of the matrix. */
	public double determinant() {
		Matrix m = gaussianElimination(true);
		double determinant = 1;
		for (int a=1; a<=rows; a++) {
			determinant *= m.getCell(a, a);
		}
		return Math.pow(-1, m.getCell(rows, 1)) * determinant;
	}
	
	/** Returns the condition number of the matrix.
	 * @return The condition number of the matrix. */
	public double conditionNumber() {
		if (rows != columns) {
			return Double.NaN;
		}
		
		Matrix inverse = inverse();
		
		return maxRowSum() * inverse.maxRowSum();
	}

	/** Calculates the highest sum of the absolute values of numbers in each row.
	 * @return The row with the largest absolute value summed. */
	private double maxRowSum() {
		double maxRowSum = 0;
		for (int rowNumber=1; rowNumber<=rows; rowNumber++) {
			double rowSum = 0;
			double[] row = getRow(rowNumber);
			
			for (double value: row) {
				rowSum += Math.abs(value);
			}
			
			if (rowSum > maxRowSum) {
				maxRowSum = rowSum;
			}
		}
		
		return maxRowSum;
	}
	
	/** A way to print out the matrix assuming that the values near zero are simply zero. */
	public void prettyPrint() {
		Matrix m = this.clone();
		for (int i=1; i<=rows; i++) {
			for (int j=1; j<=columns; j++) {
				if (Math.abs(m.getCell(i, j)) < .00000000000001) {
					m.setCell(i, j, 0);
				} 
			}
		}
		
		System.out.println(m + "\n");
	}
	
	/** Generate a string representing the matrix. 
	 * @return A string representation of the matrix. */
	@Override
	public String toString() {
		String s = rows + "x" + columns + " matrix\n";
		
		for (int i=0; i < rows; i++) {
			for (int j=0; j < columns; j++) {
				s += matrix[i][j];
				s += "\t";
			}
			
			if (i != rows-1)
				s += "\n";
		}
		
		return s + "\n";
	}
	
	/** Checks each value of each cell and compares it against the values from another matrix.
	 * @param other The matrix to test values against.
	 * @return True if the matrices are equal, otherwise false. */
	public boolean equals(Matrix other) {
		// if the rows and columns aren't the same length, the matrices can't be the same
		if (this.rows != other.rows || this.columns != other.columns) {
			return false;
		}
		
		// check each cell in both matrices and make sure they're equal
		for(int i = 1; i <= rows; i++) {
			for(int j=1; j <= columns; j++) {
				if (getCell(i, j) != other.getCell(i, j)) {
					return false;
				}
			}
		}
		
		return true;
	}
}
