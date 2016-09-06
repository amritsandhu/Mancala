import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class serves as the view and the controller for the MVC model
 * @author Marlowe DeVera, Amrit Sandhu, Shweta Sugnani
 *
 */
public class MancalaView extends JFrame implements ChangeListener {

	public MancalaModel model;
	public BoardStyle style;

	JLabel player1label;
	JLabel player2label;
	JPanel emptySpace;

	JPanel finalFrame;
	JPanel boardPanel;
	JPanel systemOut;
	JTextArea textBox;

	public MancalaPit player1;
	public MancalaPit player2;
	public JPanel gamePanel;
	
	public JPanel undoPanel;

	JLabel player2Score;
	JLabel player1Score;

	public MancalaPit[][] pits; //pits to be drawn

	/**
	 * Constructor for the view
	 * @param model - model that takes in the number of stones
	 * @param style - boardStyle to draw
	 */
	public MancalaView(final MancalaModel model, BoardStyle style) {
		this.model = model;
		this.style = style;

		finalFrame = new JPanel();
		boardPanel = new JPanel();
		systemOut = new JPanel();
		textBox = new JTextArea(10, 50); //shows users whose turn it is

		model.attach(this);

		gamePanel = new JPanel(new GridLayout(2, 6, 15, 35));
		JPanel panel1 = new JPanel(new GridLayout(1, 6, 25, 35));
		JLabel mancalaA = new JLabel("Mancala A");
		JLabel mancalaB = new JLabel(" Mancala B");

		panel1.add(mancalaB);
		for (int i = 1; i <= 6; i++) {
			JLabel temp = new JLabel("           A" + i);
			panel1.add(temp);
		}
		panel1.add(mancalaA);

		JPanel panel2 = new JPanel(new GridLayout(1, 6, 35, 35));
		JLabel mancalaA2 = new JLabel("Mancala A");
		JLabel mancalaB2 = new JLabel(" Mancala B");

		panel2.add(mancalaB2);
		for (int i = 6; i >= 1; i--) {
			JLabel temp = new JLabel("           B" + i);
			panel2.add(temp);
		}
		panel2.add(mancalaA2);

		this.setTitle("Welcome to Mancala");
		finalFrame.setLayout(new BorderLayout());
		finalFrame.add(panel2, BorderLayout.NORTH);
		finalFrame.add(gamePanel, BorderLayout.CENTER);
		finalFrame.add(panel1, BorderLayout.SOUTH);

		pits = new MancalaPit[2][6];

		player1 = new MancalaPit("", style);
		player1.setStone(model.getArrayOfBothPlayers()[0]);
		player2 = new MancalaPit("", style);
		player2.setStone(model.getArrayOfBothPlayers()[1]);
		player1.setPreferredSize(new Dimension(100, 450));
		player2.setPreferredSize(new Dimension(100, 450));
		player2Score = new JLabel("" + model.getArrayOfBothPlayers()[1]);
		player1Score = new JLabel("" + model.getArrayOfBothPlayers()[0]);
		player2.add(player2Score);
		player1.add(player1Score);

		emptySpace = new JPanel();
		emptySpace.setSize(new Dimension(15, 450));

		JPanel player1Panel = new JPanel();
		player1Panel.setLayout(new FlowLayout());
		player1Panel.add(emptySpace);
		player1Panel.add(player1);

		JPanel player2Panel = new JPanel();
		player2Panel.setLayout(new FlowLayout());
		player2Panel.add(player2);
		player2Panel.add(emptySpace);

		drawBoard();
		
		player1label = new JLabel(
				"                                               Player 1 ------------>");
		player2label = new JLabel(
				"                                                <------------ Player 2");

		player1label.setFont(new Font("Arial", Font.BOLD, 18));
		player2label.setFont(new Font("Arial", Font.BOLD, 18));
		
		finalFrame.add(player1Panel, BorderLayout.EAST);

		finalFrame.add(player2Panel, BorderLayout.WEST);

		boardPanel.setLayout(new BorderLayout());

		updateGame();

		JButton undoButton = new JButton("UNDO");
		undoButton.addActionListener(new ActionListener(){ //undo button action listener

			@Override
			public void actionPerformed(ActionEvent e) {
				model.undo();
				
			}
			
		});
		JScrollPane scroll = new JScrollPane(textBox);
		textBox.setText("System Response: \n");
		textBox.append("Welcome to our game. It is Player 1's Turn! \n");
		textBox.setVisible(true);
		systemOut.setLayout(new BorderLayout());
		systemOut.add(scroll, BorderLayout.WEST);
		
		undoPanel = new JPanel();
		undoPanel.setLayout(new BorderLayout());
		undoPanel.add(undoButton, BorderLayout.NORTH);
		systemOut.add(undoPanel, BorderLayout.EAST);

		scroll.setBorder(BorderFactory.createLineBorder(Color.BLUE, 7));

		boardPanel.add(player2label, BorderLayout.NORTH);
		boardPanel.add(finalFrame, BorderLayout.CENTER);
		boardPanel.add(player1label, BorderLayout.SOUTH);
		finalFrame.setBorder(BorderFactory.createLineBorder(Color.BLACK, 12));

		this.setLayout(new BorderLayout());
		this.add(boardPanel, BorderLayout.CENTER);
		this.add(systemOut, BorderLayout.SOUTH);
		this.setSize(1000, 500);
		this.setVisible(true);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	/**
	 * Updates the game when changes are made
	 */
	private void updateGame() { //controller portion calls model to update

		for (int j = 5; j >= 0; j--) {
			int getStones = model.getPits()[1][j];
			pits[1][j].setText(String.valueOf(getStones));
			pits[1][j].setStone(getStones);
		}	
		for (int i = 0; i < 6; i++) {
			int getStones = model.getPits()[0][i];
			pits[0][i].setText(String.valueOf(getStones));
			pits[0][i].setStone(getStones);
		}

	}

	/**
	 * Initializes the board with the appropriate pits and stones
	 * for both players
	 */
	private void drawBoard() {  

		for (int i = 5; i >= 0; i--) { //draws player2's side
			final int d = i;
			int numberStones = model.getPits()[1][i];
			pits[1][i] = new MancalaPit("", style);
			pits[1][i].setStone(numberStones);

			pits[1][i].addActionListener(new ActionListener() { 
				@Override
				public void actionPerformed(ActionEvent e) {
					model.move(1, d); //controller portion calls model to move
				}
			});
			gamePanel.add(pits[1][i]);//adds the pits to the view
		}

		for (int j = 0; j < 6; j++) { //draws player1's side of board

			final int d = j;
			int numberStones = model.getPits()[0][j];

			pits[0][j] = new MancalaPit("", style);
			pits[0][j].setStone(numberStones);

			pits[0][j].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					model.move(0, d); //controller portion calls model to move
				}

			});
			gamePanel.add(pits[0][j]); //adds the pits to the view
		}

	}

	/**
	 * Updates the model as the view changes
	 */
	public void stateChanged(ChangeEvent e) {
		
		updateGame();

		int firstPlayer = model.getArrayOfBothPlayers()[0];
		int secondPlayer = model.getArrayOfBothPlayers()[1];

		textBox.append("Player 1: " + firstPlayer + "\n");
		textBox.append("Player 2: " + secondPlayer + "\n");

		player2Score.setText(Integer.toString(secondPlayer));
		player1Score.setText(Integer.toString(firstPlayer));
		player2.setStone(secondPlayer);
		player1.setStone(firstPlayer);

		int whoseTurn = model.getPlayer() + 1;
		
		JPanel undoCountPanel = new JPanel();
		JLabel undos = new JLabel("Undo's Used: ");
		JLabel player1undo = new JLabel("Player 1: " + model.getUndoCountPlayer1());
		JLabel player2undo = new JLabel("Player 2: " + model.getUndoCountPlayer2());
		undoCountPanel.setLayout(new BoxLayout(undoCountPanel, BoxLayout.Y_AXIS));
		
		undos.setFont(new Font("Arial", Font.BOLD, 14));
		
		player1undo.setFont(new Font("Arial", Font.BOLD, 14));
		player2undo.setFont(new Font("Arial", Font.BOLD, 14));
		
		undoCountPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
		undoCountPanel.add(undos);
		undoCountPanel.add(player1undo);
		undoCountPanel.add(player2undo);
		undoCountPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN.darker().brighter(), 5));
		undoPanel.add(undoCountPanel, BorderLayout.CENTER);
		textBox.append("Now, it is player " + whoseTurn + "'s turn!\n"); //keeps track of whose turn it is

	}

}
