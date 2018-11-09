package com.risk.model;

import com.risk.services.controller.Util.WindowUtil;
import com.risk.services.controller.DiceController;
import com.risk.services.MapIO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class Player extends Observable implements Observer {

    static Player currentPlayer;

    private String name;
    
    private int armyCount;
    
    private ArrayList<Country> playerCountries;
    
    private ArrayList<Card> cardList;

    public Player() {
        armyCount = 0;
    }

    public Player(String name) {
        armyCount = 0;
        this.name = name;
        this.playerCountries = new ArrayList<>();
        this.cardList = new ArrayList<>();
    }
    

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public int getArmyCount() {
        return armyCount;
    }

    public void setArmyCount(int armyCount) {
        this.armyCount = armyCount;
    }

    public ArrayList<Country> getPlayerCountries() {
        return playerCountries;
    }
    
    public void setMyCountries(ArrayList<Country> playerCountries) {
        this.playerCountries = playerCountries;
    }
    
    /**
     *  Method to add a single country to the player's country list
     *  
     * @param country
     * 				Object of the country
     */
    public void addCountry(Country country){
        this.playerCountries.add(country);
    }

    public ArrayList<Card> getCardList() {
        return cardList;
    }

    public void setCardList(ArrayList<Card> cardList) {
        this.cardList = cardList;
    }

    public void addArmiesToCountry(Country country, int numberOfArmies) {
        if(this.getArmyCount()>0 && this.getArmyCount()>=numberOfArmies) {
            if(!this.getPlayerCountries().contains(country)) {
                System.out.println("This country is not under your Ownership.");
            }
            else {
                country.setNoOfArmies(country.getNoOfArmies() + numberOfArmies);
                this.setArmyCount(this.getArmyCount() - numberOfArmies);
            }
        }
        else {
            System.out.println("Sufficient number of armies not available.");
        }
    }


    public Player getPlayerPlaying() {
        return Player.currentPlayer;
    }


    private int CountryWon;


    public static boolean assignArmiesToPlayers(List<Player> players, TextArea textArea) {

        boolean isSuccessfulAssignment = false;
        int armiesPerPlayer = 0;

        if (players.size() == 3){
            armiesPerPlayer = 5;
    }
        else if (players.size() == 4){
            armiesPerPlayer = 30;
        }
        else if (players.size() == 5){
            armiesPerPlayer = 25;
        }
        else if (players.size() == 6){
            armiesPerPlayer = 20;
        }

        for(int playerNumber = 0; playerNumber < players.size(); playerNumber++){
            players.get(playerNumber).setArmyCount(armiesPerPlayer);
            WindowUtil.updateterminalWindow(armiesPerPlayer + " armies assigned to " +players.get(playerNumber).getName()+ ".\n", textArea);
            isSuccessfulAssignment = true;
        }

        return isSuccessfulAssignment;
    }


    public static ArrayList<Player> generatePlayer(int noOfPlayer, String [] playersList, TextArea textArea) {
        ArrayList<Player> listPlayer = new ArrayList<>();
        for (int i = 0; i < noOfPlayer; i++) {
            listPlayer.add(new Player(playersList[i].trim()));
            WindowUtil.updateterminalWindow("Created player " + playersList[i].trim() + ".\n", textArea);
        }
        return listPlayer;
    }

    public Player noOfReinsforcementArmies(Player currentPlayer) {

        int noOfCountrie = currentPlayer.getPlayerCountries().size();
        int numberOfArmies = (int) Math.floor(noOfCountrie / 3);
        HashSet<Continent> countryInContinent = new HashSet<>();
        ArrayList<Country> playerOwnedCountries = currentPlayer.getPlayerCountries();

        boolean isPlayerOwnedContinent;

        for(Country country : playerOwnedCountries){
            countryInContinent.add(country.getPartOfContinent());
        }

        for(Continent continent : countryInContinent){
            isPlayerOwnedContinent = true;
            for(Country country: continent.getListOfCountries()){
                if(!playerOwnedCountries.contains(country)){
                    isPlayerOwnedContinent = false;
                    break;
                }
            }
            if (isPlayerOwnedContinent) {
                numberOfArmies += continent.getControlValue();
            }
        }

        if (numberOfArmies < 3) {
            numberOfArmies = 3;
        }

        currentPlayer.setArmyCount(currentPlayer.getArmyCount() + numberOfArmies);

        return currentPlayer;
    }


    public List<Continent> getContinentsOwnedByPlayer(Player currentPlayer) {
        List<Continent> continents = new ArrayList<>();
        HashSet<Continent> countryInContinent = new HashSet<>();
        ArrayList<Country> playerOwnedCountries = currentPlayer.getPlayerCountries();


        boolean isPlayerOwnedContinent = true;

        for(Country country : playerOwnedCountries){
            countryInContinent.add(country.getPartOfContinent());
        }


        for(Continent continent : countryInContinent) {
            isPlayerOwnedContinent = true;
            for (Country country : continent.getListOfCountries()) {
                if (!playerOwnedCountries.contains(country)) {
                    isPlayerOwnedContinent = false;
                    break;
                }
            }
            if (isPlayerOwnedContinent) {
                continents.add(continent);
            }
        }
        return continents;
    }

    public void reinforcementPhase(Country country, TextArea textArea) {

        if (currentPlayer.getArmyCount() > 0) {
            if (country == null) {
                WindowUtil.popUpWindow("No Country Selected", "popUp", "Please select a country");
                return;
            }

            int reinsforcementArmies = Integer.valueOf(WindowUtil.userInput());
            if (currentPlayer.getArmyCount() < reinsforcementArmies) {
                WindowUtil.popUpWindow("Insufficient Armies", "popUp", currentPlayer.getName() + " don't have enough armies");
                return;
            }
            country.setNoOfArmies(country.getNoOfArmies() + reinsforcementArmies);
            currentPlayer.setArmyCount(currentPlayer.getArmyCount() - reinsforcementArmies);
            WindowUtil.updateterminalWindow(country.getName() + " was assigned " + reinsforcementArmies + " armies \n",textArea);

        }
        if (currentPlayer.getArmyCount() <= 0) {
            WindowUtil.updateterminalWindow("Reinsforcement Phase Ended, Fortification started\n",textArea);
            setChanged();
            notifyObservers("Attack");
        }
    }


    public void attackPhase(Country attackingCountry, Country defendingCountry) {
        if (attackingCountry != null && defendingCountry != null) {

            boolean playerCanAttack = isAttackMoveValid(attackingCountry, defendingCountry);

            if(playerCanAttack){
                Dice dice = new Dice(attackingCountry, defendingCountry);
                dice.addObserver(this);
                final Stage newMapStage = new Stage();
                newMapStage.setTitle("Attack Window");

                DiceController diceController = new DiceController(dice);

                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("DiceView.fxml"));
                loader.setController(diceController);

                Parent root = null;
                try {
                    root = (Parent) loader.load();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                Scene scene = new Scene(root);
                newMapStage.setScene(scene);
                newMapStage.show();
            }

        } else {
            WindowUtil.popUpWindow("Country selection problem", "Country selection pop", currentPlayer.getName() + " please select attacking and defending countries.");

        }
    }


    public void fortificationPhase(Country selectedCountry, Country adjCountry, TextArea terminalWindow) {
        if (selectedCountry == null) {
            WindowUtil.popUpWindow("No country selected", "Fortification Phase popup", "Please select a source country");
            return;
        } else if (adjCountry == null) {
            WindowUtil.popUpWindow("No adjacent country selected", "Fortification Phase popup", "Please select a country that is adjacent.");
            return;
        } else if (!(adjCountry.getPlayer().equals(currentPlayer))) {
            WindowUtil.popUpWindow("No adjacent country selected", "Fortification Phase popup", currentPlayer.getName() + " does not own the adjacent country");
            return;
        }

        Integer armies = Integer.valueOf(WindowUtil.userInput());
        if (armies > 0) {
            if (selectedCountry.getNoOfArmies() == armies) {
                WindowUtil.popUpWindow("All armies selected to move", "Fortification Phase popup", "You have to leave at least one army behind.");
                return;
            } else if (selectedCountry.getNoOfArmies() < armies) {
                WindowUtil.popUpWindow("You do not have sufficient armies", "Fortification Phase popup", "Please select less number of armies");
                return;
            } else {
                selectedCountry.setNoOfArmies(selectedCountry.getNoOfArmies() - armies);
                adjCountry.setNoOfArmies(adjCountry.getNoOfArmies() + armies);
                WindowUtil.updateterminalWindow(armies + " armies placed on " + adjCountry.getName() + " country.\n", terminalWindow);
                WindowUtil.updateterminalWindow("Fortification phase ended. \n", terminalWindow);
                setChanged();
                notifyObservers("Reinforcement");
            }
        } else {
            WindowUtil.popUpWindow("Invalid number", "Fortification Phase popup", "Please enter a valid number");
            return;
        }
    }

    public boolean isFortificationPhaseValid(MapIO map, Player playerPlaying) {
        boolean isFortificationAvaialble = false;
        outer: for (Continent continent : map.getMapGraph().getContinents().values()) {
            for (Country Country : continent.getListOfCountries()) {
                if (Country.getPlayer().equals(playerPlaying)) {
                    if (Country.getNoOfArmies() > 1) {
                        for (Country adjCountry : Country.getAdjacentCountries()) {
                            if (adjCountry.getPlayer().equals(playerPlaying)) {
                                isFortificationAvaialble = true;
                                break outer;
                            }
                        }
                    }
                }
            }
        }
        if (isFortificationAvaialble) {
            setChanged();
            notifyObservers("Fortification");
        } else {
            setChanged();
            notifyObservers("noFortificationMove");
        }
        return isFortificationAvaialble;
    }


    public void placeArmyOnCountry(Player playerPlaying, ListView<Country> selectedCountryList, List<Player> gamePlayerList, TextArea terminalWindow) {
        int playerArmies = playerPlaying.getArmyCount();
        if (playerArmies > 0) {
            Country Country = selectedCountryList.getSelectionModel().getSelectedItem();
            if (Country == null) {
                Country = selectedCountryList.getItems().get(0);
            }
            Country.setNoOfArmies(Country.getNoOfArmies() + 1);
            playerPlaying.setArmyCount(playerArmies - 1);
        }

        boolean armiesExhausted = isPlayerArmyLeft(gamePlayerList);
        if (armiesExhausted) {
            WindowUtil.updateterminalWindow("StartUp Phase Completed.\n", terminalWindow);
            setChanged();
            notifyObservers("FirstAttack");
        } else {
            setChanged();
            notifyObservers("placeArmyOnCountry");
        }
    }


    public boolean isPlayerArmyLeft(List<Player> allPlayers) {
        int count = 0;

        for (Player player : allPlayers) {
            if (player.getArmyCount() == 0) {
                count++;
            }
        }
        if (count == allPlayers.size()) {
            return true;
        } else {
            return false;
        }
    }


    public boolean isAttackMoveValid(Country attacking, Country defending)  {
        boolean isValidAttackMove = false;
        if (defending.getPlayer() != attacking.getPlayer()) {
            if (attacking.getNoOfArmies() > 1) {
                isValidAttackMove = true;
            } else {
                WindowUtil.popUpWindow("Select a country with more armies.", "Invalid game move", "There should be more than one army on the country which is attacking." );
            }
        } else {
            WindowUtil.popUpWindow("You have selected your own country", "Invalid game move", "Select another player's country to attack" );
        }
        return isValidAttackMove;
    }


    public boolean playerCanAttack(ListView<Country> countries, TextArea terminalWindow) {

        boolean canAttack = false;
        for (Country Country : countries.getItems()) {
            if (Country.getNoOfArmies() > 1) {
                canAttack = true;
            }
        }
        if (!canAttack) {
            WindowUtil.updateterminalWindow("Player cannot continue with attck phase, move to fortification phase.\n", terminalWindow);
            WindowUtil.updateterminalWindow("Attack phase ended\n", terminalWindow);
            setChanged();
            notifyObservers("checkIfFortificationPhaseValid");
            return canAttack;
        }
        return canAttack;
    }


    public Player checkPlayerLost(List<Player> playersPlaying) {
        Player playerLost = null;
        for (Player player : playersPlaying) {
            if (player.getPlayerCountries().isEmpty()) {
                playerLost = player;
                currentPlayer.getCardList().addAll(playerLost.getCardList());
            }
        }

        return playerLost;
    }


    public Player exchangeCards(List<Card> selectedCards, int numberOfCardSetExchanged, TextArea terminalWindow) {
        currentPlayer.setArmyCount(currentPlayer.getArmyCount() + (5 * numberOfCardSetExchanged));
        WindowUtil.updateterminalWindow(currentPlayer.getName() + " successfully exchanged 3 cards for 1 army! \n", terminalWindow);

            for(Card card : selectedCards){
            if (currentPlayer.getPlayerCountries().contains(card.getCountry())) {
                card.getCountry().setNoOfArmies(card.getCountry().getNoOfArmies() + 2);
                WindowUtil.updateterminalWindow(currentPlayer.getName()+ "\" got extra 2 armies on \"" + card.getCountry().getName() + "\n", terminalWindow);

                break;
            }
        }
        return currentPlayer;
    }


    public void setPlayerPlaying(Player playerPlaying) {
        Player.currentPlayer = playerPlaying;
    }


    public int getCountryWon() {
        return CountryWon;
    }

    public void setCountryWon(int CountryWon) {
        this.CountryWon = CountryWon;
    }


    public void update(Observable o, Object arg) {
        String view = (String) arg;

        if (view.equals("rollDiceComplete")) {
            Dice dice = (Dice) o;
            setCountryWon(dice.getCountriesWonCount());
            setChanged();
            notifyObservers("rollDiceComplete");
        }
    }
}
