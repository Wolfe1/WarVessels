package edu.bsu.cs222.warVessels;

import java.util.Random;

/**
 * The AI in its simplest form is able to place ships on the Grid and make
 * guesses about an opponent's ships. Its intelligence is currently limited to
 * avoiding repeat guesses and guessing coordinates near other hits when a ship
 * has not been sunk.
 * 
 * @author Steffan Byrne
 * @version .1
 */
public class AI extends AbstractAI {

	public AI(Grid ocean, Grid radar) {
		super(ocean, radar);
	}

	protected void randomGuess(){
		Random rand = new Random();

		do {
			x = rand.nextInt(10);
			y = rand.nextInt(10);
		} while (!(radar.getStatus(x, y).equals(Guess.NOT_GUESSED)));
	}

}
