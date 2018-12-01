package com.risk.model;

import com.risk.services.MapIO;
import com.risk.strategy.*;
import com.risk.strategy.Random;
import com.risk.services.Util.WindowUtil;
import com.risk.controller.GamePlayController;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.io.Serializable;
import java.util.*;

/**
 * Class for the player object for the class
 *
 * @author Karandeep Singh
 * @author Palash Jain
 */
public class Player extends Observable implements Observer,Serializable {

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

    private String playerType;

    private PlayerBehaviour playerBehaviour;

    /**
     * Number of countries won by the player
     */
    private int CountryWon;

    /**
     * Player constructor, initializes initial army count
     */
    public Player() {
        armyCount = 0;
    }
    
    public Player(String name) {
    	this.name=name;
    	this.cardList = new ArrayList<>();
    }

    /**
     * Player constructor
     *
     * @param name name
     */
    public Player(String name, String playerType, GamePlayController gamePlayController) {
        armyCount = 0;
        this.name = name;
        this.playerType = playerType;
        this.playerCountries = new ArrayList<>();
        this.cardList = new ArrayList<>();
        if (playerType.equals(IPlayerType.AGGRESSIVE))
            this.playerBehaviour = new Aggressive(gamePlayController);
        else if (playerType.equals(IPlayerType.BENEVOLENT))
            this.playerBehaviour = new Benevolent(gamePlayController);
        else if (playerType.equals(IPlayerType.CHEATER))
            this.playerBehaviour = new Cheater(gamePlayController);
        else if (playerType.equals(IPlayerType.HUMAN))
            this.playerBehaviour = new Human(gamePlayController);
        else if (playerType.equals(IPlayerType.RANDOM))
            this.playerBehaviour = new Random(gamePlayController);
        this.addObserver(gamePlayController);
    }

    public Player(String name, String playerType) {
        armyCount = 0;
        this.name = name;
        this.playerType = playerType;
        this.playerCountries = new ArrayList<>();
        this.cardList = new ArrayList<>();
        if (playerType.equals(IPlayerType.AGGRESSIVE))
            this.playerBehaviour = new Aggressive();
        else if (playerType.equals(IPlayerType.BENEVOLENT))
            this.playerBehaviour = new Benevolent();
        else if (playerType.equals(IPlayerType.CHEATER))
            this.playerBehaviour = new Cheater();
//        else if (playerType.equals(IPlayerType.HUMAN))
//            this.playerBehaviour = new Human();
        else if (playerType.equals(IPlayerType.RANDOM))
            this.playerBehaviour = new Random();
//        new WindowUtil(this);
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

    public String getPlayerType() {
        return playerType;
    }
    

    public void setPlayerBehaviour(PlayerBehaviour playerBehaviour) {
		this.playerBehaviour = playerBehaviour;
	}

	public PlayerBehaviour getPlayerBehaviour() {
        return playerBehaviour;
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
     * Method for allocating initial armies to the player,
     * depending upon the total number of players
     *
     * @param players List of all the players
     * @return true, is armies are successfully assigned,; otherwise false
     */

    public boolean assignArmiesToPlayers(List<Player> players) {
        boolean isSuccessfulAssignment = false;
        int armiesPerPlayer = 0;

        if (players.size() == 3) {
            armiesPerPlayer = 35;
        } else if (players.size() == 4) {
            armiesPerPlayer = 30;
        } else if (players.size() == 5) {
            armiesPerPlayer = 25;
        } else if (players.size() == 6) {
            armiesPerPlayer = 20;
        }

        for (int playerNumber = 0; playerNumber < players.size(); playerNumber++) {
            players.get(playerNumber).setArmyCount(armiesPerPlayer);
            System.out.println(armiesPerPlayer + " armies assigned to " + players.get(playerNumber).getName() + ".\n");
            setChanged();
            notifyObservers(armiesPerPlayer + " armies assigned to " + players.get(playerNumber).getName() + ".\n");
            isSuccessfulAssignment = true;
        }

        return isSuccessfulAssignment;
    }


    /**
     * Method for generating players according to the data entered by the user
     *
     * @param hm                 Map of all the player details
     * @param gamePlayController GamePlayController object as observer
     * @return List of player objects
     */
    public ArrayList<Player> generatePlayer(HashMap<String, String> hm, GamePlayController gamePlayController) {

        ArrayList<Player> listPlayer = new ArrayList<>();
        for (Map.Entry<String, String> playerEntry : hm.entrySet()) {
            listPlayer.add(new Player(playerEntry.getKey().trim(), playerEntry.getValue(), gamePlayController));
            System.out.println("Created player " + playerEntry.getKey().trim() + ".\n");
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
        currentPlayer.setArmyCount(currentPlayer.getArmyCount() + currentPlayer.findNoOfArmies(currentPlayer));
        System.out.println("Total number of armies available to player " + currentPlayer.getName() + ": " + currentPlayer.getArmyCount() + "\n");
        setChanged();
        notifyObservers("Total number of armies available to player " + currentPlayer.getName() + ": " + currentPlayer.getArmyCount() + "\n");
        return currentPlayer;
    }

    public int findNoOfArmies(Player player) {
        int noOfCountries = player.getPlayerCountries().size();
        int numberOfArmies = (int) Math.floor(noOfCountries / 3);
        HashSet<Continent> countryInContinent = new HashSet<>();
        ArrayList<Country> playerOwnedCountries = player.getPlayerCountries();

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
        System.out.println("Player " + player.getName() + " has been assigned " + numberOfArmies + " armies.\n");
        setChanged();
        notifyObservers("Player " + player.getName() + " has been assigned " + numberOfArmies + " armies.\n");

        return numberOfArmies;
    }

    /**
     * Method governing the reinforcement phase.
     *
     * @param countries  countries Observable List
     * @param country    country to which reinforcement armies are to be assigned
     * @param playerList list of players
     */
    public void reinforcementPhase(ObservableList<Country> countries, Country country, List<Player> playerList) {
        currentPlayer.getPlayerBehaviour().reinforcementPhase(countries, country, currentPlayer);
        if (currentPlayer.getArmyCount() <= 0 && playerList.size() > 1) {
            System.out.println("Reinforcement Phase Ended\n");
            setChanged();
            notifyObservers("Reinforcement Phase Ended\n");
            setChanged();
            notifyObservers("Attack");
        }
    }

    /**
     * Method governing the attack phase
     *
     * @param attackingCountries attacking country list
     * @param defendingCountries defending country list
     * @param playerList         list of players
     */
    public void attackPhase(ListView<Country> attackingCountries, ListView<Country> defendingCountries,
                            List<Player> playerList) {
        currentPlayer.getPlayerBehaviour().attackPhase(attackingCountries, defendingCountries, currentPlayer);
        if (!(currentPlayer.getPlayerBehaviour() instanceof Human) && playerList.size() > 1) {
            System.out.println(currentPlayer.getName() + " player with " + currentPlayer.getPlayerType() +
                    " strategy is going to call skipAttack after doing attack.\n");
            setChanged();
            notifyObservers(currentPlayer.getName() + " player with " + currentPlayer.getPlayerType() +
                    " strategy is going to call skipAttack after doing attack.\n");
            setChanged();
            notifyObservers("skipAttack");
        }
    }

    /**
     * Method governing the fortification phase
     *
     * @param selectedCountries selected countries list
     * @param adjCountries      adjacent countries list
     * @param playerList        list of players
     */

    public void fortificationPhase(ListView<Country> selectedCountries, ListView<Country> adjCountries,
                                   List<Player> playerList) {
        boolean success = currentPlayer.getPlayerBehaviour().fortificationPhase(selectedCountries, adjCountries, currentPlayer);
        if (success && playerList.size() > 1) {
            System.out.println("Fortification phase ended. \n");
            setChanged();
            notifyObservers("Fortification phase ended. \n");
            setChanged();
            notifyObservers("Reinforcement");
        }
    }

    /**
     * Method to check if the fortification move taking place in fortification is valid or not
     *
     * @param mapIO         MapIO object
     * @param currentPlayer current player
     * @return true if the move is valid; otherwise false
     */
    public boolean isFortificationPhaseValid(MapIO mapIO, Player currentPlayer) {
        boolean flag = currentPlayer.getPlayerBehaviour().isFortificationPhaseValid(mapIO, currentPlayer);
        if (flag) {
            setChanged();
            notifyObservers("Fortification");
        } else {
            setChanged();
            notifyObservers("noFortificationMove");
        }
        return flag;
    }

    /**
     * Method for placing armies on the countries during the startup phase.
     *
     * @param selectedCountryList List view for the countries of the current player.
     * @param gamePlayerList      List of all the players of playing the game.
     */
    public void placeArmyOnCountry(ListView<Country> selectedCountryList, List<Player> gamePlayerList) {
        if (this.getPlayerBehaviour() instanceof Human) {
            int playerArmies = currentPlayer.getArmyCount();
            if (playerArmies > 0) {
                Country Country = selectedCountryList.getSelectionModel().getSelectedItem();
                if (Country == null) {
                    Country = selectedCountryList.getItems().get(0);
                }
                Country.setNoOfArmies(Country.getNoOfArmies() + 1);
                this.setArmyCount(playerArmies - 1);
            }
        } else {
            automaticAssignPlayerArmiesToCountry(this);
        }

        boolean armiesExhausted = isPlayerArmyLeft(gamePlayerList);
        if (armiesExhausted) {
            System.out.println("StartUp Phase Completed.\n");
            setChanged();
            notifyObservers("StartUp Phase Completed.\n");
            setChanged();
            notifyObservers("FirstAttack");
        } else {
            setChanged();
            notifyObservers("placeArmyOnCountry");
        }
    }

    public void automaticAssignPlayerArmiesToCountry(Player currentPlayer) {
        if (currentPlayer.getArmyCount() > 0) {
            Country country = currentPlayer.getPlayerCountries()
                    .get(new java.util.Random().nextInt(currentPlayer.getPlayerCountries().size()));
            country.setNoOfArmies(country.getNoOfArmies() + 1);
            currentPlayer.setArmyCount(currentPlayer.getArmyCount() - 1);
            System.out.println("Player " + currentPlayer.getName() + " , Country " + country.getName() + " has been assigned one army.");
            setChanged();
            notifyObservers("Country " + country.getName() + " has been assigned one army.\n");
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
     * Method to check if the player can attack or not.
     *
     * @param attackingCountries List view of all the countries of the player
     * @return true if the player can attack; other wise false
     */

    public boolean playerCanAttack(ListView<Country> attackingCountries) {
        boolean canAttack = currentPlayer.getPlayerBehaviour().playerCanAttack(attackingCountries);
        if (!canAttack) {
            setChanged();
            notifyObservers("checkIfFortificationPhaseValid");
        }
        return canAttack;
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
     * Method to check if any player lost the game after every attack move
     *
     * @param playersPlaying List containing all the players playing the game
     * @return Player object of the lost player
     */

    public List<Player> checkPlayerLost(List<Player> playersPlaying) {
        List<Player> playersLost = new ArrayList<>();
        for (Player player : playersPlaying) {
            if (player.getPlayerCountries().isEmpty()) {
                currentPlayer.getCardList().addAll(player.getCardList());
                playersLost.add(player);
            }
        }
        return playersLost;
    }

    /**
     * Method to check if any player Won the game after every attack move
     *
     * @param playersPlaying List containing all the players playing the game
     * @return Player object of the winning player
     */
    public Player checkPlayerWon(List<Player> playersPlaying) {
        Player playerWon = null;
        if (playersPlaying.size() == 1) {
            playerWon = playersPlaying.get(0);
        }
        return playerWon;
    }

    /**
     * Methods for exchanging cards of the player for armies
     *
     * @param selectedCards            List of selected cards by the player
     * @param numberOfCardSetExchanged Number of card sets to be exchanged
     * @return Player object exchanging the cards
     */

    public Player exchangeCards(List<Card> selectedCards, int numberOfCardSetExchanged) {

        currentPlayer.setArmyCount(currentPlayer.getArmyCount() + (5 * numberOfCardSetExchanged));
        System.out.println(currentPlayer.getName() + " successfully exchanged 3 cards for " + (5 * numberOfCardSetExchanged) + " armies.\n");
        setChanged();
        notifyObservers(currentPlayer.getName() + " successfully exchanged 3 cards for " + (5 * numberOfCardSetExchanged) + " armies.\n");

        for (Card card : selectedCards) {
            if (currentPlayer.getPlayerCountries().contains(card.getCountry())) {
                card.getCountry().setNoOfArmies(card.getCountry().getNoOfArmies() + 2);
                System.out.println(currentPlayer.getName() + "\" got extra 2 armies on \"" + card.getCountry().getName() + "\n");
                setChanged();
                notifyObservers(currentPlayer.getName() + "\" got extra 2 armies on \"" + card.getCountry().getName() + "\n");
                break;
            }
        }
        return currentPlayer;
    }

    /**
     * Setter for setting the current player
     *
     * @param currentPlayer current player
     */
    public static void setPlayerPlaying(Player currentPlayer) {
        Player.currentPlayer = currentPlayer;
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
        }
        setChanged();
        notifyObservers(view);
    }
}
