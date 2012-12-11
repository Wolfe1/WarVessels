package edu.bsu.cs222.warVessels;

import junit.framework.Assert;

import org.junit.Test;


public class AITests {

	Grid artyOcean = new Grid();
	Grid playerOcean = new Grid();
	AI arty = new AI(artyOcean, playerOcean);
	Ship aShip = new Ship(ShipType.CARRIER);

	
	@Test
	public void testPlaceShip(){
		arty.placeShip(aShip);
		Assert.assertEquals(1, arty.getOcean().shipsAfloat().size());
	}
	
	@Test
	public void testCheckGrid(){
		arty.guessLocation();
		int numOfMiss = 0;
		
		for (int i = 0; i < 10; i++){
			for (int j = 0; j < 10; j++){
				if(playerOcean.getStatus(i, j) == Guess.MISS){
					numOfMiss++;
				}
			}
		}
		
		Assert.assertEquals(1, numOfMiss);
	}

	@Test
	public void testAIHit(){
		playerOcean.setShip(4, 4, aShip);
		
		while(playerOcean.getShip(4, 4).getHitsScoredAgainst() == 0){
			arty.guessLocation();
		}
		
		Assert.assertEquals(1, aShip.getHitsScoredAgainst());
	}
	
	@Test
	public void testSinkShip(){
		playerOcean.setShip(4, 4, aShip);
		
		while(!playerOcean.getShip(4, 4).isShipSunk()){
			arty.guessLocation();
		}
		
		Assert.assertEquals(5, aShip.getHitsScoredAgainst());
	}
}
