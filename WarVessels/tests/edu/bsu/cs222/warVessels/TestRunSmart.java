package edu.bsu.cs222.warVessels;

public class TestRunSmart {
	public static void main(String[] args) {

		int artyWins = 0;
		int bettyWins = 0;

		for (int i = 0; i < 1000; i++) {
			System.out.println("New Game");
			Grid artyOcean = new Grid();
			Grid bettyOcean = new Grid();

			SmartAI arty = new SmartAI(artyOcean, bettyOcean);
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
