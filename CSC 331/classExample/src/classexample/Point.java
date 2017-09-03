package classexample;

/**
 *
 * @author phil
 */
public class Point {
    
    private double x;
    private double y;
    
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public void setX(double x) {
        this.x = x;
    }
    
    public void setY(double y) {
        this.y = y;
    }
    
    public double distance(Point otherPoint) {
        return Math.sqrt(Math.pow(x - otherPoint.x, 2) + Math.pow(y - otherPoint.y, 2));
    }
    
}
