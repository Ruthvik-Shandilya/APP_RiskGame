package com.risk.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.risk.model.Continent;
import com.risk.model.Country;

/**
 * Test Class for MapValidation
 *
 * @author Farhan Shaheen
 *
 */
public class MapGraphTest {

	/** Object for Continent class */
	private Continent continent;

	private MapValidate mapValidate;

	/** Object for MapGraph class */
	private MapGraph mapGraph;

	/** Object for Country class */
	private Country country;

	private Country country2;

	/** Adjacent countries */
	private HashMap<Country, ArrayList<Country>> adjacentCountries;

	private HashMap<String,Continent> continents;

	private HashMap<String,Continent> countrySet;

	/** List of countries */
	private ArrayList<Country> countryList;

	/**
	 * Set up the initial objects for MapGraph
	 * 
	 */
	@Before
	public void initialize() {
		System.out.println("Inside Initialize");
		continent = new Continent("Asia", 2);
		continents = new HashMap<>();
		countrySet = new HashMap<>();
		mapGraph = new MapGraph();
		mapValidate = new MapValidate();
		country = new Country("India");
		country2 = new Country("China");
		country.setContinent("Asia");
		country2.setContinent("Asia");
		countryList = new ArrayList<Country>();
		countryList.add(country);
		countryList.add(country2);
		adjacentCountries = new HashMap<Country, ArrayList<Country>>();
		adjacentCountries.put(country, countryList);
		adjacentCountries.put(country2,countryList);
	}

	/**
	 * Test method for testing adding a continent from the map
	 * 
	 */
	@Test
	public void addContinentTest() {
		mapGraph.addContinent(continent);
		assertEquals(mapGraph.getContinents().get("Asia").getControlValue(), continent.getControlValue());
	}

	/**
	 * Test method for testing removing a continent from the map
	 *
	 */
	@Test
	public void removeContinentTest() {
		mapGraph.addContinent(continent);
		assertTrue(mapGraph.removeContinent(continent));
	}

	@Test
	public void removeCountryTest(){

		countrySet.put("India",continent);
		countrySet.put("China",continent);
		continents.put("Asia",continent);
		continent.setListOfCountries(countryList);
		mapGraph.setAdjacentCountries(adjacentCountries);
		mapGraph.setContinents(continents);
		assertEquals(true,mapGraph.removeCountry(country));
	}

}
