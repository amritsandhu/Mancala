import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

/**
 * This class serves as one of the concrete strategies of the strategy pattern
 * @author Marlowe DeVera, Amrit Sandhu, Shweta Sugnani
 *
 */

public class EllipseStyle implements BoardStyle {
	
	/**
	 * Returns an ellipse for the ellipse style of board
	 * @param height - height of ellipse 
	 * @param width the width of the ellipse
	 * @param x the x position of the ellipse
	 * @param y the y position of the ellipse
	 */
	@Override
	public Shape getShape(int height, int width, int x, int y) {
		return new Ellipse2D.Double(height, width, x, y);
		
	}
	
	/**
	 * sets the color of the ellipse
	 * @return the color to set
	 */
	public Color setColor(){
		Color paint = new Color( 173, 216, 230 );
		return paint;
	}

	/**
	 * @return the string of ellipse
	 */
	public String style(){
		return "Ellipse";
	}
	

}
