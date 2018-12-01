package com.risk.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

	private ConnectedGraph connectedGraph1;

	/** Objects for Country Class */
	private Country country0,country1, country2, country3;

	/** Set to store countries */
	private Set<Country> countrySet;

	private Set<Country> countrySet1;

	/** ArrayList to store adjacent countries */
	private ArrayList<Country> adjacentCountries0;
	private ArrayList<Country> adjacentCountries1;
	private ArrayList<Country> adjacentCountries2;
	private ArrayList<Country> adjacentCountries3;

	/**
	 * Set up the initial objects for ConnectedGraph
	 * 
	 */
	@Before
	public void initialize() {

		country0= new Country("Australia");
		country1= new Country("India");
		country2 = new Country("China");
		country3 =new Country("Indonesia");

		countrySet = new HashSet<Country>();
		countrySet.add(country1);
		countrySet.add(country2);
		countrySet.add(country3);

		countrySet1 = new HashSet<Country>();
		countrySet1.add(country0);
		countrySet1.add(country3);

		adjacentCountries0 = new ArrayList<>();
		adjacentCountries1 = new ArrayList<>();
		adjacentCountries2 = new ArrayList<>();
		adjacentCountries3 = new ArrayList<>();

		adjacentCountries0.add(country2);

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

		connectedGraph1 = new ConnectedGraph(countrySet1);

	}

	/**
	 * Test method to check if a Graph is connected
	 */
	@Test
	public void isConnectedTest() {

		assertTrue(connectedGraph.isConnected());

	}

	/**
	 * Test method to check non-connectivity of graph 
	 */
	@Test
	public void isConnectedFalseTest(){

		assertEquals(false,connectedGraph1.isConnected());
	}

	/**
	 * Test method to check connectivity of subGraph
	 */
	@Test
	public void isConnectedSubGraphTest(){

		assertTrue(connectedGraph.isConnectedSubGraph());
	}

	/**
	 * Test method to check non-connectivity of subGraph
	 */
	@Test
	public void isConnectedSubGraphFalseTest(){

		assertEquals(false,connectedGraph1.isConnectedSubGraph());
	}

}
