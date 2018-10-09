package com.risk.services;

import com.risk.model.Continent;
import com.risk.model.Country;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapGraph {

    private HashMap<String, Continent> continents;

    private HashMap<Country, ArrayList<Country>> adjacentCountries;

    private int countOfCountries=0;
    
    private MapIO mapIO;
    
    public MapGraph(){}
    
	public MapGraph(MapIO mapIO) {
    	this.mapIO = mapIO;
        this.continents = mapIO.getContinents();
        this.adjacentCountries = mapIO.getAdjacentCountries();
    }

    public HashMap<String, Continent> getContinents() {
        return continents;
    }

    public void setContinents(HashMap<String, Continent> continents) {
        this.continents = continents;
    }

    public HashMap<Country, ArrayList<Country>> getAdjacentCountries() {
        return adjacentCountries;
    }

    public void setAdjacentCountries(HashMap<Country, ArrayList<Country>> adjacentCountries) {
        this.adjacentCountries = adjacentCountries;
    }

    public int getCountOfCountries() {
		return countOfCountries;
	}

	public void setCountOfCountries(int countOfCountries) {
		this.countOfCountries = countOfCountries;
	}
	
	public MapIO getMapIO() {
		return mapIO;
	}

	public void setMapIO(MapIO mapIO) {
		this.mapIO = mapIO;
	}


	public void addEdgeBetweenCountries(Country source, Country destination) {
    	if(adjacentCountries.containsKey(source)) {
    		adjacentCountries.get(source).add(destination);
    	}
    	
    	if(adjacentCountries.containsKey(destination)) {
    		adjacentCountries.get(destination).add(source);
    	}
    }
    
    public void deleteEdgeBetweenCountries(Country source, Country destination) {
    	if(adjacentCountries.containsKey(source)) {
    		adjacentCountries.get(source).remove(destination);
    	}
    	
    	if(adjacentCountries.containsKey(destination)) {
    		adjacentCountries.get(destination).remove(source);
    	}
    }
    
    public void addCountry(Country country) {
    	continents.get(country.getContinent()).addCountry(country);
    	adjacentCountries.put(country, country.getAdjacentCountries());
    }
    
    public boolean removeCountry(Country country) {
    	if(adjacentCountries.containsKey(country)) {
    		ArrayList<Country> neighbours = adjacentCountries.get(country);
    		for(Country adjacentCountry: neighbours) {
    			adjacentCountries.get(adjacentCountry).remove(country);
    		}
    		adjacentCountries.remove(country);
    		mapIO.getCountriesInContinent().get(country.getPartOfContinent()).remove(country);
    		mapIO.getCountrySet().remove(country.getName());
    		return true;
    	}
    	return false;
    }
    
    public void addContinent(Continent continent) {
    	continents.put(continent.getName(), continent);
    }
    
    public boolean removeContinent(Continent continent) {
    	if(continents.containsKey(continent.getName())) {
    		for(Country country: continent.getListOfCountries()) {
    			if(!removeCountry(country))
    				return false;
    		}
    		continents.remove(continent.getName());
    		return true;
    	}
    	return false;
    }
    
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
