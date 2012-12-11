package edu.bsu.cs222.warVessels;


import junit.framework.Assert;

import org.junit.Test;

public class StatisticsTest {

	private Statistics testStat = new Statistics();
	
	@Test
	public void testHit(){
		int epsilon = 1;
		testStat.hit();
		Assert.assertEquals(epsilon, testStat.getHits());
	}
	
	@Test
	public void testMiss(){
		int epsilon = 1;
		testStat.miss();
		Assert.assertEquals(epsilon, testStat.getMisses());
	}
	@Test
	public void testHitRate(){
		testStat.hit();
		for(int i = 0; i < 7; i++){
			testStat.miss();	
		}
		String hitRateTest = "12.5%";
		Assert.assertEquals(hitRateTest, testStat.getHitRate());
	}
}
