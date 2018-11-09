package com.risk.model;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.risk.services.MapIO;


public class PlayerTest {
	
	private Player player1;
	
	private Player player2;
	
	private Player playerPlaying;
	
	private List<Player> players;
	
	private Country attackingCountry;
	
	private Country defendingCountry;

	private Country country1;

	private Country country2;
	
	private ArrayList<Country> myCountries;

	private ArrayList<Country> playercountries;
	
	private Continent continent;
	
	private MapIO mapIO;
	
	
	@Before
	public void initialize() {
		
		mapIO = new MapIO();
		
		player1 = new Player("player1");
		player2 = new Player("player2");
		playerPlaying = new Player("playerPlaying");
		
		players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);
		
		attackingCountry = new Country("India");
		attackingCountry.setPlayer(player1);
	
		defendingCountry = new Country("China");
		defendingCountry.setPlayer(player2);
		
		continent = new Continent("Asia", 2);
		//defendingCountry.setPartOfContinent(continent);
		
		myCountries = new ArrayList<Country>();
		myCountries.add(defendingCountry);

		country1 = new Country("Canada");
		country1.setPartOfContinent(continent);
		continent.getListOfCountries().add(country1);

		country2 = new Country("America");
		country2.setPartOfContinent(continent);
		continent.getListOfCountries().add(country2);

		country1.getAdjacentCountries().add(country2);
		country2.getAdjacentCountries().add(country1);

		mapIO.getMapGraph().addContinent(continent);
		
		continent.setListOfCountries(myCountries);
		playerPlaying.setMyCountries(myCountries);

		playercountries = new ArrayList<>();
		playercountries.add(country1);
		playercountries.add(country2);
		player1.setMyCountries(playercountries);

		
	}
	
	@Test
	public void isAttackMoveValidTest() {
		attackingCountry.setNoOfArmies(3);
		assertTrue(player1.isAttackMoveValid(attackingCountry,defendingCountry));	
	}
	
	@Test
	public void checkPlayerLostTest()  {
		player1.setMyCountries(myCountries);
		player1.setPlayerPlaying(player1);
		assertEquals(player2.getName(),player1.checkPlayerLost(players).getName());	
	}
	
	@Test
	public void checkPlayerNotLostTest() {
		player1.setMyCountries(myCountries);
		player2.setMyCountries(myCountries);
		player1.setPlayerPlaying(player1);
		assertNull(player1.checkPlayerLost(players));	
	}
	
	@Test
	public void noOfReinforcementArmiesTest() {
		assertEquals(playerPlaying,player1.noOfReinsforcementArmies(playerPlaying));
	}

//	@Test
//	public void isFortificationValidTest(){
//		country1.setPlayer(player1);
//		country1.setNoOfArmies(3);
//		country2.setPlayer(player1);
//		assertEquals(true,player1.isFortificationPhaseValid(mapIO,player1));
//	}
}