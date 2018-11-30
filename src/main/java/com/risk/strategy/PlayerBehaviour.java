package com.risk.strategy;

import com.risk.model.Continent;
import com.risk.model.Country;
import com.risk.model.Player;
import com.risk.services.MapIO;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.io.Serializable;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;

/**
 * 
 * PlayerBehaviour class contains methods for the Player Behavior.
 * There are five player behaviours 
 * i.e. Human, aggressive, benevolent, random and cheater
 * 
 * @author Karandeep Singh
 * @author Palash Jain
 * 
 */
public abstract class PlayerBehaviour extends Observable implements Serializable {

    /**
     * 
	 * Method for reinforcement phase. 
	 * Start and end of the reinforcement phase. 
	 * 
	 * @param countryList
	 *            List of countries owned by the player.
	 * @param country
	 *            Country to which reinforcement armies are to be assigned.
	 * @param currentPlayer
	 *            Current player.
	 *            
	 */
    abstract public void reinforcementPhase(ObservableList<Country> countryList, Country country, Player currentPlayer);

    /**
     * 
	 * Method for attack phase.
	 * 
	 * @param attackingCountryList
	 *            List of countries attacking.
	 * @param defendingCountryList
	 *            List of countries defending.
	 * @param currentPlayer
	 *            Current player.
	 *            
	 */ 
    abstract public void attackPhase(ListView<Country> attackingCountryList, ListView<Country> defendingCountryList,
                                     Player currentPlayer);

    /**
     * 
	 * Method for fortification phase. 
	 * Start and end of the fortification phase. 
	 * 
	 * @param selectedCountry
	 *            List of countries selected by the player.
	 * @param adjCountry
	 *            List of adjacent countries.
	 * @param playerPlaying
	 *            Current player.
	 * 
	 * @return true 
     * 			  If the fortification successful; other wise false.
     * 
	 */ 
    abstract public boolean fortificationPhase(ListView<Country> selectedCountry, ListView<Country> adjCountry,
                                               Player playerPlaying);


    /**
     * 
	 * Method to get list of defending countries. 
	 * 
	 * @param attackingCountry
	 *            Attacking country.
	 *
	 * @return List 
     * 			  List of defending countries.
     * 
	 */
    public List<Country> getDefendingCountryList(Country attackingCountry) {
        List<Country> defendingCountries = attackingCountry.getAdjacentCountries().stream()
                .filter(t -> (attackingCountry.getPlayer() != t.getPlayer())).collect(Collectors.toList());

        return defendingCountries;

    }

    /**
     * 
	 * Method for if player can attack.
	 * 
	 * @param countries
	 *            List of countries owned by the player.
	 *   
	 * @return true 
     * 			  If player can attack; other wise false.
     *            
	 */    
    abstract public boolean playerCanAttack(ListView<Country> countries);

    /**
     * 
	 * Method for to check if fortification phase is valid.
	 * 
	 * @param mapIO
	 *            MapIO Object.
	 * @param playerPlaying           
	 *   		  Player playing.
	 *   
	 * @return true 
     * 			  If fortification phase is valid; other wise false.
     *            
	 */ 
    public boolean isFortificationPhaseValid(MapIO mapIO, Player playerPlaying) {
        boolean isFortificationAvailable = false;
        outer:
        for (Continent continent : mapIO.getMapGraph().getContinents().values()) {
            for (Country Country : continent.getListOfCountries()) {
                if (Country.getPlayer().equals(playerPlaying)) {
                    if (Country.getNoOfArmies() > 1) {
                        for (Country adjCountry : Country.getAdjacentCountries()) {
                            if (adjCountry.getPlayer().equals(playerPlaying)) {
                                isFortificationAvailable = true;
                                break outer;
                            }
                        }
                    }
                }
            }
        }

        return isFortificationAvailable;
    }


}
