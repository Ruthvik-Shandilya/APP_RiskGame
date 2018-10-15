package com.risk.services.gameplay;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.risk.model.Country;

/**
 * Test class for fortification phase.
 * 
 * @author Neha Pal
 *
 */

public class FortificationPhaseTest {

	/** Object for FortificationPhase class */
	private FortificationPhase fortificationPhase;
	
	/** First Object1 for Country class */
	private Country country1;

	/** Second Object for Country Class  */
	private Country country2;

	/** Number of armies */
	private int numberOfArmies;

	/** ArrayList to hold adjacent countries for  */
	private ArrayList<Country> adjacentCountries;

	/**
	 * Set up the initial objects for Fortification Phase
	 * @throws Exception
	 */
	@Before	
	public void setUp() throws Exception {
		country1 = new Country("C1");
		country2 = new Country("C2");
		country1.setNoOfArmies(5);
		country2.setNoOfArmies(7);
		fortificationPhase = new FortificationPhase();
		adjacentCountries = new ArrayList<Country>();
		adjacentCountries.add(country2);
		country1.setAdjacentCountries(adjacentCountries);
		numberOfArmies = 1;

	}

	/**
	 * Test to validate number of armies after the player moves fix number of armies.
	 * between two adjacent countries owned by player
	 */
	
	@Test
	public void moveArmiesTest() throws Exception {
		fortificationPhase.moveArmies(country1, country2, numberOfArmies);
		assertEquals(4,country1.getNoOfArmies());
		assertEquals(8,country2.getNoOfArmies());
	}
}
