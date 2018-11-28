package com.risk.strategy;

import com.risk.model.Country;
import com.risk.model.Dice;
import com.risk.model.Player;
import com.risk.view.Util.WindowUtil;
import com.risk.view.controller.DiceController;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.util.*;

public class Aggressive extends PlayerBehaviour {

    private Country strongestCountry;

    private TextArea terminalWindow;

    public Aggressive() {
        new WindowUtil(this);
    }

    @Override
    public TextArea getTerminalWindow() {
        return this.terminalWindow;
    }

    @Override
    public void reinforcementPhase(ObservableList<Country> countryList, Country country, TextArea terminalWindow,
                                   Player currentPlayer) {
        System.out.println("Beginning Reinforcement phase for aggressive player " + currentPlayer.getName());
        setChanged();
        notifyObservers("Beginning Reinforcement phase for aggressive player " + currentPlayer.getName() + ".\n");
        System.out.println("List of countries owned: " + countryList.toString() + "\n");
        this.terminalWindow = terminalWindow;
        List<Country> sortedList = sortCountryListByArmyCount(countryList);
        for (Country country1 : sortedList) {
            System.out.println(country1.getName() + ":" + country1.getNoOfArmies());
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

    @Override
    public boolean playerCanAttack(ListView<Country> countries, TextArea terminalWindow) {

        this.terminalWindow = terminalWindow;
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


    @Override
    public boolean fortificationPhase(ListView<Country> selectedCountryList, ListView<Country> adjCountryList,
                                      TextArea terminalWindow, Player currentPlayer) {
        this.terminalWindow = terminalWindow;
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

    private void attack(Country attacking, Country defending, Player player, TextArea terminalWindow) {

        this.terminalWindow = terminalWindow;
        Dice dice = new Dice(attacking, defending);
        if (player != null) {
            dice.addObserver(player);
        }
        DiceController diceController = new DiceController(dice, this, terminalWindow);
        diceController.automateDiceRoll();
    }

    @Override
    public void attackPhase(ListView<Country> attackingCountryList, ListView<Country> defendingCountryList,
                            Player currentPlayer, TextArea terminalWindow) {
        this.terminalWindow = terminalWindow;
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
                attack(strongestCountry, defendingCountriesIterator.next(), currentPlayer, terminalWindow);
                setChanged();
                notifyObservers("Ended attack phase for aggressive player " + currentPlayer.getName() + ".\n");
                break;
            }
        }

    }

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


    public List<Country> sortCountryListByArmyCount(List<Country> list) {
        Collections.sort(list, Comparator.comparing(obj -> Integer.valueOf(obj.getNoOfArmies()), Comparator.reverseOrder()));
        return list;
    }
}
