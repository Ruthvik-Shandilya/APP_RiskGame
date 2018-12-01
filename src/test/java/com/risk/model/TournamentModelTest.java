package com.risk.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk.services.MapIO;
import com.risk.strategy.Aggressive;
import com.risk.strategy.Benevolent;
import com.risk.strategy.Random;

import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class TournamentModelTest {
	
	private Player player1;
	
	private Player player2;
	
	private Player player3;
	
	private TournamentModel tournamentModel;
	
	private HashMap<Player,Integer> results;
	
	private HashMap<String,Country> countrySet;
	
	private ArrayList<Country> myCountries1;
	
	private ArrayList<Country> myCountries2;
	
	private ArrayList<Country> myCountries3;
	
	@FXML
	private TextArea textArea;

	@FXML
	private JFXPanel jfxPanel;
	
	
	
	@Before
	public void initialize() {
		
		player1 = new Player("player1");
		player2 = new Player("player2");
		player3 = new Player("player3");
		
		tournamentModel = new TournamentModel();
		
		results = new HashMap<>();
		
		countrySet = new HashMap<>();
		
		myCountries1 = new ArrayList<>();
		myCountries2 = new ArrayList<>();
		myCountries3 = new ArrayList<>();
		
	}
	
	@Test
	public void playGameTest() {
		
		MapIO map = new MapIO();
		Continent continent = new Continent("Asia",5);
		Country country1 = new Country("India");
		Country country2 = new Country("Canada");
		country1.setPartOfContinent(continent);
		continent.getListOfCountries().add(country1);
		country2.setPartOfContinent(continent);
		continent.getListOfCountries().add(country2);
		country1.getAdjacentCountries().add(country2);
		country2.getAdjacentCountries().add(country1);
		
		Continent continent2 = new Continent("Antartica",8);
		Country country3 = new Country("America");
		country3.setPartOfContinent(continent2);
		country3.getAdjacentCountries().add(country1);
		country1.getAdjacentCountries().add(country3);
		
		continent2.getListOfCountries().add(country3);
		map.getMapGraph().addContinent(continent);
		map.getMapGraph().addContinent(continent2);
		
		countrySet.put(country1.getName(), country1);
		countrySet.put(country2.getName(), country2);
		countrySet.put(country3.getName(), country3);
		
		country1.setPlayer(player1);
		country2.setPlayer(player2);
		country3.setPlayer(player3);
		
		myCountries1.add(country1);
		myCountries2.add(country2);
		myCountries3.add(country3);
		
		player1.setMyCountries(myCountries1);
		player2.setMyCountries(myCountries2);
		player3.setMyCountries(myCountries3);
		
		jfxPanel = new JFXPanel();
		textArea= new TextArea();
		
		int numberOfTurnsToPlay = 15;
		int gameCount = 1;
		
		player1.setArmyCount(15);
		player1.setPlayerBehaviour(new Random());
		
		player2.setArmyCount(20);
		player2.setPlayerBehaviour(new Aggressive());
		
		player3.setArmyCount(25);
		player3.setPlayerBehaviour(new Benevolent());
		
		ArrayList<Player> listOfPlayers = new ArrayList<>();
		listOfPlayers.add(player1);
		listOfPlayers.add(player2);
		listOfPlayers.add(player3);
		
		Player.currentPlayer = player1;
		
		results = tournamentModel.playGame(listOfPlayers, numberOfTurnsToPlay, gameCount, map, textArea);
		
		Assert.assertNotNull(results);
	}

}
