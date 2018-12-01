package com.risk.view;

import com.risk.controller.TournamentController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class TournamentView  implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {

        final Stage tournamentStage = new Stage();
        TournamentController tournamentController =  new TournamentController();
        tournamentStage.initModality(Modality.APPLICATION_MODAL);

        tournamentStage.setTitle("Tournament");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TournamentView.fxml"));
        loader.setController(tournamentController);

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

        Scene scene = new Scene(root);
        tournamentStage.setScene(scene);
        tournamentStage.show();
    }
}
