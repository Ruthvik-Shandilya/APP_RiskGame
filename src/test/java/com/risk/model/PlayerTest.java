package com.risk.model;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.risk.exception.InvalidGameMoveException;
import com.risk.model.Continent;
import com.risk.model.Country;
import com.risk.model.Player;
import com.risk.services.MapIO;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class PlayerTest {
	
	private Player player1;
	
	private Player player2;
	
	private Player playerPlaying;
	
	private List<Player> players;
	
	private Country attackingCountry;
	
	private Country defendingCountry;
	
	private ArrayList<Country> myCountries;
	
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
		defendingCountry.setPartOfContinent(continent);
		
		myCountries = new ArrayList<Country>();
		myCountries.add(defendingCountry);
		
		continent.setListOfCountries(myCountries);
		playerPlaying.setMyCountries(myCountries); 
		
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
	public void noOfReinsforcementArmiesTest() {
		assertEquals(playerPlaying,player1.noOfReinsforcementArmies(playerPlaying));
	}
	
}