package com.risk.view;

import com.risk.services.MapIO;
import com.risk.view.controller.GamePlayController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

/**
 * This class provides the view for the user showing game screen.
 * 
 * @author Palash Jain
 * @author Farhan Shaheen
 */
public class GamePlayView implements EventHandler<ActionEvent> {
	
	/** Object for MapIO Class */
    private MapIO mapIO;

    private HashMap<String,String> playerNamesAndTypes;


    public GamePlayView(MapIO mapIO, HashMap<String,String> hm) {
        this.mapIO = mapIO;
        this.playerNamesAndTypes = hm;
    }

    /*
	 * (non-Javadoc)
	 * This method is overridden to create a scene at UI end.
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
    @Override
    public void handle(ActionEvent event) {

        GamePlayController controller = new GamePlayController(this.mapIO, this.playerNamesAndTypes);


        final Stage gamePlayStage = new Stage();
        gamePlayStage.setTitle("Game Screen");

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

        final Stage terminalStage = new Stage();
        terminalStage.setTitle("Terminal Window");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("TerminalWindow.fxml"));
        fxmlLoader.setController(controller);

        Parent myRoot = null;
        try {
            myRoot = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene myScene = new Scene(myRoot);
        terminalStage.setScene(myScene);
        terminalStage.show();
    }

}
