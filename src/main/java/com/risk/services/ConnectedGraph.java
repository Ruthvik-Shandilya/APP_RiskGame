package com.risk.services;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.risk.model.Country;

/**
 * Class containing the method to check whether a map is a connected graph or
 * not
 * 
 * @author Karandeep Singh
 * @author Ruthvik Shandilya
 * 
 */

public class ConnectedGraph {

	/** HashMap for marking a country as visited or not */
	private HashMap<Country, Boolean> visited;

	/** Set containing countries */
	private Set<Country> countrySet;

	/**
	 * Constructor for ConnectedGraph class, which sets initial data for the
	 * class. Moreover, it sets a the countries visited value as false.
	 * 
	 * @param countrySet
	 *            Set containing all the countries.
	 * 
	 */
	public ConnectedGraph(Set<Country> countrySet) {
		this.countrySet = countrySet;
		this.visited = new HashMap<>();
		Iterator<Country> iterator = this.countrySet.iterator();
		while (iterator.hasNext()) {
			visited.put(iterator.next(), false);
		}
	}

	/**
	 * Method for traversing the graph for depth first traversal
	 * 
	 * @param startCountry
	 * 
	 */
	private void depthFirstTraversal(Country startCountry) {
		visited.put(startCountry, true);
		Iterator<Country> it = startCountry.getAdjacentCountries().iterator();
		while (it.hasNext()) {
			Country adjacentCountry = it.next();
			if (!visited.get(adjacentCountry)) {
				depthFirstTraversal(adjacentCountry);
			}
		}
	}

	/**
	 * Method for checking if the graph is connected or not.
	 * 
	 * @return true if map is connect; else false.
	 * 
	 */
	public boolean isConnected() {
		depthFirstTraversal(countrySet.iterator().next());
		Iterator<Country> it = countrySet.iterator();
		while (it.hasNext()) {
			Country country = it.next();
			if (visited.get(country) == false) {
				System.out.println("Map is not a connected graph.");
				return false;
			}
		}
		return true;
	}
}