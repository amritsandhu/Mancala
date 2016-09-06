import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 * This class serves as one of the concrete strategies of the strategy pattern
 * @author Marlowe DeVera, Amrit Sandhu, Shweta Sugnani
 *
 */
public class RectangleStyle implements BoardStyle {
	
	/**
	 * Returns a rectangle for the ellipse style of board
	 * @param height - height of rectangle 
	 * @param width the width of the rectangle 
	 * @param x the x position of the rectangle 
	 * @param y the y position of the rectangle 
	 */
	@Override
	public Shape getShape(int height, int width, int x, int y) {
		return new Rectangle2D.Double(height, width, x, y);
		
	}

	/**
	 * sets the color of the rectangle
	 * @return the color to set
	 */
	@Override
	public Color setColor() {
		Color paint = new Color(189,252,201);
		return paint;
	}

	/**
	 * gets the shape in string format
	 * @return the rectangle in string
	 */
	@Override
	public String style() {
		return "Rectangle";
	}


}
