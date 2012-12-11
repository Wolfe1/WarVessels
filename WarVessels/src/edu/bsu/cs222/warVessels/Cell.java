package edu.bsu.cs222.warVessels;

/**
 * A cell is place at each coordinate to store the state of the guess and if 
 * there is a ship object present on it.  
 * 
 * @author Thomas Foreman
 *	
 */
public class Cell {

	private Ship shipPart;
	private Guess cellGuess;
	
	/**
	 * When a cell is initialized the Guess property is set to NOT_GUESSED.
	 */
	public Cell(){
		cellGuess = Guess.NOT_GUESSED;
		shipPart = null;
	}
	
	/**
	 * Changes Guess to HIT and tells the ship in the cell that it was hit
	 */
	public void hit(){
		cellGuess = Guess.HIT;
		shipPart.shipHit();
	}
	
	/**
	 * Changes Guess to MISS
	 */
	public void miss(){
		cellGuess = Guess.MISS;
	}
	
	/**
	 * 
	 * @return the current Guess state of the cell.
	 */
	public Guess getGuess(){
		Guess returnGuess = cellGuess;
		return returnGuess;
	}
	
	/**
	 * 
	 * @param aShip, the ship object on the cell.
	 */
	public void placeShipPart(Ship aShip){
		shipPart = aShip;
	}
	
	/**
	 * 
	 * @return returnShipPart, the ship object on the cell.
	 */
	public Ship getShip(){
		Ship returnShipPart = shipPart;
		return returnShipPart;
	}
	/**
	 *
	 * @return boolean true if ship is present / false if ship is not present.
	 */
	public boolean shipPresent(){
		if(shipPart instanceof Ship){
			return true;
		}
		return false;
	}
	public void removeShip(){
		shipPart = null;
	}
}
