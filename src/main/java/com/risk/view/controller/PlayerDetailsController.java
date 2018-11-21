package com.risk.view.controller;

import com.risk.model.IPlayerType;
import com.risk.services.MapIO;
import com.risk.view.GamePlayView;
import com.risk.view.Util.WindowUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This class provides the Controller options to handle the initial player details.
 *
 * @author Palash Jain
 */
public class PlayerDetailsController implements Initializable {

    static int numberOfPlayers;

    /**
     * Map Object
     */
    private MapIO mapIO;

    @FXML
    private VBox playerDetails;

    @FXML
    private VBox playerTypes;

    /**
     * Button to start the Game
     */
    @FXML
    private Button startGame;

    /**
     * A JAVA FX feature which provides choice boxes to select the number of players.
     */
    @FXML
    private ChoiceBox<Integer> playerCount;

    /**
     * Initial Selected Players
     */
    private int numOfPlayersSelected;

    /**
     * Player Details Constructor
     *
     * @param mapIO Map Object
     */
    public PlayerDetailsController(MapIO mapIO) {
        this.mapIO = mapIO;
    }

    /**
     * This method is called to initialize the player detail controller.
     *
     * @param location  Location
     * @param resources resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PlayerDetailsController.initializePlayerCountValues(playerCount);
        startGame.setDisable(true);
        playerCountListener();
    }

    public void playerCountListener() {
        playerCount.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                PlayerDetailsController.numberOfPlayers=0;
                numOfPlayersSelected = playerCount.getSelectionModel().getSelectedItem();
                refreshPlayerList();
                refreshPlayerTypes();
                startGame.setDisable(false);
            }
        });
    }

    public void refreshPlayerList() {
        playerDetails.getChildren().clear();
        playerDetails.setSpacing(10);

        for (int i = 0; i < numOfPlayersSelected; i++) {
            HBox getBox = getPlayersDetailsBox();
            playerDetails.getChildren().addAll(getBox);
        }

    }

    public void refreshPlayerTypes(){
        playerTypes.getChildren().clear();
        playerTypes.setSpacing(10);
        for (int i = 0; i < numOfPlayersSelected; i++) {
            HBox getBox = getPlayerTypesBox();
            playerTypes.getChildren().addAll(getBox);
        }
    }


    public HBox getPlayersDetailsBox() {
        PlayerDetailsController.numberOfPlayers++;

        TextField textField = new TextField();

        HBox hBox = new HBox();
        hBox.setSpacing(10);

        Label playerIdShow = new Label();
        playerIdShow.setText(String.valueOf(PlayerDetailsController.numberOfPlayers));
        hBox.getChildren().addAll(playerIdShow, textField);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        return hBox;
    }

    public HBox getPlayerTypesBox(){
        String playerTypes[] = {IPlayerType.HUMAN, IPlayerType.AGGRESSIVE, IPlayerType.BENEVOLENT, IPlayerType.RANDOM,
                                IPlayerType.CHEATER};
        ChoiceBox<String> playerType = new ChoiceBox<>();
        playerType.getItems().addAll(playerTypes);
        playerType.getSelectionModel().selectFirst();

        HBox hBox = new HBox();
        hBox.getChildren().addAll(playerType);
        return hBox;
    }

    /**
     * Choice Box to select the number of players
     *
     * @param playerCount number of players
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
    private void startPlay(ActionEvent event) {
        ObservableList<Node> hBoxList = playerDetails.getChildren();
        ObservableList<Node> hBoxList1 = playerTypes.getChildren();
        List<String> playerNames = new LinkedList<>();
        List<String> playerTypes = new LinkedList<>();
        HashMap<String,String> playerDetailsAndType = new HashMap<>();
        if(validatePlayerDetails(hBoxList)){
            for(Node n : hBoxList){
                HBox hBox = (HBox) n;
                ObservableList<Node> node = hBox.getChildren();
                if(node.get(1) instanceof  TextField){
                    playerNames.add(((TextField) node.get(1)).getText());
                }
            }
            for(Node n : hBoxList1){
                HBox hBox = (HBox) n;
                ObservableList<Node> node = hBox.getChildren();
                if(node.get(0) instanceof  ChoiceBox<?>){
                    playerTypes.add((String)((ChoiceBox<?>) node.get(0)).getSelectionModel().getSelectedItem());
                }
            }

            for(int i=0;i<numOfPlayersSelected;i++){
                playerDetailsAndType.put(playerNames.get(i),playerTypes.get(i));
            }
        }
        else {
            WindowUtil.popUpWindow("Player names warning", "Alert", "Please enter appropriate player names.");
        }



        startGame.setOnAction(new GamePlayView(this.mapIO, playerDetailsAndType));
    }

    public boolean validatePlayerDetails(ObservableList<Node> hBoxList) {
        for (Node n : hBoxList) {
            HBox box = (HBox) n;
            for (Node node : box.getChildren()) {
                if (node instanceof TextField) {
                    if ((TextField) node == null || ((TextField) node).getText().trim().isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
