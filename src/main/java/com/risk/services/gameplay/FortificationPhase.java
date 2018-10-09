package com.risk.services.gameplay;

import com.risk.model.Country;

/**
 * 
 * Fortification Phase class contains methods for 
 * the Fortification phase of gameplay.
 * 
 * @author Ruthvik Shandilya
 * @author Neha pal
 */

public class FortificationPhase {
	
	/**
	 * Method to move armies between countries owned by the player.
	 * 
	 * @param country1
	 * 				armies are moved from this Country.
	 * @param country2
	 * 				armies are moved to this Country.
	 * @param armiesCount
	 * 				number of armies to move.
	 */

	public void moveArmies(Country country1, Country country2, int armiesCount) {
		if (country1.getAdjacentCountries().contains(country2)) {
			country1.setNoOfArmies(country1.getNoOfArmies() - armiesCount);
			country2.setNoOfArmies(country2.getNoOfArmies() + armiesCount);
		}
	}

}