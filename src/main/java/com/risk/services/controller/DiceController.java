package com.risk.services.controller;

import com.risk.services.controller.Util.WindowUtil;
import com.risk.model.Country;
import com.risk.model.Dice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * 
 * DiceController class contains the methods used in dice view,
 * load attack screen, move armies, cancel move, cancel throw,
 * continue Dice Roll, attack dice value, defence dice value 
 * and start dice roll.
 * 
 * @author Farhan Shaheen
 *
 */
public class DiceController implements Initializable {

	/** Label variable for attacking player */
	@FXML
	private Label attackingPlayer;

	/** Label variable for attacking Country */
	@FXML
	private Label attackingCountry;
	
	/** Label variable for attacking armies */
	@FXML
	private Label attackingArmies;
	
	/** Label variable for defending player */
	@FXML
	private Label defendingPlayer;
	
	/** Label variable for defending country */
	@FXML
	private Label defendingCountry;
	
	/** Label variable for defending armies */
	@FXML
	private Label defendingArmies;
	
	/** Label variable for winner name */
	@FXML
	private Label winnerName;
	
	/** Checkbox variable for dice 1 attacker */
	@FXML
	private CheckBox dice1_Attacker;
	
	/** Checkbox variable for dice 2 attacker*/
	@FXML
	private CheckBox dice2_Attacker;
	
	/** Checkbox variable for dice 3 attacker*/
	@FXML
	private CheckBox dice3_Attacker;
	
	/** Checkbox variable for dice 1 denfender*/ 
	@FXML
	private CheckBox dice1_Defender;
	
	/** Checkbox variable for dice 2 defender*/ 
	@FXML
	private CheckBox dice2_Defender;	
	
	@FXML
	private Button startRoll;
	
	@FXML
	private Button cancelThrow;
	
	@FXML
	private Button continueRoll;
	
	@FXML
	private Pane afterAttackView;
	
	@FXML
	private Button moveArmies;
	
	@FXML
	private Button cancelMove;
	
	@FXML
	private TextField numberOfArmiesToMove;
	
	private Dice dice;
	
	public DiceController(Dice dice) {
		this.dice = dice;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		loadAttackScreen();
		diceView();
		
	}

	public void  loadAttackScreen() {
		// TODO Auto-generated method stub
		
		Country countryAttacking = dice.getAttackingCountry();
		attackingPlayer.setText(countryAttacking.getPlayer().getName());
		attackingCountry.setText(countryAttacking.getName());
		attackingArmies.setText("Armies: " + countryAttacking.getNoOfArmies());
		
		Country countryDefending = dice.getDefendingCountry();
		defendingPlayer.setText(countryDefending.getPlayer().getName());
		defendingCountry.setText(countryDefending.getName());
		defendingArmies.setText("Armies: " + countryDefending.getNoOfArmies());
		
		winnerName.setVisible(false);
		winnerName.setText("");
		
		WindowUtil.unCheckBoxes(dice1_Attacker, dice2_Attacker, dice3_Attacker, dice1_Defender, dice2_Defender);
		WindowUtil.enableButtonControl(startRoll);
		WindowUtil.disableButtonControl(winnerName, continueRoll);
		WindowUtil.disablePane(afterAttackView);
	}
	
	
	public void diceView() {
		if (dice.getAttackingCountry().getNoOfArmies() >= 4) {
			WindowUtil.showCheckBox(dice1_Attacker, dice2_Attacker, dice3_Attacker);
		} else if (dice.getAttackingCountry().getNoOfArmies() == 3) {
			WindowUtil.showCheckBox(dice1_Attacker, dice2_Attacker);
			WindowUtil.hideButtonControl(dice3_Attacker);
		} else if (dice.getAttackingCountry().getNoOfArmies() == 2) {
			WindowUtil.showCheckBox(dice1_Attacker);		
			WindowUtil.hideButtonControl(dice2_Attacker, dice3_Attacker);
		}
		if (dice.getDefendingCountry().getNoOfArmies() >= 2) {
			WindowUtil.showCheckBox(dice1_Defender, dice2_Defender);	
		} else if (dice.getDefendingCountry().getNoOfArmies() == 1) {
			WindowUtil.showCheckBox(dice1_Defender);	
			WindowUtil.hideButtonControl(dice2_Defender);
		}
	}
	
	@FXML
	private void moveArmies(ActionEvent event) {
		
		String getText = numberOfArmiesToMove.getText();
		
		if(getText.length() == 0){
			WindowUtil.popUpWindow("Armies Alert", " Title", "Please enter a valid number to move armies.");
			return;
		}
		else {
			int numberOfArmies = Integer.valueOf(getText.trim());
			dice.moveArmies(numberOfArmies, winnerName, moveArmies);
		}
	}
	
	@FXML
	private void cancelMove(ActionEvent event){
		dice.skipMoveArmy();
		WindowUtil.exitWindow(cancelMove);
	}
	
	@FXML
	private void cancelThrow(ActionEvent event) {
		dice.cancelDiceThrow();
		WindowUtil.exitWindow(cancelThrow);
	}
	
	
	@FXML
	private void continueDiceRoll(ActionEvent event) {
		dice.setAttackerDiceList(new ArrayList<>());
		dice.setDefenderDiceList(new ArrayList<>());
		loadAttackScreen();
		diceView();
	}
	
	
	public void attackDiceValue(CheckBox... allCheckBoxes) {
		for (CheckBox checkBox : allCheckBoxes) {
			if (checkBox.isSelected()) {
				int diceValue = dice.generateRandomNumber();
				checkBox.setText(String.valueOf(diceValue));
				dice.getAttackerDiceList().add(diceValue);
			}
		}
	}
	
	public void defenceDiceValue(CheckBox... allCheckBoxes) {
		for (CheckBox checkBox : allCheckBoxes) {
			if (checkBox.isSelected()) {
				int diceValue = dice.generateRandomNumber();
				checkBox.setText(String.valueOf(diceValue));
				dice.getDefenderDiceList().add(diceValue);
			}
		}
	}
	
	@FXML
	public void startRoll(ActionEvent event) {
		if (!dice1_Attacker.isSelected() && !dice2_Attacker.isSelected() && !dice3_Attacker.isSelected()) {
			WindowUtil.popUpWindow("Head", "Message", "Atleast one attacking dice should be selected");
			return;
		} else if (!dice1_Defender.isSelected() && !dice2_Defender.isSelected()) {
			WindowUtil.popUpWindow("Head", "Message", "Atleast one defender dice should be selected");
			return;
		}
		attackDiceValue(dice1_Attacker, dice2_Attacker, dice3_Attacker);
		defenceDiceValue(dice1_Defender, dice2_Defender);

		ArrayList<String> diceResult = dice.getDicePlayResult();

		Country countryAttacking = dice.getAttackingCountry();
		Country defendingCountry = dice.getDefendingCountry();
		
		if (defendingCountry.getNoOfArmies() <= 0) {
			diceResult.add(countryAttacking.getPlayer().getName() + " won " + defendingCountry.getName() + " Country");
			dice.setCountriesWonCount(dice.getCountriesWonCount() + 1);
			WindowUtil.enablePane(afterAttackView);
			WindowUtil.hideButtonControl(startRoll, continueRoll, cancelThrow);
		} else if (countryAttacking.getNoOfArmies() < 2) {
			diceResult.add(countryAttacking.getPlayer().getName() + " lost the match");
			WindowUtil.disableButtonControl(startRoll, continueRoll);
		} else {
			WindowUtil.disableButtonControl(startRoll);
			WindowUtil.enableButtonControl(continueRoll);
			
		}
		defendingArmies.setText("Armies: " + String.valueOf(defendingCountry.getNoOfArmies()));
		attackingArmies.setText("Armies: " + String.valueOf(countryAttacking.getNoOfArmies()));
		winnerName.setText(diceResult.toString());
		winnerName.setVisible(true);
	}

}
