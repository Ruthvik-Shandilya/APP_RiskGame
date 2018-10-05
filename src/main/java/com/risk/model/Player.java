package com.risk.model;

import java.util.ArrayList;

public class Player {
    private String name;

    private int armyCount;

    private ArrayList<Country> myCountries;

    private ArrayList<ICardType> listOfCards;


    public Player(ArrayList<Country> myCountries) {
        this.myCountries = myCountries;
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
}
