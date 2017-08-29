
public class Matrix {
	
	private int rows;
	private int columns;
	private double[][] matrix;
	
	public Matrix(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		matrix = new double[rows][columns];
	}
	
	public void setRow(int rowNumber, double[] values) {
		rowNumber--;
		
		for (int j=0; j < columns; j++) {
			matrix[rowNumber][j] = values[j];
		}
	}
	
	public double[] getRow(int rowNumber) {
		double[] row = new double[columns];
		for (int j=0; j<columns; j++) {
			row[j] = matrix[rowNumber-1][j];
		}
		return row;
	}
	
	public void setColumn(int colNumber, double[] values) {
		colNumber--;
		
		for (int i=0; i < rows; i++) {
			matrix[i][colNumber] = values[i];
		}
	}
	
	public double[] getColumn(int colNumber) {
		double[] column = new double[rows];
		for (int i=0; i<rows; i++) {
			column[i] = matrix[i][colNumber-1];
		}
		return column;
	}
	
	public void setCell(int rowNumber, int colNumber, double value) {
		matrix[rowNumber - 1][colNumber - 1] = value;
	}
	
	public double getCell(int rowNumber, int colNumber) {
		rowNumber--;
		colNumber--;
		return matrix[rowNumber][colNumber];
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getColumns() {
		return columns;
	}
	
	public Matrix scalarMultiply(double scalar) {
		Matrix m = new Matrix(rows, columns);
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				m.setCell(i+1, j+1, matrix[i][j] * scalar);
			}
		}
		return m;
	}
	
	public Matrix add(Matrix m1) {
		Matrix m;
		if (m1.getRows() != rows || m1.getColumns() != columns) {
			m = new Matrix(1, 1);
			m.setCell(1, 1, Double.NaN);
			return m;
		}
		m = new Matrix(m1.getRows(), m1.getColumns());
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				double value = m1.getCell(i+1, j+1) + matrix[i][j];
				m.setCell(i+1, j+1, value);
			}
		}
		return m;
	}
	
	public Matrix subtract(Matrix m1) {
		Matrix m;
		Matrix oppositeMatrix = m1.scalarMultiply(-1);
		m = this.add(oppositeMatrix);
		return m;
	}
	
	public void rowSwap(int row1, int row2) {
		double[] r1 = new double[columns];
		double[] r2 = new double[columns];
		
		for (int j=0; j<columns; j++) {
			r1[j] = matrix[row1-1][j];
		}
		for (int j=0; j<columns; j++) {
			r2[j] = matrix[row2-1][j];
		}
		
		this.setRow(row1, r2);
		this.setRow(row2, r1);
	}
	
	public void rowMultiply(int row, double scalar) {
		for (int j=0; j<columns; j++) {
			matrix[row-1][j] *= scalar;
		}
	}
	
	public void rowAddAndMultiply(int row1, int row2, double scalar) {
		double[] scaledRow = new double[columns];
		
		for (int j=0; j<columns; j++) {
			scaledRow[j] = matrix[row2-1][j] * scalar;
		}
		
		for (int j=0; j<columns; j++) {
			matrix[row1-1][j] += scaledRow[j];
		}
	}
	
	public void transpose() {
		double[][] transposedMatrix = new double[columns][rows];
		
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				transposedMatrix[j][i] = matrix[i][j];
			}
		}
		
		int swap = rows;
		rows = columns;
		columns = swap;
		matrix = transposedMatrix;
		
	}
	
	public Matrix duplicate() {
		Matrix newMatrix = new Matrix(rows, columns);
		
		for(int i=1; i<=rows; i++) {
			newMatrix.setRow(i, this.getRow(i));
		}
		
		return newMatrix;
	}
	
	public Matrix matrixMultiply(Matrix m1) {
		Matrix m;
		if (m1.getRows() != columns) {
			m = new Matrix(1, 1);
			m.setCell(1, 1, Double.NaN);
			return m;
		}
		int mRows = rows;
		int mColumns = m1.getColumns();
		m = new Matrix(mRows, mColumns);
		
		for (int i=0; i<mRows; i++) {
			for (int j=0; j<mColumns; j++) {
				double dotProduct = 0;
				double[] row = this.getRow(i+1);
				double[] column = m1.getColumn(j+1);
				int n = m1.getRows();
				for (int x=0; x<n; x++) {
					dotProduct += row[x] * column[x];
				}
				m.setCell(i+1, j+1, dotProduct);
			}
		}
		return m;
	}
	
	public boolean isSquare() {
		return rows == columns;
	}
	
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
