package com.risk.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.Stack;

import com.risk.exception.InvalidGameMoveException;
import com.risk.model.Card;
import com.risk.model.Continent;
import com.risk.services.MapIO;
import com.risk.model.Player;
import com.risk.model.Country;
import com.risk.map.util.WindowUtil;
import com.risk.services.RoundRobin;
import com.risk.services.StartUpPhase;
import com.risk.model.PlayerWorldDomination;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class GamePlayController implements Initializable, Observer {


    private String[] playerNames;

    private RoundRobin roundRobin;

    private MapIO map;

    private StartUpPhase startUpPhase;

    private Card card;

    private Player player;

    private PlayerWorldDomination worldDomination;

    @FXML
    private Button attack;

    @FXML
    private Button cards;

    @FXML
    private Button fortify;

    @FXML
    private Button endTurn;

    @FXML
    private Button reinforcement;

    @FXML
    private Label playerSelected;

    @FXML
    private Label phaseView;

    @FXML
    private BarChart dominationBarChart;

    @FXML
    private VBox displayBox;

    @FXML
    private ListView<Country> selectedCountryList;

    @FXML
    private ListView<Country> adjCountryList;

    @FXML
    private TextArea terminalWindow;

    @FXML
    private Button placeArmy;

    private int selectedPlayers;

    private ArrayList<Player> gamePlayerList;

    private Player CurrentPlayer;

    private Stack<Card> cardStack;

    private int numberOfCardSetExchanged;

    public GamePlayController(MapIO map, String[] names) {
        this.map = map;
        this.startUpPhase = new StartUpPhase();
        this.player = new Player();
        this.card = new Card();
        this.playerNames = names;
        player.addObserver(this);
        card.addObserver(this);
        worldDomination = new PlayerWorldDomination();
        worldDomination.addObserver(this);
        this.setNumberOfCardSetExchanged(0);
    }

    public void createPlayer() {

        setSelectedPlayers(this.playerNames.length);
        gamePlayerList.clear();
        gamePlayerList = player.createPlayer(getSelectedPlayers(), this.playerNames, terminalWindow);

        roundRobin = new RoundRobin(this.gamePlayerList);
        //playerIterator = gamePlayerList.iterator();

        player.assignArmiesToPlayers(gamePlayerList, terminalWindow);
        assignCountryToPlayer();
        loadMapContent();
        loadCurrentPlayer();
        loadWorldDominationData();
        WindowUtil.enableButtonControl(cards);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gamePlayerList = new ArrayList<>();
        createPlayer();
        loadCards();
        loadMapContent();
        phaseView.setText("Phase: Start Up");
        WindowUtil.disableButtonControl(reinforcement, fortify, attack, cards);

        selectedCountryList.setCellFactory(param -> new ListCell<Country>() {
            @Override
            protected void updateItem(Country item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(item.getName() + ":" + item.getNoOfArmies() + "-" + item.getPlayer().getName());
                }
            }
        });
        selectedCountryList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Country territory = selectedCountryList.getSelectionModel().getSelectedItem();
                occupyAdjCountry(territory);
            }
        });

        adjCountryList.setCellFactory(param -> new ListCell<Country>() {
            @Override
            protected void updateItem(Country item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(item.getName() + "-" + item.getNoOfArmies() + "-" + item.getPlayer().getName());
                }
            }
        });
    }

    private void occupyAdjCountry(Country country) {
        this.adjCountryList.getItems().clear();
        if (country != null) {
            for (Country adjTerr : country.getAdjacentCountries()) {
                this.adjCountryList.getItems().add(adjTerr);
            }
        }
    }

    private void loadCards() {
        cardStack = startUpPhase.assignCardToCountry(map, terminalWindow);
    }

    private void loadMapContent() {
        displayBox.getChildren().clear();
        for (Continent continent : map.getMapGraph().getContinents().values()) {
            displayBox.autosize();
            displayBox.getChildren().add(WindowUtil.createNewPane(continent));
        }
    }

    private void allocateCardToPlayer() {
        Card cardToBeAdded = cardStack.pop();
        CurrentPlayer.getListOfCards().add(cardToBeAdded);
        player.setCountryWon(0);
        WindowUtil.textToTerminalWindow(CurrentPlayer.getName() + " has been assigned a card with type " + cardToBeAdded.getCardType().toString() + " and territory " + cardToBeAdded.getCountry().getName() + "\n", terminalWindow);
    }

    private void attack() {
        Country attackingTerritory = selectedCountryList.getSelectionModel().getSelectedItem();
        Country defendingTerritory = adjCountryList.getSelectionModel().getSelectedItem();
    	try {
        player.attackPhase(attackingTerritory, defendingTerritory);
		} catch (InvalidGameMoveException ex) {
			WindowUtil.userInfo(ex.getMessage(), "Message", "");
			return;
		}
    }

    @FXML
    private void reinforcement(ActionEvent event) {
        Country territory = selectedCountryList.getSelectionModel().getSelectedItem();
        if (CurrentPlayer.getListOfCards().size() >= 5) {
            WindowUtil.userInfo("You have five or more Risk Card, please exchange these cards for army.", "Info", "");
            return;
        }
        player.reinforcementPhase(territory, terminalWindow);
        selectedCountryList.refresh();
        loadMapContent();
        playerSelected.setText(CurrentPlayer.getName() + ": " + CurrentPlayer.getArmyCount() + " armies left");
    }

    private void prePlaceArmy() {
        loadMapContent();
        selectedCountryList.refresh();
        loadCurrentPlayer();
    }

    @FXML
    private void placeArmy(ActionEvent event) {
        player.placeArmy(CurrentPlayer, selectedCountryList, gamePlayerList, terminalWindow);
    }

    @FXML
    private void fortify(ActionEvent event) {
        Country selectedTerritory = this.selectedCountryList.getSelectionModel().getSelectedItem();
        Country adjTerritory = this.adjCountryList.getSelectionModel().getSelectedItem();

        player.fortificationPhase(selectedTerritory, adjTerritory, terminalWindow);
        selectedCountryList.refresh();
        adjCountryList.refresh();
        loadMapContent();
    }

    @FXML
    private void endTurn(ActionEvent event) {
        adjCountryList.setOnMouseClicked(e -> System.out.print(""));
        WindowUtil.textToTerminalWindow(CurrentPlayer.getName() + " ended his turn.\n", terminalWindow);
        if (player.getCountryWon() > 0) {
            allocateCardToPlayer();
        }
        preReinforcement();
        card.openCardWindow(CurrentPlayer, card);
    }

    private void assignCountryToPlayer() {
        startUpPhase.assignCountryToPlayer(map, gamePlayerList, terminalWindow);
    }

    private void loadCurrentPlayer() {
        CurrentPlayer = roundRobin.next();
        player.setPlayerPlaying(CurrentPlayer);
        player.setCountryWon(0);
        WindowUtil.textToTerminalWindow(CurrentPlayer.getName() + "....started playing.\n", terminalWindow);
        selectedCountryList.getItems().clear();
        adjCountryList.getItems().clear();
        loadMapContent();
        for (Country country : CurrentPlayer.getMyCountries()) {
            selectedCountryList.getItems().add(country);
        }
        playerSelected.setText(CurrentPlayer.getName() + ": " + CurrentPlayer.getArmyCount() + " armies left\n");
    }

    public void calculateReinforcementArmies() {
        if (this.CurrentPlayer != null) {
            CurrentPlayer = player.calculateReinforcementArmies(CurrentPlayer);
            playerSelected.setText(CurrentPlayer.getName() + ": " + CurrentPlayer.getArmyCount() + " armies left");
        } else {
            WindowUtil.textToTerminalWindow("Error!. No player playing.", terminalWindow);
        }
    }

    @FXML
    public void initCardWindow(ActionEvent event) {
        card.openCardWindow(CurrentPlayer, card);
    }

    private void preReinforcement() {
        loadCurrentPlayer();

        phaseView.setText("Phase: Reinforcement");
        WindowUtil.disableButtonControl(placeArmy, fortify, attack);
        WindowUtil.enableButtonControl(reinforcement);
        reinforcement.requestFocus();
        calculateReinforcementArmies();
    }

    private void launchAttack() {
        adjCountryList.setOnMouseClicked(e -> attack());
    }

    private void preAttack() {
        if (player.playerHasAValidAttackMove(selectedCountryList, terminalWindow)) {
            phaseView.setText("Phase: Attack");
            WindowUtil.disableButtonControl(reinforcement, placeArmy);
            WindowUtil.enableButtonControl(attack);
            attack.requestFocus();
            launchAttack();
        }
    }

    private void preFortification() {
        WindowUtil.disableButtonControl(reinforcement, attack, placeArmy);
        WindowUtil.enableButtonControl(fortify);
        phaseView.setText("Phase: Fortification");
        fortify.requestFocus();
    }

    private void checkIfFortificationPhaseIsValid() {
        player.isFortificationPhaseValid(map, CurrentPlayer);
    }

    private void noFortificationPhase() {
        WindowUtil.textToTerminalWindow(CurrentPlayer.getName() + " has no armies to be fortified.\n", terminalWindow);
        preReinforcement();
    }

    private void loadWorldDominationData() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Player");
        yAxis.setLabel("Percentage");
        HashMap<Player, Double> playerTerPercent = worldDomination.worldDominationData(map);
        System.out.println(playerTerPercent.toString());
        ObservableList<XYChart.Series<String, Double>> answer = FXCollections.observableArrayList();
        ArrayList<XYChart.Series<String, Double>> chartData = new ArrayList<>();
        for (Entry<Player, Double> entry : playerTerPercent.entrySet()) {
            XYChart.Series<String, Double> aSeries = new XYChart.Series<>();
            aSeries.setName(entry.getKey().getName());
            aSeries.getData().add(new XYChart.Data(entry.getKey().getName(), entry.getValue()));
            System.out.println("aSeries=" + aSeries.getData().toString());
            chartData.add(aSeries);
        }
        System.out.println("charData=" + chartData.toString());
        answer.addAll(chartData);
        dominationBarChart.setData(answer);
    }

    @FXML
    private void completeAttack(ActionEvent event) {
        adjCountryList.setOnMouseClicked(e -> System.out.print(""));
        if (player.getCountryWon() > 0) {
            allocateCardToPlayer();
        }
        checkIfFortificationPhaseIsValid();
    }

    private void checkPlayerLost() {
        Player playerLost = player.checkIfAnyPlayerLostTheGame(gamePlayerList);
        if (playerLost != null) {
            gamePlayerList.remove(playerLost);
            roundRobin.updateAfterPlayerLost(playerLost);
            WindowUtil.userInfo("Player: " + playerLost.getName() + " lost all his country and is out of the game",
                    "Info", "");
            WindowUtil.textToTerminalWindow(playerLost.getName() + " lost all countries and lost the game.\n",
                    terminalWindow);
        }
    }

    private boolean checkPlayerWon() {
        boolean playerWon = false;
        if (gamePlayerList.size() == 1) {
            WindowUtil.userInfo("Player: " + gamePlayerList.get(0).getName() + " won the game!", "Info", "");
            playerWon = true;
            gameOver();
        }

        return playerWon;
    }
    
    private void viewChange() {
        checkPlayerLost();
        selectedCountryList.getItems().clear();
        adjCountryList.getItems().clear();
        for (Country country : CurrentPlayer.getMyCountries()) {
            selectedCountryList.getItems().add(country);
        }
        loadMapContent();
        loadWorldDominationData();
        playerSelected.setText(CurrentPlayer.getName() + ": " + CurrentPlayer.getArmyCount() + " armies left\n");
        if (!checkPlayerWon()) {
            player.playerHasAValidAttackMove(selectedCountryList, terminalWindow);
        }
    }

    private void gameOver() {
        WindowUtil.disableButtonControl(selectedCountryList, adjCountryList, reinforcement, attack, fortify, cards,
                endTurn);
        phaseView.setText("GAME OVER");
        playerSelected.setText(CurrentPlayer.getName().toUpperCase() + " WON THE GAME");
        WindowUtil.textToTerminalWindow(CurrentPlayer.getName().toUpperCase() + " WON THE GAME\n", terminalWindow);

    }

   
    public void exchangeCardsForArmy(Card change) {
        List<Card> tradedCards = change.getCardsToExchange();
        setNumberOfCardSetExchanged(getNumberOfCardSetExchanged() + 1);
        player.tradeCardsForArmy(tradedCards, getNumberOfCardSetExchanged(), terminalWindow);
        CurrentPlayer.getListOfCards().removeAll(tradedCards);
        cardStack.addAll(tradedCards);
        Collections.shuffle(cardStack);
        selectedCountryList.refresh();
        adjCountryList.refresh();
        loadMapContent();
        loadWorldDominationData();
        playerSelected.setText(CurrentPlayer.getName() + ": " + CurrentPlayer.getArmyCount() + " armies left\n");
    }

    public void update(Observable o, Object arg) {

        String view = (String) arg;

        if (view.equals("Attack")) {
            preAttack();
        }
        if (view.equals("FirstAttack")) {
            loadCurrentPlayer();
            preAttack();
        }
        if (view.equals("Reinforcement")) {
            preReinforcement();
            card.openCardWindow(CurrentPlayer, card);
        }
        if (view.equals("Fortification")) {
            preFortification();
        }
        if (view.equals("placeArmy")) {
            prePlaceArmy();
        }
        if (view.equals("WorldDomination")) {
            loadWorldDominationData();
        }
        if (view.equals("checkIfFortificationPhaseValid")) {
            checkIfFortificationPhaseIsValid();
        }
        if (view.equals("noFortificationMove")) {
            noFortificationPhase();
            card.openCardWindow(CurrentPlayer, card);
        }
        if (view.equals("rollDiceComplete")) {
            viewChange();
        }
        if (view.equals("cardsTrade")) {
            Card exchange = (Card) o;
            exchangeCardsForArmy(exchange);
        }
    }

    public int getSelectedPlayers() {
        return selectedPlayers;
    }


    public void setSelectedPlayers(int selectedPlayers) {
        this.selectedPlayers = selectedPlayers;
    }


    public int getNumberOfCardSetExchanged() {
        return numberOfCardSetExchanged;
    }


    public void setNumberOfCardSetExchanged(int numberOfCardSetExchanged) {
        this.numberOfCardSetExchanged = numberOfCardSetExchanged;
    }
}