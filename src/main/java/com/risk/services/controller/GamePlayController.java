package com.risk.services.controller;

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

import com.risk.services.controller.Util.WindowUtil;
import com.risk.model.Card;
import com.risk.model.Continent;
import com.risk.services.MapIO;
import com.risk.model.Player;
import com.risk.model.Country;
import com.risk.services.gameplay.RoundRobin;
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

    @FXML
    private BarChart dominationBarChart;


    private Player player;

    private PlayerWorldDomination worldDomination;


    @FXML
    private Button attack;

    @FXML
    private Button fortify;


    @FXML
    private Button endTurn;


    @FXML
    private Button reinforcement;

    @FXML
    private Button cards;

    @FXML
    private VBox displayBox;


    @FXML
    private ListView<Country> selectedCountryList;


    @FXML
    private ListView<Country> adjacentCountryList;

    @FXML
    private Label playerChosen;


    @FXML
    private Label phaseView;


    @FXML
    private TextArea terminalWindow;


    @FXML
    private Button placeArmy;


    private int numberOfPlayersSelected;


    private ArrayList<Player> gamePlayerList;


    private Player playerPlaying;


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

    public void playerCreation() {

        setNumberOfPlayersSelected(this.playerNames.length);
        gamePlayerList.clear();
        WindowUtil.updateterminalWindow("Set up phase started\n", terminalWindow );
        gamePlayerList = player.generatePlayer(getNumberOfPlayersSelected(), this.playerNames, terminalWindow);
        WindowUtil.updateterminalWindow("All players generated\n", terminalWindow );

        roundRobin = new RoundRobin(this.gamePlayerList);

        player.assignArmiesToPlayers(gamePlayerList, terminalWindow);
        allocateCountryToPlayerInGamePlay();
        WindowUtil.updateterminalWindow("All countries assigned\n", terminalWindow );

        loadMapData();
        loadCurrentPlayer();
        generateBarGraph();
        WindowUtil.enableButtonControl(cards);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gamePlayerList = new ArrayList<>();
        playerCreation();
        loadGameCard();
        loadMapData();
        phaseView.setText("Phase: Start Up");
        WindowUtil.disableButtonControl(reinforcement, fortify, attack, cards);

        selectedCountryList.setCellFactory(param -> new ListCell<Country>() {
            @Override
            protected void updateItem(Country currentCountry, boolean empty) {
                super.updateItem(currentCountry, empty);

                if (empty || currentCountry == null || currentCountry.getName() == null) {
                    setText(null);
                } else {
                    setText(currentCountry.getName() + ":-" + currentCountry.getNoOfArmies() + "-" + currentCountry.getPlayer().getName());
                }
            }
        });
        selectedCountryList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Country country = selectedCountryList.getSelectionModel().getSelectedItem();
                moveToAdjacentCountry(country);
            }
        });

        adjacentCountryList.setCellFactory(param -> new ListCell<Country>() {
            @Override
            protected void updateItem(Country currentCountry, boolean empty) {
                super.updateItem(currentCountry, empty);

                if (empty || currentCountry == null || currentCountry.getName() == null) {
                    setText(null);
                } else {
                    setText(currentCountry.getName() + "-" + currentCountry.getNoOfArmies() + "-" + currentCountry.getPlayer().getName());
                }
            }
        });
    }

    private void moveToAdjacentCountry(Country country) {
        this.adjacentCountryList.getItems().clear();
        if (country != null) {
            for (Country adjTerr : country.getAdjacentCountries()) {
                this.adjacentCountryList.getItems().add(adjTerr);
            }
        }
    }

    private void loadGameCard() {
        cardStack = startUpPhase.assignCardToCountry(map, terminalWindow);
        WindowUtil.updateterminalWindow("Cards loaded\n", terminalWindow);
    }


    @FXML
    private void completeAttack(ActionEvent event) {
        adjacentCountryList.setOnMouseClicked(e -> System.out.print(""));
        if (player.getCountryWon() > 0) {
            allocateCardToPlayer();
        }
        WindowUtil.updateterminalWindow("Attack phase ended\n", terminalWindow);
        isValidFortificationPhase();
    }


    private void allocateCardToPlayer() {
        Card cardToBeAdded = cardStack.pop();
        playerPlaying.getCardList().add(cardToBeAdded);
        player.setCountryWon(0);
        WindowUtil.updateterminalWindow(cardToBeAdded.getCardType().toString() + "card is assigned to " + playerPlaying.getName() + " and won country " + cardToBeAdded.getCountry().getName() + "\n");
    }

    private void attack() {
        Country attackingTerritory = selectedCountryList.getSelectionModel().getSelectedItem();
        Country defendingTerritory = adjacentCountryList.getSelectionModel().getSelectedItem();

        player.attackPhase(attackingTerritory, defendingTerritory);

    }

    @FXML
    private void fortify(ActionEvent event) {
        Country selectedCountry = this.selectedCountryList.getSelectionModel().getSelectedItem();
        Country adjacentCountry = this.adjacentCountryList.getSelectionModel().getSelectedItem();

        player.fortificationPhase(selectedCountry, adjacentCountry, terminalWindow);
        selectedCountryList.refresh();
        adjacentCountryList.refresh();
        loadMapData();
    }

    @FXML
    private void endTurn(ActionEvent event) {
        adjacentCountryList.setOnMouseClicked(e -> System.out.print(""));
        WindowUtil.updateterminalWindow("Player " + playerPlaying.getName() + " ended his turn.\n", terminalWindow);
        if (player.getCountryWon() > 0) {
            allocateCardToPlayer();
        }
        initializeReinforcement();
        card.openCardWindow(playerPlaying, card);
    }


    @FXML
    private void placeArmy(ActionEvent event) {
        player.placeArmyOnCountry(playerPlaying, selectedCountryList, gamePlayerList, terminalWindow);
    }


    @FXML
    private void reinforcement(ActionEvent event) {
        Country country = selectedCountryList.getSelectionModel().getSelectedItem();
        if (playerPlaying.getCardList().size() >= 5) {
            WindowUtil.popUpWindow("Exchange Card", "Card exchange popup", "You have at least 5 cards please exchange for armies");
            return;
        }
        player.reinforcementPhase(country, terminalWindow);
        selectedCountryList.refresh();
        loadMapData();
        playerChosen.setText(playerPlaying.getName() + ":- " + playerPlaying.getArmyCount() + " armies left.");
    }


    private void loadMapData() {
        displayBox.getChildren().clear();
        for (Continent continent : map.getMapGraph().getContinents().values()) {
            displayBox.autosize();
            displayBox.getChildren().add(WindowUtil.createNewPane(continent));
        }
    }

    private void allocateCountryToPlayerInGamePlay() {
        WindowUtil.updateterminalWindow("Assigning countries to all players\n", terminalWindow);
        startUpPhase.assignCountryToPlayer(map, gamePlayerList, terminalWindow);
    }

    private void loadCurrentPlayer() {
        playerPlaying = roundRobin.next();
        player.setPlayerPlaying(playerPlaying);
        player.setCountryWon(0);
        WindowUtil.updateterminalWindow(playerPlaying.getName() + "'s turn started.\n", terminalWindow);

        selectedCountryList.getItems().clear();
        adjacentCountryList.getItems().clear();
        loadMapData();
        for (Country territory : playerPlaying.getPlayerCountries()) {
            selectedCountryList.getItems().add(territory);
        }
        playerChosen.setText(playerPlaying.getName() + ":- " + playerPlaying.getArmyCount() + " armies left.\n");
    }

    public void calculateReinforcementArmies() {
        if (this.playerPlaying != null) {
            playerPlaying = player.noOfReinsforcementArmies(playerPlaying);
            playerChosen.setText(playerPlaying.getName() + ":- " + playerPlaying.getArmyCount() + " armies left.");
        } else {
            WindowUtil.updateterminalWindow("Wait, no player is assigned the position of current player\n", terminalWindow);
        }
    }


    @FXML
    public void initCardWindow(ActionEvent event) {
        card.openCardWindow(playerPlaying, card);
    }


    private void initializeReinforcement() {
        loadCurrentPlayer();

        phaseView.setText("Phase: Reinforcement");
        WindowUtil.disableButtonControl(placeArmy, fortify, attack);
        WindowUtil.enableButtonControl(reinforcement);
        reinforcement.requestFocus();
        WindowUtil.updateterminalWindow("\nReinforcement phase started\n",terminalWindow);
        calculateReinforcementArmies();
    }

    private void performAttack() {
        adjacentCountryList.setOnMouseClicked(e -> attack());
    }


    private void initializeAttack() {
        WindowUtil.updateterminalWindow("\n Attack phase started.\n",terminalWindow);

        if (player.playerCanAttack(selectedCountryList, terminalWindow)) {
            phaseView.setText("Phase: Attack");

            WindowUtil.disableButtonControl(reinforcement, placeArmy);
            WindowUtil.enableButtonControl(attack);

            attack.requestFocus();
            performAttack();
        }
    }

    private void initializeFortification() {

        WindowUtil.disableButtonControl(reinforcement, attack, placeArmy);
        WindowUtil.enableButtonControl(fortify);
        phaseView.setText("Phase: Fortification");
        fortify.requestFocus();
        WindowUtil.updateterminalWindow("\nFortification phase started.\n",terminalWindow);

    }


    private void generateBarGraph() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Player");
        yAxis.setLabel("Percentage");
        //xAxis.setTickLabelRotation(90);
        HashMap<Player, Double> playerTerPercent = worldDomination.generateWorldDominationData(map);
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

    private void isAnyPlayerLost() {
        Player playerLost = player.checkPlayerLost(gamePlayerList);
        if (playerLost != null) {
            gamePlayerList.remove(playerLost);
            roundRobin.updateAfterPlayerLost(playerLost);
            WindowUtil.popUpWindow(playerLost.getName() + " Lost", "Player Lost popup", "Player: " + playerLost.getName() + " lost the game");
            WindowUtil.updateterminalWindow(playerLost.getName() +" loast the game and hence all the countries.\n\n", terminalWindow);
        }
    }


    private void endGame() {
        WindowUtil.disableButtonControl(selectedCountryList, adjacentCountryList, reinforcement, attack, fortify, cards, endTurn);
        phaseView.setText("GAME OVER");
        playerChosen.setText(playerPlaying.getName().toUpperCase() + " WON.");
        WindowUtil.updateterminalWindow("\n " + playerPlaying.getName().toUpperCase() + " WON.\n\n");

    }

    private boolean isAnyPlayerWon() {
        boolean playerWon = false;
        if (gamePlayerList.size() == 1) {
            WindowUtil.popUpWindow("Player: " + gamePlayerList.get(0).getName(), "Winning popup", "Game complete.");
            playerWon = true;
            endGame();
        }

        return playerWon;
    }

    private void resetWindow() {
        isAnyPlayerLost();
        selectedCountryList.getItems().clear();
        adjacentCountryList.getItems().clear();
        for (Country country : playerPlaying.getPlayerCountries()) {
            selectedCountryList.getItems().add(country);
        }
        loadMapData();
        generateBarGraph();
        playerChosen.setText(playerPlaying.getName() + ": " + playerPlaying.getArmyCount() + " armies left.\n");
        if (!isAnyPlayerWon()) {
            player.playerCanAttack(selectedCountryList, terminalWindow);
        }
    }

    private void initializePlaceArmy() {
        loadMapData();
        selectedCountryList.refresh();
        loadCurrentPlayer();
    }

    private void isValidFortificationPhase() {
        player.isFortificationPhaseValid(map, playerPlaying);
    }


    private void noFortificationPhase() {
        WindowUtil.updateterminalWindow("Fortification phase started\n",terminalWindow);
        WindowUtil.updateterminalWindow(playerPlaying.getName() + " does not have armies to fortify.\n",terminalWindow);
        WindowUtil.updateterminalWindow("Fortification phase ended\n",terminalWindow);
        initializeReinforcement();
    }

    public int getNumberOfPlayersSelected() {
        return numberOfPlayersSelected;
    }

    public void setNumberOfPlayersSelected(int numberOfPlayersSelected) {
        this.numberOfPlayersSelected = numberOfPlayersSelected;
    }

    public void exchangeCards(Card exch) {
        List<Card> tradedCards = exch.getCardsToExchange();
        setNumberOfCardSetExchanged(getNumberOfCardSetExchanged() + 1);
        player.exchangeCards(tradedCards, getNumberOfCardSetExchanged(), terminalWindow);
        playerPlaying.getCardList().removeAll(tradedCards);
        cardStack.addAll(tradedCards);
        Collections.shuffle(cardStack);
        selectedCountryList.refresh();
        adjacentCountryList.refresh();
        loadMapData();
        generateBarGraph();
        playerChosen.setText(playerPlaying.getName() + ":- " + playerPlaying.getArmyCount() + " armies left.\n");
    }

    public void update(Observable o, Object arg) {

        String view = (String) arg;

        if (view.equals("Attack")) {
            initializeAttack();
        }
        if (view.equals("FirstAttack")) {
            loadCurrentPlayer();
            initializeAttack();
        }
        if (view.equals("Reinforcement")) {
            initializeReinforcement();
            card.openCardWindow(playerPlaying, card);
        }
        if (view.equals("Fortification")) {
            initializeFortification();
        }
        if (view.equals("placeArmyOnCountry")) {
            initializePlaceArmy();
        }
        if (view.equals("WorldDomination")) {
            generateBarGraph();
        }
        if (view.equals("checkIfFortificationPhaseValid")) {
            isValidFortificationPhase();
        }
        if (view.equals("noFortificationMove")) {
            noFortificationPhase();
            card.openCardWindow(playerPlaying, card);
        }
        if (view.equals("rollDiceComplete")) {
            resetWindow();
        }
        if (view.equals("cardsTrade")) {
            Card cm = (Card) o;
            exchangeCards(cm);
        }
    }


    public int getNumberOfCardSetExchanged() {
        return numberOfCardSetExchanged;
    }


    public void setNumberOfCardSetExchanged(int numberOfCardSetExchanged) {
        this.numberOfCardSetExchanged = numberOfCardSetExchanged;
    }
}