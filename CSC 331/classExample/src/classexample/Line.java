package classexample;

/**
 *
 * @author phil
 */
public class Line {
    Point a;
    Point b;
    
    public Line(Point a,Point b) {
        this.a = a;
        this.b = b;
    }
    
    public double length() {
        return a.distance(b);
    }
}
