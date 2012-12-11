package edu.bsu.cs222.warVessels;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Grid holds locations of Cells in a grid pattern for guessing. The grid is
 * responsible for placing ships and determining if the game is over.
 * 
 * @author Steffan Byrne
 * @author Brandon Wolfe
 * 
 */
public class Grid {

	private ArrayList<ShipType> shipsAfloat; // Array List of ships still on the
										// grid
	private HashMap<Point, Cell> coords; // HashMap of Cells with Points as keys
	private Collection<Cell> cellList;
	
	private Logger logger = LoggerFactory.getLogger(Grid.class);
	
	public Grid() {
		coords = new HashMap<Point, Cell>();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				coords.put(new Point(i, j), (new Cell()));
			}
		}
		shipsAfloat = new ArrayList<ShipType>(); //Add Ship to List
	}

	/**
	 * This method is used to place ships on the grid with respect to bearing.
	 * The method runs the validate Location method to determine if all of a
	 * ship can be placed on the board at the current location and bearing
	 */
	public void setShip(int x, int y, Ship aShip) {
		if (validateLocation(x, y, aShip)) {
			logger.info(aShip.getType() + " placed at "+x+","+y);
			coords.get(new Point(x, y)).placeShipPart(aShip);
			Bearing bearing = aShip.getBearing();

			if (bearing == Bearing.RIGHT) {
				for (int i = 0; i < aShip.getType().getLength(); i++) {
					coords.get(new Point(x - i, y)).placeShipPart(aShip);
				}
			}
			if (bearing == Bearing.DOWN) {
				for (int i = 0; i < aShip.getType().getLength(); i++) {
					coords.get(new Point(x, y - i)).placeShipPart(aShip);
				}
			}
			if (bearing == Bearing.LEFT) {
				for (int i = 0; i < aShip.getType().getLength(); i++) {
					coords.get(new Point(x + i, y)).placeShipPart(aShip);
				}
			}
			if (bearing == Bearing.UP) {
				for (int i = 0; i < aShip.getType().getLength(); i++) {
					coords.get(new Point(x, y + i)).placeShipPart(aShip);
				}
			}
			// As ships are added to the grid they are placed in an ArrayList
			// for
			// later use
			shipsAfloat.add(aShip.getType());
		}

	}

	/**
	 * Method used to make sure the ship is being placed in a valid location. It
	 * checks that the ship will be entirely within the Grid.
	 * 
	 * @param x
	 *            The X-coordinate of the location of the ship's bow
	 * @param y
	 *            The Y-coordinate of the location of the ship's bow
	 * @return Boolean describing if the ship is fully on the grid or not
	 */

	public boolean validateLocation(int x, int y, Ship ship) {
		int bowX = x;
		int bowY = y;
		int sternX, sternY;
		int shipLength = ship.getType().getLength();

		// stern location varies depending on the bearing of the ship
		if (ship.getBearing() == Bearing.RIGHT) {
			sternX = x - (shipLength - 1);
			sternY = y;
		} else if (ship.getBearing() == Bearing.LEFT) {
			sternX = x + (shipLength - 1);
			sternY = y;
		} else if (ship.getBearing() == Bearing.DOWN) {
			sternX = x;
			sternY = y - (shipLength - 1);
		} else {
			sternX = x;
			sternY = y + (shipLength - 1);
		}
		// if the bow is located on the grid
		if (bowX >= 0 && bowX <= 9 && bowY >= 0 && bowY <= 9) {
			// if stern is located on the grid
			if (sternX >= 0 && sternX <= 9 && sternY >= 0 && sternY <= 9) {
				if (checkCellsUnoccupied(bowX, bowY, sternX, sternY)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * A method to check if the cells that the ship is about to be place on are
	 * empty. A return value of true indicates the cells are currently
	 * unoccupied.
	 * 
	 * @param bowX
	 *            The X-coordinate of the bow
	 * @param bowY
	 *            The Y-coordinate of the bow
	 * @param sternX
	 *            The X-coordinate of the stern
	 * @param sternY
	 *            The Y-coordinate of the stern
	 * @return boolean describing if the cells are empty
	 */
	private boolean checkCellsUnoccupied(int bowX, int bowY, int sternX,
			int sternY) {
		int length;
		boolean cellsEmpty = true;
		int offset = 0;
		int startingPoint;

		// ship is oriented vertically
		if (bowX == sternX) {
			length = Math.abs(bowY - sternY) + 1;
			startingPoint = Math.min(bowY, sternY); // start at the lower end
			while (cellsEmpty) {
				// start at bow and iterate backwards

				if (coords.get(new Point(bowX, (startingPoint + offset)))
						.shipPresent()) {
					cellsEmpty = false; // if cell is occupied, change boolean,
										// getting us out of loop
				}
				if (offset == length - 1) {
					break; // we have iterated over the length of the ship,
							// break from loop
				}
				offset++;
			}
		} else { // ship is oriented horizontally
			length = Math.abs(bowX - sternX) + 1;
			startingPoint = Math.min(bowX, sternX);
			while (cellsEmpty) {
				if (coords.get(new Point((startingPoint + offset), sternY))
						.shipPresent()) {
					cellsEmpty = false;
				}
				if (offset == length - 1) {
					break;
				}
				offset++;
			}
		}
		return cellsEmpty;
	}

	/**
	 * Checks specified Cell for a ship
	 * 
	 * @param x
	 *            the column that the cell is in
	 * @param y
	 *            the row that the cell is in
	 * @return the ship at that location
	 */
	public Ship getShip(int x, int y) {
		return coords.get(new Point(x, y)).getShip();
	}

	/**
	 * Checks specified Cell for it's guess status
	 * 
	 * @param x
	 *            the column that the cell is in
	 * @param y
	 *            the row that the cell is in
	 * @return if the cell has been guessed and whether or not it was a hit
	 */
	public Guess getStatus(int x, int y) {
		return coords.get(new Point(x, y)).getGuess();
	}

	/**
	 * Checks a specified cell to see if there is part of a ship there.
	 * 
	 * @param x
	 *            the column that the cell is in
	 * @param y
	 *            the row that the cell is in
	 * @return whether or not there was a ship in the location.
	 */
	public boolean checkGuess(int x, int y) {
		Cell aCell = coords.get(new Point(x, y));

		if (!(aCell.getGuess().equals(Guess.NOT_GUESSED))) {
			throw new IllegalArgumentException("These coordinates" + x + ", "
					+ y + " have been guessed already.");
		}

		if (!(aCell.shipPresent())) {
			aCell.miss();
			return false;
		}

		aCell.hit();
		if (aCell.getShip().isShipSunk()) {
			shipsAfloat.remove(aCell.getShip().getType());
		}
		return true;

	}

	/**
	 * Method to return the ShipsAfloat ArrayList
	 * 
	 * @return shipsAfloat
	 */
	public ArrayList<ShipType> shipsAfloat() {
		return shipsAfloat;
	}
	
	public void removeShip(Ship aShip){
		cellList =  coords.values();
		for(Cell c: cellList){
			if (c.getShip() != null && c.getShip().equals(aShip)){
				shipsAfloat.remove(c.getShip().getType());
				c.removeShip();
			}
		}
	}
	

}
