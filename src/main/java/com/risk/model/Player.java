package com.risk.model;

import java.util.ArrayList;
/**
 * Player class which provides the information regarding the player.
 * 
 * @author Ruthvik Shandilya
 *
 */
public class Player {
	
	/** Name of the Player */
    private String name;
    
    /**Initial army count of the Player*/
    private int armyCount=0;
    
    /** List of countries held by the Player */
    private ArrayList<Country> myCountries;
    
    /** List of cards the Player holds.*/
    private ArrayList<ICardType> listOfCards;
    
    /** Player constructor */
    public Player() {
        this.myCountries = new ArrayList<Country>();
        this.listOfCards = new ArrayList<ICardType>();
    }
    /**
     * Get the name of the Player.
     * 
     * @return name
     *			Player name
     */
    public String getName() {
        return name;
    }
    /**
     * Set the name of the Player
     * 
     * @param name
     * 			Player name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Method to get the Count of the Army that the Player Holds.
     * 
     * @return armies count
     */
    public int getArmyCount() {
        return armyCount;
    }
    /**
     * Method to set the count of the Army
     * 
     * @param armyCount
     * 
     */
    public void setArmyCount(int armyCount) {
        this.armyCount = armyCount;
    }
    /**
     * Get the list of countries the player holds
     * 
     * @return
     * 		list of countries
     */
    public ArrayList<Country> getMyCountries() {
        return myCountries;
    }
    /**
     * Method to set the country list for player
     * 
     * @param myCountries
     * 				list of countries
     */
    public void setMyCountries(ArrayList<Country> myCountries) {
        this.myCountries = myCountries;
    }
    /**
     *  Method to add a single country to the player's country list
     *  
     * @param country
     * 				Object of the country
     */
    public void addCountry(Country country){
        this.myCountries.add(country);
    }
    /**
     * Method to get the list of cards the player holds
     * 
     * @return list of cards
     */
    public ArrayList<ICardType> getListOfCards() {
        return listOfCards;
    }
    /**
     * Method to set the list of card of the particular type
     * 
     * @param listOfCards
     */
    public void setListOfCards(ArrayList<ICardType> listOfCards) {
        this.listOfCards = listOfCards;
    }
    /**
     * Method to add the armies to a country
     * 
     * @param country
     * 				name of the country
     * @param numberOfArmies
     * 				number of armies to be added
     */
    public void addArmiesToCountry(Country country, int numberOfArmies) {
    	if(this.getArmyCount()>0 && this.getArmyCount()>=numberOfArmies) {
    		if(!this.getMyCountries().contains(country)) {
    			System.out.println("This country is not under your territory.");
    		}
    		else {
    			country.setNoOfArmies(country.getNoOfArmies() + numberOfArmies);
    			this.setArmyCount(this.getArmyCount() - numberOfArmies);
    		}
    	}
    	else {
    		System.out.println("Sufficient number of armies not available.");
    	}
    }
}
