package com.risk.view;

import com.risk.model.Card;
import com.risk.model.Player;
import com.risk.controller.CardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * 
 * This class contains methods for setting up stage to view card.
 * 
 * @author Neha Pal
 * @author Karandeep Singh
 * 
 */
public class CardView {

	/**
     * This method is used to create a scene at UI end and opens a window for dice.
     * 
     * @param currentPlayer			object of Player having current playing player
     * @param card					object of Card
     */
    public static void openCardWindow(Player currentPlayer, Card card) {
        final Stage newCardStage = new Stage();
        newCardStage.setTitle("Card Window");
        CardController cardController = new CardController(currentPlayer, card);
        FXMLLoader loader = new FXMLLoader(CardView.class.getClassLoader().getResource("Cards.fxml"));
        loader.setController(cardController);
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        newCardStage.setScene(scene);
        newCardStage.show();
    }
}
