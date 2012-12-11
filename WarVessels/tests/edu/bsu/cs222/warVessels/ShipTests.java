package edu.bsu.cs222.warVessels;

import org.junit.Assert;
import org.junit.Test;

public class ShipTests {

	Ship ship = new Ship(ShipType.PATROL_BOAT);

	// test that ship initialized to the correct length (hp)
	@Test
	public void testShipLength() {
		Ship ship = new Ship(ShipType.PATROL_BOAT);
		int hitsRemaining = ship.getType().getLength();
		Assert.assertEquals(2, hitsRemaining);
	}

	@Test
	public void testHit() {
		Ship ship = new Ship(ShipType.PATROL_BOAT);
		ship.shipHit();
		Assert.assertEquals(1, ship.getHitsScoredAgainst());
		Assert.assertFalse(ship.isShipSunk());
	}

	@Test
	public void testSink() {
		Ship ship = new Ship(ShipType.PATROL_BOAT);
		ship.shipHit();
		ship.shipHit();
		Assert.assertEquals(2, ship.getHitsScoredAgainst());
		Assert.assertTrue(ship.isShipSunk());
	}

	@Test
	public void testChangeBearing() {
		Ship ship = new Ship(ShipType.BATTLESHIP);
		// inital bearing
		

		ship.changeBearing();
		Assert.assertEquals(Bearing.DOWN, ship.getBearing());

		ship.changeBearing();
		Assert.assertEquals(Bearing.LEFT, ship.getBearing());

		ship.changeBearing();
		Assert.assertEquals(Bearing.UP, ship.getBearing());
	}
	
	@Test
	public void testRestBearing(){
		Ship ship = new Ship(ShipType.BATTLESHIP);
		ship.changeBearing();
		ship.resetBearing();
		Assert.assertEquals(Bearing.RIGHT, ship.getBearing());
	}

}