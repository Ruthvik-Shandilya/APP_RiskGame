package com.risk.strategy;

import com.risk.model.Country;
import com.risk.model.Dice;
import com.risk.model.Player;
import com.risk.view.Util.WindowUtil;
import com.risk.view.controller.DiceController;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.util.List;
import java.util.stream.Collectors;

public class Random extends PlayerBehaviour {

    private TextArea terminalWindow;

    public Random() {
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
        System.out.println("Beginning Reinforcement phase for random player " + currentPlayer.getName() + ".\n");
        setChanged();
        notifyObservers("Beginning Reinforcement phase for random player " + currentPlayer.getName() + ".\n");
        Country randomCountry = countryList.get(new java.util.Random().nextInt(countryList.size()));
        int armies = currentPlayer.getArmyCount();
        if (armies > 0) {
            randomCountry.setNoOfArmies(randomCountry.getNoOfArmies() + armies);
            currentPlayer.setArmyCount(currentPlayer.getArmyCount() - armies);
            System.out.println("Country " + randomCountry.getName() + " has been assigned " + armies + " armies.\n");
            setChanged();
            notifyObservers("Country " + randomCountry.getName() + " has been assigned " + armies + " armies.\n");
            setChanged();
        }
        System.out.println("Ended Reinforcement phase for random player " + currentPlayer.getName() + ".\n");
        notifyObservers("Ended Reinforcement phase for random player " + currentPlayer.getName() + ".\n");
    }

    @Override
    public void attackPhase(ListView<Country> attackingCountryList, ListView<Country> defendingCountryList,
                            Player currentPlayer, TextArea terminalWindow) {
        this.terminalWindow = terminalWindow;
        System.out.println("Beginning attack phase for random player " + currentPlayer.getName() + ".\n");
        setChanged();
        notifyObservers("Beginning attack phase for random player " + currentPlayer.getName() + ".\n");
        ObservableList<Country> attackableCountries = attackingCountryList.getItems();
        System.out.println("Attackable country list=" + attackableCountries.toString());
        Country attackingCountry;
        while ((attackingCountry = attackableCountries.get(new java.util.Random().nextInt(attackableCountries.size()))).getNoOfArmies() < 2) {
        }
        System.out.println("Attacking country = " + attackingCountry.getName() + " , no of armies=" + attackingCountry.getNoOfArmies());

        List<Country> defendingCountries;

        while ((defendingCountries = getDefendingCountryList(attackingCountry)).isEmpty()) {
            attackingCountry = attackableCountries.get(new java.util.Random().nextInt(attackableCountries.size()));
        }

        Country defendingCountry = defendingCountries.get(new java.util.Random().nextInt(defendingCountries.size()));

//        int maxTries = new java.util.Random().nextInt(attackingCountry.getNoOfArmies() + defendingCountry.getNoOfArmies() - 3) + 1;
//
//
//        while ((maxTries--) > 0 && attackingCountry.getNoOfArmies() > 1 && defendingCountry.getNoOfArmies() > 0 && !defendingCountry.getPlayer().equals(attackingCountry.getPlayer())) {
        System.out.println("Attacking from random country " + attackingCountry.getName() + " to random country " + defendingCountry.getName() + ".\n");
        setChanged();
        notifyObservers("Attacking from random country " + attackingCountry.getName() + " to random country " + defendingCountry.getName() + ".\n");
        attack(attackingCountry, defendingCountry, currentPlayer, terminalWindow);
//        }
        System.out.println("Ended Attack phase for random player " + currentPlayer.getName() + ".\n");
        setChanged();
        notifyObservers("Ended Attack phase for random player " + currentPlayer.getName() + ".\n");
    }

    @Override
    public boolean fortificationPhase(ListView<Country> selectedCountryList, ListView<Country> adjCountryList,
                                      TextArea terminalWindow, Player currentPlayer) {
        this.terminalWindow = terminalWindow;
        System.out.println("Beginning Fortification phase for random player " + currentPlayer.getName() + ".\n");
        notifyObservers("Beginning Fortification phase for random player " + currentPlayer.getName() + ".\n");
        System.out.println("List of countries owned: " + selectedCountryList.toString() + "\n");
        ObservableList<Country> selectedCountry = selectedCountryList.getItems();
        Country countryToFortify = selectedCountry.get(new java.util.Random().nextInt(selectedCountry.size()));
        List<Country> adjacentOwnedCountryList;
        while ((adjacentOwnedCountryList = getAdjacentOwnedCountryList(countryToFortify)).isEmpty()) {
            countryToFortify = selectedCountry.get(new java.util.Random().nextInt(selectedCountry.size()));
        }
        Country countryFromFortify = adjacentOwnedCountryList.get(new java.util.Random().nextInt(adjacentOwnedCountryList.size()));

        if (countryFromFortify.getNoOfArmies() >= 2) {
            int randomArmies = new java.util.Random().nextInt(countryFromFortify.getNoOfArmies() - 1) + 1;
            countryToFortify.setNoOfArmies(countryToFortify.getNoOfArmies() + randomArmies);
            countryFromFortify.setNoOfArmies(countryFromFortify.getNoOfArmies() - randomArmies);
            System.out.println("Fortified " + randomArmies + " from random country " + countryFromFortify.getName()
                    + " to random country " + countryToFortify.getName() + ".\n");
            setChanged();
            notifyObservers("Fortified " + randomArmies + " from random country " + countryFromFortify.getName()
                    + " to random country " + countryToFortify.getName() + ".\n");
        }
        System.out.println("Ended Fortification phase for random player " + currentPlayer.getName() + ".\n");
        setChanged();
        notifyObservers("Ended Fortification phase for random player " + currentPlayer.getName() + ".\n");
        return true;
    }

    @Override
    public boolean playerCanAttack(ListView<Country> countries, TextArea terminalWindow) {
        this.terminalWindow = terminalWindow;
        boolean canAttack = false;
        for (Country country : countries.getItems()) {
            if (country.getNoOfArmies() > 1 && getDefendingCountryList(country).size() > 0) {
                canAttack = true;
            }
        }

        if (!canAttack) {
            System.out.println("Random player cannot continue with attack phase, move to fortification phase.\n");
            System.out.println("Attack phase ended for random player.\n");
            setChanged();
            notifyObservers("Random player cannot continue with attack phase, move to fortification phase.\n");
            setChanged();
            notifyObservers("Attack phase ended for random player.\n");
        }
        return canAttack;
    }

    private void attack(Country attacking, Country defending, Player currentPlayer, TextArea terminalWindow) {
        Dice diceModel = new Dice(attacking, defending);
        if (currentPlayer != null) {
            diceModel.addObserver(currentPlayer);
        }
        DiceController diceController = new DiceController(diceModel, this, terminalWindow);
        diceController.automateDiceRoll();
    }

    public List<Country> getAdjacentOwnedCountryList(Country attackingCountry) {
        List<Country> adjacentOwnedCountries = attackingCountry.getAdjacentCountries().stream()
                .filter(t -> ((attackingCountry.getPlayer() == t.getPlayer()) && t.getNoOfArmies() > 1)).collect(Collectors.toList());

        return adjacentOwnedCountries;

    }

}
