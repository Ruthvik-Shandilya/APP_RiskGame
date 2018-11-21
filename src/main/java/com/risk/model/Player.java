package com.risk.model;

import com.risk.services.MapIO;
import com.risk.view.Util.WindowUtil;
import com.risk.view.controller.DiceController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

/**
 * Class for the player object for the class
 *
 * @author KaranDeep Singh
 * @author Palash Jain
 */
public class Player extends Observable implements Observer {

    /**
     * Player currently playing.
     */

    public static Player currentPlayer;

    /**
     * Name of the player
     */
    private String name;

    /**
     * Number of armies
     */
    private int armyCount;

    /**
     * Player countries
     */
    private ArrayList<Country> playerCountries;

    /**
     * Player's cards
     */
    private ArrayList<Card> cardList;

    private TextArea terminalWindow;

    private String playerType;

    public TextArea getTerminalWindow() {
        return terminalWindow;
    }

    /**
     * Player constructor, initializes initial army count
     */
    public Player() {
        armyCount = 0;
        new WindowUtil(this);
    }

    /**
     * Player constructor
     *
     * @param name name
     */
    public Player(String name, String playerType) {
        armyCount = 0;
        this.name = name;
        this.playerType = playerType;
        this.playerCountries = new ArrayList<>();
        this.cardList = new ArrayList<>();
        new WindowUtil(this);
    }

    /**
     * Method to get name of the player
     *
     * @return player's name
     */

    public String getName() {
        return name;
    }

    /**
     * Setter got player's name
     *
     * @param name of the player
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for player armies
     *
     * @return armyCount of the player
     */
    public int getArmyCount() {
        return armyCount;
    }

    /**
     * Setter for players army count
     *
     * @param armyCount armyCount of the player
     */
    public void setArmyCount(int armyCount) {
        this.armyCount = armyCount;
    }

    /**
     * Getter for list of player's armies.
     *
     * @return List of playerCountries
     */
    public ArrayList<Country> getPlayerCountries() {
        return playerCountries;
    }

    /**
     * Setter for list of player's armies.
     *
     * @param playerCountries set player armies
     */
    public void setMyCountries(ArrayList<Country> playerCountries) {
        this.playerCountries = playerCountries;
    }

    /**
     * Method to add a country to the player's country list
     *
     * @param country country object
     */
    public void addCountry(Country country) {
        this.playerCountries.add(country);
    }

    /**
     * Method for getting the player's list of card
     *
     * @return cardList of player
     */
    public ArrayList<Card> getCardList() {
        return cardList;
    }

    /**
     * Method for returning the player's list of card
     *
     * @param cardList of player
     */
    public void setCardList(ArrayList<Card> cardList) {
        this.cardList = cardList;
    }

    /**
     * Method for adding armies to a country
     *
     * @param country        Country to which armies are to be assigned
     * @param numberOfArmies number for armies to be assigned
     */
    public void addArmiesToCountry(Country country, int numberOfArmies) {
        if (this.getArmyCount() > 0 && this.getArmyCount() >= numberOfArmies) {
            if (!this.getPlayerCountries().contains(country)) {
                System.out.println("This country is not under your Ownership.");
            } else {
                country.setNoOfArmies(country.getNoOfArmies() + numberOfArmies);
                this.setArmyCount(this.getArmyCount() - numberOfArmies);
            }
        } else {
            System.out.println("Sufficient number of armies not available.");
        }
    }

    /**
     * Getter for current PLayer
     *
     * @return current Player
     */

    public static Player getPlayerPlaying() {
        return Player.currentPlayer;
    }

    /**
     * Number of countries won by the player
     */
    private int CountryWon;

    /**
     * Method for allocating initial armies to the player,
     * depending upon the total number of players
     *
     * @param players  List of all the players
     * @param textArea TeaxtArea for displaying game details
     * @return true, is armies are successfully assigned,; otherwise false
     */

    public boolean assignArmiesToPlayers(List<Player> players, TextArea textArea) {

        this.terminalWindow = textArea;
        boolean isSuccessfulAssignment = false;
        int armiesPerPlayer = 0;

        if (players.size() == 3) {
            armiesPerPlayer = 7;
        } else if (players.size() == 4) {
            armiesPerPlayer = 30;
        } else if (players.size() == 5) {
            armiesPerPlayer = 25;
        } else if (players.size() == 6) {
            armiesPerPlayer = 20;
        }

        for (int playerNumber = 0; playerNumber < players.size(); playerNumber++) {
            players.get(playerNumber).setArmyCount(armiesPerPlayer);
            setChanged();
            notifyObservers(armiesPerPlayer + " armies assigned to " + players.get(playerNumber).getName() + ".\n");
            //WindowUtil.updateTerminalWindow(armiesPerPlayer + " armies assigned to " + players.get(playerNumber).getName() + ".\n", textArea);
            isSuccessfulAssignment = true;
        }

        return isSuccessfulAssignment;
    }


    /**
     * Method for generating players according to the data entered by the user
     *
     * @param hm Map of all the player details
     * @param textArea    TextArea for displaying gamed details
     * @return List of player objects
     */
    public ArrayList<Player> generatePlayer(HashMap<String,String> hm, TextArea textArea) {
        this.terminalWindow = textArea;
        ArrayList<Player> listPlayer = new ArrayList<>();
        for(Map.Entry<String,String> playerEntry : hm.entrySet()){
            listPlayer.add(new Player(playerEntry.getKey().trim(), playerEntry.getValue()));
            setChanged();
            notifyObservers("Created player " + playerEntry.getKey().trim() + ".\n");
        }
        return listPlayer;
    }

    /**
     * Method for calculating number of reinforcement armies to be allocated to the player
     *
     * @param currentPlayer Player to which armies are to be allocated
     * @return Player, object of the current player
     */

    public Player noOfReinforcementArmies(Player currentPlayer) {

        int noOfCountrie = currentPlayer.getPlayerCountries().size();
        int numberOfArmies = (int) Math.floor(noOfCountrie / 3);
        HashSet<Continent> countryInContinent = new HashSet<>();
        ArrayList<Country> playerOwnedCountries = currentPlayer.getPlayerCountries();

        boolean isPlayerOwnedContinent;

        for (Country country : playerOwnedCountries) {
            countryInContinent.add(country.getPartOfContinent());
        }

        for (Continent continent : countryInContinent) {
            isPlayerOwnedContinent = true;
            for (Country country : continent.getListOfCountries()) {
                if (!playerOwnedCountries.contains(country)) {
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


    /**
     * Method for getting a list of all the continents of the player
     *
     * @param currentPlayer currentPlayer
     * @return List of continents owned by the player
     */
    public List<Continent> getContinentsOwnedByPlayer(Player currentPlayer) {
        List<Continent> continents = new ArrayList<>();
        HashSet<Continent> countryInContinent = new HashSet<>();
        ArrayList<Country> playerOwnedCountries = currentPlayer.getPlayerCountries();


        boolean isPlayerOwnedContinent = true;

        for (Country country : playerOwnedCountries) {
            countryInContinent.add(country.getPartOfContinent());
        }


        for (Continent continent : countryInContinent) {
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

    /**
     * Method governing the reinforcement phase.
     *
     * @param country  country to which reinforcement armies are to be assigned
     * @param textArea TextArea to which current game information will be displayed
     */
    public void reinforcementPhase(Country country, TextArea textArea) {
        this.terminalWindow = textArea;
        if (currentPlayer.getArmyCount() > 0) {
            if (country == null) {
                WindowUtil.popUpWindow("No Country Selected", "popUp", "Please select a country");
                return;
            }

            int reinforcementArmies = Integer.valueOf(WindowUtil.userInput());
            if (currentPlayer.getArmyCount() < reinforcementArmies) {
                WindowUtil.popUpWindow("Insufficient Armies", "popUp", currentPlayer.getName() + " don't have enough armies");
                return;
            }
            country.setNoOfArmies(country.getNoOfArmies() + reinforcementArmies);
            currentPlayer.setArmyCount(currentPlayer.getArmyCount() - reinforcementArmies);
            setChanged();
            notifyObservers(country.getName() + " was assigned " + reinforcementArmies + " armies \n");
            //WindowUtil.updateTerminalWindow(country.getName() + " was assigned " + reinforcementArmies + " armies \n", textArea);

        }
        if (currentPlayer.getArmyCount() <= 0) {
            setChanged();
            notifyObservers("Reinforcement Phase Ended, Fortification started\n");
            //WindowUtil.updateTerminalWindow("Reinforcement Phase Ended, Fortification started\n", textArea);
            setChanged();
            notifyObservers("Attack");
        }
    }

    /**
     * Method governing the attack phase
     *
     * @param attackingCountry attacking country
     * @param defendingCountry Country under attack
     */
    public void attackPhase(Country attackingCountry, Country defendingCountry) {
        if (attackingCountry != null && defendingCountry != null) {

            boolean playerCanAttack = isAttackMoveValid(attackingCountry, defendingCountry);

            if (playerCanAttack) {
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

    /**
     * Method governing the fortification phase
     *
     * @param selectedCountry Source country, from which armies would be taken
     * @param adjCountry      Destination country, to which armies would be allocated
     * @param terminalWindow  TextArea to which current game information will be displayed
     */

    public void fortificationPhase(Country selectedCountry, Country adjCountry, TextArea terminalWindow) {
        this.terminalWindow = terminalWindow;
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
                setChanged();
                notifyObservers(armies + " armies placed on " + adjCountry.getName() + " country.\n");
                //WindowUtil.updateTerminalWindow(armies + " armies placed on " + adjCountry.getName() + " country.\n", terminalWindow);
                setChanged();
                notifyObservers("Fortification phase ended. \n");
                //WindowUtil.updateTerminalWindow("Fortification phase ended. \n", terminalWindow);
                setChanged();
                notifyObservers("Reinforcement");
            }
        } else {
            WindowUtil.popUpWindow("Invalid number", "Fortification Phase popup", "Please enter a valid number");
            return;
        }
    }

    /**
     * Method to check if the fortification move taking place in fortification is valid or not
     *
     * @param map           MapIO object
     * @param playerPlaying current player
     * @return true if the move is valid; otherwise false
     */
    public boolean isFortificationPhaseValid(MapIO map, Player playerPlaying) {
        boolean isFortificationAvaialble = false;
        outer:
        for (Continent continent : map.getMapGraph().getContinents().values()) {
            for (com.risk.model.Country Country : continent.getListOfCountries()) {
                if (Country.getPlayer().equals(playerPlaying)) {
                    if (Country.getNoOfArmies() > 1) {
                        for (com.risk.model.Country adjCountry : Country.getAdjacentCountries()) {
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

    /**
     * Method for placing armies on the countries during the startup phase.
     *
     * @param playerPlaying       current Player.
     * @param selectedCountryList List view for the countries of the current player.
     * @param gamePlayerList      List of all the players of playing the game.
     * @param terminalWindow      TextArea to which current game information will be displayed
     */
    public void placeArmyOnCountry(Player playerPlaying, ListView<Country> selectedCountryList, List<Player> gamePlayerList, TextArea terminalWindow) {
        this.terminalWindow = terminalWindow;
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
            setChanged();
            notifyObservers("StartUp Phase Completed.\n");
            //WindowUtil.updateTerminalWindow("StartUp Phase Completed.\n", terminalWindow);
            setChanged();
            notifyObservers("FirstAttack");
        } else {
            setChanged();
            notifyObservers("placeArmyOnCountry");
        }
    }

    /**
     * Method to check if the player has armies or not.
     *
     * @param allPlayers List of all the players of playing the game.
     * @return true if player has armies left; otherwise false.
     */


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

    /**
     * Method to check if the attack move is valid or not
     *
     * @param attacking Country attacking
     * @param defending Country under attack
     * @return true if the attack move is valid; other wise false
     */

    public boolean isAttackMoveValid(Country attacking, Country defending) {
        boolean isValidAttackMove = false;
        if (defending.getPlayer() != attacking.getPlayer()) {
            if (attacking.getNoOfArmies() > 1) {
                isValidAttackMove = true;
            } else {
                WindowUtil.popUpWindow("Select a country with more armies.", "Invalid game move", "There should be more than one army on the country which is attacking.");
            }
        } else {
            WindowUtil.popUpWindow("You have selected your own country", "Invalid game move", "Select another player's country to attack");
        }
        return isValidAttackMove;
    }

    /**
     * Method to check if the player can attack or not.
     *
     * @param countries      List view of all the countries of the player
     * @param terminalWindow TextArea to which current game information will be displayed
     * @return true if the player can attack; other wise false
     */

    public boolean playerCanAttack(ListView<Country> countries, TextArea terminalWindow) {
        this.terminalWindow = terminalWindow;
        boolean canAttack = false;
        for (com.risk.model.Country Country : countries.getItems()) {
            if (Country.getNoOfArmies() > 1) {
                canAttack = true;
            }
        }
        if (!canAttack) {
            setChanged();
            notifyObservers("Player cannot continue with attack phase, move to fortification phase.\n");
            //WindowUtil.updateTerminalWindow("Player cannot continue with attack phase, move to fortification phase.\n", terminalWindow);
            setChanged();
            notifyObservers("Attack phase ended\n");
            //WindowUtil.updateTerminalWindow("Attack phase ended\n", terminalWindow);
            setChanged();
            notifyObservers("checkIfFortificationPhaseValid");
            return canAttack;
        }
        return canAttack;
    }

    /**
     * Method to check if any player lost the game after every attack move
     *
     * @param playersPlaying List containing all the players playing the game
     * @return Player object of the lost player
     */

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

    /**
     * Methods for exchanging cards of the player for armies
     *
     * @param selectedCards            List of selected cards by the player
     * @param numberOfCardSetExchanged Number of card sets to be exchanged
     * @param terminalWindow           TextArea to which current game information will be displayed
     * @return Player object exchanging the cards
     */

    public Player exchangeCards(List<Card> selectedCards, int numberOfCardSetExchanged, TextArea terminalWindow) {
        this.terminalWindow = terminalWindow;
        currentPlayer.setArmyCount(currentPlayer.getArmyCount() + (5 * numberOfCardSetExchanged));
        setChanged();
        notifyObservers(currentPlayer.getName() + " successfully exchanged 3 cards for 1 army! \n");
        //WindowUtil.updateTerminalWindow(currentPlayer.getName() + " successfully exchanged 3 cards for 1 army! \n", terminalWindow);

        for (Card card : selectedCards) {
            if (currentPlayer.getPlayerCountries().contains(card.getCountry())) {
                card.getCountry().setNoOfArmies(card.getCountry().getNoOfArmies() + 2);
                setChanged();
                notifyObservers(currentPlayer.getName() + "\" got extra 2 armies on \"" + card.getCountry().getName() + "\n");
                //WindowUtil.updateTerminalWindow(currentPlayer.getName() + "\" got extra 2 armies on \"" + card.getCountry().getName() + "\n", terminalWindow);
                break;
            }
        }
        return currentPlayer;
    }

    /**
     * Setter for setting the current player
     *
     * @param playerPlaying current player
     */
    public static void setPlayerPlaying(Player playerPlaying) {
        Player.currentPlayer = playerPlaying;
    }

    /**
     * Getter for number of countries won by the player
     *
     * @return countryWonCount
     */

    public int getCountryWon() {
        return CountryWon;
    }

    /**
     * Setter for countries won by the player
     *
     * @param CountryWon number of countries won by the player
     */
    public void setCountryWon(int CountryWon) {
        this.CountryWon = CountryWon;
    }

    /**
     * update method for PLayer object
     *
     * @param o   Observable
     * @param arg String which is passed t the player object
     */
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
