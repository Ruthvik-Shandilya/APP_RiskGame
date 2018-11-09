package com.risk.services.controller;

import com.risk.services.controller.Util.WindowUtil;
import com.risk.services.MapIO;
import com.risk.view.GamePlayView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * This class provides the Controller options to handle the initial player details.
 *
 * @author Palash Jain
 */
public class PlayerDetailsController implements Initializable{

    /** Map Object */
    private MapIO mapIO;

    /** PlayerDetails terminalWindow */
    @FXML
    private TextArea playerDetails;

    /** Button to start the Game */
    @FXML
    private Button startGame;

    /** A JAVA FX feature which provides choice boxes to select the number of players. */
    @FXML
    private ChoiceBox<Integer> playerCount;

    /** Initial Selected Players */
    private int numOfPlayersSelected;

    /**
     * Player Details Constructor
     *
     * @param mapIO Map Object
     */
    public PlayerDetailsController(MapIO mapIO){
        this.mapIO = mapIO;
    }

    /**
     * This method is called to initialize the player detail controller.
     *
     * @param location Location
     * @param resources resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PlayerDetailsController.initializePlayerCountValues(playerCount);
    }

    /**
     * Choice Box to select the number of players
     *
     * @param playerCount number of players
     *
     * @return player count
     */
    public static ChoiceBox<Integer> initializePlayerCountValues(ChoiceBox<Integer> playerCount) {
        playerCount.getItems().removeAll(playerCount.getItems());
        playerCount.getItems().addAll(3, 4, 5, 6);

        return playerCount;
    }

    /**
     * Start Method provides the Initial Window of any Game Play which uses JavaFX.
     *
     * @param event StartPlay Action Event
     */
    @FXML
    private void startPlay(ActionEvent event){
        numOfPlayersSelected = playerCount.getSelectionModel().getSelectedItem();
        String players[] = playerDetails.getText().split("\n");
        System.out.println("Player list=" + Arrays.asList(players).toString());
        if(players.length!=numOfPlayersSelected){
            WindowUtil.popUpWindow("Player names warning", "Alert", "Please enter appropriate player names.");
        }
        startGame.setOnAction(new GamePlayView(this.mapIO, players));
    }

}
