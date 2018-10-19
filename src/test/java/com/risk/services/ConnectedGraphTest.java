package com.risk.services;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.risk.model.Country;

public class ConnectedGraphTest {

	/**Object for ConnectedGraph Class*/
	private ConnectedGraph connectedGraph;
	
	/** Objects for Country Class */
	private Country country1, country2, country3;
	
	/**Set to store countries*/
	private Set<Country> countrySet;
	
	/**ArrayList to store adjacent countries*/
	private ArrayList<Country> adjacentCountries;
		
	/**
	* Set up the initial objects for ConnectedGraph
	* @throws Exception
	*/
	@Before
	public void setUp() throws Exception {
		
		country1= new Country("India");
		country2 = new Country("China");
		country3 =new Country("Indonesia");
		
		countrySet = new HashSet<Country>();
		countrySet.add(country1);
		countrySet.add(country2);
		countrySet.add(country3);
		
		adjacentCountries = new ArrayList<>();
		adjacentCountries.add(country1);
		adjacentCountries.add(country2);
		adjacentCountries.add(country3);
		
		connectedGraph = new ConnectedGraph(countrySet);
		country1.setAdjacentCountries(adjacentCountries);
		country2.setAdjacentCountries(adjacentCountries);
		country3.setAdjacentCountries(adjacentCountries);
	}

	@Test
	public void testIsConnected() {
		
		assertTrue(connectedGraph.isConnected());
		
	}

}
