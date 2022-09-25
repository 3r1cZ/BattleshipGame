/*
 * Eric Zhou
 * 1/20/2022
 * Ships
 * Manages the ships and their actions
 */

package battleship;

public class Ships {

	String name; // name of ship
	int x; // x position of ship
	int y; // y position of ship
	int height; // height of ship
	int width; // width of ship
	int holes; // number of holes of ship

	boolean upright = true; // if the ship is vertical or horizontal
	boolean isValid = false; // if the placement of the ship is valid or not
	boolean isActive = false; // if the ship is currently clicked on
	int i, j; // values for ships when they are placed on a grid
	boolean shipPlaced = false; // if the ship has been placed
	int count; // hp of the ship
	boolean sunk = false; // if the ship has sunk

	public Ships(String name, int x, int y, int width, int height, int holes) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.holes = holes;
		shipHP();
	}

	/*
	 * Determines if the ship is vertical or horizontal 
	 * pre: boolean upright, value that shows if the ship is vertical or horizontal
	 */
	public void setOrientation(boolean upright) {
		this.upright = upright;
	}

	/*
	 * Determines the HP of the ship as the number of holes in the ship
	 */
	public void shipHP() {
		this.count = holes;
	}

	/*
	 * Resets all variables in the Ships class
	 */
	public void resetShips() {
		upright = true;
		isValid = false;
		isActive = false;
		i = 0;
		j = 0;
		shipPlaced = false;
		sunk = false;
		count = holes;
		if (name.equals("Carrier")) { // resetting location of carriership
			x = 750;
			y = 150;
			width = 40;
			height = 120;
		} else if (name.equals("Battleship")) { // resetting location of battleship
			x = 750;
			y = 300;
			width = 40;
			height = 90;
		} else if (name.equals("Cruiser")) { // resetting location of cruiser
			x = 750;
			y = 420;
			width = 40;
			height = 60;
		} else if (name.equals("Destroyer")) { // resetting location of destroyer
			x = 750;
			y = 515;
			width = 40;
			height = 60;
		} else if (name.equals("Patrol")) { // resetting location of patrol boat
			x = 750;
			y = 620;
			width = 40;
			height = 50;
		}
	}

}
