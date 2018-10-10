package com.risk.services.gameplay;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

import com.risk.model.Continent;
import com.risk.model.Country;
import com.risk.model.Player;

/**
 * Test class for Reinforcement Phase.
 * 
 * @author Neha Pal
 *
 */

class ReinforcementPhaseTest {

	/** Object for ReinforcementPhase class */
	private ReinforcementPhase reinforcementPhase;

	/** Object for Player class */
	private Player player;

	/** Object for Country class */
	private Country country;

	/** Object for Continent class */
	private Continent continent;

	/** ArrayList to hold countries owned by the player */
	private ArrayList<Country> playerOwnedContries;

	/** ArrayList to hold list of countries in continent */
	private ArrayList<Country> continentListOfCountries;

	/**
	 * Set up the initial objects for Reinforcement Phase
	 * 
	 * @throws Exception
	 */

	@BeforeEach
	void setUp() throws Exception {
		reinforcementPhase = new ReinforcementPhase();
		playerOwnedContries = new ArrayList<Country>();
		continentListOfCountries = new ArrayList<Country>();
		
		country = new Country("C1");
		playerOwnedContries.add(country);
		continentListOfCountries.add(country);

		country = new Country("C2");
		playerOwnedContries.add(country);
		continentListOfCountries.add(country);

		country = new Country("C3");
		playerOwnedContries.add(country);
		continentListOfCountries.add(country);

		player = new Player();
		player.setMyCountries(playerOwnedContries);
		continent = new Continent("Europe", 2);

		continent.setListOfCountries(continentListOfCountries);
	}

	/**
	 * Test to validate number of armies when the whole continent is owned by the player
	 */

	@Test
	void testFindNoOfArmiesWhenPlayerOwnContinent() {
		assertEquals(continent.getControlValue(), reinforcementPhase.findNoOfArmies(player, continent));
	}

	
	/**
	 * Test to validate number of armies when player does not owns the continent
	 */
	@Test
	void testFindNoOfArmiesWhenPlayerDoesNotOwnContinent() {

		country = new Country("C4");
		continentListOfCountries.add(country);
		assertEquals(3, reinforcementPhase.findNoOfArmies(player, continent));
	}
}
