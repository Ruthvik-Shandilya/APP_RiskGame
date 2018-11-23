package com.risk.services.saveload;

import com.risk.model.Card;
import com.risk.model.Player;
import com.risk.model.PlayerWorldDomination;
import com.risk.services.MapIO;
import com.risk.services.StartUpPhase;
import javafx.fxml.FXML;

import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.Stack;

public class SaveData implements Serializable {

    public MapIO mapIO;

    public StartUpPhase startUpPhase;

    public Player playingPlayer;

    public Player playerGamePhase;

    public Card card;

    public Stack<Card> cardStack;

    public List<Player> gamePlayerList;

    @FXML
    public TextArea terminalWindow;

    @FXML
    public Label phaseView;

    public PlayerWorldDomination playerWorldDomination;

    public int numberOfCardsExchanged;

    public int attackCount = 5;

    @FXML
    public Label currentPlayer;
}
