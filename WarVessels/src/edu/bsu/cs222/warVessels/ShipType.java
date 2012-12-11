package edu.bsu.cs222.warVessels;
/**
 * Enum that holds information that is specific to each type of ship in a game of battleship.
 * Ties the length of the ship to the name.
 * @author Dan
 *
 */
public enum ShipType {
	CARRIER (5),
	BATTLESHIP(4),
	SUBMARINE(3),
	DESTROYER(3),
	PATROL_BOAT(2);
	/**
	 * The length of the ship.
	 */
	private int boatLength;
	
	/**
	 * constructor to create a ShipType
	 * @param length the length of the ship
	 */
	private ShipType(int length){
		this.boatLength=length;
	}
	
	/**
	 * Allows outside classes to know the length of the ship.
	 * @return the length of the ship.
	 */
	public int getLength(){
		return boatLength;
	}
	

}
