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

	private Country attackingCountry;
	
	private Country defendingCountry;
	
	private Dice dice;
	
	private Player player1;
	
	private Player player2;
	
	private ArrayList<Country> myCountries;
	
	private ArrayList<String> playResult;
	
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
		
		defendingCountry.setNoOfArmies(1);
				
		dice = new Dice(attackingCountry, defendingCountry);
		
		playResult = new ArrayList<>();
	}
	
	@Test
	public void checkDiceThrowPossibleTest() {
		dice.updateCountryList();
		assertEquals(player1, defendingCountry.getPlayer());
	}
	
	@Test
	public void updateCountryListTest() {
		assertTrue(dice.checkDiceThrowPossible());
	}
	
	@Test
	public void updateArmiesAfterAttackDefenderLostTest() {
		dice.updateArmiesAfterAttack(2, 3, playResult);
		assertEquals("Defender has lost one army.",playResult.get(0));
	}
	
	@Test
	public void updateArmiesAfterAttackAttackerLostTest() {
		dice.updateArmiesAfterAttack(3, 2, playResult);
		assertEquals("Attacker has lost one army.",playResult.get(0));
	}


}
