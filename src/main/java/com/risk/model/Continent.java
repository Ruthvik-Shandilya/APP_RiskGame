package com.risk.model;

import java.util.ArrayList;

public class Continent {

    private String name;

    private int controlValue;

    private ArrayList<Country> listOfCountries;

    public Continent(String name, int controlValue) {
        this.name = name;
        this.controlValue = controlValue;
        listOfCountries = new ArrayList<Country>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getControlValue() {
        return controlValue;
    }

    public void setControlValue(int controlValue) {
        this.controlValue = controlValue;
    }

    public ArrayList<Country> getListOfCountries() {
        return listOfCountries;
    }

    public void addCountry(Country country){
        listOfCountries.add(country);
    }

    public void setListOfCountries(ArrayList<Country> listOfCountries) {
        this.listOfCountries = listOfCountries;
    }

    public void deleteCountry(Country country){
        listOfCountries.remove(country);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==this)
            return true;
        if(!(obj instanceof Continent))
            return false;
        return this.getName().toLowerCase().equals(((Continent)obj).getName().toLowerCase());
    }

    @Override
    public int hashCode(){
        return getName().toLowerCase().hashCode();
    }
}
