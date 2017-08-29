
public class Vector {
	
	private double i;
	private double j;
	
	public Vector(double i, double j) {
		this.i = i;
		this.j = j;
	}
	
	public void add(Vector v) {
		i += v.getI();
		j += v.getJ();
	}
	
	public void subtract(Vector v) {
		Vector oppositeVector = v.duplicate();
		oppositeVector.scalarMultiply(-1);
		this.add(oppositeVector);
	}
	
	public void scalarMultiply(double scalar) {
		i *= scalar;
		j *= scalar;
	}
	
	public double getI() {
		return i;
	}
	
	public double getJ() {
		return j;
	}
	
	public Vector duplicate() {
		Vector v = new Vector(this.i, this.j);
		return v;
	}
	
	@Override
	public String toString() {
		return "<" + i + ", " + j + ">";
	}
	
}
