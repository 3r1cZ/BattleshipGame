/*
 * Eric Zhou
 * 1/20/2022
 * AI
 * Manages the computer turn 
 */

package battleship;

public class AI extends Thread {

	// location of a random spot for the computer to hit
	int randomX, randomY;

	IPanel panel; // IPanel object
	Setup setup = new Setup(panel); // Setup object

	Thread runAI; // Thread that runs the delay between turns

	public AI(IPanel panel) {
		this.panel = panel;
		runAI = new Thread(this); // creating the new Thread
		runAI.start();
	}

	/*
	 * Method that runs when the Thread begins execution: 
	 * creates delay between turns and determines a random spot for the computer to hit
	 */
	public void run() {

		while (panel.gameStart) {

			// a statement is needed in order for the if statement to run (any statement works, and I do not know why this is required)
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (!panel.playerTurn) { // computer's turn

				// generating random position to hit
				randomX = (int) (Math.random() * 10 + 1);
				randomY = (int) (Math.random() * 10 + 1);

				if (setup.grid[randomX - 1][randomY - 1] == 0) { // if miss
					// delays the computer turn by one second
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					panel.lbl.setText("The computer has hit nothing.");
					panel.playerTurn = true;
					setup.grid[randomX - 1][randomY - 1] = 2;
				} else if (setup.grid[randomX - 1][randomY - 1] == 1) { // if hit
					// delays the computer turn by one second
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					panel.lbl.setText("The computer has hit a ship!");
					panel.playerTurn = true;
					setup.grid[randomX - 1][randomY - 1] = 3;
					determineShip(panel.carriership, 51 * randomY + 100, 51 * randomX + 100);
					determineShip(panel.battleship, 51 * randomY + 100, 51 * randomX + 100);
					determineShip(panel.cruiser, 51 * randomY + 100, 51 * randomX + 100);
					determineShip(panel.destroyer, 51 * randomY + 100, 51 * randomX + 100);
					determineShip(panel.patrol, 51 * randomY + 100, 51 * randomX + 100);
				}
			}
		}

	}

	/*
	 * Determines which ship has been hit by the computer 
	 * pre: Ships ship, the ship that is being checked, int x, y, x and y variables of the generated location
	 */
	public void determineShip(Ships ship, int x, int y) {
		// if the shot is within the ship boundaries
		if (x >= ship.x && x <= (ship.x + ship.width)) {
			if (y >= ship.y && y <= (ship.y + ship.height)) {
				if (ship.count > 0) {
					ship.count--;
					if (ship.count == 0) { // if the ship has sunk
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
		// player ships sinking
		if (panel.carriership.sunk) {
			panel.lbl.setText("Your Carriership has sunk!");
			panel.carriership.sunk = false;
			panel.numShipsSunk++;
		}
		if (panel.battleship.sunk) {
			panel.lbl.setText("Your Battleship has sunk!");
			panel.battleship.sunk = false;
			panel.numShipsSunk++;
		}
		if (panel.cruiser.sunk) {
			panel.lbl.setText("Your Cruiser has sunk!");
			panel.cruiser.sunk = false;
			panel.numShipsSunk++;
		}
		if (panel.destroyer.sunk) {
			panel.lbl.setText("Your Destroyer has sunk!");
			panel.destroyer.sunk = false;
			panel.numShipsSunk++;
		}
		if (panel.patrol.sunk) {
			panel.lbl.setText("Your Patrol Boat has sunk!");
			panel.patrol.sunk = false;
			panel.numShipsSunk++;
		}
	}
}
