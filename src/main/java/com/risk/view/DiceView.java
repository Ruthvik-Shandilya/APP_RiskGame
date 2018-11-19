package com.risk.view;

import java.io.IOException;

import com.risk.view.controller.DiceController;
import com.risk.model.Dice;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class will setup attack window
 * @author Ruthvik Shandilya
 * @author Neha Pal
 *
 */
public class DiceView implements EventHandler<ActionEvent> {

	/** Object for Dice Class */
    private Dice dice;

    /**
     * Constructor for DiceView
     * @param dice	object of dice class
     */
    public DiceView(Dice dice) {

        this.dice = dice;
    }

    /*
	 * (non-Javadoc)
	 * This method is overridden to create a scene at UI end.
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
    public void handle(ActionEvent event) {
        final Stage diceStage = new Stage();
        diceStage.setTitle("Attack Window");

        DiceController diceController = new DiceController(dice);

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("DiceView.fxml"));
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
