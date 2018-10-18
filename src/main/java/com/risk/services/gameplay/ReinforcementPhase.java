package com.risk.services.gameplay;

import java.util.ArrayList;

import com.risk.model.Continent;
import com.risk.model.Country;
import com.risk.model.Player;

/**
 * 
 * ReinforcementPhase class contains the methods used in
 * the reinforcement phase gameplay.
 * 
 * @author Ruthvik Shandilya
 * @author Palash Jain
 *
 */

public class ReinforcementPhase {
	
	boolean isPlayerOwnedContinent = true;
	ArrayList<Country> playerOwnedContries;
	ArrayList<Country> continentListOfCountries;
	
	/**
	 * Method to find the number of countries owned by the player
	 * and to assign the armies based on the countries list.
	 * 
	 * @param player
	 * 				Current Player
	 * @param continent
	 * 				Continent
	 * @return
	 * 		reinforcement armies
	 */

	public int findNoOfArmies(Player player, Continent continent) {
		int myCountries = player.getMyCountries().size();
		int armiesCount = (int) Math.floor(myCountries / 3);
		playerOwnedContries = player.getMyCountries();
		continentListOfCountries = continent.getListOfCountries();
		
		// Minimum number of armies for a player in case armies count is less
		// than 3.
		if (armiesCount < 3) {
			armiesCount = 3;
		}

		for (Country country : continentListOfCountries) {
			if (!playerOwnedContries.contains(country)) {
				isPlayerOwnedContinent = false;
				break;
			}
		}
		
		//If a player owns all the countries in a continent, then armies count will be equal
		//to the control value of the continent.
		if (isPlayerOwnedContinent) {
			armiesCount = continent.getControlValue();
		}

		return armiesCount;
	}
}
