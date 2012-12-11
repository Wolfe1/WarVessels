package edu.bsu.cs222.warVessels;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class GridTest {

	Grid testGrid = new Grid();
	Ship testBoat = new Ship(ShipType.PATROL_BOAT);
	Ship testBoat2 = new Ship(ShipType.CARRIER);
	ArrayList<ShipType> expectedAfloat = new ArrayList<ShipType>();

	@Test
	public void testShipLength() {
		Assert.assertEquals(2, testBoat.getType().getLength());
	}

	@Test
	public void testGuess() {
		testGrid.setShip (3, 3, testBoat);
		Assert.assertTrue(testGrid.checkGuess(3, 3));
	}

	@Test
	public void testGuessTwo() {
		testBoat2.changeBearing();
		testGrid.setShip(4, 4, testBoat2);
		Assert.assertTrue(testGrid.checkGuess(4, 1));
	}

	@Test
	public void testValidLocationForShip() {
		Ship ship = new Ship(ShipType.CARRIER);
		Grid grid = new Grid();

		// bearing currently RIGHT
		Assert.assertFalse(grid.validateLocation(0, 0, ship));
		Assert.assertTrue(grid.validateLocation(6, 6, ship));

		ship.changeBearing(); // bearing now DOWN

		Assert.assertFalse(grid.validateLocation(3, 3, ship));
		Assert.assertTrue(grid.validateLocation(8, 8, ship));

		// TODO check for cell collisions
	}
	
	@Test
	public void checkShipsAfloat(){
		expectedAfloat.add(testBoat.getType());
		testGrid.setShip(7, 7, testBoat);
		Assert.assertEquals(expectedAfloat, testGrid.shipsAfloat());
	}
	
	@Test
	public void testShipsNotOnTopOfEachOther(){
		testGrid.setShip(7, 7, testBoat);
		Assert.assertFalse(testGrid.validateLocation(6, 7, testBoat2));
	}

}
