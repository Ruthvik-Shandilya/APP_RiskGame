package com.risk.services.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.risk.services.GameUtil;
import com.risk.services.controller.Util.WindowUtil;
//import com.risk.entity.Territory;
//import com.risk.map.util.MapUtil;
import com.risk.model.Country;
import com.risk.model.Dice;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;

public class DiceController implements Initializable {

	@FXML
	private Label attackingPlayer;
	
	@FXML
	private Label attackingCountry;
	
	@FXML
	private Label attackingArmies;
	
	@FXML
	private Label defendingPlayer;
	
	@FXML
	private Label defendingCountry;
	
	@FXML
	private Label defendingArmies;
	
	@FXML
	private Label winnerName;
	
	@FXML
	private CheckBox dice1_Attacker;
	
	@FXML
	private CheckBox dice2_Attacker;
	
	@FXML
	private CheckBox dice3_Attacker;
	
	@FXML
	private CheckBox dice1_Defender;
	
	@FXML
	private CheckBox dice2_Defender;	
	
	@FXML
	private Button startRoll;
	
	@FXML
	private Button cancelAttack;
	
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
		attackingArmies.setText("Availaible armies are: " + countryAttacking.getNoOfArmies());
		
		Country countryDefending = dice.getDefendingCountry();
		defendingPlayer.setText(countryDefending.getPlayer().getName());
		defendingCountry.setText(countryDefending.getName());
		defendingArmies.setText("Availaible armies are: " + countryDefending.getNoOfArmies());
		
		winnerName.setVisible(false);
		winnerName.setText("");
		
		unCheckBoxes(dice1_Attacker, dice2_Attacker, dice3_Attacker, dice1_Defender, dice2_Defender);
		enableButton(startRoll);
		disableControl(winnerName, continueRoll);
		WindowUtil.disablePane(afterAttackView);
	}
	
	
	public void diceView() {
		if (dice.getAttackingCountry().getNoOfArmies() >= 4) {
			WindowUtil.showCheckBox(dice1_Attacker, dice2_Attacker, dice3_Attacker);
		} else if (dice.getAttackingCountry().getNoOfArmies() >= 3) {
			WindowUtil.showCheckBox(dice1_Attacker, dice2_Attacker);
			WindowUtil.hideButtonControl(dice1_Attacker);
		} else if (dice.getAttackingCountry().getNoOfArmies() >= 2) {
			WindowUtil.showCheckBox(dice1_Attacker);		
			WindowUtil.hideButtonControl(dice2_Attacker, dice3_Attacker);
		}
		if (dice.getDefendingCountry().getNoOfArmies() > 2) {
			WindowUtil.showCheckBox(dice1_Defender, dice2_Defender);	
		} else if (dice.getDefendingCountry().getNoOfArmies() >= 1) {
			WindowUtil.showCheckBox(dice1_Defender);	
			WindowUtil.hideButtonControl(dice2_Attacker);
		}
	}
	
	
	
	
	
	
	
	@FXML
	private void moveArmies(ActionEvent event) {
		
		String getText = numberOfArmiesToMove.getText();
		
		if(getText.length() == 0){
			WindowUtil.userInfo("Armies Alert", " Title", "Please enter a valid number to move armies.");
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
	private void cancelAttack(ActionEvent event) {
		dice.cancelDiceThrow();
		GameUtil.closeScreen(cancelAttack);
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
			WindowUtil.userInfo("Head", "Message", "Atleast one attacking dice should be selected");
			return;
		} else if (!dice1_Defender.isSelected() && !dice2_Defender.isSelected()) {
			WindowUtil.userInfo("Head", "Message", "Atleast one defender dice should be selected");
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
			WindowUtil.hideButtonControl(startRoll, continueRoll, cancelAttack);
		} else if (countryAttacking.getNoOfArmies() < 2) {
			diceResult.add(countryAttacking.getPlayer().getName() + " lost the match");
			WindowUtil.disableButtonControl(startRoll, continueRoll);
		} else {
			WindowUtil.disableButtonControl(startRoll);
//			GameUtil.disableControl(roll);
			WindowUtil.enableButtonControl(continueRoll);
			
		}
		defendingArmies.setText("Armies: " + String.valueOf(defendingCountry.getNoOfArmies()));
		attackingArmies.setText("Armies: " + String.valueOf(countryAttacking.getNoOfArmies()));
		winnerName.setText(diceResult.toString());
		winnerName.setVisible(true);
	}
	
	
	public static void unCheckBoxes(CheckBox... checkBoxes) {
		for (CheckBox checkBox: checkBoxes) {
//			checkBox.setText("");
			checkBox.setSelected(false);
		}
	}
	
	public static void enableButton(Control button) {
		button.setDisable(false);
	}
	
	public static void disableControl(Control... controls) {
		for (Control control : controls) {
			control.setDisable(true);
		}
	}
	
}
