package com.risk.model;

import com.risk.services.controller.CardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Card extends Observable {

    /** Type of the card*/
    String cardType;

    /** Object of country, which is the on the card*/
    private Country country;

    /** Player who is the card Holder*/
    private Player currentPlayer;

    /** List of cards which can be exchanged*/
    private List<Card> cardsToExchange;

    /**
     * Cards constructor
     *
     * */

    public Card(){ }

    /**
     * Cards constructor
     *
     * @param cardType  Type of card
     *
     * */

    public Card(String cardType) {
        this.cardType = cardType;
    }

    /**
     * Get card type
     *
     * @return Type of card
     *
     * */

    public String getCardType() {
        return cardType;
    }

    /**
     * get Country of the card
     *
     * @return country of the card
     *
     * */
    public Country getCountry() {
        return country;
    }

    /**
     * Set Country
     *
     * @param country of the card
     *
     * */

    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * Getter for list of cards for exchange.
     *
     * @return  list of cards
     *
     * */

    public List<Card> getCardsToExchange() {
        return cardsToExchange;
    }
    /** *
     * Set cardsToExchange
     *
     * @param cardsToExchange
     */

    public void setCardsToExchange(List<Card> cardsToExchange) {
        this.cardsToExchange = cardsToExchange;
    }

    /**
     * Method is used to open the window, for displaying
     * the cards owned by the player.
     *
     * @param player Player having the turn
     * @param card card to display for the player
     */
    public void openCardWindow(Player player, Card card) {
        this.currentPlayer = player;
        final Stage newMapStage = new Stage();
        newMapStage.setTitle("Card Window");
        CardController cardController = new CardController(this.currentPlayer, card);
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Cards.fxml"));
        loader.setController(cardController);
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        newMapStage.setScene(scene);
        newMapStage.show();
    }

    /**
     * Method returns a list of cards which
     * are seleted by the current player
     *
     * @param list
     * @param checkboxes
     * @return List of cards selected by the player
     */
    public List<Card> retrieveSelectedCardsFromCheckbox(List<Card> list, CheckBox[] checkboxes) {
        List<Card> selectedCards = new ArrayList<>();
        for (int i=0;i<checkboxes.length;++i){
            if(checkboxes[i].isSelected()) {
                selectedCards.add(list.get(i));
            }
        }
        return selectedCards;
    }

    /**
     * Method is used to verify ,
     * if cards can be exchanged for army or not
     *
     *
     * @param selectedCards
     * @return true if the exchange is possible; otherwise false
     */
    public boolean checkTradePossible(List<Card> selectedCards) {
        boolean returnFlag = false;
        if(selectedCards.size()==3) {
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
            if((infantry==1 && cavalry==1 && artillery==1) || infantry==3 || cavalry==3 || artillery==3) {
                returnFlag = true;
            }
        }
        return returnFlag;
    }

    /**
     * Method notifies the observers of the card,
     * Also sets the cards which are selected for exchange.
     *
     * @param selectedCards cards which are selected by the user to exchange
     */

    public void setCardsExchangeable(List<Card> selectedCards) {
        setCardsToExchange(selectedCards);
        setChanged();
        notifyObservers("cardsTrade");
    }
}
