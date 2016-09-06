import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * This class displays the two initial screens that ask user 
 * which style and how many stones they want
 * then it calls model and view constructors
 * @author Marlowe DeVera, Amrit Sandhu, Shweta Sugnani
 *
 */

public class InitialScreen {
	
	public static int numberOfStones; //user chooses 
	public static BoardStyle style; //user chooses
	
	public InitialScreen(){
		
		final JFrame initialScreen = new JFrame();
		final JRadioButton threeStones = new JRadioButton("3 stones");
		JRadioButton fourStones = new JRadioButton("4 stones");
		initialScreen.setLayout(new FlowLayout());
		ButtonGroup group = new ButtonGroup(); //created so user cannot select more than one option
		group.add(threeStones);
		group.add(fourStones);
		JLabel stonesChoose = new JLabel("How many stones would you like to start with? If you don't choose, 4 will be assumed");
		initialScreen.add(stonesChoose);
		initialScreen.add(threeStones);
		initialScreen.add(fourStones);
		
		JButton go = new JButton("Go!");
		go.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {//number of stones button
				if (threeStones.isSelected() == true){ 
					numberOfStones = 3; //if radio button is set to 3, then set number of stones to 3
				}
				else {
					numberOfStones = 4; //otherwise 4
				}
				initialScreen.dispose(); //discard the first screen after number of stones is selected
				final JFrame shapeSelection = new JFrame();
				JLabel styleChoose = new JLabel("Which style would you like? If you don't choose, rectangle will be assumed.");
				final JRadioButton ellipse = new JRadioButton("Ellipse Style");
				JRadioButton rectangle = new JRadioButton("Rectangle Style");
				shapeSelection.setLayout(new FlowLayout());
				ButtonGroup group2 = new ButtonGroup();
				group2.add(ellipse);
				group2.add(rectangle);
				shapeSelection.add(styleChoose);
				shapeSelection.add(ellipse);
				shapeSelection.add(rectangle);
				
				JButton go2 = new JButton("Go!");
				go2.addActionListener(new ActionListener(){ //style button

					@Override
					public void actionPerformed(ActionEvent e) {
						if (ellipse.isSelected() == true){
							style = new EllipseStyle(); //set style selected to ellipse style if ellipse button is selected
						}
						else {
							style = new RectangleStyle();//set style selected to rectandle style if ellipse button is selected
						}
						
						MancalaModel model = new MancalaModel(numberOfStones); //creates a model with the number of stones
						MancalaView view = new MancalaView(model, style); //creates a view that passes in the model and the style selected
						view.setVisible(true);
						shapeSelection.dispose();
					}
					
				});
				shapeSelection.add(go2); 
				shapeSelection.setVisible(true);
				shapeSelection.pack();
				shapeSelection.setSize(550, 300);
				shapeSelection.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
		
		initialScreen.add(go);
		
		initialScreen.setVisible(true);
		initialScreen.pack();
		initialScreen.setSize(550, 300);
		initialScreen.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		
	}
}
