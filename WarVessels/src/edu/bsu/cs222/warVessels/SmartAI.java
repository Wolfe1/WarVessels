package edu.bsu.cs222.warVessels;

import java.util.Random;

public class SmartAI extends AbstractAI {

	private int lastRandX;
	private int lastRandY;

	public SmartAI(Grid ocean, Grid radar) {
		super(ocean, radar);
		lastRandX = 0;
		lastRandY = 0;
	}
	
	@Override
	protected void randomGuess() {
		int offset = 5;
		int attempts = 0;

		for(ShipType s: radar.shipsAfloat()){	//sets offset to the length of the smallest ship on the board.
			if(s.getLength() < offset){
				offset = s.getLength();
			}
		}
		
		Random rand = new Random();

		do {
			attempts++;
			if (attempts > 100){
				do {
					x = rand.nextInt(10);
					y = rand.nextInt(10);
				} while (!(radar.getStatus(x, y).equals(Guess.NOT_GUESSED)));
				break;
			}
			x = rand.nextInt(10);
			y = rand.nextInt(10);
		} while ((!(radar.getStatus(x, y).equals(Guess.NOT_GUESSED)))
				|| (!(isOffset(offset))));
		lastRandX = x;
		lastRandY = y;
		
	}

	/**
	 * Checks to see if the random coordinate is situated on the board so that
	 * it is placed where it maximizes efficiency in guessing.
	 * 
	 * @param offset
	 * @return
	 */
	private boolean isOffset(int offset) {
		if ((Math.abs(x - lastRandX) + Math.abs(y - lastRandY)) % offset == 0) {
			return true;
		}
		return false;
	}
	
}
