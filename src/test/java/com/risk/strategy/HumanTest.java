package com.risk.strategy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.risk.model.Country;
import com.risk.model.Player;
import com.risk.view.Util.WindowUtil;
import com.risk.view.controller.GamePlayController;

import javafx.scene.control.ListView;

public class HumanTest {

	private Country attacking;
	
	private Country defending;
	
	private Player player1;
	
	private Player player2;
	
	private Human human;
	
	private GamePlayController gamePlayController;
	
	@Before
	public void initialize() {
		
		attacking = new Country("India");
		player1 = new Player();
		attacking.setPlayer(player1);
		
		
		defending = new Country("China");
		player2 = new Player();
		defending.setPlayer(player2);
		
		gamePlayController = new GamePlayController();
		human = new Human(gamePlayController);
		
	}
	
	@Test
	public void isAttackMoveValidTest() {
		attacking.setNoOfArmies(3);
		assertTrue(human.isAttackMoveValid(attacking, defending));
	}

}
