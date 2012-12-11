package edu.bsu.cs222.warVessels;

import junit.framework.Assert;

import org.junit.Test;

public class CellTest {
	
	private Cell testCell = new Cell();
	private Guess testGuess;
	
	/**
	 * Test a default cell to see if guess is NOT_GUESS
	 */
	@Test
	public void testNotGuess(){
		testGuess = Guess.NOT_GUESSED;
		Assert.assertEquals(testGuess, testCell.getGuess());
	}
	
	/**
	 * Sets a test cell property to HIT and then checks to see if the cell
	 * returns the correct guess.
	 */
	@Test
	public void testHit(){
		testGuess = Guess.HIT;
		Ship aShip = new Ship(ShipType.BATTLESHIP);
		testCell.placeShipPart(aShip);
		testCell.hit();
		Assert.assertEquals(testGuess, testCell.getGuess());
	}
	
	/**
	 * Sets a test cell property to MISS and then checks to see if the cell
	 * returns the correct guess.
	 */
	@Test
	public void testMiss(){
		testGuess = Guess.MISS;
		testCell.miss();
		Assert.assertEquals(testGuess, testCell.getGuess());
	}
	
	/**
	 * Places a ship on the cell and then check to see if the cell 
	 * returns the correct ship object.
	 */
	@Test
	public void shipOnCell(){
		Ship aShip = new Ship(ShipType.CARRIER);
		testCell.placeShipPart(aShip);
		Assert.assertEquals(aShip, testCell.getShip());
	}
}
