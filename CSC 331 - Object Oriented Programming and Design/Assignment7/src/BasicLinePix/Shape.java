package BasicLinePix;
import java.awt.Graphics;
import java.awt.Color;

public abstract class Shape {
    
    private Color myColor;
	private int color;
	private static Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.MAGENTA, Color.YELLOW};
	protected int startx, starty, endx, endy;
	
	/**
	 * Allows a shape to be drawn to a canvas.
	 * @param g The "canvas" to which the shape should be drawn. 
	 */
	public abstract void draw(Graphics g);
    
	/**
	 * Determines whether or not a point is close to this shape.
	 * @param x The x coordinate of the point.
	 * @param y The y coordinate of the point.
	 * @return True if the point is close enough. Otherwise, false.
	 */
    public abstract boolean contains(int x, int y);
    
    /**
     * Allows the shape color to be set at random.
     */
    public void setRandomColor() {
    	color = (int) (Math.random() * 4);
		this.myColor = colors[color];
    }
	
    /**
     * Allows the shape color to be set to the specified color.
     * @param color The color that the shape should be drawn in.
     */
    public void setColor(int color) {
    	this.color = color;
    	this.myColor = colors[color];
    }
    
	/**
	 * Gets the color enum of the color of the line.
	 * @return A color.
	 */
	protected Color getColor() {
		return myColor;
	}
	
	/**
     * Allows the shape color to be set to the specified color.
     * @param c The color that the shape should be drawn in.
     */
	protected void setColor(Color c) {
		myColor = c;
		for (int x=0; x<colors.length; x++) {
			if (c == colors[x]) {
				color = x;
				break;
			}
		}
	}
	
	/**
	 * Gets the index of the color in the color array.
	 * @return The index of the color.
	 */
	protected int getColorIndex() {
		return color;
	}
	
	/**
	 * Sets the starting point of this shape.
	 * @param x The starting point's x coordinate.
	 * @param y The starting point's y coordinate.
	 */
	public void setStartingPoint(int x, int y) {
		this.startx = x;
		this.starty = y;
	}

	/**
	 * Sets the end point of this shape.
	 * @param x The end point's x coordinate.
	 * @param y The end point's y coordinate.
	 */
	public void setEndPoint(int x, int y) {
		this.endx = x;
		this.endy = y;
	}
	
	/**
	 * Gets the starting x coordinate of the shape.
	 * @return The starting x coordinate of the shape.
	 */
	public int getStartx() {
		return startx;
	}
	
	/**
	 * Gets the starting y coordinate of the shape.
	 * @return The starting y coordinate of the shape.
	 */
	public int getStarty() {
		return starty;
	}
	
	/**
	 * Gets the ending x coordinate of the shape.
	 * @return The ending x coordinate of the shape.
	 */
	public int getEndx() {
		return endx;
	}
	
	/**
	 * Gets the ending y coordinate of the shape.
	 * @return The ending y coordinate of the shape.
	 */
	public int getEndy() {
		return endy;
	}

	/**
	 * Allows the shape to be written to a file.
	 * @return A string representing the shape.
	 */
	public abstract String toWritableString();
    
}