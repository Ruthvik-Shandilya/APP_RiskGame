package com.risk.strategy;

import com.risk.model.Country;
import com.risk.model.Player;
import com.risk.view.controller.GamePlayController;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Benevolent class contains methods for the Player Behavior
 * Strategies of computer player.
 * <p>
 * A benevolent computer player strategy that focuses on protecting
 * its weak countries (reinforces its weakest countries, never
 * attacks, then fortifies in order to move armies to weaker countries).
 *
 * @author Karandeep Singh
 * @author Farhan Shaheen
 */
public class Benevolent extends PlayerBehaviour {

    /**
     * Object of GamePlayController, control various activities during the game play.
     */
    private GamePlayController gamePlayController;

    public Benevolent() {
    }


    /**
     * Constructor method for Benevolent class.
     *
     * @param gamePlayController Attaching with observer.
     */
    public Benevolent(GamePlayController gamePlayController) {
        this.gamePlayController = gamePlayController;
        this.addObserver(gamePlayController);
    }

    /**
     * Method for Benevolent class for reinforcement phase.
     * Start and end of the reinforcement phase.
     *
     * @param countryList   List of countries owned by the player.
     * @param country       Country to which reinforcement armies are to be assigned.
     * @param currentPlayer Current player.
     */
    @Override
    public void reinforcementPhase(ObservableList<Country> countryList, Country country, Player currentPlayer) {
        System.out.println("Beginning Reinforcement phase for benevolent player " + currentPlayer.getName());
        setChanged();
        notifyObservers("Beginning Reinforcement phase for benevolent player " + currentPlayer.getName() + ".\n");
        System.out.println("List of countries owned: " + countryList.toString() + "\n");
        setChanged();
        notifyObservers("List of countries owned: " + countryList.toString() + "\n");
        List<Country> sortedList = sortCountryListByArmyCount(countryList);
        for (Country country1 : sortedList) {
            System.out.println(country1.getName() + ":" + country1.getNoOfArmies());
        }
        if (!sortedList.isEmpty()) {
            country = sortedList.get(0);
            country.setNoOfArmies(country.getNoOfArmies() + currentPlayer.getArmyCount());
            System.out.println(currentPlayer.getName() + " benevolent player has been assigned all the " + currentPlayer.getArmyCount()
                    + " armies to the weakest country " + country.getName());
            System.out.println("Ended Reinforcement phase for benevolent player " + currentPlayer.getName());
            setChanged();
            notifyObservers(currentPlayer.getName() + " benevolent player has been assigned all the " + currentPlayer.getArmyCount()
                    + " armies to the weakest country " + country.getName() + ".\n");
            currentPlayer.setArmyCount(0);

            setChanged();
            notifyObservers("Ended Reinforcement phase for benevolent player " + currentPlayer.getName() + ".\n");
        }
    }

    /**
     * Method for Benevolent class for attack phase.
     *
     * @param attackingCountryList List of countries attacking.
     * @param defendingCountryList List of countries defending.
     * @param gamePhase            Current player.
     */
    @Override
    public void attackPhase(ListView<Country> attackingCountryList, ListView<Country> defendingCountryList,
                            Player gamePhase) {
        System.out.println("Benevolent player cannot attack.");
        setChanged();
        notifyObservers("Benevolent player cannot attack.\n");
    }

    /**
     * Method for Benevolent class for fortification phase.
     * Start and end of the fortification phase.
     *
     * @param selectedCountryList List of countries selected by the player.
     * @param adjCountry          List of adjacent countries.
     * @param currentPlayer       Current player.
     * @return true
     * If the fortification successful; other wise false.
     */
    @Override
    public boolean fortificationPhase(ListView<Country> selectedCountryList, ListView<Country> adjCountry,
                                      Player currentPlayer) {
        System.out.println("Beginning Fortification phase for benevolent player " + currentPlayer.getName());
        setChanged();
        notifyObservers("Beginning Fortification phase for benevolent player " + currentPlayer.getName() + ".\n");
        for (Country country : sortCountryListByArmyCount(selectedCountryList.getItems())) {
            System.out.println(country.getName() + ":" + country.getNoOfArmies());
            setChanged();
            notifyObservers(country.getName() + ":" + country.getNoOfArmies());
        }
        Country weakestCountry = checkAndFindWeakestIfNoAdjacentCountryToFortify(selectedCountryList.getItems());
        if (weakestCountry != null) {
            Country strongestAdjacentCountry = getStrongestAdjacentCountry(weakestCountry);
            if (strongestAdjacentCountry != null) {
                int fortificationArmies = (strongestAdjacentCountry.getNoOfArmies() - weakestCountry.getNoOfArmies()) / 2;
                weakestCountry.setNoOfArmies(weakestCountry.getNoOfArmies() + fortificationArmies);
                strongestAdjacentCountry.setNoOfArmies(strongestAdjacentCountry.getNoOfArmies() - fortificationArmies);
                System.out.println("Country " + weakestCountry + " has been assigned " + fortificationArmies +
                        " armies from country " + strongestAdjacentCountry.getName());
                System.out.println("Ended Fortification phase for benevolent player " + currentPlayer.getName());
                setChanged();
                notifyObservers("Country " + weakestCountry + " has been assigned " + fortificationArmies +
                        " armies from country " + strongestAdjacentCountry.getName() + ".\n");
                setChanged();
                notifyObservers("Ended Fortification phase for benevolent player " + currentPlayer.getName() + ".\n");
                return true;
            }
        }
        System.out.println("Ended Fortification phase for benevolent player " + currentPlayer.getName());
        setChanged();
        notifyObservers("Ended Fortification phase for benevolent player " + currentPlayer.getName() + ".\n");
        return false;
    }

    /**
     * Method for Benevolent class for if player can attack.
     *
     * @param countries List of countries owned by the player.
     * @return true
     * If player can attack; other wise false.
     */
    @Override
    public boolean playerCanAttack(ListView<Country> countries) {
        System.out.println("Benevolent player cannot attack.");
        setChanged();
        notifyObservers("Benevolent player cannot attack.\n");
        return false;
    }

    /**
     * Method to find the strongest adjacent country.
     *
     * @param country Country against which to find the strongest adjacent country.
     * @return Country
     * Strongest country.
     */
    public Country getStrongestAdjacentCountry(Country country) {
        if (country == null) {
            return null;
        }
        List<Country> adjacentCountries = new ArrayList<>();
        for (Country adjacentCountry : country.getAdjacentCountries()) {
            if (country.getPlayer().equals(adjacentCountry.getPlayer())) {
                adjacentCountries.add(adjacentCountry);
            }
        }
        if (adjacentCountries.isEmpty()) {
            return null;
        }
        Collections.sort(adjacentCountries, Comparator.comparing(obj -> Integer.valueOf(obj.getNoOfArmies()), Collections.reverseOrder()));
        if (adjacentCountries.get(0).getNoOfArmies() > 1)
            return adjacentCountries.get(0);
        return null;
    }

    /**
     * Method to check and find the weakest country if
     * no adjacent country to fortify.
     *
     * @param list List of countries.
     * @return Country
     * Strongest country.
     */
    public Country checkAndFindWeakestIfNoAdjacentCountryToFortify(List<Country> list) {
        if (!list.isEmpty()) {
            for (Country country : list) {
                if (country != null && country.getNoOfArmies() >= 1 && getStrongestAdjacentCountry(country) != null) {
                    return country;
                }
            }
        }
        return null;
    }

    /**
     * Method to sort country list by army count.
     *
     * @param list List of countries.
     * @return List
     * List of countries which have been sorted.
     */
    public List<Country> sortCountryListByArmyCount(List<Country> list) {
        Collections.sort(list, Comparator.comparing(obj -> Integer.valueOf(obj.getNoOfArmies())));
        return list;
    }
}
