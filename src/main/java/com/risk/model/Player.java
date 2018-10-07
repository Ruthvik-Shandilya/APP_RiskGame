package com.risk.model;

import java.util.ArrayList;

public class Player {
    private String name;

    private int armyCount=0;

    private ArrayList<Country> myCountries;

    private ArrayList<ICardType> listOfCards;


    public Player() {
        this.myCountries = new ArrayList<Country>();
        this.listOfCards = new ArrayList<ICardType>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArmyCount() {
        return armyCount;
    }

    public void setArmyCount(int armyCount) {
        this.armyCount = armyCount;
    }

    public ArrayList<Country> getMyCountries() {
        return myCountries;
    }


    public void setMyCountries(ArrayList<Country> myCountries) {
        this.myCountries = myCountries;
    }

    public void addCountry(Country country){
        this.myCountries.add(country);
    }

    public ArrayList<ICardType> getListOfCards() {
        return listOfCards;
    }

    public void setListOfCards(ArrayList<ICardType> listOfCards) {
        this.listOfCards = listOfCards;
    }
    
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
