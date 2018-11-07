package com.risk.view;

import java.io.IOException;

import com.risk.controller.GamePlayController;
import com.risk.services.MapIO;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GamePlayView implements EventHandler<ActionEvent> {
    private MapIO mapIO;

    private String[] names;

    public GamePlayView(MapIO mapIO, String[] names) {
        this.mapIO = mapIO;
        this.names = names;
    }

    @Override
    public void handle(ActionEvent event) {

        GamePlayController controller = new GamePlayController(this.mapIO, this.names);


        final Stage gamePlayStage = new Stage();
        gamePlayStage.setTitle("Game Screen");
        gamePlayStage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("MapSelectorLayout.fxml"));
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
