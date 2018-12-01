package com.risk.strategy;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.risk.model.Country;
import com.risk.model.Player;
import com.risk.controller.GamePlayController;

public class BenevolentTest {
	
	private Country country;
	
	private Country adjCountry1;
	
	private Country adjCountry2;
	
	private ArrayList<Country> adjacentCountries;
	
	private Player player;

	private Benevolent benevolent;
	
	private GamePlayController gamePlayController;
	
	private List<Country> list;
	
	@Before
	public void initialize() {
		player = new Player();
		country = new Country("India");
		country.setPlayer(player);
		
		list = new ArrayList<Country>();
		list.add(country);
		
		adjCountry1 = new Country("China");
		adjCountry1.setPlayer(player);
		
		adjCountry2 = new Country("Malaysia");
		adjCountry2.setPlayer(player);
		
		adjacentCountries = new ArrayList<Country>();
		adjacentCountries.add(adjCountry1);
		adjacentCountries.add(adjCountry2);
		
		country.setAdjacentCountries(adjacentCountries);
		
		gamePlayController = new GamePlayController();
		benevolent = new Benevolent(gamePlayController);
	}
	
	@Test
	public void getStrongestAdjacentCountryTest() {
		adjCountry1.setNoOfArmies(3);
		assertEquals(adjCountry1,benevolent.getStrongestAdjacentCountry(country));
	}
	
	@Test
	public void getNullStrongestAdjacentCountryTest() {
		adjCountry1.setNoOfArmies(1);
		assertEquals(null,benevolent.getStrongestAdjacentCountry(country));
	}
	
	@Test
	public void checkAndFindWeakestIfNoAdjacentCountryToFortifyTest() {
		list.get(0).setNoOfArmies(3);
		adjCountry1.setNoOfArmies(3);
		System.out.println(benevolent.checkAndFindWeakestIfNoAdjacentCountryToFortify(list));
		assertEquals(list.get(0), benevolent.checkAndFindWeakestIfNoAdjacentCountryToFortify(list));
	}

}
