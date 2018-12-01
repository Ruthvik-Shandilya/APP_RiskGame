package com.risk.strategy;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.risk.controller.GamePlayController;
import com.risk.model.Country;
import com.risk.model.Player;

/**
 * Test class for Round Robin.
 * 
 * @author Farhan Shaheen
 * @author Neha Pal
 *
 */
public class BenevolentTest {

	/** Object for Country class */
	private Country country;

	/** Object for Country class */
	private Country adjCountry1;

	/** Object for Country class */
	private Country adjCountry2;

	/** ArrayList to hold adjacent countries */
	private ArrayList<Country> adjacentCountries;

	/** Object for Player class */
	private Player player;

	/** Object for Benevolent class */
	private Benevolent benevolent;

	/** Object for GamePlayController class */
	private GamePlayController gamePlayController;

	private List<Country> list;

	/**
	 * Set up the initial objects for Benevolent class
	 * 
	 */
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

	/**
	 * Test to check strongest adjacent country.
	 */
	@Test
	public void getStrongestAdjacentCountryTest() {
		adjCountry1.setNoOfArmies(3);
		assertEquals(adjCountry1,benevolent.getStrongestAdjacentCountry(country));
	}

	/**
	 * Test to check strongest adjacent country returns null
	 */
	@Test
	public void getNullStrongestAdjacentCountryTest() {
		adjCountry1.setNoOfArmies(1);
		assertEquals(null,benevolent.getStrongestAdjacentCountry(country));
	}

	/**
	 * Test to check check and find the weakest country if
     * no adjacent country to fortify
	 */
	@Test
	public void checkAndFindWeakestIfNoAdjacentCountryToFortifyTest() {
		list.get(0).setNoOfArmies(3);
		adjCountry1.setNoOfArmies(3);
		System.out.println(benevolent.checkAndFindWeakestIfNoAdjacentCountryToFortify(list));
		assertEquals(list.get(0), benevolent.checkAndFindWeakestIfNoAdjacentCountryToFortify(list));
	}

}
