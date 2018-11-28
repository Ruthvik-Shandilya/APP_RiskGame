package com.risk.strategy;

import com.risk.model.Country;
import com.risk.model.Player;
import com.risk.view.Util.WindowUtil;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Benevolent extends PlayerBehaviour {

    private TextArea terminalWindow;

    public Benevolent() {
        new WindowUtil(this);
    }

    @Override
    public TextArea getTerminalWindow() {
        return this.terminalWindow;
    }

    @Override
    public void reinforcementPhase(ObservableList<Country> countryList, Country country, TextArea terminalWindow,
                                   Player currentPlayer) {
        this.terminalWindow = terminalWindow;
        System.out.println("Beginning Reinforcement phase for benevolent player " + currentPlayer.getName());
        setChanged();
        notifyObservers("Beginning Reinforcement phase for benevolent player " + currentPlayer.getName() + ".\n");
        System.out.println("List of countries owned: " + countryList.toString() + "\n");
        this.terminalWindow = terminalWindow;
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

    @Override
    public void attackPhase(ListView<Country> attackingCountryList, ListView<Country> defendingCountryList,
                            Player gamePhase, TextArea terminalWindow) {
        this.terminalWindow = terminalWindow;
        System.out.println("Benevolent player cannot attack.");
        setChanged();
        notifyObservers("Benevolent player cannot attack.\n");
    }

    @Override
    public boolean fortificationPhase(ListView<Country> selectedCountryList, ListView<Country> adjCountry,
                                      TextArea terminalWindow, Player currentPlayer) {
        this.terminalWindow = terminalWindow;
        System.out.println("Beginning Fortification phase for benevolent player " + currentPlayer.getName());
        setChanged();
        notifyObservers("Beginning Fortification phase for benevolent player " + currentPlayer.getName() + ".\n");
        for (Country country : sortCountryListByArmyCount(selectedCountryList.getItems()))
            System.out.println(country.getName() + ":" + country.getNoOfArmies());
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

    @Override
    public boolean playerCanAttack(ListView<Country> countries, TextArea terminalWindow) {
        this.terminalWindow = terminalWindow;
        System.out.println("Benevolent player cannot attack.");
        setChanged();
        notifyObservers("Benevolent player cannot attack.\n");
        return false;
    }

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

    public List<Country> sortCountryListByArmyCount(List<Country> list) {
        Collections.sort(list, Comparator.comparing(obj -> Integer.valueOf(obj.getNoOfArmies())));
        return list;
    }
}
