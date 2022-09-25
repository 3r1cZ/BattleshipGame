/*
 * Eric Zhou
 * 1/20/2022
 * Combat
 * Manages the player turn and placing of the ships for the computer
 */

package battleship;

import java.awt.*;

public class Combat {

	// variables for computer ship placement
	private int randomX; // a random value for the computer ship x posiiton
	private int randomY; // a random value for the computer ship y position
	private int randomUpright; // a random value to determine if the computer ship should be upright
	boolean computerPlaced = false; // if the computer's ships have all been placed

	protected static int[][] grid = new int[10][10]; // computer grid containing numbers to determine if there is a ship

	// variables to highlight the computer grid
	int highlightX;
	int highlightY;

	IPanel panel; // IPanel object

	public Combat(IPanel panel) {
		this.panel = panel;
	}

	/*
	 * Creates locations to place the computer ships 
	 * pre: Ships ship, the ship that is being placed
	 */
	public void placeComputers(Ships ship) {
		if (panel.gameStart) {
			// generating random positions for the ships
			randomUpright = (int) (Math.random() * 2); // random orientation for the ship
			if (randomUpright == 0) { // ship is vertical
				randomX = (int) (Math.random() * 10 + 1);
				randomY = (int) (Math.random() * 5 + 1);
				ship.width = 50;
				ship.height = 50 * ship.holes;
				ship.upright = true;
			} else { // ship is horizontal
				randomX = (int) (Math.random() * 5 + 1);
				randomY = (int) (Math.random() * 10 + 1);
				ship.width = 50 * ship.holes;
				ship.height = 50;
				ship.upright = false;
			}
			ship.x = 51 * randomX + 850;
			ship.y = 51 * randomY + 100;

			// making sure the ships do not overlap
			for (int i = 1; i <= 10; i++) {
				for (int j = 1; j <= 10; j++) {
					// if a ship is on a square
					if (ship.x >= 51 * j + 850 && ship.x <= 51 * j + 900) {
						if (ship.y >= 51 * i + 100 && ship.y <= 51 * i + 150) {
							if (ship.upright) { // when ship is vertical
								if (i + ship.holes - 1 <= 10) { // if the ship fits on the grid
									for (int k = 0; k < ship.holes; k++) {
										if (grid[i - 1 + k][j - 1] == 1) { // if the spot is taken
											ship.shipPlaced = false;
											return; // exits method
										}
									}
									for (int k = 0; k < ship.holes; k++) { // marks the location as taken
										grid[i - 1 + k][j - 1] = 1;
										ship.shipPlaced = true; // ship has been placed
									}
								} else { // less than the space required to fit the ship
									ship.shipPlaced = false;
								}
							} else { // when ship is horizontal
								if (j + ship.holes - 1 <= 10) { // if the ship fits on the grid
									for (int k = 0; k < ship.holes; k++) {
										if (grid[i - 1][j - 1 + k] == 1) { // if the spot is taken
											ship.shipPlaced = false;
											return; // exits method
										}
									}
									for (int k = 0; k < ship.holes; k++) { // marks the location as taken
										grid[i - 1][j - 1 + k] = 1;
										ship.shipPlaced = true; // ship has been placed
									}
								} else { // less than the space required to fit the ship
									ship.shipPlaced = false;
								}
							}
						}
					}
				}
			}

		}
	}

	/*
	 * Highlights the rectangle that the user hovers over on the computer grid
	 */
	public void highlightComputer() {
		for (int i = 1; i <= 10; i++) {
			for (int j = 1; j <= 10; j++) {
				// if the mouse is on a square
				if ((int) MouseInfo.getPointerInfo().getLocation().getX() - 5 >= 51 * j + 850
						&& (int) MouseInfo.getPointerInfo().getLocation().getX() - 5 <= 51 * j + 900) {
					if ((int) MouseInfo.getPointerInfo().getLocation().getY() - 32 >= 51 * i + 100
							&& (int) MouseInfo.getPointerInfo().getLocation().getY() - 32 <= 51 * i + 150) {
						highlightX = 51 * j + 850;
						highlightY = 51 * i + 100;
						panel.highlightComputer = true;
						return;
					} else {
						panel.highlightComputer = false;
					}
				} else {
					panel.highlightComputer = false;
				}
			}
		}
	}

	/*
	 * Allows the user to choose a spot to hit on the computer grid
	 */
	public void playerTurn() {
		panel.lbl.setText("<html>Please choose a point to hit on the <br>Computer's Board</br></html>");
		for (int i = 1; i <= 10; i++) {
			for (int j = 1; j <= 10; j++) {
				// if the mouse is on a square
				if ((int) MouseInfo.getPointerInfo().getLocation().getX() - 5 >= 51 * j + 850
						&& (int) MouseInfo.getPointerInfo().getLocation().getX() - 5 <= 51 * j + 900) {
					if ((int) MouseInfo.getPointerInfo().getLocation().getY() - 32 >= 51 * i + 100
							&& (int) MouseInfo.getPointerInfo().getLocation().getY() - 32 <= 51 * i + 150) {
						if (grid[i - 1][j - 1] == 1) { // mouse is hitting a square with a ship
							panel.lbl.setText("You hit a ship!");
							panel.playerTurn = false;
							grid[i - 1][j - 1] = 3;
							//checks which ship is hit
							determineShip(panel.computerCarrier,
									(int) MouseInfo.getPointerInfo().getLocation().getX() - 5,
									(int) MouseInfo.getPointerInfo().getLocation().getY() - 32);
							determineShip(panel.computerBattleship,
									(int) MouseInfo.getPointerInfo().getLocation().getX() - 5,
									(int) MouseInfo.getPointerInfo().getLocation().getY() - 32);
							determineShip(panel.computerCruiser,
									(int) MouseInfo.getPointerInfo().getLocation().getX() - 5,
									(int) MouseInfo.getPointerInfo().getLocation().getY() - 32);
							determineShip(panel.computerDestroyer,
									(int) MouseInfo.getPointerInfo().getLocation().getX() - 5,
									(int) MouseInfo.getPointerInfo().getLocation().getY() - 32);
							determineShip(panel.computerPatrol,
									(int) MouseInfo.getPointerInfo().getLocation().getX() - 5,
									(int) MouseInfo.getPointerInfo().getLocation().getY() - 32);
						} else if (grid[i - 1][j - 1] == 0) { // mouse is hitting a square without a ship
							panel.lbl.setText("You hit nothing.");
							panel.playerTurn = false;
							grid[i - 1][j - 1] = 2;
						}
					}
				}
			}
		}
	}

	/*
	 * Determines which ship has been hit by the player 
	 * pre: Ships ship, the ship that is being checked, int mouseX, mouseY, x and y variables of the mouse
	 */
	public void determineShip(Ships ship, int mouseX, int mouseY) {
		// if the mouse is within the ship boundaries
		if (mouseX >= ship.x && mouseX <= (ship.x + ship.width)) {
			if (mouseY >= ship.y && mouseY <= (ship.y + ship.height)) {
				if (ship.count > 0) {
					ship.count--;
					if (ship.count == 0) { // ship has sunk
						ship.sunk = true;
						shipSunkStatements();
					}
				}
			}
		}

	}

	/*
	 * Displays statements if a ship has been sunk
	 */
	public void shipSunkStatements() {
		// computer ships sinking
		if (panel.computerCarrier.sunk) {
			panel.lbl.setText("The opponent's Carriership has sunk!");
			panel.computerCarrier.sunk = false;
			panel.numComputerShipsSunk++;
		}
		if (panel.computerBattleship.sunk) {
			panel.lbl.setText("The opponent's Battleship has sunk!");
			panel.computerBattleship.sunk = false;
			panel.numComputerShipsSunk++;
		}
		if (panel.computerCruiser.sunk) {
			panel.lbl.setText("The opponent's Cruiser has sunk!");
			panel.computerCruiser.sunk = false;
			panel.numComputerShipsSunk++;
		}
		if (panel.computerDestroyer.sunk) {
			panel.lbl.setText("The opponent's Destroyer has sunk!");
			panel.computerDestroyer.sunk = false;
			panel.numComputerShipsSunk++;
		}
		if (panel.computerPatrol.sunk) {
			panel.lbl.setText("The opponent's Patrol Boat has sunk!");
			panel.computerPatrol.sunk = false;
			panel.numComputerShipsSunk++;
		}

	}

	/*
	 * Resets the grid in the Combat class
	 */
	public void resetCombat() {
		computerPlaced = false;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				grid[i][j] = 0;
			}
		}
	}
}
