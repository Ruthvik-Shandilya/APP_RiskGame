package com.risk.view;

import com.risk.model.Card;
import com.risk.model.Player;
import com.risk.controller.CardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CardView {

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
