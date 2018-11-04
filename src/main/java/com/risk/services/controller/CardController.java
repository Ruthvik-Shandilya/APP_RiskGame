package com.risk.services.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import com.risk.model.Card;
import com.risk.model.Country;
import com.risk.model.ICardType;
import com.risk.model.Player;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CardController implements Initializable {
	
	private Player player;
	
	private Card card;
	
	private Label currentPlayer;
	
	private Button exchange;
	
	private ArrayList<Card> playerOwnedCards;
	
	private CheckBox[] checkBox;
	
	private VBox cardVbox;
	
	private Label text;
	
	private Button cancelView;
	
	public CardController(Player player, Card card){
		this.player=player;
		this.card=card;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		currentPlayer.setText("Cards of "+player.getName());
		playerOwnedCards = player.getListOfCards();
		if(playerOwnedCards.size() < 3){
			exchange.setDisable(true);
		}else{
			exchange.setDisable(false);
		}
	}

	
	public void loadCards(){
		int initialCardsCount = playerOwnedCards.size();
		checkBox = new CheckBox[initialCardsCount];
		for(int i = 0;i<initialCardsCount;i++){
			checkBox[i] = new CheckBox(playerOwnedCards.get(i).getCardType().toString() + " -> " +playerOwnedCards.get(i).getCountry().getName());
		}
		cardVbox.getChildren().addAll(checkBox);
	}
	
	private void cancelView(ActionEvent event){
//		GameUtil.closeScreen(cancelView);
	}
	
	private void checkExchange(ActionEvent event){
		exchange.setDisable(false);
		text.setText(null);
		List<Card> selectedCards = card.retrieveSelectedCardsFromCheckbox(playerOwnedCards, checkBox);
		
		if(selectedCards.size()==3){
			boolean flag = card.checkTradePossible(selectedCards);
			
			if(flag){
				card.setCardsExchangeable(selectedCards);
//				GameUtil.closeScreen(exchange);
			}
			else{
				text.setText("This Combination is not Valid");
				exchange.setDisable(false);
				return;
			}
		}
		else{
			text.setText("Select only 3 Cards");
			return;
		}
		
	}
}
