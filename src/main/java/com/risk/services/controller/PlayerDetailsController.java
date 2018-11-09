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

public class PlayerDetailsController implements Initializable{
    private MapIO mapIO;

    @FXML
    private TextArea playerDetails;

    @FXML
    private Button startGame;

    @FXML
    private ChoiceBox<Integer> playerCount;

    private int numOfPlayersSelected;

    public PlayerDetailsController(MapIO mapIO){
        this.mapIO = mapIO;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PlayerDetailsController.initializePlayerCountValues(playerCount);
    }

    public static ChoiceBox<Integer> initializePlayerCountValues(ChoiceBox<Integer> playerCount) {
        playerCount.getItems().removeAll(playerCount.getItems());
        playerCount.getItems().addAll(3, 4, 5, 6);

        return playerCount;
    }

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
