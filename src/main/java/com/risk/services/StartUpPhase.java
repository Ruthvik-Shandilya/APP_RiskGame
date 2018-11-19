package com.risk.services;

import java.util.*;

import com.risk.model.*;
import com.risk.view.Util.WindowUtil;

import javafx.scene.control.TextArea;

/**
 * Class for governing the startup phase of the game. It sets up the game by
 * getting the player data, and allocating countries and armies according to the
 * rules.
 *
 * @author Karandeep Singh
 * @author Neha Pal
 */
public class StartUpPhase {

    /**
     * Method to assign countries to a player
     *
     * @param map      MapIO Object
     * @param textArea to show data on UI
     * @return stackOfCards
     */
    public Stack<Card> assignCardToCountry(MapIO map, TextArea textArea) {
        Stack<Card> stackOfCards = new Stack<>();

        List<Country> allCountries = new ArrayList<>(map.getMapGraph().getCountrySet().values());

        List<String> cardTypes = new ArrayList<>();
        cardTypes.add(ICardType.ARTILLERY);
        cardTypes.add(ICardType.CAVALRY);
        cardTypes.add(ICardType.INFANTRY);

        for (Country country : allCountries) {
            Card card = new Card(cardTypes.get(new Random().nextInt(cardTypes.size())));
            card.setCountry(country);
            stackOfCards.push(card);
        }
        return stackOfCards;
    }

    /**
     * Method to assign countries to a player
     *
     * @param map      MapIO Object
     * @param players  list of players
     * @param textArea to show data on UI
     * @return players
     */

    public List<Player> assignCountryToPlayer(MapIO map, List<Player> players, TextArea textArea) {

        ArrayList<Country> countries = new ArrayList<>(map.getMapGraph().getCountrySet().values());
        while (countries.size() > 0) {
            for (int i = 0; i < players.size(); ++i) {
                if (countries.size() > 1) {
                    int assignCountryIndex = new Random().nextInt(countries.size());
                    players.get(i).addCountry(countries.get(assignCountryIndex));
                    countries.get(assignCountryIndex).setPlayer(players.get(i));
                    countries.get(assignCountryIndex).setNoOfArmies(1);
                    WindowUtil.updateTerminalWindow(countries.get(assignCountryIndex).getName() + " assigned to " +
                            players.get(i).getName() + " ! \n", textArea);
                    countries.remove(assignCountryIndex);
                } else if (countries.size() == 1) {
                    players.get(i).addCountry(countries.get(0));
                    countries.get(0).setPlayer(players.get(i));
                    countries.get(0).setNoOfArmies(1);
                    WindowUtil.updateTerminalWindow(countries.get(0).getName() + " assigned to " +
                            players.get(i).getName() + " ! \n", textArea);
                    countries.remove(0);
                    break;
                }
            }
        }
        for (Player player : players) {
            player.setArmyCount(player.getArmyCount() - player.getPlayerCountries().size());
        }
        return players;
    }

}
