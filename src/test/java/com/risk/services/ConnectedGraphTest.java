package com.risk.services;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.risk.model.Country;
/**
 * Test Class for MapValidation
 *
 * @author Neha Pal
 *
 */
public class ConnectedGraphTest {

	/** Object for ConnectedGraph Class */
	private ConnectedGraph connectedGraph;

	/** Objects for Country Class */
	private Country country1, country2, country3;

	/** Set to store countries */
	private Set<Country> countrySet;

	/** ArrayList to store adjacent countries */
	private ArrayList<Country> adjacentCountries1;
	private ArrayList<Country> adjacentCountries2;
	private ArrayList<Country> adjacentCountries3;

	/**
	 * Set up the initial objects for ConnectedGraph
	 * 
	 */
	@Before
	public void initialize() {

		country1= new Country("India");
		country2 = new Country("China");
		country3 =new Country("Indonesia");

		countrySet = new HashSet<Country>();
		countrySet.add(country1);
		countrySet.add(country2);
		countrySet.add(country3);

		adjacentCountries1 = new ArrayList<>();
		adjacentCountries2 = new ArrayList<>();
		adjacentCountries3 = new ArrayList<>();

		adjacentCountries1.add(country2);
		adjacentCountries1.add(country3);

		adjacentCountries2.add(country1);
		adjacentCountries2.add(country3);

		adjacentCountries3.add(country1);
		adjacentCountries3.add(country2);

		connectedGraph = new ConnectedGraph(countrySet);
		country1.setAdjacentCountries(adjacentCountries1);
		country2.setAdjacentCountries(adjacentCountries2);
		country3.setAdjacentCountries(adjacentCountries3);

		System.out.println(country1.getAdjacentCountries());
	}

	/**
	 * Test method to check if a Graph is connected
	 */
	@Test
	public void isConnectedTest() {

		assertTrue(connectedGraph.isConnected());

	}

	@Test
	public void isConnectedSubGraphTest(){

		assertTrue(connectedGraph.isConnectedSubGraph());
	}

}
