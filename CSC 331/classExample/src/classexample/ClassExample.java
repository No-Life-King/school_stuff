
package classexample;

/**
 * @author phil
 */
public class ClassExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Point a = new Point(0, 0);
        Point b = new Point(3, 4);
        
        System.out.println(a.distance(b));
        
        Line hypotenuse = new Line(a, b);
        System.out.println(hypotenuse.length());
    }
    
}
