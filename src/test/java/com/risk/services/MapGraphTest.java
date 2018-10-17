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

	/** Object for Continent class*/
	private Continent continent;
	/** Object for MapGraph class*/
	private MapGraph mapGraph;
	/** Object for Country class*/
	private Country country;
	/** Adjacent countries*/
	private HashMap<Country, ArrayList<Country>> adjacentCountries;
	/** List of countries*/
	private ArrayList<Country> countryList;

	/**
	* Set up the initial objects for MapGraph
	* @throws Exception
	*/
	@Before
	public void setUp() throws Exception {
		continent = new Continent("Asia", 2);
		mapGraph = new MapGraph();
		country = new Country("India");
		country.setContinent("Asia");
		countryList = new ArrayList<Country>();
		countryList.add(country);
		adjacentCountries = new HashMap<Country, ArrayList<Country>>();
		adjacentCountries.put(country, countryList);

	}

	/**
	* Test method for testing adding a continent from the map
	* @throws Exception
	*/
	@Test
	public void testAddContinent() throws Exception{
		mapGraph.addContinent(continent);
		assertEquals(mapGraph.getContinents().get("Asia").getControlValue(), continent.getControlValue());
	}

	/**
	* Test method for testing removing a continent from the map
	* @throws Exception
	*/
	@Test
	public void testRemoveContinent() throws Exception{
		mapGraph.addContinent(continent);
		assertTrue(mapGraph.removeContinent(continent));
	}
	
	 
	 
}
