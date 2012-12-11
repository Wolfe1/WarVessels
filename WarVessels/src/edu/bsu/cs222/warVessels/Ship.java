package edu.bsu.cs222.warVessels;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * A Ship represents a ship used in a game of battleship. Each boat has type
 * specific information which it gets from the BoatType enum. The ship is
 * responsible for communicating its status with the Grid.
 * 
 * @author Dan Buis
 * 
 */
public class Ship {

	/**
	 * The ShipType represented by the ship
	 */
	private ShipType type;

	/**
	 * How many times has this ship been hit
	 */
	private int hitsScoredAgainst = 0;

	/**
	 * Bearing describing which direction the ship is facing
	 */
	private Bearing bearing = Bearing.RIGHT;

	/**
	 * Has the ship been sunk
	 */
	private boolean sunk = false;
	/**
	 * logger to log the actions the ship makes
	 */
	private Logger logger = LoggerFactory.getLogger(Ship.class);
	
	/**
	 * Ship is constructed based on its type.
	 * 
	 * @param type
	 *            The type of ship
	 */
	
	public Ship(ShipType type) {
		this.type = type;
	}

	/**
	 * This method is called each time the ship is hit. It increments the
	 * hitsScoredAgainst field and then checks to see if the ship has sunk.
	 */
	public void shipHit() {
		hitsScoredAgainst = hitsScoredAgainst + 1;
		logger.info(this.type +" hit");
		if (doesShipSink()) {
			sunk = true;
			logger.info(this.type + " sunk");
		}
	}

	/**
	 * Method checks if the ship has suffered enough damage to sink by comparing
	 * the hitsScoredAgainst field with the length of the ship given in the
	 * ShipType enum.
	 * 
	 * @return a boolean describing if the ship sinks
	 */
	private boolean doesShipSink() {
		if (hitsScoredAgainst == type.getLength()) {
			return true;
		}
		return false;
	}

	/**
	 * This method is designed with the Grid in mind. After every hit that is
	 * guessed the Grid will check with all ships to see which ones have sunk.
	 * 
	 * @return a boolean describing if the ship has been sunk
	 */
	public boolean isShipSunk() {
		return sunk;
	}

	/**
	 * Method to change the bearing of the ship through a repeated event.(ie RMB
	 * click) Utilizes a helper method.
	 */
	public void changeBearing() {
		bearing = changeBearing(bearing);
		logger.info(this.type + " rotated");
	}

	/**
	 * Helper method used within the class to incrementally change the Bearing
	 * of the ship. A repeated event will increment the ship clockwise through
	 * the available Bearings.
	 * 
	 * @param currentBearing
	 *            The current Bearing of the ship.
	 * @return The new Bearing of the ship.
	 */
	private Bearing changeBearing(Bearing currentBearing) {
		if (currentBearing == Bearing.RIGHT) {
			return Bearing.DOWN;
		} else if (currentBearing == Bearing.DOWN) {
			return Bearing.LEFT;
		} else if (currentBearing == Bearing.LEFT) {
			return Bearing.UP;
		} else
			return Bearing.RIGHT;
	}

	public void resetBearing(){
		bearing = Bearing.RIGHT;
	}
	
	/**
	 * Allows other objects to access the bearing of the ship.
	 * 
	 * @return the bearing of the ship
	 */
	public Bearing getBearing() {
		return bearing;
	}

	/**
	 * Allows other objects to know what type of ship is being used.
	 * 
	 * @return the type of ship
	 */
	public ShipType getType() {
		return type;
	}

	/**
	 * Allows access to the hitsScoredAgainst field. At runtime this method is
	 * never called, but it is necessary for the tests.
	 * 
	 * @return how many hits have been scored on the ship
	 */
	public int getHitsScoredAgainst() {
		return hitsScoredAgainst;
	}

}
