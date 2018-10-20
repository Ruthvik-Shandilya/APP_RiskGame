package com.risk.services;

import com.risk.model.Continent;
import com.risk.model.Country;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This MapGraph class contains all the methods to add , remove countries ,
 * continents and also to add , delete edges between countries. This also
 * contains methods to set and get the adjacent countries list.
 * 
 * @author Karandeep Singh
 * @author Ruthvik Shandilya
 *
 */
public class MapGraph {

	/** HashMap to store the continent names */
	private HashMap<String, Continent> continents;

	/** HashMap to store the list of adjacent countries */
	private HashMap<Country, ArrayList<Country>> adjacentCountries;

	/** HashMap for set of countries */
	private HashMap<String, Country> countrySet;

	/** Count of the number of countries */
	private int countOfCountries = 0;

	/**
	 * MapGraph constructor
	 */
	public MapGraph() {
		this.continents = new HashMap<>();
		this.adjacentCountries = new HashMap<>();
		this.countrySet = new HashMap<>();
	}

	/**
	 * Method to get the continents
	 * 
	 * @return continents
	 */
	public HashMap<String, Continent> getContinents() {
		return continents;
	}

	/**
	 * Method to set a continent
	 * 
	 * @param continents
	 *            Name of the continent
	 */
	public void setContinents(HashMap<String, Continent> continents) {
		this.continents = continents;
	}

	/**
	 * Method to get the list of adjacent countries.
	 * 
	 * @return adjacent countries
	 */
	public HashMap<Country, ArrayList<Country>> getAdjacentCountries() {
		return adjacentCountries;
	}

	/**
	 * Method to set the adjacent countries
	 * 
	 * @param adjacentCountries countries which are adjacent to eachother
	 */
	public void setAdjacentCountries(HashMap<Country, ArrayList<Country>> adjacentCountries) {
		this.adjacentCountries = adjacentCountries;
	}

	/**
	 * Method to get the count of the countries.
	 * 
	 * @return No of countries
	 */
	public int getCountOfCountries() {
		return countOfCountries;
	}

	/**
	 * Method to set the count of the countries.
	 * 
	 * @param countOfCountries Number of countries
	 */
	public void setCountOfCountries(int countOfCountries) {
		this.countOfCountries = countOfCountries;
	}

	/**
	 * Method to get the countries from a set
	 * 
	 * @return countrySet
	 */
	public HashMap<String, Country> getCountrySet() {
		return countrySet;
	}

	/**
	 * Method to the set the country in a set.
	 * 
	 * @param countrySet Hashset to store country names
	 */
	public void setCountrySet(HashMap<String, Country> countrySet) {
		this.countrySet = countrySet;
	}

	/**
	 * Method to add an edge between countries.
	 * 
	 * @param source Country
	 *            
	 * @param destination Country
	 *            
	 * 
	 */
	public void addEdgeBetweenCountries(Country source, Country destination) {
		if (adjacentCountries.containsKey(source)) {
			adjacentCountries.get(source).add(destination);
		}

		if (adjacentCountries.containsKey(destination)) {
			adjacentCountries.get(destination).add(source);
		}
	}

	/**
	 * Method to delete an edge between countries
	 * 
	 * @param source
	 *            Country
	 * 
	 * @param destination
	 *            Country
	 * 
	 */
	public void deleteEdgeBetweenCountries(Country source, Country destination) {
		if (adjacentCountries.containsKey(source)) {
			adjacentCountries.get(source).remove(destination);
		}

		if (adjacentCountries.containsKey(destination)) {
			adjacentCountries.get(destination).remove(source);
		}
	}

	/**
	 * Method to add a country to the Graph
	 * 
	 * @param country
	 *            Object
	 * 
	 */
	public void addCountry(Country country) {
		continents.get(country.getContinent()).addCountry(country);
		adjacentCountries.put(country, country.getAdjacentCountries());
		countrySet.put(country.getName(), country);
	}

	/**
	 * Method to remove a country
	 * 
	 * @param country
	 *            Object
	 * 
	 * @return True if country is removed successfully
	 */
	public boolean removeCountry(Country country) {
		if (adjacentCountries.containsKey(country)) {
			ArrayList<Country> neighbours = adjacentCountries.get(country);
			for (Country adjacentCountry : neighbours) {
				adjacentCountries.get(adjacentCountry).remove(country);
			}
			adjacentCountries.remove(country);
			continents.get(country.getContinent()).getListOfCountries().remove(country);
			countrySet.remove(country.getName());
			return true;
		}
		return false;
	}

	/**
	 * Method to add a continent
	 * 
	 * @param continent
	 *            Object
	 * 
	 */
	public void addContinent(Continent continent) {
		continents.put(continent.getName(), continent);
	}

	/**
	 * Method to remove a continent
	 * 
	 * @param continent
	 *            Object
	 * 
	 * @return True if continent is removed successfully
	 * 
	 */
	public boolean removeContinent(Continent continent) {
		if (continents.containsKey(continent.getName())) {
			for (Country country : continent.getListOfCountries()) {
				if (!removeCountry(country))
					return false;
			}
			continents.remove(continent.getName());
			return true;
		}
		return false;
	}

	/**
	 * Method to check if there exists an adjacency between two countries
	 * 
	 * @param country1
	 *            Object
	 * 
	 * @param country2
	 *            Object
	 * 
	 * @return True if exists an adjacency
	 */
	public boolean checkAdjacency(Country country1, Country country2) {
		for (Map.Entry<Country, ArrayList<Country>> countries : adjacentCountries.entrySet()) {
			Country checkCountry = countries.getKey();
			ArrayList<Country> neighbours = countries.getValue();
			for (Country adjacent : neighbours) {
				if (!adjacentCountries.get(adjacent).contains(checkCountry)) {
					return false;
				}
			}
		}
		return true;
	}

}
