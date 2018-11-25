package com.risk.view.controller;

import com.risk.model.Player;
import com.risk.model.TournamentModel;
import com.risk.services.MapIO;
import com.risk.view.Util.WindowUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TournamentController implements Initializable {

    private TournamentModel tournamentModel;

    @FXML
    private TextField numberOfGames;

    @FXML
    private TextField numberOfTurns;

    @FXML
    private TextField numberOfMaps;

    @FXML
    private TextField numberOfPlayers;

    @FXML
    private ChoiceBox<String> player1;

    @FXML
    private ChoiceBox<String> player2;

    @FXML
    private ChoiceBox<String> player3;

    @FXML
    private ChoiceBox<String> player4;

    @FXML
    private Button map1;

    @FXML
    private Button map2;

    @FXML
    private Button map3;

    @FXML
    private Button map4;

    @FXML
    private Button map5;

    @FXML
    private Button start;

    @FXML
    private Button exit;

    @FXML
    private Button register;

    @FXML
    private Button showPlayers;

    @FXML
    private Label errorMessage;


    public int getNumberOfTurnsToPlay() {
        return numberOfTurnsToPlay;
    }

    public void setNumberOfTurnsToPlay(int numberOfTurnsToPlay) {
        this.numberOfTurnsToPlay = numberOfTurnsToPlay;
    }

    private int numberOfTurnsToPlay;

    public int getNumberOfGamesToPlay() {
        return numberOfGamesToPlay;
    }

    public void setNumberOfGamesToPlay(int numberOfGamesToPlay) {
        this.numberOfGamesToPlay = numberOfGamesToPlay;
    }

    private int numberOfGamesToPlay;

    public int getNumberOfPlayersPlaying() {
        return numberOfPlayersPlaying;
    }

    public void setNumberOfPlayersPlaying(int numberOfPlayersPlaying) {
        this.numberOfPlayersPlaying = numberOfPlayersPlaying;
    }

    private int numberOfPlayersPlaying;

    public void setNumberOfMapsToPlay(int numberOfMapsToPlay) {
        this.numberOfMapsToPlay = numberOfMapsToPlay;
    }

    private int numberOfMapsToPlay;

    private List<MapIO> mapList;

    private List<Player> playerList;

    public TournamentController() {
        mapList = new ArrayList<>();
        playerList = new ArrayList<>();
    }

   @FXML
    private void register(ActionEvent event) throws NumberFormatException{
       System.out.println("In register");
        try {
            int bufferNumberOfGames =  Integer.parseInt(numberOfGames.getText().trim());
            int bufferNumberOfTurns =  Integer.parseInt(numberOfTurns.getText().trim());
            int bufferNumberOfMaps =  Integer.parseInt(numberOfMaps.getText().trim());

            setNumberOfGamesToPlay(bufferNumberOfGames);
            setNumberOfTurnsToPlay(bufferNumberOfTurns);
            setNumberOfMapsToPlay(bufferNumberOfMaps);

            System.out.println(numberOfGamesToPlay);
            System.out.println(numberOfTurnsToPlay);
            System.out.println(numberOfMapsToPlay);

        } catch (NumberFormatException exception) {
            WindowUtil.popUpWindow("Please enter a number", "NumberFormatException", "You did not enter a number");
       }
   }










    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        numberOfGames =  new TextField();
//        numberOfTurns =  new TextField();
//        numberOfMaps = new TextField();
//        numberOfPlayers = new TextField();
    }
}
