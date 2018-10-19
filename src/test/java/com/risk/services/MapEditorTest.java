package com.risk.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import com.risk.model.Continent;
import com.risk.model.Country;

/**
 * Test Class for MapEditor
 *
 * @author Neha Pal
 * @author Farhan Shaheen
 *
 */

public class MapEditorTest {

	/** Object for MapValidate Class */
	private MapValidate mapValidate;

	/** Object for MapIO Class */
	private MapIO mapIO;

	/** Object for MapEditor Class */
	private MapEditor mapEditor;

	/** Object for Continent Class */
	private Continent continent;

	/** Objects for Country Class */
	private Country country1, country2, country3;

	/** ArrayList to store list of countries */
	private ArrayList<Country> listOfCountries;

	/** HashSet to store unique Countries */
	private HashSet<Country> countries;

	/** HashMap to store counties in a continent */
	private HashMap<Continent, HashSet<Country>> countriesInContinent;

	/** HashMap to store adjacent counties */
	private HashMap<Country, ArrayList<Country>> adjacentCountries;

	/**
	 * Setup intial objects for MapEditor 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {

		continent = new Continent("Asia", 2);
		listOfCountries = new ArrayList<>();

		country1 = new Country("India");
		country2 = new Country("China");
		country3 = new Country("Indonesia");

		listOfCountries.add(country1);
		listOfCountries.add(country2);
		listOfCountries.add(country3);

		continent.setListOfCountries(listOfCountries);

		countries = new HashSet<Country>();
		countries.add(country1);
		countries.add(country2);
		countries.add(country3);

		countriesInContinent = new HashMap<Continent, HashSet<Country>>();
		countriesInContinent.put(continent, countries);

		mapValidate = new MapValidate();
		mapIO = new MapIO(mapValidate);
		mapIO.getMapGraph().setCountriesInContinent(countriesInContinent);

		adjacentCountries = new HashMap<Country, ArrayList<Country>>();
		adjacentCountries.put(country1, listOfCountries);
		adjacentCountries.put(country2, listOfCountries);
		adjacentCountries.put(country3, listOfCountries);

		mapIO.getMapGraph().setAdjacentCountries(adjacentCountries);

		mapEditor = new MapEditor(mapIO);

	}

	/** Test method for checking minimum number of countries in a continent.
	 * @throws Exception
	 */
	@Test
	public void testCheckMinimumCountriesInContinent() {

		assertTrue(mapEditor.checkMinimumCountriesInContinent());

	}

	/** Test method for checking if countries are adjacent.
	 * @throws Exception
	 */
	@Test
	public void testCheckCountriesAdjacent() {

		assertTrue(mapEditor.checkCountriesAreAdjacent());

	}
	
}
