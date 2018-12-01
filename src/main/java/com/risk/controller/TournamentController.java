package com.risk.controller;

import com.risk.model.IPlayerType;
import com.risk.model.Player;
import com.risk.model.TournamentModel;
import com.risk.services.MapIO;
import com.risk.services.Util.WindowUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

public class TournamentController extends Observable implements Initializable {

    private TournamentModel tournamentModel;

    private TextArea terminalWindow;

    public TextArea getTerminalWindow() {

        return terminalWindow;

    }

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

    @FXML
    private TextArea textArea;


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
        tournamentModel = new TournamentModel();
    }

    @FXML
    private void register(ActionEvent event) throws NumberFormatException {
        System.out.println("In register");
        try {
            int bufferNumberOfGames = Integer.parseInt(numberOfGames.getText().trim());
            int bufferNumberOfTurns = Integer.parseInt(numberOfTurns.getText().trim());
            int bufferNumberOfMaps = Integer.parseInt(numberOfMaps.getText().trim());

            setNumberOfGamesToPlay(bufferNumberOfGames);
            setNumberOfTurnsToPlay(bufferNumberOfTurns);
            setNumberOfMapsToPlay(bufferNumberOfMaps);

            if (numberOfGamesToPlay > 5 || numberOfGamesToPlay < 1) {
                WindowUtil.popUpWindow("Games to be played should be between 1 to 5", "Problem in games to play", "You have entered a value less than 1 or greater than 5");
                WindowUtil.disableButtonControl(start);
                return;
            }

            if (numberOfTurnsToPlay > 50 || numberOfTurnsToPlay < 10) {
                WindowUtil.popUpWindow("Turns to be played should be between 10 to 50", "Problem in turns to play", "You have entered a value less than 10 or greater than 50");
                WindowUtil.disableButtonControl(start);
                return;
            }

            if (numberOfMapsToPlay > 5 || numberOfMapsToPlay < 1) {
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

            for (mapToEnable = 0; mapToEnable < numberOfMapsToPlay; mapToEnable++) {
                WindowUtil.enableButtonControl(mapButtonList.get(mapToEnable));
            }
            for (int i = mapToEnable; i < 5; i++) {
                WindowUtil.disableButtonControl(mapButtonList.get(i));
            }

            if (numberOfGamesToPlay != 0 && numberOfTurnsToPlay != 0 && numberOfPlayersPlaying != 0 && numberOfTurnsToPlay != 0) {
                WindowUtil.enableButtonControl(start);
                errorMessage.setText("Select players properly and then click start");
            }


        } catch (NumberFormatException exception) {
            WindowUtil.popUpWindow("Please enter a number", "NumberFormatException", "You did not enter a number");
        }
    }

    @FXML
    public void showPlayer(ActionEvent event) throws NumberFormatException {
        System.out.println("In Player");

        try {
            int bufferNumberOfPlayers = Integer.parseInt(numberOfPlayers.getText().trim());

            setNumberOfPlayersPlaying(bufferNumberOfPlayers);

            if (numberOfPlayersPlaying > 4 || numberOfPlayersPlaying < 1) {
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

            if (numberOfGamesToPlay != 0 && numberOfMapsToPlay != 0 && numberOfPlayersPlaying != 0 && (numberOfTurnsToPlay >= 10 && numberOfTurnsToPlay <= 50)) {
                WindowUtil.enableButtonControl(start);
                errorMessage.setText("Select players properly and then click start");
            }

        } catch (NumberFormatException e) {
            WindowUtil.popUpWindow("Please enter a number", "NumberFormatException", "You did not enter a number");
        }


    }

    @FXML
    private void map1(ActionEvent event) {
        File selectedFile = tournamentModel.checkAndLoadMap(mapList);
        if (selectedFile == null) {
            map1.setText("Invalidmap selected");
            errorMessage.setText("Invalidmap selected");
            WindowUtil.disableButtonControl(start);
            return;
        }
        map1.setText(selectedFile.getName());
    }

    @FXML
    private void map2(ActionEvent event) {
        File selectedFile = tournamentModel.checkAndLoadMap(mapList);
        if (selectedFile == null) {
            map2.setText("Invalidmap selected");
            errorMessage.setText("Invalidmap selected");
            WindowUtil.disableButtonControl(start);
            return;
        }
        map2.setText(selectedFile.getName());
    }

    @FXML
    private void map3(ActionEvent event) {
        File selectedFile = tournamentModel.checkAndLoadMap(mapList);
        if (selectedFile == null) {
            map3.setText("Invalidmap selected");
            errorMessage.setText("Invalidmap selected");
            WindowUtil.disableButtonControl(start);
            return;
        }
        map3.setText(selectedFile.getName());
    }

    @FXML
    private void map4(ActionEvent event) {
        File selectedFile = tournamentModel.checkAndLoadMap(mapList);
        if (selectedFile == null) {
            map4.setText("Invalidmap selected");
            errorMessage.setText("Invalidmap selected");
            WindowUtil.disableButtonControl(start);
            return;
        }
        map4.setText(selectedFile.getName());
    }

    @FXML
    private void map5(ActionEvent event) {
        File selectedFile = tournamentModel.checkAndLoadMap(mapList);
        if (selectedFile == null) {
            map5.setText("Invalidmap selected");
            errorMessage.setText("Invalidmap selected");
            WindowUtil.disableButtonControl(start);
            return;
        }
        map5.setText(selectedFile.getName());
    }


    public void populatePlayerCheckBox() {
        String playerTypes[] = {IPlayerType.AGGRESSIVE, IPlayerType.BENEVOLENT, IPlayerType.RANDOM, IPlayerType.CHEATER};
        player1.getItems().addAll(playerTypes);
        player2.getItems().addAll(playerTypes);
        player3.getItems().addAll(playerTypes);
        player4.getItems().addAll(playerTypes);

    }

    @FXML
    public void start(ActionEvent event) {

        HashMap<String, ArrayList<HashMap<Player, Integer>>> result = new HashMap<>();
        System.out.println("In start");

        playerList.clear();
        for (int i = 0; i < numberOfPlayersPlaying; i++) {
            playerList.add(new Player("Player " + (i + 1), (String) playerDropDownList.get(i).getValue()));
        }

        for (MapIO mapIO : mapList) {
            playerList.clear();
            for (int i = 0; i < numberOfPlayersPlaying; i++) {
                playerList.add(new Player("Player " + (i + 1), (String) playerDropDownList.get(i).getValue()));
            }
            result.put(mapIO.getFileName(), new ArrayList<>());
            for (int gameCount = 1; gameCount <= numberOfGamesToPlay; gameCount++) {
                // Adding result
                playerList.clear();
                for (int i = 0; i < numberOfPlayersPlaying; i++) {
                    playerList.add(new Player("Player " + (i + 1), (String) playerDropDownList.get(i).getValue()));
                }
                result.get(mapIO.getFileName()).add(tournamentModel.playGame(playerList, numberOfTurnsToPlay, gameCount, mapIO, textArea));
                System.out.println(gameCount);
            }
            System.out.println(result.toString());

            String text="";
            for (Map.Entry<String, ArrayList<HashMap<Player, Integer>>> mapEntry: result.entrySet() ) {
                String str = mapEntry.getKey();
                String separator = "\\";
                String[] mapSplit = str.replaceAll(Pattern.quote(separator), "\\\\").split("\\\\");

                text = text + "Map: " + mapSplit[mapSplit.length - 1]  + " -> \n";
                for(HashMap<Player, Integer> winnerHashMap: mapEntry.getValue()){
                    for(Map.Entry<Player, Integer> winnerHashMapMapntry: winnerHashMap.entrySet()){
                        if(winnerHashMapMapntry.getKey() != null){
                            text = text + " Winner: " + winnerHashMapMapntry.getKey().getName() + " | Game Number " + winnerHashMapMapntry.getValue();
                        }
                        else {
                            text = text + " Winner: Draw |" + " Game Number" + winnerHashMapMapntry.getValue();
                        }
                    }
                    text = text + "\n\n";
                }
            }
            textArea.setText(text);
        }
    }

}
