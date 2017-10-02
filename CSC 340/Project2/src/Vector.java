/**  @author Philip Smith */

public class Vector implements Cloneable {
	
	private double i;
	private double j;
	
	public Vector(double i, double j) {
		this.i = i;
		this.j = j;
	}
	
	public Vector(double[] coords) {
		i = coords[0];
		j = coords[1];
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
	
	public double magnitude() {
		return Math.sqrt(Math.pow(i, 2) + Math.pow(j, 2));
	}
	
	public double getI() {
		return i;
	}
	
	public double getJ() {
		return j;
	}
	
	public Vector clone() {
		Vector v = new Vector(i, j);
		return v;
	}
	
	public double[] toDouble() {
		double[] values = new double[2];
		values[0] = i;
		values[1] = j;
		return values;
	}
	
	@Override
	public String toString() {
		return "<" + i + ", " + j + ">";
	}
	
}
