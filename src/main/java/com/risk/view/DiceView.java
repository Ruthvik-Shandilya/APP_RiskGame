package com.risk.view;

import java.io.IOException;

import com.risk.model.Player;
import com.risk.controller.DiceController;
import com.risk.model.Dice;

import com.risk.controller.GamePlayController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class will setup attack window
 *
 * @author Ruthvik Shandilya
 * @author Neha Pal
 */
public class DiceView {

    /**
     * This method create a scene at UI end and opens a window for dice.
     * 
     * @param dice					object of Dice
     * @param player				object of Player
     * @param gamePlayController		object of GamePlayController
     * 
     */
    public static void openDiceWindow(Dice dice, Player player, GamePlayController gamePlayController) {
        final Stage diceStage = new Stage();
        diceStage.setTitle("Attack Window");

        DiceController diceController = new DiceController(dice, player.getPlayerBehaviour(), gamePlayController);

        FXMLLoader loader = new FXMLLoader(DiceView.class.getClassLoader().getResource("DiceView.fxml"));
        loader.setController(diceController);

        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        diceStage.setScene(scene);
        diceStage.show();
    }
}
