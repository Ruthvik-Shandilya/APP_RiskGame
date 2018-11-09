package com.risk.model;

import com.risk.map.util.GameUtil;
import com.risk.map.util.WindowUtil;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Random;

public class Dice extends Observable {

    private Country attackingCountry;

    private ArrayList<Integer> attackerDiceList;

    private Country defendingCountry;

    private ArrayList<Integer> defenderDiceList;

    private int countriesWonCount;

    public Dice(Country attackingCountry, Country defendingCountry) {
        this.attackingCountry = attackingCountry;
        this.defendingCountry = defendingCountry;
        attackerDiceList = new ArrayList<>();
        defenderDiceList = new ArrayList<>();
    }

    public Country getAttackingCountry() {
        return attackingCountry;
    }

    public void setAttackingCountry(Country attackingCountry) {
        this.attackingCountry = attackingCountry;
    }

    public ArrayList<Integer> getAttackerDiceList() {
        return attackerDiceList;
    }

    public void setAttackerDiceList(ArrayList<Integer> attackerDiceList) {
        this.attackerDiceList = attackerDiceList;
    }

    public Country getDefendingCountry() {
        return defendingCountry;
    }

    public void setDefendingCountry(Country defendingCountry) {
        this.defendingCountry = defendingCountry;
    }

    public ArrayList<Integer> getDefenderDiceList() {
        return defenderDiceList;
    }

    public void setDefenderDiceList(ArrayList<Integer> defenderDiceList) {
        this.defenderDiceList = defenderDiceList;
    }

    public int getCountriesWonCount() {
        return countriesWonCount;
    }

    public void setCountriesWonCount(int countriesWonCount) {
        this.countriesWonCount = countriesWonCount;
    }

    public ArrayList<String> getDicePlayResult(){
        ArrayList<String> diceThrowResult = new ArrayList<>();
        Collections.sort(attackerDiceList,Collections.reverseOrder());
        Collections.sort(defenderDiceList,Collections.reverseOrder());

        for(Integer defenderValue : defenderDiceList ){
            for(Integer attackerValue : attackerDiceList){
                updateArmiesAfterAttack(defenderValue, attackerValue, diceThrowResult);
                break;
            }
            if(attackerDiceList.size() >= 1){
                attackerDiceList.remove(0);
            }
        }
        return diceThrowResult;
    }

    public void updateArmiesAfterAttack(Integer defenderValue, Integer attackerValue, ArrayList<String> playResult) {
        if (attackerValue.compareTo(defenderValue) > 0) {
            playResult.add("Defender has lost one army.");
            if (defendingCountry.getNoOfArmies() > 0) {
                defendingCountry.setNoOfArmies(defendingCountry.getNoOfArmies() - 1);
            }
        } else {
            playResult.add("Attacker has lost one army.");
            if (attackingCountry.getNoOfArmies() > 1) {
                attackingCountry.setNoOfArmies(attackingCountry.getNoOfArmies() - 1);
            }
        }
    }

    public void cancelDiceThrow(){
        setChanged();
        notifyObservers("rollDiceComplete");
    }

    public void moveAllArmies() {
        int attackingArmyCount = getAttackingCountry().getNoOfArmies();
        getAttackingCountry().setNoOfArmies(1);
        getDefendingCountry().setNoOfArmies(attackingArmyCount - 1);
        updateCountryList();
        setChanged();
        notifyObservers("rollDiceComplete");
    }

    public void skipMoveArmy() {
        int attackingArmyCount = getAttackingCountry().getNoOfArmies();
        getAttackingCountry().setNoOfArmies(attackingArmyCount - 1);
        getDefendingCountry().setNoOfArmies(1);
        updateCountryList();
        setChanged();
        notifyObservers("rollDiceComplete");
    }

    public void updateCountryList() {
        ArrayList<Country> defenderCountries = defendingCountry.getPlayer().getPlayerCountries();
        defenderCountries.remove(defendingCountry);

        defendingCountry.setPlayer(attackingCountry.getPlayer());
        defendingCountry.getPlayer().getPlayerCountries().add(defendingCountry);

    }

    public void moveArmies(int armiesToMove, Label message, Button moveArmies) {
        int currentArmies = getAttackingCountry().getNoOfArmies();
        if (currentArmies <= armiesToMove) {
            message.setVisible(true);
            message.setText("You can move a maximum of " + (currentArmies - 1) + " armies");
            return;
        } else {
            getAttackingCountry().setNoOfArmies(currentArmies - armiesToMove);
            getDefendingCountry().setNoOfArmies(armiesToMove);
            updateCountryList();
            GameUtil.closeScreen(moveArmies);
            setChanged();
            notifyObservers("rollDiceComplete");
        }
    }

    public int generateRandomNumber() {
        return new Random().nextInt(6) + 1;
    }

    public boolean checkDiceThrowPossible() {
        boolean diceThrowPossible = true;
        if (attackingCountry.getNoOfArmies() < 2 || defendingCountry.getNoOfArmies() <= 0) {
            diceThrowPossible = false;
        }
        return diceThrowPossible;
    }
}
