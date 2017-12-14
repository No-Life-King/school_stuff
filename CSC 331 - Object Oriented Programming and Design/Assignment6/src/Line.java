/** @author Phil Smith */

import java.awt.Color;
import java.awt.Graphics;

public class Line {
	/**
	 * A class that represents a line as defined by a starting point, end point, and color.
	 */
	
	private int startx, starty, endx, endy;
	private int color;
	private static Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.MAGENTA};
	
	/**
	 * Initializes a new line with a random color. 
	 */
	public Line() {
		color = (int) (Math.random() * 4);
	}
	
	/**
	 * Initializes a new line with the specified color.
	 * @param color The index of the color.
	 */
	public Line(int color) {
		this.color = color;
	}
	
	/**
	 * Draws this line to the canvas.
	 * @param g The graphics of the main JPanel.
	 */
	public void draw(Graphics g) {
		g.setColor(colors[color]);
		g.drawLine(startx, starty, endx, endy);
	}
	
	/**
	 * Gets the color enum of the color of the line.
	 * @return A color.
	 */
	public Color getColor() {
		return colors[color];
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
		return startx + "\t" + starty + "\t" + endx + "\t" + endy + "\t" + color;
	}
	
	@Override
	/**
	 * Returns a text version of the starting coordinates and ending coordinates of this line.
	 */
	public String toString() {
		return "(" + startx + ", " + starty + "), (" + endx + ", " + endy + ") "; 
	}
	
	/**
	 * Sets the starting point of this line.
	 * @param x The starting point's x coordinate.
	 * @param y The starting point's y coordinate.
	 */
	public void setStartingPoint(int x, int y) {
		this.startx = x;
		this.starty = y;
	}

	/**
	 * Sets the end point of this line.
	 * @param x The end point's x coordinate.
	 * @param y The end point's y coordinate.
	 */
	public void setEndPoint(int x, int y) {
		this.endx = x;
		this.endy = y;
	}
	
}
