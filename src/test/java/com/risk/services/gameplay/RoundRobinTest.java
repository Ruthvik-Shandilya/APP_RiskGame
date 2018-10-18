package com.risk.services.gameplay;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.risk.model.Continent;
import com.risk.model.Country;
import com.risk.model.Player;
import com.risk.services.MapIO;
import com.risk.services.MapValidate;
import com.risk.services.StartUpPhase;

/**
 * Test class for Round Robin.
 * 
 * @author Farhan Shaheen
 *
 */

public class RoundRobinTest {

	/** Object for RoundRobin class */
	private RoundRobin roundRobin;

	/** Object for Player class */
	Player player;
	Player player1;
	Player player2;
	/** Object for Country class */
	private Country country;

	/** Object for Continent class */
	private Continent continent;

	/** ArrayList to hold countries owned by the player */
	private ArrayList<Country> playerOwnedContries;

	/** ArrayList to hold list of countries in continent */
	private ArrayList<Country> continentListOfCountries;
	
	private ArrayList<Player> listOfPlayers;
	/**
	 * Set up the initial objects for Reinforcement Phase
	 * 
	 * @throws Exception
	 */

	@Before
	public void setUp() throws Exception {

		playerOwnedContries = new ArrayList<Country>();
		continentListOfCountries = new ArrayList<Country>();
		listOfPlayers = new ArrayList<Player>();
		player = new Player();
		
		country = new Country("C1");
		playerOwnedContries.add(country);
		continentListOfCountries.add(country);

		country = new Country("C2");
		playerOwnedContries.add(country);
		continentListOfCountries.add(country);

		player1 = new Player();
		player1.setMyCountries(playerOwnedContries);
		continent = new Continent("Europe", 2);
		
		country = new Country("C3");
		playerOwnedContries.add(country);
		continentListOfCountries.add(country);
		
		player2 = new Player();
		player2.setMyCountries(playerOwnedContries);
		continent = new Continent("Asia", 2);
		
		listOfPlayers.add(player1);
		listOfPlayers.add(player2);
		
		roundRobin = new RoundRobin(listOfPlayers);
	}

	/**
	* Test to return the next player.
	*/
	@Test
	public void testnext() throws Exception{
		player = roundRobin.next();
		System.out.println(player.getName());
		assertEquals(3, player.getName());
	}
}
