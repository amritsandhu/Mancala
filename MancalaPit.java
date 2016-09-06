import java.awt.*;
import java.awt.List;
import java.util.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * This class draws each pit and draws the stones in each pit
 * @author Marlowe DeVera, Amrit Sandhu, Shweta Sugnani
 *
 */
public class MancalaPit extends JButton{
	
	Dimension dimensions;
	int numberOfstones;
	ArrayList<Shape> shapes;
	BoardStyle style;

	/**
	 * Constructor for MancalaPit
	 * @param text - number of stones in the pit
	 * @param style - which style the user chooses
	 */
	public MancalaPit(String text, BoardStyle style) {
		
		this.setText(text);
		this.style = style;
		
		numberOfstones = 2;
		
		shapes = new ArrayList<Shape>();
		
		dimensions = new Dimension();
		
		dimensions.width = Math.max(dimensions.width, dimensions.height); //set them to the maximum width can be
		dimensions.height = Math.max(dimensions.width, dimensions.height); //set them to the maximum height can be
		
		setPreferredSize(dimensions);
		
		setContentAreaFilled(false);
		setBorderPainted(false);
		setOpaque(false);
	}

	/**
	 * Draws the pit and the stones in each pit
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(style.setColor().darker());
		
		
		if (style.style().equals("Ellipse")){
			g2.fillOval(0, 0, getWidth(), getHeight());
		}
		else{
			g2.fillRect(0, 0, getWidth(), getHeight());
		}

		g2.setColor(Color.BLACK);

		for (Shape s : shapes) {
			g2.setColor(Color.PINK);
			g2.fill(s);
			Color border = new Color( 70, 130, 180 );
			g2.setColor( border );
			g2.draw(s);
		}
		super.paintComponent(g);
	}

	/**
	 * Sets the number of stones in each pit
	 * @param stone - number of stones to put in each pit
	 */
	public void setStone(int numberOfstones) {
		this.numberOfstones = numberOfstones;
		shapes = new ArrayList<Shape>();
		for (int i = 0; i < numberOfstones; i++) {
				shapes.add(style.getShape(34 + i, 25 + 50 *i / 4, 10, 10)); //draws the stone with given x and y positions	
		}
		super.repaint();
	}

}
