import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;

/**
 * This interface defines the strategy portion of the strategy pattern
 * @author Marlowe DeVera, Amrit Sandhu, Shweta Sugnani
 *
 */

public interface BoardStyle {
	
	public Shape getShape(int height, int width, int x, int y); //sets and returns the style

	public Color setColor(); //sets the color of the style

	public String style(); //returns a string to print the style format
	

}
