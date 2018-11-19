package com.risk.view;

import com.risk.services.controller.PlayerDetailsController;
import com.risk.services.MapIO;
import com.risk.services.MapValidate;
import com.risk.services.controller.Util.WindowUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Provides the view for the user showing details of the players
 *
 * @author Ruthvik Shandilya
 * @author Neha Pal
 */
public class PlayerDetailsView implements EventHandler<ActionEvent> {

	/*
	* (non-Javadoc)
	* This method is overridden to create a scene at UI end.
	* @see javafx.event.EventHandler#handle(javafx.event.Event)
	*/
    @Override
    public void handle(ActionEvent event) {

        File selectedFile = WindowUtil.showFileChooser();
        String fileName = selectedFile.getAbsolutePath();
        System.out.println("File location: " + fileName);
        MapValidate mapValidate = new MapValidate();
        MapIO readMap=null;
        if (mapValidate.validateMapFile(fileName)) {
            readMap = new MapIO(mapValidate);
        }
        else{
            WindowUtil.popUpWindow("","Error","Invalid Map file.");
            return;
        }

        final Stage playerDetailsStage = new Stage();

        PlayerDetailsController playerDetailsController = new PlayerDetailsController(readMap);
        playerDetailsStage.initModality(Modality.APPLICATION_MODAL);

        playerDetailsStage.setTitle("Player Details");

       FXMLLoader loader = new FXMLLoader(getClass().getResource("/PlayerDetails.fxml"));
        loader.setController(playerDetailsController);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

        Scene scene = new Scene(root);
        playerDetailsStage.setScene(scene);
        playerDetailsStage.show();
    }

}
