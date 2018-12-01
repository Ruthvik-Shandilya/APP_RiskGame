package com.risk.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for Dice.
 * 
 * @author Farhan Shaheen
 *
 */
public class DiceTest {

	/** Object for Country class for attacking country */
	private Country attackingCountry;
	
	/** Object for Country class for defending country */
	private Country defendingCountry;
	
	/** Object for Dice class */
	private Dice dice;
	
	/** Object for Player class for player 1 */
	private Player player1;
	
	/** Object for Player class for player 2 */
	private Player player2;
	
	/** ArrayList to hold list of players countries */
	private ArrayList<Country> myCountries;
	
	private ArrayList<Country> myCountries1;
	
	/** ArrayList to hold play result */
	private ArrayList<String> playResult;
	
	/**
	 * Set up the initial objects for Round Robin Phase
	 * 
	 */
	@Before
	public void initialize() {
		
		player1 = new Player("Player1");
		attackingCountry = new Country("India");
		attackingCountry.setPlayer(player1);
		attackingCountry.setNoOfArmies(3);
		
		player2 = new Player("Player2");
		defendingCountry = new Country("China");
		
		myCountries = new ArrayList<>();
		myCountries.add(defendingCountry);
		player2.setMyCountries(myCountries);
		defendingCountry.setPlayer(player2);
		
		myCountries1 = new ArrayList<>();
		myCountries1.add(attackingCountry);
		player1.setMyCountries(myCountries1);
		
		defendingCountry.setNoOfArmies(1);
				
		dice = new Dice(attackingCountry, defendingCountry);
		
		dice.setDefendingCountry(defendingCountry);
		
		playResult = new ArrayList<>();
	}
	
	/**
	* Test to check if dice throw is possible.
	* 
	*/
	@Test
	public void checkDiceThrowPossibleTest() {
		dice.updateCountryList();
		assertEquals(player1, defendingCountry.getPlayer());
	}
	
	/**
	* Test to check country list update after dice throw.
	* 
	*/
	@Test
	public void updateCountryListTest() {
		assertTrue(dice.checkDiceThrowPossible());
	}
	
	/**
	* Test to check update armies after attack defender lost.
	* 
	*/
	@Test
	public void updateArmiesAfterAttackDefenderLostTest() {
		dice.updateArmiesAfterAttack(2, 3, playResult);
		assertEquals("Defender has lost one army.",playResult.get(0));
	}
	
	/**
	* Test to check update armies after attack attacker lost.
	* 
	*/
	@Test
	public void updateArmiesAfterAttackAttackerLostTest() {
		dice.updateArmiesAfterAttack(3, 2, playResult);
		assertEquals("Attacker has lost one army.",playResult.get(0));
	}


}
