package com.risk.strategy;

import com.risk.model.Country;
import com.risk.model.Dice;
import com.risk.model.Player;
import com.risk.view.DiceView;
import com.risk.view.Util.WindowUtil;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class Human extends PlayerBehaviour {

    private TextArea terminalWindow;

    @Override
    public TextArea getTerminalWindow() {
        return terminalWindow;
    }

    public Human() {
        new WindowUtil(this);
    }

    @Override
    public void reinforcementPhase(ObservableList<Country> countryList, Country country, TextArea terminalWindow,
                                   Player currentPlayer) {
        this.terminalWindow = terminalWindow;
        System.out.println("Beginning Reinforcement phase for human player " + currentPlayer.getName() + ".\n");
        setChanged();
        notifyObservers("Beginning Reinforcement phase for human player " + currentPlayer.getName() + ".\n");
        System.out.println("List of countries owned: " + countryList.toString() + "\n");
        if (currentPlayer.getArmyCount() > 0) {
            if (country == null) {
                WindowUtil.popUpWindow("No Country Selected", "popUp", "Please select a country");
                return;
            }

            int reinforcementArmies = Integer.valueOf(WindowUtil.userInput());
            if (currentPlayer.getArmyCount() < reinforcementArmies) {
                WindowUtil.popUpWindow("Insufficient Armies", "popUp", currentPlayer.getName() + " don't have enough armies");
                return;
            }
            country.setNoOfArmies(country.getNoOfArmies() + reinforcementArmies);
            currentPlayer.setArmyCount(currentPlayer.getArmyCount() - reinforcementArmies);
            System.out.println(country.getName() + " was assigned " + reinforcementArmies + " armies.\n");
            setChanged();
            notifyObservers(country.getName() + " was assigned " + reinforcementArmies + " armies.\n");

        }
    }

    @Override
    public void attackPhase(ListView<Country> attackingCountryList, ListView<Country> defendingCountryList,
                            Player currentPlayer, TextArea terminalWindow) {
        Country attackingCountry = attackingCountryList.getSelectionModel().getSelectedItem();
        Country defendingCountry = defendingCountryList.getSelectionModel().getSelectedItem();
        if (attackingCountry != null && defendingCountry != null) {

            boolean playerCanAttack = isAttackMoveValid(attackingCountry, defendingCountry);

            if (playerCanAttack) {
                Dice dice = new Dice(attackingCountry, defendingCountry);
                dice.addObserver(currentPlayer);
                DiceView.openDiceWindow(dice, currentPlayer, terminalWindow);
            }

        } else {
            WindowUtil.popUpWindow("Country selection problem", "Country selection pop", currentPlayer.getName() + " please select attacking and defending countries.");

        }

    }

    @Override
    public boolean fortificationPhase(ListView<Country> selectedCountryList, ListView<Country> adjCountryList,
                                      TextArea terminalWindow, Player playerPlaying) {
        Country selectedCountry = selectedCountryList.getSelectionModel().getSelectedItem();
        Country adjCountry = adjCountryList.getSelectionModel().getSelectedItem();
        if (selectedCountry == null) {
            WindowUtil.popUpWindow("Please choose Selected Country as source.", "Message", "");
            return false;
        } else if (adjCountry == null) {
            WindowUtil.popUpWindow("Please choose Adjacent Country as destination.", "Message", "");
            return false;
        } else if (!(adjCountry.getPlayer().equals(playerPlaying))) {
            WindowUtil.popUpWindow("Adjacent Country does not belong to you.", "Message", "");
            return false;
        }

        Integer armies = Integer.valueOf(WindowUtil.userInput());
        if (armies > 0) {
            if (selectedCountry.getNoOfArmies() == armies) {
                WindowUtil.popUpWindow("All armies selected to move", "Fortification Phase popup", "You have to leave at least one army behind.");
                return false;
            } else if (selectedCountry.getNoOfArmies() < armies) {
                WindowUtil.popUpWindow("You do not have sufficient armies", "Fortification Phase popup", "Please select less number of armies");
                return false;
            } else {
                selectedCountry.setNoOfArmies(selectedCountry.getNoOfArmies() - armies);
                adjCountry.setNoOfArmies(adjCountry.getNoOfArmies() + armies);
                System.out.println(armies + " armies placed on " + adjCountry.getName() + " country.\n");
                System.out.println("Fortification phase ended.\n");
                setChanged();
                notifyObservers(armies + " armies placed on " + adjCountry.getName() + " country.\n");
                setChanged();
                notifyObservers("Fortification phase ended.\n");
                setChanged();
                notifyObservers("Reinforcement");
                return true;
            }
        } else {
            WindowUtil.popUpWindow("Invalid number", "Fortification Phase popup", "Please enter a valid number");
            return false;
        }
    }

    /**
     * Method to check if the attack move is valid or not
     *
     * @param attacking Country attacking
     * @param defending Country under attack
     * @return true if the attack move is valid; other wise false
     */

    public boolean isAttackMoveValid(Country attacking, Country defending) {
        boolean isValidAttackMove = false;
        if (defending.getPlayer() != attacking.getPlayer()) {
            if (attacking.getNoOfArmies() > 1) {
                isValidAttackMove = true;
            } else {
                WindowUtil.popUpWindow("Select a country with more armies.", "Invalid game move", "There should be more than one army on the country which is attacking.");
            }
        } else {
            WindowUtil.popUpWindow("You have selected your own country", "Invalid game move", "Select another player's country to attack");
        }
        return isValidAttackMove;
    }


    /**
     * Method to check if the player can attack or not.
     *
     * @param countries      List view of all the countries of the player
     * @param terminalWindow TextArea to which current game information will be displayed
     * @return true if the player can attack; other wise false
     */
    @Override
    public boolean playerCanAttack(ListView<Country> countries, TextArea terminalWindow) {
        this.terminalWindow = terminalWindow;
        boolean canAttack = false;
        for (com.risk.model.Country Country : countries.getItems()) {
            if (Country.getNoOfArmies() > 1) {
                canAttack = true;
            }
        }
        if (!canAttack) {
            System.out.println("Player cannot continue with attack phase, move to fortification phase.\n");
            System.out.println("Attack phase ended\n");
            setChanged();
            notifyObservers("Player cannot continue with attack phase, move to fortification phase.\n");
            setChanged();
            notifyObservers("Attack phase ended\n");
        }
        return canAttack;
    }

}
