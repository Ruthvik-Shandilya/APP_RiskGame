package com.risk.strategy;

import com.risk.model.Country;
import com.risk.model.Dice;
import com.risk.model.Player;
import com.risk.model.TournamentModel;
import com.risk.view.controller.DiceController;
import com.risk.view.controller.GamePlayController;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.util.*;

/**
 * Aggressive class contains methods for the Player Behavior
 * Strategies of computer player.
 * <p>
 * An aggressive computer player strategy that focuses on attack
 * (reinforces its strongest country, then always attack with it
 * until it cannot attack anymore, then fortifies in order to
 * maximize aggregation of forces in one country).
 *
 * @author Karandeep Singh
 * @author Farhan Shaheen
 */

public class Aggressive extends PlayerBehaviour {

    /**
     * Object of country, which is the strongest country.
     */
    private Country strongestCountry;


    public Aggressive() {
    }


    /**
     * Object of GamePlayController, control various activities during the game play.
     */
    private GamePlayController gamePlayController;

    /**
     * Constructor method for Aggressive class.
     *
     * @param gamePlayController Attaching with observer.
     */
    public Aggressive(GamePlayController gamePlayController) {
        this.gamePlayController = gamePlayController;
        this.addObserver(gamePlayController);
    }

    /**
     * Method for Aggressive class for reinforcement phase.
     * Start and end of the reinforcement phase.
     *
     * @param countryList   List of countries owned by the player.
     * @param country       Country to which reinforcement armies are to be assigned.
     * @param currentPlayer Current player.
     */
    @Override
    public void reinforcementPhase(ObservableList<Country> countryList, Country country, Player currentPlayer) {
        System.out.println("Beginning Reinforcement phase for aggressive player " + currentPlayer.getName());
        setChanged();
        notifyObservers("Beginning Reinforcement phase for aggressive player " + currentPlayer.getName() + ".\n");
        System.out.println("List of countries owned: " + countryList.toString() + "\n");
        setChanged();
        notifyObservers("List of countries owned: " + countryList.toString() + "\n");
        List<Country> sortedList = sortCountryListByArmyCount(countryList);
        for (Country country1 : sortedList) {
            System.out.println(country1.getName() + ":" + country1.getNoOfArmies());
            setChanged();
            notifyObservers(country1.getName() + ":" + country1.getNoOfArmies());
        }
        if (!sortedList.isEmpty()) {
            country = findStrongestCountryForReinforcement(sortedList);
            if (country != null) {
                country.setNoOfArmies(country.getNoOfArmies() + currentPlayer.getArmyCount());
                System.out.println(currentPlayer.getName() + " aggressive player has been assigned all the " + currentPlayer.getArmyCount()
                        + " armies to the strongest country " + country.getName() + " , army count " + country.getNoOfArmies());
                setChanged();
                notifyObservers(currentPlayer.getName() + " aggressive player has been assigned all the " + currentPlayer.getArmyCount()
                        + " armies to the strongest country " + country.getName() + " , army count " + country.getNoOfArmies() + "\n");
                currentPlayer.setArmyCount(0);
            }
            System.out.println("Ended Reinforcement phase for aggressive player " + currentPlayer.getName());
            setChanged();
            notifyObservers("Ended Reinforcement phase for aggressive player " + currentPlayer.getName() + ".\n");
        }
    }

    /**
     * Method for Aggressive class for if player can attack.
     *
     * @param countries List of countries owned by the player.
     * @return true
     * If player can attack; other wise false.
     */
    @Override
    public boolean playerCanAttack(ListView<Country> countries) {
        strongestCountry = checkAndFindStrongestIfNoAdjacentCountryToAttack(sortCountryListByArmyCount(countries.getItems()));
        if (strongestCountry == null) {
            System.out.println("Aggressive player cannot continue with attack phase, move to fortification phase.");
            System.out.println("Attack phase ended for aggressive player.");
            setChanged();
            notifyObservers("Aggressive player cannot continue with attack phase, move to fortification phase.\n");
            setChanged();
            notifyObservers("Attack phase ended for aggressive player.\n");
            return false;
        }

        return true;
    }

    /**
     * Method for Aggressive class for fortification phase.
     * Start and end of the fortification phase.
     *
     * @param selectedCountryList List of countries selected by the player.
     * @param adjCountryList      List of adjacent countries.
     * @param currentPlayer       Current player.
     * @return true
     * If the fortification successful; other wise false.
     */
    @Override
    public boolean fortificationPhase(ListView<Country> selectedCountryList, ListView<Country> adjCountryList,
                                      Player currentPlayer) {
        System.out.println("Beginning Fortification phase for aggressive player " + currentPlayer.getName());
        setChanged();
        notifyObservers("Beginning Fortification phase for aggressive player " + currentPlayer.getName() + ".\n");
        Country countryToFortify = null;
        Country countryFromFortify = null;
        int maxSum = 0;
        int sum;
        for (Country country : selectedCountryList.getItems()) {
            sum = country.getNoOfArmies();
            for (Country adjacentCountry : country.getAdjacentCountries()) {
                if (country.getPlayer().equals(adjacentCountry.getPlayer())) {
                    System.out.println(country.getName() + ":" + country.getNoOfArmies() + ", " + adjacentCountry.getName()
                            + ":" + adjacentCountry.getNoOfArmies());
                    setChanged();
                    notifyObservers(country.getName() + ":" + country.getNoOfArmies() + ", " + adjacentCountry.getName()
                            + ":" + adjacentCountry.getNoOfArmies());
                    sum += adjacentCountry.getNoOfArmies() > 1 ? (adjacentCountry.getNoOfArmies() - 1) : 0;
                    if (sum > maxSum) {
                        maxSum = sum;
                        countryToFortify = country;
                        countryFromFortify = adjacentCountry;
                        sum = country.getNoOfArmies();
                    }
                }
            }
        }
        System.out.println("Maximum sum =" + maxSum);

        if (countryToFortify != null && countryFromFortify != null) {
            countryToFortify.setNoOfArmies(countryToFortify.getNoOfArmies() + countryFromFortify.getNoOfArmies() - 1);
            countryFromFortify.setNoOfArmies(1);
            System.out.println("Country " + countryToFortify + " has been assigned maximum armies from country " + countryFromFortify.getName());
            System.out.println("Ended Fortification phase for aggressive player " + currentPlayer.getName());
            setChanged();
            notifyObservers("Country " + countryToFortify + " has been assigned maximum armies from country " + countryFromFortify.getName() + ".\n");
            setChanged();
            notifyObservers("Ended Fortification phase for aggressive player " + currentPlayer.getName() + ".\n");
            return true;
        }

        return false;
    }

    /**
     * Method for Aggressive class for attack.
     *
     * @param attacking Country attacking.
     * @param defending Country defending.
     * @param player    Current player.
     */
    private void attack(Country attacking, Country defending, Player player) {
        Dice dice = new Dice(attacking, defending);
        if (player != null) {
            dice.addObserver(player);
        }
        if (TournamentModel.isTournament) {
            DiceController diceController = new DiceController(dice, this);
            diceController.automateDiceRoll();
        } else {
            DiceController diceController = new DiceController(dice, this, this.gamePlayController);
            diceController.automateDiceRoll();
        }
    }

    /**
     * Method for Aggressive class for attack phase.
     * Start and end of the attack phase.
     *
     * @param attackingCountryList List of countries attacking.
     * @param defendingCountryList List of countries defending.
     * @param currentPlayer        Current player.
     */
    @Override
    public void attackPhase(ListView<Country> attackingCountryList, ListView<Country> defendingCountryList,
                            Player currentPlayer) {
        System.out.println("Beginning attack phase for aggressive player " + currentPlayer.getName());
        setChanged();
        notifyObservers("Beginning attack phase for aggressive player " + currentPlayer.getName() + ".\n");
        strongestCountry = checkAndFindStrongestIfNoAdjacentCountryToAttack(sortCountryListByArmyCount(attackingCountryList.getItems()));
        List<Country> defendingCountries = getDefendingCountryList(strongestCountry);
        Iterator<Country> defendingCountriesIterator = defendingCountries.iterator();
        while (defendingCountriesIterator.hasNext()) {
            if (strongestCountry.getNoOfArmies() > 1) {
                System.out.println("Attacking with strongest country " + strongestCountry.getName());
                System.out.println("Ended attack phase for aggressive player " + currentPlayer.getName());
                setChanged();
                notifyObservers("Attacking with strongest country " + strongestCountry.getName() + ".\n");
                attack(strongestCountry, defendingCountriesIterator.next(), currentPlayer);
                setChanged();
                notifyObservers("Ended attack phase for aggressive player " + currentPlayer.getName() + ".\n");
                break;
            }
        }

    }

    /**
     * Method to check and find the strongest country if
     * no adjacent country to attack.
     *
     * @param list List of countries.
     * @return Country
     * Strongest country.
     */
    public Country checkAndFindStrongestIfNoAdjacentCountryToAttack(List<Country> list) {
        if (!list.isEmpty()) {
            for (Country country : list) {
                if (country != null && country.getNoOfArmies() > 1 && getDefendingCountryList(country).size() > 0) {
                    return country;
                }
            }
        }
        return null;
    }

    /**
     * Method to find the strongest country for reinforcement.
     *
     * @param list List of countries.
     * @return Country
     * Strongest country.
     */
    public Country findStrongestCountryForReinforcement(List<Country> list) {
        if (!list.isEmpty()) {
            for (Country country : list) {
                if (country != null && country.getNoOfArmies() >= 1 && getDefendingCountryList(country).size() > 0) {
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
        Collections.sort(list, Comparator.comparing(obj -> Integer.valueOf(obj.getNoOfArmies()), Comparator.reverseOrder()));
        return list;
    }
}
