import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class serves as the model portion of the MVC pattern
 * @author Marlowe DeVera, Amrit Sandhu, Shweta Sugnani
 *
 */

public class MancalaModel {

	private final int sixPits = 6; //number of pits for each player
	private final int twoPlayers = 2; //number of players
	
	ArrayList<ChangeListener> listeners; //listeners
	int currentPlayer; 
	int undoPlayer;
	
	int[][] pits; //creates a two dimensional array which first serving as number of players and second as number of pits
	int[][] undoPits;
	
	//for undo purposes
	public final int maximumUndos = 3;
	int player1undosCount;
	int player2undosCount;
	boolean undoPossible;
	boolean lastStoneDropped;
	
	int[] arrayOfbothPlayers; //stores both players
	int[] undoArrayOfBothPlayers;
	
	 
	/**
	 * Constructor for the model
	 * @param numberOfStones number of stones - option to choose from 3 or 4
	 */
	public MancalaModel(int numberOfStones) {
		
		listeners = new ArrayList<ChangeListener>();
		
		pits = new int[twoPlayers][sixPits];
		
		arrayOfbothPlayers = new int[twoPlayers];
		currentPlayer = 0; //default to first player's turn
		
		
		player1undosCount = 0;
		player2undosCount = 0;
		
		for (int player = 0; player < twoPlayers; player++) {
			for (int p = 0; p < sixPits; p++) {
				pits[player][p] = numberOfStones; //sets the number of stones in each pit
			}
		}
		undoPits = pits.clone();
		undoArrayOfBothPlayers = arrayOfbothPlayers.clone();
		lastStoneDropped = false;
		undoPossible = false;
		
		update(); 
	}

	/**
	 * Makes the move from the pit selected
	 * @param currentPlayerNumber - current player
	 * @param pitSelected - pit that is selected
	 */
	public void move(int currentPlayerNumber, int pitSelected) { //makes the move
		undoArrayOfBothPlayers = arrayOfbothPlayers.clone();
		undoPits = pits.clone();
		
		if (currentPlayerNumber != currentPlayer) { //if it is not the player's turn
			final JFrame dialog = new JFrame();
			dialog.setTitle("Whoops, it isn't your turn!"); //open error message and let them know
			JButton ok = new JButton("Okay, I got it!");
			ok.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					dialog.dispose(); //dispose after they click okay	
				}
			});
			dialog.add(ok);
			dialog.setSize(300, 50);
			dialog.setVisible(true);
			
			return;
		}
		
		if (pits[currentPlayerNumber][pitSelected] == 0){
			return;
		} //if an empty pit is clicked on then don't do anything

		undoArrayOfBothPlayers = arrayOfbothPlayers.clone();
		undoPits[0] = pits[0].clone();
		undoPits[1] = pits[1].clone();
		
		if (lastStoneDropped == true){
			if (currentPlayerNumber == 0){
			player1undosCount  = 0;}
			else{
			player2undosCount = 0;}
			lastStoneDropped = false;
		}
		else if (player1undosCount == 0 || player2undosCount == 0)
		{
			if (nextPerson(currentPlayerNumber) == 0){
				player1undosCount  = 0;}
			else{
				player2undosCount = 0;}
			undoPlayer = currentPlayerNumber;
		}
		
		
		undoPossible = true;
		int stonesInPitChosen = pits[currentPlayerNumber][pitSelected]; //move is pit number to move from
		pits[currentPlayerNumber][pitSelected] = 0; //set it to zero because all elements from pit are moved
		while (stonesInPitChosen > 0) { 
			pitSelected = pitSelected + 1; 

			if (pitSelected >= sixPits){ //if the last pit in a player's mancala is selected, reset it to zero for next
				pitSelected = 0; 
			}
			
			if (pitSelected == 0) {  //if it is reset to zero
				if (currentPlayerNumber == currentPlayer) {
					
					++arrayOfbothPlayers[currentPlayerNumber]; 
					--stonesInPitChosen;
					if (stonesInPitChosen <= 0) {
						
						isDone(currentPlayerNumber); //see whose turn it is
						lastStoneDropped = true;
						update();
						return;
					}
				}
				currentPlayerNumber = nextPerson(currentPlayerNumber);
			}
			++pits[currentPlayerNumber][pitSelected]; //increment the pits by 1 stone eacy
			--stonesInPitChosen; //otherwise, decrement
		}
		stoneInEmpty(currentPlayerNumber, pitSelected); //sees whether last stone dropped was in an empty pit
		update();
		isDone(currentPlayerNumber); //check if game is done
		isDone(nextPerson(currentPlayerNumber)); //check for other player

	}

	/**
	 * Moves to next player's turn
	 * @param currentPlayerNumber current player
	 * @return next player
	 */
	public int nextPerson(int currentPlayerNumber) {
		if (currentPlayerNumber == 0){
			currentPlayerNumber = 1;
		}
		else{
			currentPlayerNumber = 0;
		}
		
		return currentPlayerNumber;
	}

	/**
	 * If last stone dropped was in an empty pit, take that stone and opponent's stones
	 * @param currentPlayerNumber the current player
	 * @param lastPitIn
	 */
	public void stoneInEmpty(int currentPlayerNumber, int lastPitIn) {
		if (currentPlayerNumber == currentPlayer && pits[currentPlayerNumber][lastPitIn] == 1) {
			
			int nextPerson = this.nextPerson(currentPlayer);
			int oppositeNumMarbles = pits[nextPerson][5 - lastPitIn] + 1; //get opposite marbles
			pits[nextPerson][5 - lastPitIn] = 0; //reset your pit and opponent's opposite pit to zero
			pits[currentPlayerNumber][lastPitIn] = 0;
			arrayOfbothPlayers[currentPlayerNumber] += oppositeNumMarbles; //increment the player who got the marble's mancala

		}
		currentPlayer = nextPerson(currentPlayer);
	}

	/**
	 * check if game is done
	 * game is done when one of the sides is completely empty
	 * @param side side to check
	 * @return boolean true if it is, false otherwise
	 */
	public boolean isDone(int side) {
		int sumOne = 0;
		int sumTwo = 0;
		
		for(int i = 0; i < 6; i++)
		{
			sumOne += pits[0][i];
			sumTwo += pits[1][i];
		}
		if (sumOne == 0 || sumTwo == 0){
			gameOver();
			return true;
		}
		return false;
	}

	/**
	 * Game is over when one side is empty
	 * Moves remaining pits to the other mancala
	 * and displays final score
	 */
	public void gameOver() {
		int sumOne = 0;
		int sumTwo = 0;

	
		for (int i = 0; i < sixPits; i++) {
			sumOne += pits[0][i];
			sumTwo += pits[1][i];

			pits[0][i] = 0;
			pits[1][i] = 0;

		}

		arrayOfbothPlayers[0] += sumOne;
		arrayOfbothPlayers[1] += sumTwo;
		
		update();
		
		
		int firstScore = arrayOfbothPlayers[0];
		int secondScore = arrayOfbothPlayers[1];
		
		String whoWins = null;
		
		if (firstScore > secondScore){
			whoWins = "Player 1 wins!";
		}
		else if(secondScore > firstScore){
			whoWins = "Player 2 wins!";
		}
		else{
			whoWins = "Looks like it's a tie!";
		}
		
		final JFrame gameOver = new JFrame(); //window for when the game is over
		JLabel gameO = new JLabel("Game Over!" + whoWins);
		JLabel firstPersonScore = new JLabel("Player 1: " + firstScore);
		JLabel secondPersonScore = new JLabel("Player 2: " + secondScore);
		JButton again = new JButton("Cool! Let's play again!");
		again.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				InitialScreen playAgain = new InitialScreen();
				gameOver.dispose();
			}
			
		});
		gameOver.add(gameO);
		gameOver.add(firstPersonScore);
		gameOver.add(secondPersonScore);
		gameOver.add(again);
		gameOver.setLayout(new FlowLayout());
		gameOver.setSize(300, 300);
		gameOver.setVisible(true);

	}
	
	/**
	 * Undos a move to a previous state
	 */
	public void undo(){
			
		if (player1undosCount == maximumUndos || player2undosCount == maximumUndos){
			
			final JFrame undo = new JFrame("ALL UNDOS USED UP");
			JButton okaygot = new JButton("Okay, I got it!");
			okaygot.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					undo.dispose();
					
				}
				
			});
			JLabel undoLabel = new JLabel(" I'm sorry, you've used used up all your undos!!");
			
			undo.add(undoLabel, BorderLayout.NORTH);
			undo.add(okaygot, BorderLayout.SOUTH);
			undo.setSize(300, 100);
			
			undo.setVisible(true);
			return;
		}
			
		pits[0] = undoPits[0].clone();
		pits[1] = undoPits[1].clone();
		
		arrayOfbothPlayers = undoArrayOfBothPlayers.clone();
		currentPlayer = undoPlayer;
		
		if (undoPlayer == 0){
			player1undosCount++;
		}
		else if (undoPlayer ==1){
			player2undosCount++;
		}
		
		lastStoneDropped = false;
		undoArrayOfBothPlayers = arrayOfbothPlayers.clone();
		undoPits[0] = pits[0].clone();
		undoPits[1] = pits[1].clone();
		update();

	}
	
	/**
	 * Attaches listeners
	 * @param c
	 */
	public void attach(ChangeListener c) {
		listeners.add(c);
	}

	/**
	 * Updates the view
	 */
	public void update() {
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}

	/**
	 * Clones the pits and returns them
	 * @return cloned pits
	 */
	public int[][] getPits() {
		return pits.clone();
	}

	/**
	 * Clones the array of both players and returns them
	 * @return arrayOfbothPlayers
	 */
	public int[] getArrayOfBothPlayers() {
		return arrayOfbothPlayers.clone();
	}

	/**
	 * returns the current player
	 * @return current player - 0 or 1
	 */
	public int getPlayer() {
		return currentPlayer;
	}
	
	/**
	 * Gets the first player's undos used up
	 * @return undo count for first player
	 */
	public int getUndoCountPlayer1(){
		return player1undosCount;
	}
	
	/**
	 * Gets the second player's undos used up
	 * @return undo count for second player
	 */
	public int getUndoCountPlayer2(){
		return player2undosCount;
	}
	

}