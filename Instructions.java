/*
 * Eric Zhou
 * 1/20/2022
 * Instructions
 * Manages the Instructions in the Battleship Game
 */

package battleship;

import java.awt.*;
import javax.swing.*;

public class Instructions extends JPanel {

	Image ocean; //background image
	Image howToPlayImage; //board image
	
	//buttons to reach other panels
	JButton back = new JButton("<html><font size=6>Back<font><html>");
	JButton play = new JButton("<html><font size=6>Play<font><html>");
	
	//instructions and other information
	JLabel instructions = new JLabel("<html><font size=20><U>Instructions</U></font><html>", SwingConstants.CENTER);
	JLabel howToPlay = new JLabel("<html><font size=7><U>How To Play:</U></font><br>"
			+ "<font size=5>1. First, you will get the option to place down 5 ships with different sizes on a 10x10 grid.</font><br>"
			+ "<font size=5>2. Next, you will choose spots on the opponent's grid to hit, and attempt to sink their ships.</font><br>"
			+ "<font size=5>3. After your turn, the opponent will choose a spot on your grid and attempt to sink your ships.</font><br>"
			+ "<font size=5>4. When a ship is sunk: When all of the boxes containing the ship are hit, the ship is considered 'sunk'.</font><br><br>"
			+ "<font size=5>How to <font color=green>WIN: </font>When all of the ships on the opponent's grid are sunk, you are declared the winner.</font><br><br>"
			+ "<font size=5>How to <font color=red>LOSE: </font>When all of the ships on your grid are sunk, the opponent is declared the winner.</font></html>");
	JLabel ships = new JLabel("<html><font size=7><U>Types Of Ships:</U></font><br><br>"
			+ "<font size=5 color=orange>Carriership:</font><br>"
			+ "<font size=4 color=black><p style='text-Indent: 20px'>Size: 5 squares</p></font><br>"
			+ "<font size=5 color=orange>Battleship:</font><br>"
			+ "<font size=4 color=black><p style='text-Indent: 20px'>Size: 4 squares</p></font><br>"
			+ "<font size=5 color=orange>Cruiser:</font><br>"
			+ "<font size=4 color=black><p style='text-Indent: 20px'>Size: 3 squares</p></font><br>"
			+ "<font size=5 color=orange>Destroyer:</font><br>"
			+ "<font size=4 color=black><p style='text-Indent: 20px'>Size: 3 squares</p></font><br>"
			+ "<font size=5 color=orange>Patrol Boat:</font><br>"
			+ "<font size=4 color=black><p style='text-Indent: 20px'>Size: 2 squares</p></font><br></html>");

	public Instructions(MainTesting main) {
		super();
		setLayout(null);
		//adding buttons and instructions
		back.setBounds(30, 650, 160, 80);
		back.setBackground(Color.GREEN);
		back.setForeground(Color.BLACK);
		back.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		back.addActionListener(main);
		add(back);
		play.setBounds(1340, 650, 160, 80);
		play.setBackground(Color.GREEN);
		play.setForeground(Color.BLACK);
		play.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		play.addActionListener(main);
		add(play);
		instructions.setBounds(215, 60, 1100, 50);
		instructions.setForeground(Color.BLACK);
		add(instructions);
		howToPlay.setBounds(230, 0, 1100, 400);
		howToPlay.setForeground(Color.BLACK);
		add(howToPlay);
		ships.setBounds(230, 300, 1100, 400);
		ships.setForeground(Color.BLACK);
		add(ships);
	}

	/*
	 * Drawing all graphics in the instructions panel
	 * pre: Graphics g
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Toolkit kit = Toolkit.getDefaultToolkit();
		ocean = kit.getImage("ocean1.jpg");
		howToPlayImage = kit.getImage("howToPlay.png");
		g2.drawImage(ocean, 0, 0, this);
		// background for instructions label
		g2.setColor(Color.WHITE);
		g2.fillRect(215, 50, 1100, 680);
		g2.setColor(Color.BLACK);
		g2.drawRect(215, 50, 1100, 680);
		g2.drawImage(howToPlayImage, 650, 370, 600, 300, this);
	}

	/*
	 * Returns the value of the "Back" JButton
	 * post: The value of the "Back" JButton is returned
	 */
	public JButton getBackButton() {
		return back;
	}
	
	/*
	 * Returns the value of the "Play" JButton
	 * post: The value of the "Play" JButton is returned
	 */
	public JButton getPlayButton() {
		return play;
	}
}
