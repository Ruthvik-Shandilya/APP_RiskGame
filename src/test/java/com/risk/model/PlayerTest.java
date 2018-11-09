package com.risk.model;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.risk.services.MapIO;

import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Test class for Card.
 * 
 * @author Neha Pal
 *
 */

public class PlayerTest {
	
	/** Object for Player Class */
	private Player player1;
	
	/** Object for Player Class */
	private Player player2;

	/** Object for Player Class */
	private static Player player3;
	
	/** Object for Player Class */
	private static Player testPlayer;
	
	/** Object for Player Class */
	private Player playerPlaying;
	
	/** List to hold list of players */
	private List<Player> players;
	
	/** Object for Country Class */
	private Country attackingCountry;
	
	/** Object for Country Class */
	private Country defendingCountry;

	/** Object for Country Class */
	private Country country1;

	/** Object for Country Class */
	private Country country2;
	
	/** ArrayList to hold list of countries */
	private ArrayList<Country> myCountries;

	/** ArrayList to hold list of countries */
	private ArrayList<Country> playercountries;
	
	/** Object for Continent Class */
	private Continent continent1;

	/** Object for Continent Class */
	private Continent continent2;
	
	/** Object for MapIO CLass */
	private MapIO mapIO;

	/** The @fxPanel */
	@FXML
	private JFXPanel jfxPanel;

	/** The @textArea */
	@FXML
	private TextArea textArea;
	
	/**
	 * Set up the initial objects for PlayerTest
	 */
	@Before
	public void initialize() {
		
		mapIO = new MapIO();
		
		player1 = new Player("player1");
		player2 = new Player("player2");
		playerPlaying = new Player("playerPlaying");
		player3 = new Player();
		
		players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);
		
		attackingCountry = new Country("India");
		attackingCountry.setPlayer(player1);
	
		defendingCountry = new Country("China");
		defendingCountry.setPlayer(player2);
		
		continent1 = new Continent("Asia", 2);
		defendingCountry.setPartOfContinent(continent1);
		
		myCountries = new ArrayList<Country>();
		myCountries.add(defendingCountry);

		continent2 = new Continent("NorthAmerica",4);

		country1 = new Country("Canada");
		country1.setPartOfContinent(continent2);
		continent1.getListOfCountries().add(country1);

		country2 = new Country("America");
		country2.setPartOfContinent(continent2);
		continent1.getListOfCountries().add(country2);

		country1.getAdjacentCountries().add(country2);
		country2.getAdjacentCountries().add(country1);

		mapIO.getMapGraph().addContinent(continent2);
		
		continent2.setListOfCountries(myCountries);
		playerPlaying.setMyCountries(myCountries);

		playercountries = new ArrayList<>();
		playercountries.add(country1);
		playercountries.add(country2);
		continent2.setListOfCountries(playercountries);
		player1.setMyCountries(playercountries);

		jfxPanel = new JFXPanel();
		textArea= new TextArea();
		
	}
	
	/**
	 * Test to check if Attack move is valid
	 */
	@Test
	public void isAttackMoveValidTest() {
		attackingCountry.setNoOfArmies(3);
		assertTrue(player1.isAttackMoveValid(attackingCountry,defendingCountry));	
	}
	
	/**
	 * Test to check if player lost the game after attack move
	 */
	@Test
	public void checkPlayerLostTest()  {
		player1.setMyCountries(myCountries);
		player1.setPlayerPlaying(player1);
		assertEquals(player2.getName(),player1.checkPlayerLost(players).getName());	
	}
	
	/**
	 * Test to check if player did not lost the game after attack move
	 */
	@Test
	public void checkPlayerNotLostTest() {
		player1.setMyCountries(myCountries);
		player2.setMyCountries(myCountries);
		player1.setPlayerPlaying(player1);
		assertNull(player1.checkPlayerLost(players));	
	}
	
	/**
	 * Test to check calculation of number of reinforcement armies to be allocated to the player
	 */
	@Test
	public void noOfReinforcementArmiesTest() {
		assertEquals(playerPlaying,player1.noOfReinforcementArmies(playerPlaying));
	}

	/**
	 * Test to check invalid of fortification move
	 */
	@Test
	public void isFortificationValidTest(){
		country1.setPlayer(player1);
		country1.setNoOfArmies(3);
		country2.setPlayer(player1);
		assertEquals(true,player1.isFortificationPhaseValid(mapIO,player1));
	}

	/**
	 * Test to check valid fortification move
	 */
	@Test
	public void isFortificationValidFalse(){
		country1.setPlayer(player1);
		country1.setNoOfArmies(0);
		country2.setPlayer(player1);
		assertEquals(false,player1.isFortificationPhaseValid(mapIO,player1));
	}

	/**
	 * Test to check exchange of cards of the player for armies
	 */
	@Test
	public void exchangeCardsTest(){
		List<Card> listOfCards = new ArrayList<>();
		listOfCards.add(new Card(ICardType.ARTILLERY));
		listOfCards.add(new Card(ICardType.INFANTRY));
		listOfCards.add(new Card(ICardType.CAVALRY));
		testPlayer = player3.exchangeCards(listOfCards,1,textArea);
		assertEquals(5,testPlayer.getArmyCount());
	}
}