
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
		v.scalarMultiply(-1);
		this.add(v);
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
	
	@Override
	public String toString() {
		return "<" + i + ", " + j + ">";
	}
	
}
