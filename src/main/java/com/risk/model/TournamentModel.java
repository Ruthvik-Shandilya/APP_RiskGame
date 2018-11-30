package com.risk.model;

import com.risk.services.MapIO;
import com.risk.services.MapValidate;
import com.risk.services.StartUpPhase;
import com.risk.view.Util.WindowUtil;
import com.risk.view.controller.GamePlayController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class TournamentModel {

    public static boolean isTournament = false;

    public TournamentModel(){
        isTournament = true;
    }

    public File checkAndLoadMap(List<MapIO> mapList) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a Map File");
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Map File Extensions (*.map or *.MAP)", "*.map", "*.MAP"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {

            String fileName = selectedFile.getAbsolutePath();
            System.out.println("File location: " + fileName);
            MapValidate mapValidate = new MapValidate();

            if (mapValidate.validateMapFile(fileName)) {
                MapIO readMap = new MapIO(mapValidate);
                mapList.add(readMap);
                return selectedFile;
            }
            WindowUtil.popUpWindow("Invalid Map", "Problem with map file", "Please selecte another file");

        }

        return null;
    }

    public HashMap<Player, Integer> playGame(List<Player> playerList, int numberOfTurnsToPlay, int gameCount, MapIO mapIO, TextArea textArea) {

        List<Player> currentGamePlayerList = new ArrayList<>();

        Player winner;

        for(Player p: playerList){
            currentGamePlayerList.add(p);
        }



        // TEXT CONSOLE game started
        System.out.println("TEXT CONSOLE game started");
        Player player = new Player();

        HashMap<String, String> hashMapForGameController = new HashMap<>();

        for(Player p: currentGamePlayerList){
            hashMapForGameController.put(p.getName(), p.getPlayerType());
        }
        GamePlayController gamePlayController = new GamePlayController(mapIO, hashMapForGameController);
        gamePlayController.setGamePlayerList(new ArrayList<Player>());
        gamePlayController.getGamePlayerList().clear();

//    CARD    Card card = new Card();

        // Startup phase started
        System.out.println(" Startup phase started");
        StartUpPhase startUpPhase =  new StartUpPhase();
        // Assign armies to player
        // Loading cards
//   CARD     gamePlayController.setCardStack(startUpPhase.assignCardToCountry(mapIO, textArea));
//        startUpPhase.assignCardToCountry(mapIO, textArea);

        player.assignArmiesToPlayers(currentGamePlayerList);
        // Assign country to players
        startUpPhase.assignCountryToPlayer(mapIO, currentGamePlayerList);

        // Text Console armies assigned

        System.out.println("Text Console armies assigned");
        for(Player p: currentGamePlayerList){
            while(p.getArmyCount() > 0) {
                p.automaticAssignPlayerArmiesToCountry(p);
            }
            gamePlayController.getGamePlayerList().add(p);
        }
        System.out.println("Assign armies to countries of the players, startup phase complete");
        // Assign armies to countries of the players
        // Startup phase completed


        while (numberOfTurnsToPlay > 0){
            Iterator<Player> playerListIterator = currentGamePlayerList.iterator();
            while(playerListIterator.hasNext()){

                Player.setPlayerPlaying(playerListIterator.next());
//                System.out.println(Player.currentPlayer.getName());

                // Reinforcement phase
//       CARD         card.automateCardWindow(Player.currentPlayer);
                ObservableList<Country> observableListReinforcementPhase = FXCollections.observableArrayList(Player.currentPlayer.getPlayerCountries());
                player.noOfReinforcementArmies(Player.currentPlayer);

                if(Player.currentPlayer.getArmyCount() > 0){
                    Player.currentPlayer.reinforcementPhase(observableListReinforcementPhase, null, gamePlayController.getGamePlayerList());
                    System.out.println(Player.currentPlayer.getName() + " RINFOR complete");
                }
                // Reinforcement phase ended

                // Attack phase
                System.out.println("Attack phase started");

                ListView<Country> listViewOfCountries = new ListView<Country>(FXCollections.observableArrayList(Player.currentPlayer.getPlayerCountries()));
                while(Player.currentPlayer.playerCanAttack(listViewOfCountries)){
                    System.out.println("true");
                    Player.currentPlayer.getPlayerBehaviour().attackPhase(listViewOfCountries, null,  Player.currentPlayer);

                    System.out.println(Player.currentPlayer.getCountryWon() + " 8*************************");
                    List<Player> lostPlayerList = player.checkPlayerLost(currentGamePlayerList);

                    if(!lostPlayerList.isEmpty()){

                        for(Player p: lostPlayerList){
//                            System.out.println("Player Lost" + p.getName() + "**********************************");
                            currentGamePlayerList.remove(p);
                            playerListIterator = currentGamePlayerList.iterator();
                        }
                    }
//                    System.out.println(currentGamePlayerList.size() + " ******************************************888 ");

//                    for (Player p: currentGamePlayerList){
//                        System.out.println(p.getName() + " " + p.getPlayerCountries().size());
//                    }

                    winner = player.checkPlayerWon(currentGamePlayerList);
                    if(winner != null){
                        HashMap<Player, Integer> winnerMap = new HashMap<Player, Integer>();
                        winnerMap.put(winner, gameCount);
//                        System.out.println(currentGamePlayerList.size());
//                        System.out.println("Player won: " +  winner.getName());
                        return winnerMap;
                    }

//                    if(currentGamePlayerList.size() == 1){
//                        winner = currentGamePlayerList.get(0);
//                        HashMap<Player, Integer> winnerMap = new HashMap<Player, Integer>();
//                        winnerMap.put(winner, gameCount);
//                        return winnerMap;
//                    }
                }

                System.out.println("Attack phase ended");
                System.out.println("Fortification started");

                ListView<Country> listViewOfCountriesForFortification = new ListView<Country>(FXCollections.observableArrayList(Player.currentPlayer.getPlayerCountries()));
                if(player.isFortificationPhaseValid(mapIO, Player.currentPlayer)){
                    Player.currentPlayer.getPlayerBehaviour().fortificationPhase(listViewOfCountriesForFortification, null, Player.currentPlayer);
                }
                else {
                    System.out.println("No fortification move possible");
                }



            }
            numberOfTurnsToPlay--;
        }
        HashMap<Player, Integer> winnerMap = new HashMap<Player, Integer>();
        winnerMap.put(null, gameCount);
        return winnerMap;
    }
}
