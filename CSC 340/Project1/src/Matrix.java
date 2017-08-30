// Author: Philip Smith

public class Matrix {
	
	/* 
	 * This class contains the necessary functionality for working with matrices
	 * and solving matrix-related problems.
	 */
	
	private int rows;
	private int columns;
	private double[][] matrix;
	
	// This constructor initializes an empty 2D array with the form Rows x Columns.
	public Matrix(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		
		matrix = new double[rows][columns];
	}
	
	// This constructor accepts an existing 2D array and converts it to the matrix type.
	public Matrix(double[][] matrix) {
		rows = matrix.length;
		columns = matrix[0].length;
		
		this.matrix = matrix;
	}
	
	// Sets the values of the specified row to the values of the passed-in array.
	public void setRow(int rowNumber, double[] values) {
		rowNumber--;
		
		for (int j=0; j < columns; j++) {
			matrix[rowNumber][j] = values[j];
		}
	}
	
	// Returns the values of the specified row in an array.
	public double[] getRow(int rowNumber) {
		double[] row = new double[columns];
		
		for (int j=0; j<columns; j++) { 
			row[j] = matrix[rowNumber-1][j];
		}
		
		return row;
	}
	
	// Sets the values of the specified column to the values of the passed-in array.
	public void setColumn(int colNumber, double[] values) {
		colNumber--;
		
		for (int i=0; i < rows; i++) {
			matrix[i][colNumber] = values[i];
		}
	}
	
	// Returns the values of the specified column in an array.
	public double[] getColumn(int colNumber) {
		double[] column = new double[rows];
		
		for (int i=0; i<rows; i++) {
			column[i] = matrix[i][colNumber-1];
		}
		
		return column;
	}
	
	// Sets the value of a single cell in the matrix.
	public void setCell(int rowNumber, int colNumber, double value) {
		matrix[rowNumber - 1][colNumber - 1] = value;
	}
	
	// Returns the value of a single cell in the matrix.
	public double getCell(int rowNumber, int colNumber) {
		return matrix[rowNumber - 1][colNumber - 1];
	}
	
	// Returns the number of rows 'A' in an 'AxB' array.
	public int getRows() {
		return rows;
	}
	
	// Returns the number of columns 'B' in an 'AxB' array.
	public int getColumns() {
		return columns;
	}
	
	// Returns a new matrix containing values that are multiplied by the scalar.
	public Matrix scalarMultiply(double scalar) {
		Matrix m = new Matrix(rows, columns);
		
		for (int i=1; i<=rows; i++) {
			for (int j=1; j<=columns; j++) {
				m.setCell(i, j, matrix[i-1][j-1] * scalar);
			}
		}
		
		return m;
	}
	
	// Returns a new matrix containing the result of adding this matrix to another matrix.
	public Matrix add(Matrix other) {
		Matrix m;
		
		// Return a matrix containing only 'NaN' if the matrices are not the same size.
		if (other.getRows() != rows || other.getColumns() != columns) {
			m = new Matrix(1, 1);
			m.setCell(1, 1, Double.NaN);
			return m;
		}
		
		m = new Matrix(other.getRows(), other.getColumns());
		
		// Add the matrices.
		for (int i=1; i<=rows; i++) {
			for (int j=1; j<=columns; j++) {
				double value = other.getCell(i, j) + matrix[i-1][j-1];
				m.setCell(i, j, value);
			}
		}
		
		return m;
	}
	
	// Returns a new matrix containing the result of subtracting another matrix from this matrix.
	public Matrix subtract(Matrix other) {
		Matrix m;
		
		// create another matrix containing the opposite quantities of the matrix to subtract
		Matrix oppositeMatrix = other.scalarMultiply(-1);
		
		// add the opposite
		m = this.add(oppositeMatrix);
		
		return m;
	}
	
	// A matrix operation that swaps the specified rows of this matrix.
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
	
	// Multiply a row in this matrix by the scalar quantity.
	public void rowMultiply(int row, double scalar) {
		for (int j=0; j<columns; j++) {
			matrix[row-1][j] *= scalar;
		}
	}
	
	/*
	 *  A matrix row operation that consists of multiplying row2 by the  
	 *  scalar (nondestructively) and then adding the result to row1. 
	 */
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
	
	/* 
	 * Transpose the matrix. Coordinates become flipped so (1, 2) goes to (2, 1), 
	 * (1, 3) goes to (3, 1) etc. The resulting matrix goes from 'AxB' to 'BxA'.
	 */
	public void transpose() {
		double[][] transposedMatrix = new double[columns][rows];
		
		// move the values to their new positions
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				transposedMatrix[j][i] = matrix[i][j];
			}
		}
		
		// flip the dimensions of the matrix
		int swap = rows;
		rows = columns;
		columns = swap;
		
		matrix = transposedMatrix;
	}
	
	// Create a copy of this matrix in a new matrix object.
	public Matrix duplicate() {
		return new Matrix(matrix);
	}
	
	// Multiply this matrix by another matrix. This operation is NOT commutative.
	public Matrix matrixMultiply(Matrix other) {
		Matrix m;
		
		/* 
		 * Return a matrix containing only 'NaN' if the matrices are not of compatible sizes.
		 * To be compatible for multiplication, the column length of the first array must equal
		 * the row length of the second array. In other words, they must take the form 'AxB' and
		 * 'BxC' in respective order.
		 */
		if (other.getRows() != columns) {
			m = new Matrix(1, 1);
			m.setCell(1, 1, Double.NaN);
			return m;
		}
		
		int mRows = rows;
		int mColumns = other.getColumns();
		m = new Matrix(mRows, mColumns);
		
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
	
	// Return 'true' if the matrix is square. 'false' otherwise.
	public boolean isSquare() {
		return rows == columns;
	}
	
	// Employ the Gauss-Jordan Elimination algorithm for reducing a matrix to a partitioned identity matrix.
	public Matrix GaussJordanElimination() {
		Matrix m;
		
		// matrix must be Ax(A+1) 
		if (rows != columns-1) {
			m = new Matrix(1, 1);
			m.setCell(1, 1, Double.NaN);
			return m;
		}
		
		m = this.duplicate();
		
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
				m = new Matrix(1, 1);
				m.setCell(1, 1, Double.NaN);
				return m;
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
	
	// generate a string representing the matrix
	@Override
	public String toString() {
		String s = "";
		
		for (int i=0; i < rows; i++) {
			for (int j=0; j < columns; j++) {
				s += matrix[i][j];
				s += "\t";
			}
			
			if (i != rows-1)
				s += "\n";
		}
		
		return s;
	}
}
