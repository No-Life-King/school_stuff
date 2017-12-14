package BasicLinePix;
/** @author Phil Smith */

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Shape {
	/**
	 * A class that represents a line as defined by a starting point, end point, and color.
	 */
	
	/**
	 * Initializes a new line with a random color. 
	 */
	public Line() {
		setRandomColor();
	}
	
	/**
	 * Initializes a new line with the specified color.
	 * @param color The index of the color.
	 */
	public Line(int color) {
		super.setColor(color);
	}
	
	/**
	 * Draws this line to the canvas.
	 * @param g The graphics of the main JPanel.
	 */
	public void draw(Graphics g) {
		g.setColor(super.getColor());
		g.drawLine(startx, starty, endx, endy);
	}
	
	/**
	 * Determines if a point (or click) is within 5 pixels of this line.
	 * @param x The x coordinate of the point.
	 * @param y The y coordinate of the point.
	 * @return True if the point is close enough. Otherwise, false.
	 */
	public boolean contains(int x, int y) {
		if (pointDistanceFromLine(x, y) <= 5) {
			return true;
		}
		return false;
	}
	
	/**
	 * Calculates the distance of a point from this line.
	 * @param x The x coordinate of the point.
	 * @param y The y coordinate of the point.
	 * @return The distance of the point from the line.
	 */
	private double pointDistanceFromLine(int x, int y) {
		double numerator = Math.abs((endy - starty) * x - (endx - startx) * y + endx * starty - endy * startx);
		double denominator = Math.sqrt(Math.pow(endy - starty, 2) + Math.pow(endx - startx, 2));
		double distance = numerator/denominator;
		return distance;
	}
	
	/**
	 * Renders the line values in a way that can be easily encoded and decoded to and from a file.
	 * @return An easily interpreted String. 
	 */
	public String toWritableString() {
		return startx + "\t" + starty + "\t" + endx + "\t" + endy + "\t" + getColorIndex() + "\r\n";
	}
	
	@Override
	/**
	 * Returns a text version of the starting coordinates and ending coordinates of this line.
	 * @return A text version of the line.
	 */
	public String toString() {
		return "(" + startx + ", " + starty + "), (" + endx + ", " + endy + ") "; 
	}
	

	
}
