package com.risk.model;

import java.util.ArrayList;

/**
 * Class representing country data for game play.
 *
 * @author Karandeep Singh
 * @author Palash Jain
 */

public class Country {

	/** Name of country. */
	private String name;

	/** Country holder */
	private Player player;

	/** Part of continent */
	private String continent;

	/** X dimension */
	private String xValue;

	/** Y dimension */
	private String yValue;

	/** Number of armies */
	private int noOfArmies;

	/** Adjacent Country holder */
	private ArrayList<Country> adjacentCountries;

	/** Part of Continent */
	private Continent partOfContinent;

	/**
	 * Country constructor.
	 * 
	 * @param name
	 *            name of the country
	 */
	public Country(String name) {
		this.name = name;
		adjacentCountries = new ArrayList<Country>();
	}

	/**
	 * Get country name
	 *
	 * @return The country name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set country name
	 *
	 * @param name
	 *            The name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get Player name
	 * 
	 * @return player The name of player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Set the name of the Player.
	 * 
	 * @param player
	 *            name of the player.
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Get the continent name.
	 * 
	 * @return name of the continent
	 */
	public String getContinent() {
		return continent;
	}

	/**
	 * Set the name of the continent
	 * 
	 * @param continent
	 *            name of the continent
	 */
	public void setContinent(String continent) {
		this.continent = continent;
	}

	/**
	 * get the value of X coordinate of the map
	 * 
	 * @return X coordinate value
	 */
	public String getxValue() {
		return xValue;
	}

	/**
	 * Set the value of X coordinate
	 * 
	 * @param xValue
	 *            X cooridnate value
	 */
	public void setxValue(String xValue) {
		this.xValue = xValue;
	}

	/**
	 * get the value of Y coordinate of the map
	 * 
	 * @return Y coordinate value
	 */
	public String getyValue() {
		return yValue;
	}

	/**
	 * Set the value of Y coordinate
	 * 
	 * @param yValue
	 *            Y cooridnate value
	 */
	public void setyValue(String yValue) {
		this.yValue = yValue;
	}

	/**
	 * Method to get the initial number of armies in a country
	 * 
	 * @return Integer value of number of armies
	 */
	public int getNoOfArmies() {
		return noOfArmies;
	}

	/**
	 * Method to set the initial number of armies in a country
	 * 
	 * @param noOfArmies
	 *            Integer value of number of armies
	 */
	public void setNoOfArmies(int noOfArmies) {
		this.noOfArmies = noOfArmies;
	}

	/**
	 * Method to get the list of Adjacent countries.
	 * 
	 * @return list of adjacent countries
	 */
	public ArrayList<Country> getAdjacentCountries() {
		return adjacentCountries;
	}

	/**
	 * Method to set the adjacent countries.
	 * 
	 * @param adjacentCountries
	 *            List of adjacent countries
	 */
	public void setAdjacentCountries(ArrayList<Country> adjacentCountries) {
		this.adjacentCountries = adjacentCountries;
	}

	/**
	 * Method to check whether the country is part of the continent
	 * 
	 * @return continent name
	 */
	public Continent getPartOfContinent() {
		return partOfContinent;
	}

	/**
	 * Method to set the country as part of this continent
	 * 
	 * @param partOfContinent
	 *            Continent name
	 */
	public void setPartOfContinent(Continent partOfContinent) {
		this.partOfContinent = partOfContinent;
	}
	
	/**
	 * {@inheritDoc} Used to check if names of two countries are same.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Country)) {
			return false;
		}
		return this.getName().toLowerCase().trim().equals(((Country) obj).getName().toLowerCase().trim());
	}

	/**
	 * {@inheritDoc} returns the hashcode for country's name.
	 */
	@Override
	public int hashCode() {
		return getName().hashCode();
	}

	/**
	 * {@inheritDoc} returns the name of the country.
	 */
	@Override
	public String toString() {
		return this.getName();
	}
}
