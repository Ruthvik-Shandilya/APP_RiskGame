package com.risk.services.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.risk.services.controller.Util.WindowUtil;
import com.risk.model.Card;
import com.risk.model.Player;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Card Controller Class
 * 
 * @author Farhan Shaheen
 *
 */

public class CardController implements Initializable {

	/** Object for Player class */
    private Player player;

    /** Object for Card class */
    private Card card;

    /** Label variable for the current Player */
    @FXML
    private Label currentPlayer;

    /** Variable for the exchange button */
    @FXML
    private Button exchange;

    /** List of cards owned by the player */
    private ArrayList<Card> playerOwnedCards;

    /** checkbox array */
    @FXML
    private CheckBox[] checkBox;

    /** variable for VBox cardVbox */
    @FXML
    private VBox cardVbox;

    /** Variable for the text */
    @FXML
    private Label text;

    /** variable for the cancel view button */
    @FXML
    private Button cancelView;

    /**
	 * Card Controller constructor class
	 * 
	 * @param player
	 *            Current Player
	 * @param card
	 *            player card
	 */
    public CardController(Player player, Card card) {
        this.player = player;
        this.card = card;
    }

    /**
	 * Method to initialize player attributes for cards
	 * 
	 * @param location
	 *            URL
	 * @param resources
	 *            ResourceBundle
	 */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        currentPlayer.setText("Cards of " + player.getName());
        playerOwnedCards = player.getCardList();
        if (playerOwnedCards.size() < 3) {
            exchange.setDisable(true);
        } else {
            exchange.setDisable(false);
        }
    }

    /** Method to load cards */
    public void loadCards() {
        int initialCardsCount = playerOwnedCards.size();
        checkBox = new CheckBox[initialCardsCount];
        for (int i = 0; i < initialCardsCount; i++) {
            checkBox[i] = new CheckBox(playerOwnedCards.get(i).getCardType().toString() + " -> " + playerOwnedCards.get(i).getCountry().getName());
        }
        cardVbox.getChildren().addAll(checkBox);
    }

    /** Method for cancel view action */
    @FXML
    private void cancelView(ActionEvent event) {
        WindowUtil.exitWindow(cancelView);
    }

    /**
	 * Method for check exchange
	 * 
	 * @param location
	 *            URL
	 * @param resources
	 *            ResourceBundle
	 */
    @FXML
    private void checkExchange(ActionEvent event) {
        exchange.setDisable(false);
        text.setText(null);
        List<Card> selectedCards = card.retrieveSelectedCardsFromCheckbox(playerOwnedCards, checkBox);

        if (selectedCards.size() == 3) {
            boolean flag = card.checkTradePossible(selectedCards);

            if (flag) {
                card.setCardsExchangeable(selectedCards);
				WindowUtil.exitWindow(exchange);
            } else {
                text.setText("This Combination is not Valid");
                exchange.setDisable(false);
                return;
            }
        } else {
            text.setText("Select only 3 Cards");
            return;
        }

    }
}
