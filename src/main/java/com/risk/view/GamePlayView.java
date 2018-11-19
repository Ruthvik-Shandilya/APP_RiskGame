package com.risk.view;

import java.io.IOException;

import com.risk.services.MapIO;

import com.risk.services.controller.GamePlayController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class provides the view for the user showing game screen.
 * 
 * @author Palash Jain
 * @author Farhan Shaheen
 */
public class GamePlayView implements EventHandler<ActionEvent> {
	
	/** Object for MapIO Class */
    private MapIO mapIO;

    /** Array of String to hold names of players */
    private String[] names;

    /**
     * Constructor to load contents of map 
     * @param mapIO		MapIO Object
     * @param names		names of players
     */
    public GamePlayView(MapIO mapIO, String[] names) {
        this.mapIO = mapIO;
        this.names = names;
    }

    /*
	 * (non-Javadoc)
	 * This method is overridden to create a scene at UI end.
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
    @Override
    public void handle(ActionEvent event) {

        GamePlayController controller = new GamePlayController(this.mapIO, this.names);


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
