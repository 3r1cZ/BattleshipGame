/*
 * Eric Zhou
 * 1/20/2022
 * MainMenu
 * Manages the Main Menu in the Battleship Game
 */

package battleship;

import java.awt.*;
import javax.swing.*;

public class MainMenu extends JPanel {

	Image ocean; //background image
	Image logo; //Battleship logo image
	
	//buttons to reach other panels
	JButton play = new JButton("<html><font size=6>Play<font><html>");
	JButton instructions = new JButton("<html><font size=6>Instructions<font><html>");

	public MainMenu(MainTesting main) {
		super();
		setLayout(null);
		//adding buttons
		play.setBounds(550, 470, 200, 100);
		play.addActionListener(main);
		add(play);
		instructions.setBounds(800, 470, 200, 100);
		instructions.addActionListener(main);
		add(instructions);
	}

	/*
	 * Drawing all graphics in the main menu panel
	 * pre: Graphics g
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Toolkit kit = Toolkit.getDefaultToolkit();
		ocean = kit.getImage("ocean1.jpg");
		logo = kit.getImage("logo.png");
		g.drawImage(ocean, 0, 0, 1600, 800, this);
		g.drawImage(logo, 380, 80, 800, 300, this);
	}

	/*
	 * Returns the value of the "Play" JButton
	 * post: The value of the "Play" JButton is returned
	 */
	public JButton getPlayButton() {
		return play;
	}
	
	/*
	 * Returns the value of the "Instructions" JButton
	 * post: The value of the "Instructions" JButton is returned
	 */
	public JButton getInstructionsButton() {
		return instructions;
	}

}
