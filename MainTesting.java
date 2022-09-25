/*
 * Eric Zhou
 * 1/20/2022
 * MainTesting
 * Main class of the Battleship Game
 */

package battleship;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainTesting extends JFrame implements ActionListener {

	// different screens to be displayed
	MainMenu mainMenu;
	Instructions instructions;
	IPanel game;

	public MainTesting() {
		super("Battleship"); // title of the game
		mainMenu = new MainMenu(this);
		instructions = new Instructions(this);
		game = new IPanel(this);
		// Building the window
		setContentPane(mainMenu); // first screen to be displayed
		setSize(1600, 800);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
	}

	/*
	 * Changes the content pane to a new class when a button is clicked. 
	 * pre: ActionEvent e
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == mainMenu.getPlayButton()) { // play button in MainMenu class
			mainMenu.setVisible(false);
			setContentPane(game);
			revalidate();
		}
		if (e.getSource() == mainMenu.getInstructionsButton()) { // instructions button in MainMenu class
			setContentPane(instructions);
			instructions.setVisible(true);
			revalidate();
		}
		if (e.getSource() == instructions.getBackButton()) { // back button in Instructions class
			setContentPane(mainMenu);
			revalidate();
		}
		if (e.getSource() == instructions.getPlayButton()) { // play button in Instructions class
			instructions.setVisible(false);
			setContentPane(game);
			revalidate();
		}

	}

	public static void main(String[] args) {
		MainTesting frame = new MainTesting();

	}

}
