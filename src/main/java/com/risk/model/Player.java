package com.risk.model;

import java.io.IOException;
import java.util.*;

import com.risk.services.MapIO;
import com.risk.services.MapUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * Player class which provides the information regarding the player.
 * 
 * @author Ruthvik Shandilya
 * @author Palash Jain
 *
 */
public class Player extends Observable implements Observer {

    static Player currentPlayer;

	/** Name of the Player */
    private String name;
    
    /**Initial army count of the Player*/
    private int armyCount=0;
    
    /** List of countries held by the Player */
    private ArrayList<Country> myCountries;
    
    /** List of cards the Player holds.*/
    private ArrayList<Card> listOfCards;
    
    /** Player constructor */
    public Player(String name) {
        this.name = name;
        this.myCountries = new ArrayList<>();
        this.listOfCards = new ArrayList<>();
    }
    
    /**
     * Get the name of the Player.
     * 
     * @return name
     *			Player name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set the name of the Player
     * 
     * @param name
     * 			Player name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Method to get the Count of the Army that the Player Holds.
     * 
     * @return armies count
     */
    public int getArmyCount() {
        return armyCount;
    }
    
    /**
     * Method to set the count of the Army
     * 
     * @param armyCount Count of the Army
     * 
     */
    public void setArmyCount(int armyCount) {
        this.armyCount = armyCount;
    }
    
    /**
     * Get the list of countries the player holds
     * 
     * @return
     * 		list of countries
     */
    public ArrayList<Country> getMyCountries() {
        return myCountries;
    }
    
    /**
     * Method to set the country list for player
     * 
     * @param myCountries list of countries
     * 				
     */
    public void setMyCountries(ArrayList<Country> myCountries) {
        this.myCountries = myCountries;
    }
    
    /**
     *  Method to add a single country to the player's country list
     *  
     * @param country
     * 				Object of the country
     */
    public void addCountry(Country country){
        this.myCountries.add(country);
    }
    
    /**
     * Method to get the list of cards the player holds
     * 
     * @return list of cards
     */
    public ArrayList<Card> getListOfCards() {
        return listOfCards;
    }
    
    /**
     * Method to set the list of card of the particular type
     * 
     * @param listOfCards Types of cards
     */
    public void setListOfCards(ArrayList<Card> listOfCards) {
        this.listOfCards = listOfCards;
    }


	/**
     * Method to add the armies to a country
     * 
     * @param country
     * 				name of the country
     * @param numberOfArmies
     * 				number of armies to be added
     */
    public void addArmiesToCountry(Country country, int numberOfArmies) {
    	if(this.getArmyCount()>0 && this.getArmyCount()>=numberOfArmies) {
    		if(!this.getMyCountries().contains(country)) {
    			System.out.println("This country is not under your territory.");
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

    /**
     * the @CountryWon
     */
    private int CountryWon;

    /**
     * This method is used to assign armies to players and display data in the
     * textarea in UI.
     * @param players players list
     * @param textArea textarea
     * @return isAssignationSuccess boolean true if armies assign.
     */
    public boolean assignArmiesToPlayers(List<Player> players, TextArea textArea) {
        //MapUtil.appendTextToGameConsole("===Assigning armies to players.===\n", textArea);
        boolean isAssignationSuccess = false;
        int armySizePerPlayer = 0;
        int noOfPlayers = players.size();

        if (noOfPlayers == 3)
            armySizePerPlayer = 35;
        else if (noOfPlayers == 4)
            armySizePerPlayer = 30;
        else if (noOfPlayers == 5)
            armySizePerPlayer = 25;
        else if (noOfPlayers == 6)
            armySizePerPlayer = 20;

        for (Player player : players) {
            player.setArmyCount(armySizePerPlayer);
            //MapUtil.appendTextToGameConsole(player.getName() + " assigned: " + armySizePerPlayer + "\n", textArea);
            isAssignationSuccess = true;
        }
        return isAssignationSuccess;
    }

    /**
     * This method is used to create a number of instances of Player class.
     *
     * @param noOfPlayer
     *            user input.
     * @param players
     *            objects of class {@link Player}
     * @param textArea
     *            to show up data on UI.
     * @return players
     * 			  list of players.
     */
    public List<Player> createPlayer(int noOfPlayer, List<Player> players, TextArea textArea) {
        for (int i = 0; i < noOfPlayer; i++) {
            String name = "Player" + i;
            players.add(new Player(name));
            //MapUtil.appendTextToGameConsole(name + " created!\n", textArea);
        }
        return players;
    }

    /**
     * Calculate the number of armies for each reinforcement phase as per the Risk
     * rules
     *
     * @param playerPlaying
     *            current player playing
     * @return playerPlaying
     * 			  player updated
     */
    public Player calculateReinforcementArmies(Player playerPlaying) {

        int myCountries = playerPlaying.getMyCountries().size();
        int armiesCount = (int) Math.floor(myCountries / 3);
        HashSet<Continent> countryInContinent = new HashSet<>();
        ArrayList<Country> playerOwnedCountries = playerPlaying.getMyCountries();

        for(Country country : playerOwnedCountries){
            countryInContinent.add(country.getPartOfContinent());
        }

        boolean isPlayerOwnedContinent = true;
        // If a player owns all the countries in a continent, then armies count
        // will be equal
        // to the control value of the continent.
        for(Continent continent : countryInContinent){
            isPlayerOwnedContinent = true;
            for(Country country: continent.getListOfCountries()){
                if(!playerOwnedCountries.contains(country)){
                    isPlayerOwnedContinent = false;
                    break;
                }
            }
            if (isPlayerOwnedContinent) {
                armiesCount += continent.getControlValue();
            }
        }

        // Minimum number of armies for a player in case armies count is less
        // than 3.
        if (armiesCount < 3) {
            armiesCount = 3;
        }

        playerPlaying.setArmyCount(playerPlaying.getArmyCount() + armiesCount);

        return playerPlaying;
    }


    /**
     * Get the list of continents owned by the player.
     *
     * @param playerPlaying
     *            the player currently playing
     * @return continents
     * 			  continents owned by player.
     */
    public List<Continent> getContinentsOwnedByPlayer(Player playerPlaying) {
        List<Continent> continents = new ArrayList<>();
        HashSet<Continent> countryInContinent = new HashSet<>();
        ArrayList<Country> playerOwnedCountries = playerPlaying.getMyCountries();

        for(Country country : playerOwnedCountries){
            countryInContinent.add(country.getPartOfContinent());
        }

        boolean isPlayerOwnedContinent = true;

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

    /**
     * Reinforcement Phase
     *
     * @param gameConsole
     *            the Game Console
     */
    public void reinforcementPhase(Country country, TextArea gameConsole) {
        if (currentPlayer.getArmyCount() > 0) {
            if (country == null) {
                MapUtil.infoBox("Select a Country to place army on.", "Message", "");
                return;
            }

            Integer armies=0;
            //Integer armies = Integer.valueOf(MapUtil.inputDailougeBox());
            if (currentPlayer.getArmyCount() < armies) {
                MapUtil.infoBox("You do not have sufficent armies.", "Message", "");
                return;
            }
            country.setNoOfArmies(country.getNoOfArmies() + armies);
            currentPlayer.setArmyCount(currentPlayer.getArmyCount() - armies);
            //MapUtil.appendTextToGameConsole(armies + ": assigned to Country " + country.getName() + "\n",
            //gameConsole);
        }
        // start attack phase
        if (currentPlayer.getArmyCount() <= 0) {
            MapUtil.appendTextToGameConsole("===Reinforcement phase Ended! ===\n", gameConsole);
            setChanged();
            notifyObservers("Attack");
        }
    }

    /**
     * Attack phase
     * @param attackingCountry attacking Country
     * @param defendingCountry defending Country
     *
     */
    public void attackPhase(Country attackingCountry, Country defendingCountry)
    {
        if (attackingCountry != null && defendingCountry != null) {
            isAValidAttackMove(attackingCountry, defendingCountry);

            Dice dice = new Dice(attackingCountry, defendingCountry);
            dice.addObserver(this);
            final Stage newMapStage = new Stage();
            newMapStage.setTitle("Attack Window");

            //  DiceRollController diceController = new DiceRollController(dice);

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("DiceView.fxml"));
            //   loader.setController(diceController);

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
        } else {
            //throw new InvalidGameMoveException("Please choose both attacking and defending Country.");
            MapUtil.infoBox("Please choose both attacking and defending Country.", "Message", "");
            return;
        }
    }

    /**
     * Fortification Phase
     *
     * @param selectedCountry
     *            selected Country object
     * @param adjCountry
     *            adj Country object
     * @param gameConsole
     *            gameConsole
     */
    public void fortificationPhase(Country selectedCountry, Country adjCountry, TextArea gameConsole) {
        if (selectedCountry == null) {
            MapUtil.infoBox("Please choose Selected Country as source.", "Message", "");
            return;
        } else if (adjCountry == null) {
            MapUtil.infoBox("Please choose Adjacent Country as destination.", "Message", "");
            return;
        } else if (!(adjCountry.getPlayer().equals(currentPlayer))) {
            MapUtil.infoBox("Adjacent Country does not belong to you.", "Message", "");
            return;
        }

        Integer armies = Integer.valueOf(MapUtil.inputDialogueBoxForArmiesFortification());
        if (armies > 0) {
            if (selectedCountry.getNoOfArmies() == armies) {
                MapUtil.infoBox("You cannot move all the armies.", "Message", "");
                return;
            } else if (selectedCountry.getNoOfArmies() < armies) {
                MapUtil.infoBox("You don't have " + armies + " armies.", "Message", "");
                return;
            } else {
                selectedCountry.setNoOfArmies(selectedCountry.getNoOfArmies() - armies);
                adjCountry.setNoOfArmies(adjCountry.getNoOfArmies() + armies);
                MapUtil.appendTextToGameConsole(
                        armies + " armies fortified on Country: " + adjCountry.getName() + "\n", gameConsole);
                MapUtil.appendTextToGameConsole("=======Fortification ended=======\n", gameConsole);
                setChanged();
                notifyObservers("Reinforcement");
            }
        } else {
            MapUtil.infoBox("Invalid entry", "Message", "");
            return;
        }
    }

    /**
     * Check if there are armies to be fortified.
     *
     * @param map
     *            map object
     * @param playerPlaying
     *            current player playing
     * @return isFortificationAvaialble
     * 			  is fortification of armies available.
     */
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

    /**
     * Place Armies
     *
     * @param playerPlaying
     *            get current PlayerPlaying object
     * @param selectedCountryList
     *            get Selected Country List for List View
     * @param gamePlayerList
     *            gamePlayer List
     * @param gameConsole gameConsole
     */
    public void placeArmy(Player playerPlaying, ListView<Country> selectedCountryList, List<Player> gamePlayerList,
                          TextArea gameConsole) {
        int playerArmies = playerPlaying.getArmyCount();
        if (playerArmies > 0) {
            Country Country = selectedCountryList.getSelectionModel().getSelectedItem();
            if (Country == null) {
                Country = selectedCountryList.getItems().get(0);
            }
            Country.setNoOfArmies(Country.getNoOfArmies() + 1);
            playerPlaying.setArmyCount(playerArmies - 1);
        }

        boolean armiesExhausted = checkIfPlayersArmiesExhausted(gamePlayerList);
        if (armiesExhausted) {
            MapUtil.appendTextToGameConsole("===Setup Phase Completed!===\n", gameConsole);
            setChanged();
            notifyObservers("FirstAttack");
        } else {
            setChanged();
            notifyObservers("placeArmy");
        }
    }

    /**
     * CHeck if player armies is exhausted
     *
     * @param players
     *            player object
     * @return boolean if player armies is exhausted
     */
    public boolean checkIfPlayersArmiesExhausted(List<Player> players) {
        int count = 0;

        for (Player player : players) {
            if (player.getArmyCount() == 0) {
                count++;
            }
        }
        if (count == players.size()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if Attack Move is Valid
     *
     * @param attacking
     *            attacking Country
     * @param defending
     *            defending Country
     *
     * @return isValidAttackMove if the attack move is valid
     *
     */
    public boolean isAValidAttackMove(Country attacking, Country defending) {
        boolean isValidAttackMove = false;
        if (defending.getPlayer() != attacking.getPlayer()) {
            if (attacking.getNoOfArmies() > 1) {
                isValidAttackMove = true;
            } else {
                //throw new InvalidGameMoveException("Attacking Country should have more than one army to attack.");
                MapUtil.infoBox("Attacking Country should have more than one army to attack.",
                        "Message,","");
                return false;
            }
        } else {
            //throw new InvalidGameMoveException("You cannot attack on your own Country.");
            MapUtil.infoBox("You cannot attack on your own Country.","Message", "");
            return false;
        }
        return isValidAttackMove;
    }

    /**
     * Check if player has valid attack move
     *
     * @param territories
     *            territories List View
     * @param gameConsole
     *            gameConsole text area
     *
     * @return hasAValidMove true if player has valid move else false
     */
    public boolean playerHasAValidAttackMove(ListView<Country> territories, TextArea gameConsole) {
        boolean hasAValidMove = false;
        for (Country Country : territories.getItems()) {
            if (Country.getNoOfArmies() > 1) {
                hasAValidMove = true;
            }
        }
        if (!hasAValidMove) {
            MapUtil.appendTextToGameConsole("No valid attack move avialble move to Fortification phase.\n", gameConsole);
            MapUtil.appendTextToGameConsole("===Attack phase ended! === \n", gameConsole);
            setChanged();
            notifyObservers("checkIfFortificationPhaseValid");
            return hasAValidMove;
        }
        return hasAValidMove;
    }

    /**
     * Check if any Player Lost the Game
     *
     * @param playersPlaying
     *            playerPlaying List
     *
     * @return playerLost Player Object who lost the game
     */
    public Player checkIfAnyPlayerLostTheGame(List<Player> playersPlaying) {
        Player playerLost = null;
        for (Player player : playersPlaying) {
            if (player.getMyCountries().isEmpty()) {
                playerLost = player;
                currentPlayer.getListOfCards().addAll(playerLost.getListOfCards());
            }
        }
        return playerLost;
    }

    /**
     * This method is used to trade armies for valid combination of cards.
     *
     * @param selectedCards
     *            list of cards selected by currently playing player for exchange.
     * @param numberOfCardSetExchanged
     *            counter of number of times cards get changed.
     * @param gameConsole
     *            Console of the game.
     * @return playerPlaying player object
     */
    public Player tradeCardsForArmy(HashMap<Country, List<String>> selectedCards, int numberOfCardSetExchanged, TextArea gameConsole) {
        currentPlayer.setArmyCount(currentPlayer.getArmyCount() + (5 * numberOfCardSetExchanged));
        MapUtil.appendTextToGameConsole(currentPlayer.getName() + " successfully exchanged 3 cards for 1 army! \n",
                gameConsole);
        for (Country t : currentPlayer.getMyCountries()) {
            if (selectedCards.get(t) != null) {
                t.setNoOfArmies(t.getNoOfArmies() + 2);
                MapUtil.appendTextToGameConsole(
                        currentPlayer.getName() + " got extra 2 armies on " + t.getName() + "\n", gameConsole);
                break;
            }
        }
        return currentPlayer;
    }

    /**
     * Set Player Playing
     *
     * @param playerPlaying
     *            the playerPlaying to set
     */
    public void setPlayerPlaying(Player playerPlaying) {
        this.currentPlayer = playerPlaying;
    }

    /**
     * Get Country Won
     *
     * @return CountryWon
     */
    public int getCountryWon() {
        return CountryWon;
    }

    /**
     * Set Country Won
     *
     * @param CountryWon
     *            the CountryWon to set
     */
    public void setCountryWon(int CountryWon) {
        this.CountryWon = CountryWon;
    }

    /**
     * Update
     *
     * @param o
     *            Observable object
     * @param arg
     *            Object arg
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
