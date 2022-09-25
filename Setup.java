/*
 * Eric Zhou
 * 1/20/2022
 * Setup
 * Manages the placing of the ships for the player
 */

package battleship;

import java.awt.MouseInfo;

public class Setup {

	IPanel panel; // IPanel object

	// variables for drawing the highlights on the grid
	int highlightX;
	int highlightY;
	int shipHighlightX;
	int shipHighlightY;

	protected static int[][] grid = new int[10][10]; // player grid containing numbers to determine if there is a ship

	public Setup(IPanel panel) {
		this.panel = panel;
	}

	/*
	 * Highlights the rectangle that the user hovers over on the player grid
	 * post: exits the method if the highlight is true
	 */
	public void highlightGrid() {
		if (!panel.gameStart) {
			for (int i = 1; i <= 10; i++) {
				for (int j = 1; j <= 10; j++) {
					//if the mouse is on a square
					if ((int) (int) MouseInfo.getPointerInfo().getLocation().getX() - 5 >= 51 * j + 100
							&& (int) (int) MouseInfo.getPointerInfo().getLocation().getX() - 5 <= 51 * j + 150) {
						if ((int) (int) MouseInfo.getPointerInfo().getLocation().getY() - 32 >= 51 * i + 100
								&& (int) (int) MouseInfo.getPointerInfo().getLocation().getY() - 32 <= 51 * i + 150) {
							highlightX = 51 * j + 100;
							highlightY = 51 * i + 100;
							panel.highlight = true;
							return;
						} else {
							panel.highlight = false;
						}
					} else {
						panel.highlight = false;
					}
				}
			}
		}
	}

	/*
	 * Highlights the player grid when a ship hovers over a spot
	 * pre: Ships ship, the ship that is being checked
	 * post: exits the method if the highlight is true
	 */
	public void highlightShip(Ships ship) {
		if (ship.isActive) {
			if (ship.upright) { // when the ship is vertical
				for (int i = 1; i <= 10; i++) {
					for (int j = 1; j <= 10; j++) {
						//if the mouse is on a square
						if ((int) MouseInfo.getPointerInfo().getLocation().getX() - 5 >= 51 * j + 100
								&& (int) MouseInfo.getPointerInfo().getLocation().getX() - 5 <= 51 * j + 150) {
							if ((int) MouseInfo.getPointerInfo().getLocation().getY() - 32 >= 51 * i + 100
									&& (int) MouseInfo.getPointerInfo().getLocation().getY() - 32 <= 51 * i + 150) {
								shipHighlightX = 51 * j + 100;
								shipHighlightY = i;
								panel.highlightVertical = true;
								return;
							} else {
								panel.highlightVertical = false;
							}
						} else {
							panel.highlightVertical = false;
						}
					}
				}
			} else { // when the ship is horizontal
				for (int i = 1; i <= 10; i++) {
					for (int j = 1; j <= 10; j++) {
						//if the mouse is on a square
						if ((int) MouseInfo.getPointerInfo().getLocation().getX() - 5 >= 51 * j + 100
								&& (int) MouseInfo.getPointerInfo().getLocation().getX() - 5 <= 51 * j + 150) {
							if ((int) MouseInfo.getPointerInfo().getLocation().getY() - 32 >= 51 * i + 100
									&& (int) MouseInfo.getPointerInfo().getLocation().getY() - 32 <= 51 * i + 150) {
								shipHighlightX = j;
								shipHighlightY = 51 * i + 100;
								panel.highlightHorizontal = true;
								return;
							} else {
								panel.highlightHorizontal = false;
							}
						} else {
							panel.highlightHorizontal = false;
						}
					}
				}
			}
		}
	}

	/*
	 * Scaling a ship when it is placed on the grid
	 * pre: Ships ship, the ship that is being checked 
	 */
	public void scalingShip(Ships ship) {
		if (ship.upright) { // scales when ship is vertical
			ship.width = 50;
			ship.height = 50 * ship.holes;
		} else { // scales when ship is horizontal
			ship.width = 50 * ship.holes;
			ship.height = 50;
		}
	}

	/*
	 * Determining if a ship can be placed in a spot on the player grid
	 * pre: Ships ship, the ship that is being checked
	 * post: exits the method if the ship location is invalid
	 */
	public void isLegal(Ships ship) {
		for (int i = 1; i <= 10; i++) {
			for (int j = 1; j <= 10; j++) {
				//if a ship is on a square 
				if (ship.x >= 51 * j + 100 && ship.x <= 51 * j + 150) {
					if (ship.y >= 51 * i + 100 && ship.y <= 51 * i + 150) {
						if (ship.upright) { // when ship is vertical
							if (i + ship.holes - 1 <= 10) { // if the ship can fit on the grid
								for (int k = 0; k < ship.holes; k++) {
									if (grid[i - 1 + k][j - 1] == 1) { // if the spot is taken
										ship.isValid = false; 
										return; // exits method
									}
								}
								for (int k = 0; k < ship.holes; k++) { // marks the location as taken
									grid[i - 1 + k][j - 1] = 1;
									ship.shipPlaced = true; // ship has been placed
								}

								ship.i = i;
								ship.j = j;
								ship.x = 51 * j + 100;
								ship.y = 51 * i + 100;
								ship.isValid = true;
								scalingShip(ship); //scales the ship
							} else { // less than the space required to fit the ship
								ship.isValid = false;
							}
						} else { // when ship is horizontal
							if (j + ship.holes - 1 <= 10) { // if the ship can fit on the grid
								for (int k = 0; k < ship.holes; k++) {
									if (grid[i - 1][j - 1 + k] == 1) { // if the spot is taken
										ship.isValid = false; 
										return; // exits method
									}
								}
								for (int k = 0; k < ship.holes; k++) { // marks the location as taken
									grid[i - 1][j - 1 + k] = 1;
									ship.shipPlaced = true; // ship has been placed
								}
								ship.i = i;
								ship.j = j;
								ship.x = 51 * j + 100;
								ship.y = 51 * i + 100;
								ship.isValid = true;
								scalingShip(ship); //scales the ship
							} else { // less than the space required to fit the ship
								ship.isValid = false;
							}
						}
					}
				}
			}
		}
	}

	/*
	 * Determines when a ship is placed outside of the grid
	 * pre: Ships ship, the ship that is being checked
	 */
	public void outsideGrid(Ships ship) {
		if (ship.x < 51 * 1 + 100 || ship.x > 51 * 10 + 150 || ship.y < 51 * 1 + 100 || ship.y > 51 * 10 + 150) {
			ship.isValid = false;
			ship.upright = true;
		}
	}

	/*
	 * Resets the location of a ship when it is deemed invalid
	 * pre: Ships ship, the ship that is being checked
	 */
	public void isInvalid(Ships ship) {
		if (!ship.isValid) {
			if (ship.name.equals("Carrier")) { //resetting location of carriership
				ship.x = 750;
				ship.y = 150;
				ship.width = 40;
				ship.height = 120;
				panel.start.setVisible(false);
				panel.lbl.setText(
						"<html>NOTE:<br>Please place your ships.<br><font color=orange>Tip: Right-click to rotate</font><html>");
			} else if (ship.name.equals("Battleship")) { //resetting location of battleship
				ship.x = 750;
				ship.y = 300;
				ship.width = 40;
				ship.height = 90;
				panel.start.setVisible(false);
				panel.lbl.setText(
						"<html>NOTE:<br>Please place your ships.<br><font color=orange>Tip: Right-click to rotate</font><html>");
			} else if (ship.name.equals("Cruiser")) { //resetting location of cruiser
				ship.x = 750;
				ship.y = 420;
				ship.width = 40;
				ship.height = 60;
				panel.start.setVisible(false);
				panel.lbl.setText(
						"<html>NOTE:<br>Please place your ships.<br><font color=orange>Tip: Right-click to rotate</font><html>");
			} else if (ship.name.equals("Destroyer")) { //resetting location of destroyer
				ship.x = 750;
				ship.y = 515;
				ship.width = 40;
				ship.height = 60;
				panel.start.setVisible(false);
				panel.lbl.setText(
						"<html>NOTE:<br>Please place your ships.<br><font color=orange>Tip: Right-click to rotate</font><html>");
			} else if (ship.name.equals("Patrol")) { //resetting location of patrol boat
				ship.x = 750;
				ship.y = 620;
				ship.width = 40;
				ship.height = 50;
				panel.start.setVisible(false);
				panel.lbl.setText(
						"<html>NOTE:<br>Please place your ships.<br><font color=orange>Tip: Right-click to rotate</font><html>");
			}
		}
	}

	/*
	 * Changes the orientation of a ship
	 * pre: Ships ship, the ship being rotated
	 */
	public void shipRotate(Ships ship) {
		if (ship.isActive) {
			if (ship.upright) {
				ship.setOrientation(false);
			} else {
				ship.setOrientation(true);
			}
		}
	}

	/*
	 * Sets a new location for the ship where the mouse is
	 * pre: Ships ship, the ship being checked
	 */
	public void shipRearrange(Ships ship) {
		if (ship.isActive) {
			ship.x = (int) ((int) MouseInfo.getPointerInfo().getLocation().getX() - 5);
			ship.y = (int) ((int) MouseInfo.getPointerInfo().getLocation().getY() - 32);
			ship.isActive = false;
		}
	}

	/*
	 * Determines if a ship has been clicked
	 * pre: Ships ship, the ship that is being checked
	 */
	public void shipClicked(Ships ship) {
		// if the mouse is within the ship bounds
		if (MouseInfo.getPointerInfo().getLocation().getX() - 5 >= ship.x
				&& MouseInfo.getPointerInfo().getLocation().getX() - 5 <= (ship.x + ship.width)) {
			if (MouseInfo.getPointerInfo().getLocation().getY() - 32 >= ship.y
					&& MouseInfo.getPointerInfo().getLocation().getY() - 32 <= (ship.y + ship.height)) {
				ship.isActive = true;
				panel.drag = true;
				
				// if ship is within grid bounds
				if (ship.x >= 51 * 1 + 100 && ship.x <= 51 * 10 + 150 && ship.y >= 51 * 1 + 100
						&& ship.y <= 51 * 10 + 150) { 
					// resets grid value (since the ship has been removed from the grid)
					if (ship.upright) {
						for (int k = 0; k < ship.holes; k++) {
							grid[ship.i - 1 + k][ship.j - 1] = 0;
							ship.shipPlaced = false;
						}
					} else {
						for (int k = 0; k < ship.holes; k++) {
							grid[ship.i - 1][ship.j - 1 + k] = 0;
							ship.shipPlaced = false;
						}
					}
				}
			}
		}
	}
	
	/*
	 * Resets the grid in the Setup class
	 */
	public void resetSetup() {
		for(int i=0; i<10; i++) {
			for(int j=0; j<10; j++) {
				grid[i][j] = 0;
			}
		}
	}

}
