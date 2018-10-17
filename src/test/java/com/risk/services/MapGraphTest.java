package com.risk.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.risk.model.Continent;
import com.risk.model.Country;

public class MapGraphTest {

	private Continent continent;

	private MapGraph mapGraph;

	private Country country;

	private HashMap<Country, ArrayList<Country>> adjacentCountries;

	private ArrayList<Country> countryList;

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

	@Test
	public void testAddContinent() {
		mapGraph.addContinent(continent);
		assertEquals(mapGraph.getContinents().get("Asia").getControlValue(), continent.getControlValue());
	}

	@Test
	public void testRemoveContinent() {
		mapGraph.addContinent(continent);
		assertTrue(mapGraph.removeContinent(continent));
	}
	
	 
	 
}
