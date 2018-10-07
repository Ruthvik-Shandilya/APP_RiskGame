package com.risk.services.gameplay;

import java.util.ArrayList;

import com.risk.model.Continent;
import com.risk.model.Country;
import com.risk.model.Player;

public class ReinforcementPhase {

	boolean isPlayerOwnedContinent = true;
	ArrayList<Country> playerOwnedContries;
	ArrayList<Country> continentListOfCountries;

	public int calculateNoOfCountries(Player player, Continent continent) {
		int myCountries = player.getMyCountries().size();
		int armiesCount = (int) Math.round(myCountries / 3);

		playerOwnedContries = player.getMyCountries();
		continentListOfCountries = continent.getListOfCountries();

		if (armiesCount < 3) {
			armiesCount = 3;
		}

		for (Country country : continentListOfCountries) {
			if (!playerOwnedContries.contains(country)) {
				isPlayerOwnedContinent = false;
				System.out.println("Player does not own this continent");
				break;
			}
		}

		if (isPlayerOwnedContinent) {
			armiesCount = continent.getControlValue();
			System.out.println("Player owns this continent");
		}

		return armiesCount;
	}
}
