package hu.unideb.progtech.headswitcher.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import hu.unideb.progtech.headswitcher.game.utility.DiceRoll;

public class DiceRollTest {

	DiceRoll dc;

	

	@Before
	public void setUp() throws Exception {
		dc = new DiceRoll();
	}

	

	@Test
	public void rollFromUntilTest() throws Exception {
		assertEquals(1, dc.roll(1, 1));
	}
	
	@Test(expected = Exception.class)
	public void rollFromUntilFailTest() throws Exception{
		assertEquals(1, dc.roll(2, 1));
	}

}
