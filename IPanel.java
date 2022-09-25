/*
 * Eric Zhou
 * 1/20/2022
 * IPanel
 * Draws all of the graphics and manages all game-related listeners
 */

package battleship;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class IPanel extends JPanel implements MouseListener, ActionListener {

	// ship images
	Image carrierImage;
	Image carrierRotated;
	Image battleshipImage;
	Image battleshipRotated;
	Image cruiserImage;
	Image cruiserRotated;
	Image destroyerImage;
	Image destroyerRotated;
	Image patrolImage;
	Image patrolRotated;

	Image ocean; // background image
	Image explosion; // image to display when a ship is hit
	Image waterPuddle; // image to display when nothing is hit

	protected static boolean drag = false; // if the ship is being moved
	private boolean isTrue = false; // if the player name has been entered
	protected static boolean gameStart = false; // if the game has been started after placing the ships
	protected static boolean playerTurn = false; // if it is the player's turn
	protected static boolean gameWin = false; // if the player has won the game
	protected static boolean gameLose = false; // if the player has lost the game

	// variables for highlighting grids
	protected static boolean highlight = false; // highlights player grid
	protected static boolean highlightVertical = false; // highlights vertical ship
	protected static boolean highlightHorizontal = false; // highlights horizontal ship
	protected static boolean highlightComputer = false; // highlights computer grid

	int numShipsSunk = 0; // ships sunk on player board
	int numComputerShipsSunk = 0; // ships sunk on computer board

	// user input
	JButton player = new JButton("Submit"); // button to submit name
	JLabel question = new JLabel("<html><U>What is your name?</U></html>");
	JTextField answer = new JTextField(10); // input for user name

	// end game
	JButton menu = new JButton("Main Menu");

	// visuals for game panel
	JLabel playerName = new JLabel("", SwingConstants.CENTER); // name above player's game board
	JLabel computerName = new JLabel("<html><font size=20 color=red><U>Computer's Board</U></font><html>"); // name above computer's game board
	JLabel lbl = new JLabel(
			"<html>NOTE:<br>Please place your ships.<br><font color=orange>Tip: Right-click to rotate</font><html>",
			SwingConstants.CENTER); // instructions and tips throughout the game screen
	JButton start = new JButton("START"); // starts the game after placing the ships

	// Player Ships objects
	Ships carriership = new Ships("Carrier", 750, 150, 40, 120, 5);
	Ships battleship = new Ships("Battleship", 750, 300, 40, 90, 4);
	Ships cruiser = new Ships("Cruiser", 750, 420, 40, 60, 3);
	Ships destroyer = new Ships("Destroyer", 750, 515, 40, 60, 3);
	Ships patrol = new Ships("Patrol", 750, 620, 40, 30, 2);
	// Computer Ships objects
	Ships computerCarrier = new Ships("Carrier", 0, 0, 40, 120, 5);
	Ships computerBattleship = new Ships("Battleship", 0, 0, 40, 90, 4);
	Ships computerCruiser = new Ships("Cruiser", 0, 0, 40, 60, 3);
	Ships computerDestroyer = new Ships("Destroyer", 0, 0, 40, 60, 3);
	Ships computerPatrol = new Ships("Patrol", 0, 0, 40, 30, 2);

	// Creating objects of other classes
	Combat combat = new Combat(this);
	AI ai;
	Setup setup = new Setup(this);
	MainTesting main;

	public IPanel(MainTesting main) {
		this.main = main;
		setLayout(null);
		addMouseListener(this); // adding a mouseListener to the panel

		// visuals for game panel
		lbl.setBounds(663, 680, 237, 70);
		lbl.setForeground(Color.BLACK);
		add(lbl);
		lbl.setVisible(false);
		start.setBounds(740, 630, 80, 40);
		start.addActionListener(this);
		add(start);
		start.setVisible(false);
		playerName.setBounds(150, 50, 500, 100);
		add(playerName);
		playerName.setVisible(false);
		computerName.setBounds(1000, 50, 500, 100);
		add(computerName);
		computerName.setVisible(false);
	}

	/*
	 * Drawing all graphics in the game panel
	 * pre: Graphics g
	 */
	public void paintComponent(Graphics g) {

		if (!isTrue) { // if user input has not been entered (start of game)
			question.setBounds(650, 200, 150, 50);
			answer.setBounds(650, 270, 150, 40);
			player.setBounds(850, 270, 80, 40);
			player.addActionListener(this);
			add(question);
			add(answer);
			add(player);
		} else { // if user input has been entered
			Graphics2D g2 = (Graphics2D) g;
			// getting the images
			Toolkit kit = Toolkit.getDefaultToolkit();
			ocean = kit.getImage("ocean1.jpg");
			carrierImage = kit.getImage("carrier1.png");
			carrierRotated = kit.getImage("carrier1_rotate.png");
			battleshipImage = kit.getImage("battleship1.png");
			battleshipRotated = kit.getImage("battleship1_rotate.png");
			cruiserImage = kit.getImage("cruiser1.png");
			cruiserRotated = kit.getImage("cruiser1_rotate.png");
			destroyerImage = kit.getImage("destroyer1.png");
			destroyerRotated = kit.getImage("destroyer1_rotate.png");
			patrolImage = kit.getImage("patrol1.png");
			patrolRotated = kit.getImage("patrol1_rotate.png");
			explosion = kit.getImage("explosion1.png");
			waterPuddle = kit.getImage("waterPuddle1.png");

			g2.drawImage(ocean, 0, 0, this); // drawing background image
			// drawing background for label
			g2.setColor(Color.WHITE);
			g2.fillRect(663, 680, 237, 70);
			g2.setColor(Color.BLACK);
			g2.drawRect(663, 680, 237, 70);

			// player grid
			for (int i = 1; i <= 10; i++) {
				for (int j = 1; j <= 10; j++) {
					g2.setStroke(new BasicStroke(1));
					g2.setColor(Color.BLUE);
					g2.fillRect(51 * j + 100, 51 * i + 100, 50, 50);
					g2.setColor(Color.BLACK);
					g2.drawRect(51 * j + 100, 51 * i + 100, 50, 50);
				}
			}
			// computer grid
			for (int i = 1; i <= 10; i++) {
				for (int j = 1; j <= 10; j++) {
					g2.setStroke(new BasicStroke(1));
					g2.setColor(Color.RED);
					g2.fillRect(51 * j + 850, 51 * i + 100, 50, 50);
					g2.setColor(Color.BLACK);
					g2.drawRect(51 * j + 850, 51 * i + 100, 50, 50);
				}
			}

			// if the user is not clicking a ship
			if (!drag) {
				if (carriership.upright) { // drawing vertical ship
					highlightVertical = false;
					g2.drawImage(carrierImage, carriership.x, carriership.y, carriership.width, carriership.height,
							this);
				} else { // drawing horizontal ship
					highlightHorizontal = false;
					g2.drawImage(carrierRotated, carriership.x, carriership.y, carriership.width, carriership.height,
							this);
				}
				if (battleship.upright) { // drawing vertical ship
					highlightVertical = false;
					g2.drawImage(battleshipImage, battleship.x, battleship.y, battleship.width, battleship.height,
							this);
				} else { // drawing horizontal ship
					highlightHorizontal = false;
					g2.drawImage(battleshipRotated, battleship.x, battleship.y, battleship.width, battleship.height,
							this);
				}
				if (cruiser.upright) { // drawing vertical ship
					highlightVertical = false;
					g2.drawImage(cruiserImage, cruiser.x, cruiser.y, cruiser.width, cruiser.height, this);
				} else { // drawing horizontal ship
					highlightHorizontal = false;
					g2.drawImage(cruiserRotated, cruiser.x, cruiser.y, cruiser.width, cruiser.height, this);
				}
				if (destroyer.upright) { // drawing vertical ship
					highlightVertical = false;
					g2.drawImage(destroyerImage, destroyer.x, destroyer.y, destroyer.width, destroyer.height, this);
				} else { // drawing horizontal ship
					highlightHorizontal = false;
					g2.drawImage(destroyerRotated, destroyer.x, destroyer.y, destroyer.width, destroyer.height, this);
				}
				if (patrol.upright) { // drawing vertical ship
					highlightVertical = false;
					g2.drawImage(patrolImage, patrol.x, patrol.y, patrol.width, patrol.height, this);
				} else { // drawing horizontal ship
					highlightHorizontal = false;
					g2.drawImage(patrolRotated, patrol.x, patrol.y, patrol.width, patrol.height, this);
				}

				// highlights the rectangle that the user hovers over (player grid)
				setup.highlightGrid();
				if (highlight) {
					g2.setColor(Color.WHITE);
					g2.setStroke(new BasicStroke(5));
					g2.drawRect(setup.highlightX, setup.highlightY, 50, 50);
				}

				// placing the ships when they are on the grid
				if (!carriership.shipPlaced)
					setup.isLegal(carriership);
				if (!battleship.shipPlaced)
					setup.isLegal(battleship);
				if (!cruiser.shipPlaced)
					setup.isLegal(cruiser);
				if (!destroyer.shipPlaced)
					setup.isLegal(destroyer);
				if (!patrol.shipPlaced)
					setup.isLegal(patrol);

				// when a ship is placed outside of the grid
				setup.outsideGrid(carriership);
				setup.outsideGrid(battleship);
				setup.outsideGrid(cruiser);
				setup.outsideGrid(destroyer);
				setup.outsideGrid(patrol);

				// resetting ship location
				setup.isInvalid(carriership);
				setup.isInvalid(battleship);
				setup.isInvalid(cruiser);
				setup.isInvalid(destroyer);
				setup.isInvalid(patrol);

				// when all of the ships are placed down
				if (carriership.isValid && battleship.isValid && cruiser.isValid && destroyer.isValid && patrol.isValid
						&& !gameStart) {
					start.setVisible(true); //game starts
					lbl.setText("Click the button to start the game!");
				}

			} else { // if a ship has been clicked
				if (carriership.isActive) { // ensures the other ships are drawn when the carriership is clicked
					start.setVisible(false);
					lbl.setText(
							"<html>NOTE:<br>Please place your ships.<br><font color=orange>Tip: Right-click to rotate</font><html>");
					if (battleship.upright) { // drawing vertical ship
						g2.drawImage(battleshipImage, battleship.x, battleship.y, battleship.width, battleship.height,
								this);
					} else { // drawing horizontal ship
						g2.drawImage(battleshipRotated, battleship.x, battleship.y, battleship.width, battleship.height,
								this);
					}
					if (cruiser.upright) { // drawing vertical ship
						g2.drawImage(cruiserImage, cruiser.x, cruiser.y, cruiser.width, cruiser.height, this);
					} else { // drawing horizontal ship
						g2.drawImage(cruiserRotated, cruiser.x, cruiser.y, cruiser.width, cruiser.height, this);
					}
					if (destroyer.upright) { // drawing vertical ship
						g2.drawImage(destroyerImage, destroyer.x, destroyer.y, destroyer.width, destroyer.height, this);
					} else { // drawing horizontal ship
						g2.drawImage(destroyerRotated, destroyer.x, destroyer.y, destroyer.width, destroyer.height,
								this);
					}
					if (patrol.upright) { // drawing vertical ship
						g2.drawImage(patrolImage, patrol.x, patrol.y, patrol.width, patrol.height, this);
					} else { // drawing horizontal ship
						g2.drawImage(patrolRotated, patrol.x, patrol.y, patrol.width, patrol.height, this);
					}
					setup.highlightShip(carriership); // highlighting the grid when the ship is hovered over it
					if (highlightVertical) {
						if (setup.shipHighlightY + carriership.holes - 1 <= 10) {
							for (int k = 0; k < carriership.holes; k++) { // number of spaces the ship takes up
								g2.setColor(Color.WHITE);
								g2.setStroke(new BasicStroke(5));
								g2.drawRect(setup.shipHighlightX, 51 * (setup.shipHighlightY + k) + 100, 50, 50);
							}
						}
					}
					if (highlightHorizontal) {
						if (setup.shipHighlightX + carriership.holes - 1 <= 10) {
							for (int k = 0; k < carriership.holes; k++) { // number of spaces the ship takes up
								g2.setColor(Color.WHITE);
								g2.setStroke(new BasicStroke(5));
								g2.drawRect(51 * (setup.shipHighlightX + k) + 100, setup.shipHighlightY, 50, 50);
							}
						}
					}
				}
				if (battleship.isActive) { // ensures the other ships are drawn when the battleship is clicked
					start.setVisible(false);
					lbl.setText(
							"<html>NOTE:<br>Please place your ships.<br><font color=orange>Tip: Right-click to rotate</font><html>");
					if (carriership.upright) { // drawing vertical ship
						g2.drawImage(carrierImage, carriership.x, carriership.y, carriership.width, carriership.height,
								this);
					} else { // drawing horizontal ship
						g2.drawImage(carrierRotated, carriership.x, carriership.y, carriership.width,
								carriership.height, this);
					}
					if (cruiser.upright) { // drawing vertical ship
						g2.drawImage(cruiserImage, cruiser.x, cruiser.y, cruiser.width, cruiser.height, this);
					} else { // drawing horizontal ship
						g2.drawImage(cruiserRotated, cruiser.x, cruiser.y, cruiser.width, cruiser.height, this);
					}
					if (destroyer.upright) { // drawing vertical ship
						g2.drawImage(destroyerImage, destroyer.x, destroyer.y, destroyer.width, destroyer.height, this);
					} else { // drawing horizontal ship
						g2.drawImage(destroyerRotated, destroyer.x, destroyer.y, destroyer.width, destroyer.height,
								this);
					}
					if (patrol.upright) { // drawing vertical ship
						g2.drawImage(patrolImage, patrol.x, patrol.y, patrol.width, patrol.height, this);
					} else { // drawing horizontal ship
						g2.drawImage(patrolRotated, patrol.x, patrol.y, patrol.width, patrol.height, this);
					}
					setup.highlightShip(battleship); // highlighting the grid when the ship is hovered over it
					if (highlightVertical) {
						if (setup.shipHighlightY + battleship.holes - 1 <= 10) {
							for (int k = 0; k < battleship.holes; k++) { // number of spaces the ship takes up
								g2.setColor(Color.WHITE);
								g2.setStroke(new BasicStroke(5));
								g2.drawRect(setup.shipHighlightX, 51 * (setup.shipHighlightY + k) + 100, 50, 50);
							}
						}
					}
					if (highlightHorizontal) {
						if (setup.shipHighlightX + battleship.holes - 1 <= 10) {
							for (int k = 0; k < battleship.holes; k++) { // number of spaces the ship takes up
								g2.setColor(Color.WHITE);
								g2.setStroke(new BasicStroke(5));
								g2.drawRect(51 * (setup.shipHighlightX + k) + 100, setup.shipHighlightY, 50, 50);
							}
						}
					}
				}
				if (cruiser.isActive) { // ensures the other ships are drawn when the cruiser is clicked
					start.setVisible(false);
					lbl.setText(
							"<html>NOTE:<br>Please place your ships.<br><font color=orange>Tip: Right-click to rotate</font><html>");
					if (carriership.upright) { // drawing vertical ship
						g2.drawImage(carrierImage, carriership.x, carriership.y, carriership.width, carriership.height,
								this);
					} else { // drawing horizontal ship
						g2.drawImage(carrierRotated, carriership.x, carriership.y, carriership.width,
								carriership.height, this);
					}
					if (battleship.upright) { // drawing vertical ship
						g2.drawImage(battleshipImage, battleship.x, battleship.y, battleship.width, battleship.height,
								this);
					} else { // drawing horizontal ship
						g2.drawImage(battleshipRotated, battleship.x, battleship.y, battleship.width, battleship.height,
								this);
					}
					if (destroyer.upright) { // drawing vertical ship
						g2.drawImage(destroyerImage, destroyer.x, destroyer.y, destroyer.width, destroyer.height, this);
					} else { // drawing horizontal ship
						g2.drawImage(destroyerRotated, destroyer.x, destroyer.y, destroyer.width, destroyer.height,
								this);
					}
					if (patrol.upright) { // drawing vertical ship
						g2.drawImage(patrolImage, patrol.x, patrol.y, patrol.width, patrol.height, this);
					} else { // drawing horizontal ship
						g2.drawImage(patrolRotated, patrol.x, patrol.y, patrol.width, patrol.height, this);
					}
					setup.highlightShip(cruiser); // highlighting the grid when the ship is hovered over it
					if (highlightVertical) {
						if (setup.shipHighlightY + cruiser.holes - 1 <= 10) {
							for (int k = 0; k < cruiser.holes; k++) { // number of spaces the ship takes up
								g2.setColor(Color.WHITE);
								g2.setStroke(new BasicStroke(5));
								g2.drawRect(setup.shipHighlightX, 51 * (setup.shipHighlightY + k) + 100, 50, 50);
							}
						}
					}
					if (highlightHorizontal) {
						if (setup.shipHighlightX + cruiser.holes - 1 <= 10) {
							for (int k = 0; k < cruiser.holes; k++) { // number of spaces the ship takes up
								g2.setColor(Color.WHITE);
								g2.setStroke(new BasicStroke(5));
								g2.drawRect(51 * (setup.shipHighlightX + k) + 100, setup.shipHighlightY, 50, 50);
							}
						}
					}
				}
				if (destroyer.isActive) { // ensures the other ships are drawn when the destroyer is clicked
					start.setVisible(false);
					lbl.setText(
							"<html>NOTE:<br>Please place your ships.<br><font color=orange>Tip: Right-click to rotate</font><html>");
					if (carriership.upright) { // drawing vertical ship
						g2.drawImage(carrierImage, carriership.x, carriership.y, carriership.width, carriership.height,
								this);
					} else { // drawing horizontal ship
						g2.drawImage(carrierRotated, carriership.x, carriership.y, carriership.width,
								carriership.height, this);
					}
					if (battleship.upright) { // drawing vertical ship
						g2.drawImage(battleshipImage, battleship.x, battleship.y, battleship.width, battleship.height,
								this);
					} else { // drawing horizontal ship
						g2.drawImage(battleshipRotated, battleship.x, battleship.y, battleship.width, battleship.height,
								this);
					}
					if (cruiser.upright) { // drawing vertical ship
						g2.drawImage(cruiserImage, cruiser.x, cruiser.y, cruiser.width, cruiser.height, this);
					} else { // drawing horizontal ship
						g2.drawImage(cruiserRotated, cruiser.x, cruiser.y, cruiser.width, cruiser.height, this);
					}
					if (patrol.upright) { // drawing vertical ship
						g2.drawImage(patrolImage, patrol.x, patrol.y, patrol.width, patrol.height, this);
					} else { // drawing horizontal ship
						g2.drawImage(patrolRotated, patrol.x, patrol.y, patrol.width, patrol.height, this);
					}
					setup.highlightShip(destroyer); // highlighting the grid when the ship is hovered over it
					if (highlightVertical) {
						if (setup.shipHighlightY + destroyer.holes - 1 <= 10) {
							for (int k = 0; k < destroyer.holes; k++) { // number of spaces the ship takes up
								g2.setColor(Color.WHITE);
								g2.setStroke(new BasicStroke(5));
								g2.drawRect(setup.shipHighlightX, 51 * (setup.shipHighlightY + k) + 100, 50, 50);
							}
						}
					}
					if (highlightHorizontal) {
						if (setup.shipHighlightX + destroyer.holes - 1 <= 10) {
							for (int k = 0; k < destroyer.holes; k++) { // number of spaces the ship takes up
								g2.setColor(Color.WHITE);
								g2.setStroke(new BasicStroke(5));
								g2.drawRect(51 * (setup.shipHighlightX + k) + 100, setup.shipHighlightY, 50, 50);
							}
						}
					}
				}
				if (patrol.isActive) { // ensures the other ships are drawn when the patrol boat is clicked
					start.setVisible(false);
					lbl.setText(
							"<html>NOTE:<br>Please place your ships.<br><font color=orange>Tip: Right-click to rotate</font><html>");
					if (carriership.upright) { // drawing vertical ship
						g2.drawImage(carrierImage, carriership.x, carriership.y, carriership.width, carriership.height,
								this);
					} else { // drawing horizontal ship
						g2.drawImage(carrierRotated, carriership.x, carriership.y, carriership.width,
								carriership.height, this);
					}
					if (battleship.upright) { // drawing vertical ship
						g2.drawImage(battleshipImage, battleship.x, battleship.y, battleship.width, battleship.height,
								this);
					} else { // drawing horizontal ship
						g2.drawImage(battleshipRotated, battleship.x, battleship.y, battleship.width, battleship.height,
								this);
					}
					if (cruiser.upright) { // drawing vertical ship
						g2.drawImage(cruiserImage, cruiser.x, cruiser.y, cruiser.width, cruiser.height, this);
					} else { // drawing horizontal ship
						g2.drawImage(cruiserRotated, cruiser.x, cruiser.y, cruiser.width, cruiser.height, this);
					}
					if (destroyer.upright) { // drawing vertical ship
						g2.drawImage(destroyerImage, destroyer.x, destroyer.y, destroyer.width, destroyer.height, this);
					} else { // drawing horizontal ship
						g2.drawImage(destroyerRotated, destroyer.x, destroyer.y, destroyer.width, destroyer.height,
								this);
					}
					setup.highlightShip(patrol); // highlighting the grid when the ship is hovered over it
					if (highlightVertical) {
						if (setup.shipHighlightY + patrol.holes - 1 <= 10) {
							for (int k = 0; k < patrol.holes; k++) { // number of spaces the ship takes up
								g2.setColor(Color.WHITE);
								g2.setStroke(new BasicStroke(5));
								g2.drawRect(setup.shipHighlightX, 51 * (setup.shipHighlightY + k) + 100, 50, 50);
							}
						}
					}
					if (highlightHorizontal) {
						if (setup.shipHighlightX + patrol.holes - 1 <= 10) {
							for (int k = 0; k < patrol.holes; k++) { // number of spaces the ship takes up
								g2.setColor(Color.WHITE);
								g2.setStroke(new BasicStroke(5));
								g2.drawRect(51 * (setup.shipHighlightX + k) + 100, setup.shipHighlightY, 50, 50);
							}
						}
					}
				}
			}

			// placing computer ships
			if (!computerCarrier.shipPlaced) {
				combat.placeComputers(computerCarrier);
			}
			if (!computerBattleship.shipPlaced) {
				combat.placeComputers(computerBattleship);
			}
			if (!computerCruiser.shipPlaced) {
				combat.placeComputers(computerCruiser);
			}
			if (!computerDestroyer.shipPlaced) {
				combat.placeComputers(computerDestroyer);
			}
			if (!computerPatrol.shipPlaced) {
				combat.placeComputers(computerPatrol);
			}

			// drawing the computer ships
			if (gameStart) {
				if (computerCarrier.upright) {
					g2.drawImage(carrierImage, computerCarrier.x, computerCarrier.y, computerCarrier.width,
							computerCarrier.height, this);
				} else {
					g2.drawImage(carrierRotated, computerCarrier.x, computerCarrier.y, computerCarrier.width,
							computerCarrier.height, this);
				}
				if (computerBattleship.upright) {
					g2.drawImage(battleshipImage, computerBattleship.x, computerBattleship.y, computerBattleship.width,
							computerBattleship.height, this);
				} else {
					g2.drawImage(battleshipRotated, computerBattleship.x, computerBattleship.y,
							computerBattleship.width, computerBattleship.height, this);
				}
				if (computerCruiser.upright) {
					g2.drawImage(cruiserImage, computerCruiser.x, computerCruiser.y, computerCruiser.width,
							computerCruiser.height, this);
				} else {
					g2.drawImage(cruiserRotated, computerCruiser.x, computerCruiser.y, computerCruiser.width,
							computerCruiser.height, this);
				}
				if (computerDestroyer.upright) {
					g2.drawImage(destroyerImage, computerDestroyer.x, computerDestroyer.y, computerDestroyer.width,
							computerDestroyer.height, this);
				} else {
					g2.drawImage(destroyerRotated, computerDestroyer.x, computerDestroyer.y, computerDestroyer.width,
							computerDestroyer.height, this);
				}
				if (computerPatrol.upright) {
					g2.drawImage(patrolImage, computerPatrol.x, computerPatrol.y, computerPatrol.width,
							computerPatrol.height, this);
				} else {
					g2.drawImage(patrolRotated, computerPatrol.x, computerPatrol.y, computerPatrol.width,
							computerPatrol.height, this);
				}
			}

			// if it is currently the player's turn
			if (playerTurn) {
				combat.highlightComputer(); // highlighting computer grid
				if (highlightComputer) {
					g2.setColor(Color.WHITE);
					g2.setStroke(new BasicStroke(5));
					g2.drawRect(combat.highlightX, combat.highlightY, 50, 50);
				}
			}

			// drawing shots for the player
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					if (combat.grid[i][j] == 2) { // if miss
						g2.drawImage(waterPuddle, 51 * (j + 1) + 850, 51 * (i + 1) + 100, 50, 50, this);
					}
					if (combat.grid[i][j] == 3 || combat.grid[i][j] == 4) { // if hit
						g2.drawImage(explosion, 51 * (j + 1) + 850, 51 * (i + 1) + 100, 50, 50, this);
					}

				}
			}

			// drawing shots for the computer
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					if (setup.grid[i][j] == 2) { // if miss
						g2.drawImage(waterPuddle, 51 * (j + 1) + 100, 51 * (i + 1) + 100, 50, 50, this);
					}
					if (setup.grid[i][j] == 3 || setup.grid[i][j] == 4) { // if hit
						g2.drawImage(explosion, 51 * (j + 1) + 100, 51 * (i + 1) + 100, 50, 50, this);
					}
				}
			}

			if (numShipsSunk == 5) { // computer sinks all of the player ships
				gameLose = true;
			}
			if (numComputerShipsSunk == 5) { // player sinks all of the computer ships
				gameWin = true;
			}

			// player wins
			if (gameWin) {
				menu.setBounds(725, 600, 120, 60);
				menu.addActionListener(this);
				add(menu);
				lbl.setText("YOU WIN!");
			}
			// player loses
			if (gameLose) {
				menu.setBounds(725, 600, 120, 60);
				menu.addActionListener(this);
				add(menu);
				lbl.setText("YOU LOSE!");
			}
			repaint();
		}

	}

	/*
	 * Resets all variables in the IPanel class
	 */
	public void resetGame() {
		drag = false;
		gameStart = false;
		playerTurn = false;
		gameWin = false;
		gameLose = false;
		highlight = false;
		highlightVertical = false;
		highlightHorizontal = false;
		highlightComputer = false;
		numShipsSunk = 0;
		numComputerShipsSunk = 0;
		lbl.setText(
				"<html>NOTE:<br>Please place your ships.<br><font color=orange>Tip: Right-click to rotate</font><html>");
	}

	/*
	 * Changes the orientation of the ship after the mouse has been right-clicked
	 * pre: MouseEvent e
	 */
	public void mouseClicked(MouseEvent e) {
		// if the mouse is right-clicked
		if (e.getButton() == MouseEvent.BUTTON3) {
			if (!gameStart) {
				if (drag) {
					// changes the orientation of the ship
					setup.shipRotate(carriership);
					setup.shipRotate(battleship);
					setup.shipRotate(cruiser);
					setup.shipRotate(destroyer);
					setup.shipRotate(patrol);
				}
			}
		}
	}

	/*
	 * Allows user input on the grid and ships when the mouse is left-clicked
	 * pre: MouseEvent e
	 */
	public void mousePressed(MouseEvent e) {
		// if the mouse is left-clicked
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (!gameStart) {
				if (drag) { // changing x and y of the ship to the current location
					drag = false;
					setup.shipRearrange(carriership);
					setup.shipRearrange(battleship);
					setup.shipRearrange(cruiser);
					setup.shipRearrange(destroyer);
					setup.shipRearrange(patrol);
				} else { // if the mouse is within the ship bounds, it will be redrawn
					setup.shipClicked(carriership);
					setup.shipClicked(battleship);
					setup.shipClicked(cruiser);
					setup.shipClicked(destroyer);
					setup.shipClicked(patrol);
				}
			}
			if (gameStart && playerTurn && !gameWin && !gameLose) {
				combat.playerTurn(); // allows player to choose a spot to hit
			}
		}
	}

	// The mouseReleased, mouseEntered, and mouseExited methods must be implemented in order for the MouseListener to work

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	/*
	 * Button listener that changes the value of variables after a button is clicked
	 * pre: ActionEvent e
	 */
	public void actionPerformed(ActionEvent e) {
		// player submits their name
		if (e.getSource() == player) {
			answer.setVisible(false);
			question.setVisible(false);
			player.setVisible(false);
			playerName.setText("<html><font size=20 color=red><U>" + answer.getText() + "'s Board</U></font></html>");
			isTrue = true;
			lbl.setVisible(true);
			playerName.setVisible(true);
			computerName.setVisible(true);
		}
		// starts combat
		if (e.getSource() == start) {
			gameStart = true;
			playerTurn = true;
			ai = new AI(this); // runs the AI
			start.setVisible(false);
			lbl.setText("<html>Please choose a point to hit on the <br>Computer's Board</html>");
		}
		// back to main menu
		if (e.getSource() == menu) {
			main.setContentPane(main.mainMenu);
			main.mainMenu.setVisible(true);
			carriership.resetShips();
			battleship.resetShips();
			cruiser.resetShips();
			destroyer.resetShips();
			patrol.resetShips();
			computerCarrier.resetShips();
			computerBattleship.resetShips();
			computerCruiser.resetShips();
			computerDestroyer.resetShips();
			computerPatrol.resetShips();
			setup.resetSetup();
			combat.resetCombat();
			resetGame();
			remove(menu);
		}

	}

}