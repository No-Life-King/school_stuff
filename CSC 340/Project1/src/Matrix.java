
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
	
	public void setColumn(int colNumber, double[] values) {
		colNumber--;
		
		for (int i=0; i < rows; i++) {
			matrix[i][colNumber] = values[i];
		}
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
	
	public void scalarMultiply(double scalar) {
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				matrix[i][j] = matrix[i][j] * scalar;
			}
		}
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
		m1.scalarMultiply(-1);
		m = this.add(m1);
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
	
	@Override
	public String toString() {
		String s = "";
		for (int i=0; i < rows; i++) {
			for (int j=0; j < columns; j++) {
				s += matrix[i][j];
				s += "\t";
			}
			s += "\n";
		}
		
		return s;
	}
}
