package com.risk.services;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.risk.model.Country;

public class ConnectedGraph {
	
    private HashMap<Country,Boolean> visited;
    private Set<Country> countrySet;
    
    public ConnectedGraph(Set<Country> countrySet) {
    	this.countrySet = countrySet;
    	this.visited = new HashMap<>();
    	Iterator<Country> iterator = this.countrySet.iterator();
    	while(iterator.hasNext()) {
    		visited.put(iterator.next(), false);
    	}
    }
    
    private void DFS(Country startCountry) {
    	visited.put(startCountry, true);
    	Iterator<Country> it = startCountry.getAdjacentCountries().iterator();
    	while(it.hasNext()) {
    		Country adjacentCountry = it.next();
    		if(!visited.get(adjacentCountry)) {
    			DFS(adjacentCountry);
    		}
    	}
    }
    
    public boolean isConnected() {
    	DFS(countrySet.iterator().next());	
    	Iterator<Country> it = countrySet.iterator();
    	while(it.hasNext()) {
    		Country country = it.next();
    		if(visited.get(country)==false) {
    			System.out.println("Map is not a connected graph.");
    			return false;
    		}
    	}
    	return true;
    	
    }
}
