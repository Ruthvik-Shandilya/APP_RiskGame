package com.risk.view;

import java.io.IOException;

import com.risk.model.Player;
import com.risk.view.controller.DiceController;
import com.risk.model.Dice;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * This class will setup attack window
 * @author Ruthvik Shandilya
 * @author Neha Pal
 *
 */
public class DiceView {

    /*
	 * (non-Javadoc)
	 * This method is overridden to create a scene at UI end.
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
    public static void openDiceWindow(Dice dice, Player player, TextArea terminalWindow) {
        final Stage diceStage = new Stage();
        diceStage.setTitle("Attack Window");

        DiceController diceController = new DiceController(dice, player.getPlayerBehaviour(), terminalWindow);

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
