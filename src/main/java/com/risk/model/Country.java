package com.risk.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Class representing country data for game play.
 *
 * @author karandeep
 * @author palash
 */

public class Country {

    /** Name of country.*/
    private String name;

    /** Country holder*/
    private String player;

    /** Part of continent*/
    private String continent;

    /** X dimension*/
    private String xValue;

    /** Y dimension*/
    private String yValue;

    /** NUmber of armies*/
    private int noOfArmies;

    private ArrayList<Country> adjacentCountries;

    private Continent partOfContinent;

    /**
     * @param name
     * */
    public Country(String name) {
        this.name = name;
        adjacentCountries = new ArrayList<Country>();
    }

    /**
     * Get country name
     *
     * @return The country name
     * */
    public String getName() {
        return name;
    }

    /**
     * Set country name
     *
     * @param name The name to set
     * */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get Player name
     * @return player The name of player
     */
    public String getPlayer() {
        return player;
    }


    public void setPlayer(String player) {
        this.player = player;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getxValue() {
        return xValue;
    }

    public void setxValue(String xValue) {
        this.xValue = xValue;
    }

    public String getyValue() {
        return yValue;
    }

    public void setyValue(String yValue) {
        this.yValue = yValue;
    }

    public int getNoOfArmies() {
        return noOfArmies;
    }

    public void setNoOfArmies(int noOfArmies) {
        this.noOfArmies = noOfArmies;
    }

    public ArrayList<Country> getAdjacentCountries() {
        return adjacentCountries;
    }

    public void setAdjacentCountries(ArrayList<Country> adjacentCountries) {
        this.adjacentCountries = adjacentCountries;
    }

    public Continent getPartOfContinent() {
        return partOfContinent;
    }

    public void setPartOfContinent(Continent partOfContinent) {
        this.partOfContinent = partOfContinent;
    }

   @Override
    public boolean equals(Object obj) {
        if(obj==this)
            return true;
        if(!(obj instanceof Country))
            return false;
        return this.getName().toLowerCase().equals(((Country)obj).getName().toLowerCase());
    }

    @Override
    public int hashCode(){
        return getName().hashCode();
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
