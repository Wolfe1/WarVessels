package edu.bsu.cs222.warVessels;

/**
 * This is just a simple test that pits two AIs against each other in a game to
 * showcase the functionality of our objects.
 * 
 * @author Steffan Byrne
 * 
 */
public class TestRun {

	public static void main(String[] args) {

		int artyWins = 0;
		int bettyWins = 0;
		
		for (int i = 0; i < 1000; i++) {
			System.out.println("New Game");
			
			Grid artyOcean = new Grid();
			Grid bettyOcean = new Grid();

			AI arty = new AI(artyOcean, bettyOcean);
			AI betty = new AI(bettyOcean, artyOcean);

			arty.placeAllShips();
			betty.placeAllShips();

			while (arty.getOcean().shipsAfloat().size() > 0
					|| betty.getOcean().shipsAfloat().size() > 0) {
				arty.guessLocation();
				System.out.print("Arty: ");
				System.out.println(arty.guessToString());

				if (betty.getOcean().shipsAfloat().size() == 0) {
					System.out.println("Game over! Arty Wins. ");
					artyWins++;
					break;
				}

				betty.guessLocation();
				System.out.print("Betty: ");
				System.out.println(betty.guessToString());

				if (arty.getOcean().shipsAfloat().size() == 0) {
					System.out.println("Game over! Betty wins.");
					bettyWins++;
					break;
				}
			}
		}
		System.out.println("Arty: " + artyWins);
		System.out.println("Betty: " + bettyWins);
	}
}
