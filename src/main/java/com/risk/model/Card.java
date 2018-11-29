package com.risk.model;

import com.risk.view.controller.CardController;
import javafx.scene.control.CheckBox;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Card class which represents the card model of the Risk game.
 * It provides methods for performing operations on the cards
 * like exchange cards for armies etc.
 *
 * @author Palash
 * @author Ruthvik Shandilya
 */

public class Card extends Observable {

    /**
     * Type of the card
     */
    String cardType;

    /**
     * Object of country, which is the on the card
     */
    private Country country;

    /**
     * Player who is the card Holder
     */
    private Player currentPlayer;

    /**
     * List of cards which can be exchanged
     */
    private List<Card> cardsToExchange;

    /**
     * Cards constructor
     */

    public Card() {
    }

    /**
     * Cards constructor
     *
     * @param cardType Type of card
     */

    public Card(String cardType) {
        this.cardType = cardType;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Get card type
     *
     * @return Type of card
     */

    public String getCardType() {
        return cardType;
    }

    /**
     * get Country of the card
     *
     * @return country of the card
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Set Country
     *
     * @param country of the card
     */

    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * Getter for list of cards for exchange.
     *
     * @return list of cards
     */

    public List<Card> getCardsToExchange() {
        return cardsToExchange;
    }

    /**
     * Set cardsToExchange
     *
     * @param cardsToExchange cards to exchange
     */

    public void setCardsToExchange(List<Card> cardsToExchange) {
        this.cardsToExchange = cardsToExchange;
    }

    public void automateCardWindow(Player player){
        CardController cardController = new CardController(player, this);
        cardController.automaticCardInitialization();
    }


    /**
     * Method returns a list of cards which
     * are selected by the current player
     *
     * @param list list
     * @param checkboxes checkboxes
     * @return List of cards selected by the player
     */
    public List<Card> chooseCards(List<Card> list, CheckBox[] checkboxes) {
        List<Card> selectedCards = new ArrayList<>();
        for (int i = 0; i < checkboxes.length; ++i) {
            if (checkboxes[i].isSelected()) {
                selectedCards.add(list.get(i));
            }
        }
        return selectedCards;
    }



    /**
     * Method is used to verify ,
     * if cards can be exchanged for army or not
     *
     * @param selectedCards selected cards
     * @return true if the exchange is possible; otherwise false
     */
    public boolean isExchangePossible(List<Card> selectedCards) {
        boolean isPossible = false;
        if (selectedCards.size() == 3) {
            int infantry = 0, cavalry = 0, artillery = 0;
            for (Card card : selectedCards) {
                if (card.getCardType().equals(ICardType.INFANTRY)) {
                    infantry++;
                } else if (card.getCardType().equals(ICardType.CAVALRY)) {
                    cavalry++;
                } else if (card.getCardType().equals(ICardType.ARTILLERY)) {
                    artillery++;
                }
            }
            if ((infantry == 1 && cavalry == 1 && artillery == 1) || infantry == 3 || cavalry == 3 || artillery == 3) {
                isPossible = true;
            }
        }
        return isPossible;
    }

    /**
     * Method notifies the observers of the card,
     * Also sets the cards which are selected for exchange.
     *
     * @param selectedCards cards which are selected by the user to exchange
     */

    public void cardsToBeExchanged(List<Card> selectedCards) {
        setCardsToExchange(selectedCards);
        setChanged();
        notifyObservers("cardsExchange");
    }

    public List<Card> generateValidCardCombination(List<Card> selectedCards) {
        HashMap<String, Integer> cardCountMap = new HashMap<>();
        for (Card card : selectedCards) {
            if (cardCountMap.containsKey(card.getCardType())) {
                cardCountMap.put(card.getCardType(), cardCountMap.get(card.getCardType()) + 1);
            } else {
                cardCountMap.put(card.getCardType(), 1);
            }

        }
        for (Map.Entry<String, Integer> entry : cardCountMap.entrySet()) {
            if (entry.getValue() >= 3) {
                return selectedCards.stream().filter(t -> t.getCardType().equals(entry.getKey()))
                        .collect(Collectors.toList());
            }
        }
        return null;
    }
}
