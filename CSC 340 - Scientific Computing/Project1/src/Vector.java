/**  @author Philip Smith */

public class Vector {
	
	private double i;
	private double j;
	
	public Vector(double i, double j) {
		this.i = i;
		this.j = j;
	}
	
	public Vector add(Vector v) {
		return new Vector(i + v.getI(), j + v.getJ());
	}
	
	public Vector subtract(Vector v) {
		Vector oppositeVector = v.scalarMultiply(-1);
		return add(oppositeVector);
	}
	
	public Vector scalarMultiply(double scalar) {
		return new Vector(i * scalar, j * scalar);
	}
	
	public double getI() {
		return i;
	}
	
	public double getJ() {
		return j;
	}
	
	public Vector duplicate() {
		Vector v = new Vector(i, j);
		return v;
	}
	
	@Override
	public String toString() {
		return "<" + i + ", " + j + ">";
	}
	
}
