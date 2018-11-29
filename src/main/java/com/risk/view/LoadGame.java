package com.risk.view;

import com.risk.view.controller.GamePlayController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoadGame {

    public static void openLoadGame() {

        GamePlayController controller = new GamePlayController().loadGame();

        final Stage gamePlayStage = new Stage();
        gamePlayStage.setTitle("Game Screen");

        FXMLLoader loader = new FXMLLoader(LoadGame.class.getClassLoader().getResource("MapSelectorLayout.fxml"));
        loader.setController(controller);
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        gamePlayStage.setScene(scene);
        gamePlayStage.show();

    }


}
