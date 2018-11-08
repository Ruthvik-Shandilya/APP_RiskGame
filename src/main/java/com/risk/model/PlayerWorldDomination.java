package com.risk.model;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Observable;

import com.risk.services.MapIO;

public class PlayerWorldDomination extends Observable {

	public HashMap<Player, Double> generateWorldDominationData(MapIO map) {

		HashMap<Player, Double> playerCountryCount = new HashMap<>();
		Double countryCount = 0.0;
		for (Continent cont : map.getMapGraph().getContinents().values()) {
			for (Country ter : cont.getListOfCountries()) {
				countryCount++;
				Player player = ter.getPlayer();
				if(playerCountryCount.containsKey(player)) {
					playerCountryCount.put(player, playerCountryCount.get(player)+1);
				} else {
					playerCountryCount.put(player, Double.valueOf("1"));
				}
			}
		}

		HashMap<Player, Double> playerTerPercent = new HashMap<>();
		for(Entry<Player, Double> entry : playerCountryCount.entrySet()) {
			playerTerPercent.put(entry.getKey(), (entry.getValue()/countryCount * 100));
		}
		return playerTerPercent;
	}

}
