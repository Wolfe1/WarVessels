package edu.bsu.cs222.warVessels;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**Object to handle statistics accrued over the duration of the game
 * 
 * @author Thomas Foreman
 *
 */


public class Statistics {

	/**
	 * number of hits scored
	 */
	private int hits = 0;
	
	/**
	 * number of misses
	 */
	private int misses = 0;
	
	/**
	 * total number of guesses
	 */
	private int guesses = 0;
	
	/**
	 * percentage of guesses that hit
	 */
	private double hitRate = 0.00;
	
	private static NumberFormat percent = NumberFormat.getPercentInstance();
	
	/**
	 * constructor
	 */
    public Statistics(){
		
	}
	/**
	 * record a hit
	 */
	
	public void hit(){
		hits++;
		incrementGuesses();
	}
	
	/**
	 * record a miss
	 */
	public void miss(){
		misses++;
		incrementGuesses();
	}
	
	public int getHits(){
		int totalHits = hits;
		return totalHits;
	}
	
	public int getMisses(){
		int totalMisses = misses;
		return totalMisses;
	}
	
	
	public String getHitRate(){
		hitRate = (double)hits / guesses;
		percent = new DecimalFormat("0.0#%");
		String hitRateString = percent.format(hitRate);
		return hitRateString;
	}
	
	public int getGuesses(){
		return guesses;
	}
	
	/**
	 * record a guess
	 */
	private void incrementGuesses(){
		guesses++;
	}
}