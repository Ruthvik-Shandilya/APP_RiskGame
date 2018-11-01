package com.risk.model;

import com.risk.services.MapIO;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Observable;


public class PlayerWorldDomination extends Observable {
	
	/**
	 * Populate World Domination Data according to playerTerritoryCount
	 * 
	 * @param map
	 *            map object
	 * @return playerTerPercent.
	 */
	public HashMap<Player, Double> populateWorldDominationData(MapIO map) {

		HashMap<Player, Double> playerTerritoryCount = new HashMap<>();
		Double territoryCount = 0.0;
		for (Continent cont : map.getMapGraph().getContinents().values()) {
			for (Country country : cont.getListOfCountries()) {
				territoryCount++;
				Player player = country.getPlayer();
				if(playerTerritoryCount.containsKey(player)) {
					playerTerritoryCount.put(player, playerTerritoryCount.get(player)+1);
				} else {
					playerTerritoryCount.put(player, Double.valueOf("1"));
				}
			}
		}

		HashMap<Player, Double> playerTerPercent = new HashMap<>();
		for(Entry<Player, Double> entry : playerTerritoryCount.entrySet()) {
			playerTerPercent.put(entry.getKey(), (entry.getValue()/territoryCount * 100));
		}
		return playerTerPercent;
	}

}
