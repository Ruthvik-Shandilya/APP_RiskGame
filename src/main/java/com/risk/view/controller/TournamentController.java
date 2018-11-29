package com.risk.view.controller;

import com.risk.model.IPlayerType;
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

    private int numberOfTurnsToPlay;

    private int numberOfMapsToPlay;

    private List<MapIO> mapList;

    private List<Player> playerList;

    private List<ChoiceBox> playerDropDownList = new ArrayList<>();

    private int numberOfGamesToPlay;

    public int getNumberOfTurnsToPlay() {
        return numberOfTurnsToPlay;
    }

    public void setNumberOfTurnsToPlay(int numberOfTurnsToPlay) {
        this.numberOfTurnsToPlay = numberOfTurnsToPlay;
    }


    public int getNumberOfGamesToPlay() {
        return numberOfGamesToPlay;
    }

    public void setNumberOfGamesToPlay(int numberOfGamesToPlay) {
        this.numberOfGamesToPlay = numberOfGamesToPlay;
    }


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


    public TournamentController() {
        mapList = new ArrayList<>();
        playerList = new ArrayList<>();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        WindowUtil.disableButtonControl(player1, player2, player3, player4, map1, map2, map3, map4, map5, start);
        populatePlayerCheckBox();
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

            if(numberOfGamesToPlay > 5 || numberOfGamesToPlay < 1){
                WindowUtil.popUpWindow("Games to be played should be between 1 to 5", "Problem in games to play", "You have entered a value less than 1 or greater than 5");
                WindowUtil.disableButtonControl(start);
                return;
            }

            if(numberOfTurnsToPlay > 50 || numberOfTurnsToPlay < 10){
                WindowUtil.popUpWindow("Turns to be played should be between 10 to 50", "Problem in turns to play", "You have entered a value less than 10 or greater than 50");
                WindowUtil.disableButtonControl(start);
                return;
            }

            if(numberOfMapsToPlay > 5 || numberOfMapsToPlay < 1){
                WindowUtil.popUpWindow("Maps to be played should be between 1 to 5", "Problem in maps to play", "You have entered a value less than 1 or greater than 5");
                WindowUtil.disableButtonControl(start);
                return;
            }

            List<Button> mapButtonList = new ArrayList<Button>();
            mapButtonList.add(map1);
            mapButtonList.add(map2);
            mapButtonList.add(map3);
            mapButtonList.add(map4);
            mapButtonList.add(map5);

            int mapToEnable;

            for ( mapToEnable = 0; mapToEnable < numberOfMapsToPlay; mapToEnable++){
                WindowUtil.enableButtonControl(mapButtonList.get(mapToEnable));
            }
            for(int i = mapToEnable; i < 5; i++){
                WindowUtil.disableButtonControl(mapButtonList.get(i));
            }

            if(numberOfGamesToPlay != 0 && numberOfTurnsToPlay != 0 && numberOfPlayersPlaying != 0 && numberOfTurnsToPlay != 0){
                WindowUtil.enableButtonControl(start);
            }


        } catch (NumberFormatException exception) {
            WindowUtil.popUpWindow("Please enter a number", "NumberFormatException", "You did not enter a number");
       }
   }

   @FXML
   public void showPlayer(ActionEvent event) throws NumberFormatException{
       System.out.println("In Player");

       try {
           int bufferNumberOfPlayers = Integer.parseInt(numberOfPlayers.getText().trim());

           setNumberOfPlayersPlaying(bufferNumberOfPlayers);

           if(numberOfPlayersPlaying > 4 || numberOfPlayersPlaying < 1){
               WindowUtil.popUpWindow("Players playing should be between 1 to 4", "Problem in number of players", "You have entered a value less than 1 or greater than 4");
               WindowUtil.disableButtonControl(start);
               return;
           }

           playerDropDownList.add(player1);
           playerDropDownList.add(player2);
           playerDropDownList.add(player3);
           playerDropDownList.add(player4);

           int playerToEnable;

           for (playerToEnable = 0; playerToEnable < numberOfPlayersPlaying; playerToEnable++) {
               WindowUtil.enableButtonControl(playerDropDownList.get(playerToEnable));
           }
           for (int i = playerToEnable; i < 4; i++) {
               WindowUtil.disableButtonControl(playerDropDownList.get(i));
           }

           if(numberOfGamesToPlay != 0 && numberOfMapsToPlay != 0 && numberOfPlayersPlaying != 0 && (numberOfTurnsToPlay >= 10 && numberOfTurnsToPlay <= 50)){
                WindowUtil.enableButtonControl(start);
           }

       } catch (NumberFormatException e){
           WindowUtil.popUpWindow("Please enter a number", "NumberFormatException", "You did not enter a number");
       }


   }

   public void populatePlayerCheckBox(){
       String playerTypes[] = {IPlayerType.AGGRESSIVE, IPlayerType.BENEVOLENT, IPlayerType.RANDOM, IPlayerType.CHEATER};
       player1.getItems().addAll(playerTypes);
       player2.getItems().addAll(playerTypes);
       player3.getItems().addAll(playerTypes);
       player4.getItems().addAll(playerTypes);

   }

   @FXML
    public void start(ActionEvent event){
        for (int i = 0; i < numberOfPlayersPlaying; i++){

            playerList.add(new Player("Player "+ (i+1), (String)playerDropDownList.get(i).getValue()));
//            System.out.println(playerDropDownList.get(i).getValue());
        }

        for(Player player: playerList){
            System.out.println(player.getName());
        }
   }










}
