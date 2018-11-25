package com.risk.services.saveload;

import com.risk.model.Card;
import com.risk.model.Player;
import com.risk.model.PlayerWorldDomination;
import com.risk.services.MapIO;
import com.risk.services.StartUpPhase;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

public class SaveData implements Serializable {

    public MapIO mapIO;

    public StartUpPhase startUpPhase;

    public Player playingPlayer;

   // public Player playerGamePhase;

    public Card card;

    public Stack<Card> cardStack;

    public ArrayList<Player> gamePlayerList;


    @FXML
    public TextArea terminalWindow;

    public String phaseView;

    public PlayerWorldDomination playerWorldDomination;

    public int numberOfCardsExchanged;

    public int attackCount = 5;

    @FXML
    public Label playerChosen;
}
