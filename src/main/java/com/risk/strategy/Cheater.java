package com.risk.strategy;

import com.risk.model.Country;
import com.risk.model.Player;
import com.risk.controller.GamePlayController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Cheater class contains methods for the Player Behavior
 * Strategies of computer player.
 * <p>
 * A cheater computer player strategy whose reinforce() method
 * doubles the number of armies on all its countries, whose
 * attack() method automatically conquers all the neighbors of
 * all its countries, and whose fortify() method doubles the
 * number of armies on its countries that have neighbors that
 * belong to other players.
 *
 * @author Karandeep Singh
 * @author Ruthvik Shandilya
 */
public class Cheater extends PlayerBehaviour {

    /**
     * Object of ObservableList.
     */
    private ObservableList<Country> attackerCountryList = FXCollections.observableArrayList();

    /**
     * Object of GamePlayController, control various activities during the game play.
     */
    private GamePlayController gamePlayController;

    public Cheater() {
    }

    /**
     * Constructor method for Cheater class.
     *
     * @param gamePlayController Attaching with observer.
     */
    public Cheater(GamePlayController gamePlayController) {
        this.gamePlayController = gamePlayController;
        this.addObserver(gamePlayController);
    }

    /**
     * Method for Cheater class for reinforcement phase.
     * Start and end of the reinforcement phase.
     *
     * @param countryList   List of countries owned by the player.
     * @param country       Country to which reinforcement armies are to be assigned.
     * @param currentPlayer Current player.
     */
    @Override
    public void reinforcementPhase(ObservableList<Country> countryList, Country country, Player currentPlayer) {
        System.out.println("Beginning Reinforcement phase for cheater player " + currentPlayer.getName() + ".\n");
        setChanged();
        notifyObservers("Beginning Reinforcement phase for cheater player " + currentPlayer.getName() + ".\n");
        System.out.println("List of countries owned: " + countryList.toString() + "\n");
        setChanged();
        notifyObservers("List of countries owned: " + countryList.toString() + "\n");
        for (Country country1 : countryList) {
            if (country1.getNoOfArmies() < Integer.MAX_VALUE / 2)
                country1.setNoOfArmies(country1.getNoOfArmies() * 2);
            System.out.println("Army count of country " + country1.getName() + " has been doubled to " + country1.getNoOfArmies() + ".\n");
            setChanged();
            notifyObservers("Army count of country " + country1.getName() + " has been doubled to " + country1.getNoOfArmies() + ".\n");
        }
        System.out.println("Army count of all the countries owned by cheater player " + currentPlayer.getName() + " has been doubled.\n");
        setChanged();
        notifyObservers("Army count of all the countries owned by cheater player " + currentPlayer.getName() + " has been doubled.\n");
        attackerCountryList.clear();
        attackerCountryList.addAll(countryList);
        currentPlayer.setArmyCount(0);
        System.out.println("Ended Reinforcement phase for cheater player " + currentPlayer.getName() + ".\n");
        setChanged();
        notifyObservers("Ended Reinforcement phase for cheater player " + currentPlayer.getName() + ".\n");
    }

    /**
     * Method for Cheater class for attack phase.
     * Start and end of the attack phase.
     *
     * @param attackingCountryList List of countries attacking.
     * @param defendingCountryList List of countries defending.
     * @param currentPlayer        Current player.
     */
    @Override
    public void attackPhase(ListView<Country> attackingCountryList, ListView<Country> defendingCountryList,
                            Player currentPlayer) {
        System.out.println("Beginning attack phase for cheater player " + currentPlayer.getName() + ".\n");
        setChanged();
        notifyObservers("Beginning attack phase for cheater player " + currentPlayer.getName() + ".\n");
        List<Country> countryWon = new ArrayList<>();
        ObservableList<Country> attackerCountries = this.attackerCountryList;
        for (Country country1 : attackerCountries) {
            System.out.println(country1.getName() + ":" + getDefendingCountryList(country1).toString());
            setChanged();
            notifyObservers(country1.getName() + ":" + getDefendingCountryList(country1).toString());
        }
        Iterator<Country> countryIterator = attackerCountries.iterator();
        boolean flag = false;
        while (countryIterator.hasNext()) {
            Country attackingCountry = countryIterator.next();
            List<Country> defendingCountries = getDefendingCountryList(attackingCountry);
            for (Country defendingCountry : defendingCountries) {
                defendingCountry.setNoOfArmies(1);
                if (attackingCountry.getNoOfArmies() > 1) {
                    attackingCountry.setNoOfArmies(attackingCountry.getNoOfArmies() - 1);
                }
                defendingCountry.getPlayer().getPlayerCountries().remove(defendingCountry);
                defendingCountry.setPlayer(attackingCountry.getPlayer());

                attackingCountry.getPlayer().getPlayerCountries().add(defendingCountry);

                countryWon.add(defendingCountry);
                flag = true;
                System.out.println("Country " + attackingCountry.getName() + " has won country " + defendingCountry.getName() + ".\n");
                setChanged();
                notifyObservers("Country " + attackingCountry.getName() + " has won country " + defendingCountry.getName() + ".\n");
            }
        }
        if (flag) {
            System.out.println("Cheater player " + currentPlayer.getName() + " has won all his neighbouring countries.\n");
            setChanged();
            notifyObservers("Cheater player " + currentPlayer.getName() + " has won all his neighbouring countries.\n");
        }
        attackingCountryList.getItems().addAll(countryWon);
        System.out.println("Ended attack phase for cheater player " + currentPlayer.getName() + ".\n");
        setChanged();
        notifyObservers("Ended attack phase for cheater player " + currentPlayer.getName() + ".\n");
    }

    /**
     * Method for Cheater class for fortification phase.
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
        System.out.println("Beginning Fortification phase for cheater player " + currentPlayer.getName() + ".\n");
        setChanged();
        notifyObservers("Beginning Fortification phase for cheater player " + currentPlayer.getName() + ".\n");
        boolean flag = false;
        for (Country fortifyCountry : attackerCountryList) {
            List<Country> defendingCountries = getDefendingCountryList(fortifyCountry);
            if (!defendingCountries.isEmpty()) {
                if (fortifyCountry.getNoOfArmies() < Integer.MAX_VALUE / 2)
                    fortifyCountry.setNoOfArmies(fortifyCountry.getNoOfArmies() * 2);
                flag = true;
                System.out.println("Army count of country " + fortifyCountry.getName() + " has been doubled to "
                        + fortifyCountry.getNoOfArmies() + ".\n");
                setChanged();
                notifyObservers("Army count of country " + fortifyCountry.getName() + " has been doubled to "
                        + fortifyCountry.getNoOfArmies() + ".\n");
            }
        }
        if (flag) {
            System.out.println("Army count of countries belonging to cheater player " + currentPlayer.getName() +
                    " and having some neighbours belonging to other players has been doubled.\n");
            setChanged();
            notifyObservers("Army count of countries belonging to cheater player " + currentPlayer.getName() +
                    " and having some neighbours belonging to other players has been doubled.\n");
        }
        System.out.println("Ended Fortification phase for cheater player " + currentPlayer.getName() + ".\n");
        setChanged();
        notifyObservers("Ended Fortification phase for cheater player " + currentPlayer.getName() + ".\n");

        return true;
    }

    /**
     * Method for Cheater class for if player can attack.
     *
     * @param countries List of countries owned by the player.
     * @return true
     * If player can attack; other wise false.
     */
    @Override
    public boolean playerCanAttack(ListView<Country> countries) {
        boolean canAttack = false;
        if (attackerCountryList == null || attackerCountryList.isEmpty()) {
            attackerCountryList.addAll(countries.getItems());
        }
        for (Country attackerCountry : attackerCountryList) {
            if (getDefendingCountryList(attackerCountry).size() > 0) {
                canAttack = true;
            }
        }

        if (!canAttack) {
            System.out.println("Cheater player cannot continue with attack phase, move to fortification phase.\n");
            System.out.println("Attack phase ended for cheater player\n");
            setChanged();
            notifyObservers("Cheater player cannot continue with attack phase, move to fortification phase.\n");
            setChanged();
            notifyObservers("Attack phase ended for cheater player\n");
        }

        return canAttack;
    }

}
