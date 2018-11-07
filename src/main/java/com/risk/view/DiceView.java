package com.risk.view;

import java.io.IOException;

import com.risk.controller.DiceController;
import com.risk.model.Dice;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DiceView implements EventHandler<ActionEvent> {

    private Dice dice;

    public DiceView(Dice dice) {

        this.dice = dice;
    }

    public void handle(ActionEvent event) {
        final Stage newMapStage = new Stage();
        newMapStage.setTitle("Attack Window");

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
        newMapStage.setScene(scene);
        newMapStage.show();
    }
}
