package BasicLinePix;

import java.awt.Color;
import java.awt.Graphics;

/** @author Phil Smith */
public class Rectangle extends Shape {
	
	private Line s1, s2, s3, s4;
	
	/**
	 * Initializes a new rectangle with a random color. 
	 */
	public Rectangle() {
		setRandomColor();
	}
	
	/**
	 * Allows a new rectangle to be instantiated with the provided color.
	 * @param c The color that the rectangle should be.
	 */
	public Rectangle(Color c) {
		setColor(c);
	}
	
	/**
	 * Allows a new rectangle to be created out of four Line objects.
	 * @param s1 Side 1 of the rectangle.
	 * @param s2 Side 2 of the rectangle.
	 * @param s3 Side 3 of the rectangle.
	 * @param s4 Side 4 of the rectangle.
	 */
	public Rectangle(Line s1, Line s2, Line s3, Line s4) {
		setColor(s1.getColor());
		
		this.s1 = s1;
		this.s2 = s2;
		this.s3 = s3;
		this.s4 = s4;
	}
	
	/**
	 * Draws a rectangle.
	 * @param g The graphics panel to be drawn to.
	 */
	@Override
	public void draw(Graphics g) {
		
		if (s1 == null) {
			s1 = new Line(getColorIndex());
			s1.setStartingPoint(getStartx(), getStarty());
			s1.setEndPoint(getEndx(), getStarty());
			
			s2 = new Line(getColorIndex());
			s2.setStartingPoint(getEndx(), getStarty());
			s2.setEndPoint(getEndx(), getEndy());
			
			s3 = new Line(getColorIndex());
			s3.setStartingPoint(getEndx(), getEndy());
			s3.setEndPoint(getStartx(), getEndy());
			
			s4 = new Line(getColorIndex());
			s4.setStartingPoint(getStartx(), getEndy());
			s4.setEndPoint(getStartx(), getStarty());
		}
		
		s1.draw(g);
		s2.draw(g);
		s3.draw(g);
		s4.draw(g);
	}
	
	/**
	 * Determines if a point (or click) is within 5 pixels of this rectangle.
	 * @return True if the click is close enough to the rectangle. Otherwise, false. 
	 */
	@Override
	public boolean contains(int x, int y) {
		if (s1.contains(x, y) || s2.contains(x, y) || s3.contains(x, y) || s4.contains(x, y)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Allows the rectangle to be printed.
	 * @return A printable string.
	 */
	@Override
	public String toString() {
		return toWritableString();
	}
	
	/**
	 * Allows the rectangle to be written to a file.
	 * @return A string representing the rectangle.
	 */
	@Override
	public String toWritableString() {
		StringBuilder s = new StringBuilder("rectangle\r\n");
		s.append(s1.toWritableString());
		s.append(s2.toWritableString());
		s.append(s3.toWritableString());
		s.append(s4.toWritableString());
		return s.toString();
	}
	
}
