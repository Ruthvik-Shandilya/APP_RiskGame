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
/**
 * @author Palash Jain
 * @author Neha Pal
 * This class provides the view for the user showing game screen..
 */
public class TournamentView  implements EventHandler<ActionEvent> {

	/* This method is overridden to create a scene at UI end.
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
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
