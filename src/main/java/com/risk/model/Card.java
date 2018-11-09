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

    String cardType;

    private Country country;

    private Player currentPlayer;

    private List<Card> cardsToExchange;

    public Card(){ }

    public Card(String cardType) {
        this.cardType = cardType;
    }

    public String getCardType() {
        return cardType;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<Card> getCardsToExchange() {
        return cardsToExchange;
    }

    public void setCardsToExchange(List<Card> cardsToExchange) {
        this.cardsToExchange = cardsToExchange;
    }


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
     * This method is used to return selected cards on the basis of checkboxes selected.
     * @param list list of cards held by currently playing player.
     * @param checkboxes array of checkboxes depicting each card.
     * @return selectedCards list of selected cards which is subset of main list.
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


    public void setCardsExchangeable(List<Card> selectedCards) {
        setCardsToExchange(selectedCards);
        setChanged();
        notifyObservers("cardsTrade");
    }
}
