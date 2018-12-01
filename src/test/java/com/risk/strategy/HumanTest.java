package com.risk.strategy;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.risk.controller.GamePlayController;
import com.risk.model.Country;
import com.risk.model.Player;

/**
 * Test class for Round Robin.
 * 
 * @author Neha Pal
 * @author Palash Jain
 *
 */
public class HumanTest {

	/** Object for Country class */
	private Country attacking;
	
	/** Object for Country class */
	private Country defending;
	
	/** Object for Player class */
	private Player player1;
	
	/** Object for Player class */
	private Player player2;
	
	/** Object for Human class */
	private Human human;
	
	/** Object for GamePlayController class */
	private GamePlayController gamePlayController;
	
	/**
	 * Set up the initial objects for Human class
	 * 
	 */
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
	/**
	* Test to check valid attack move.
	*/
	@Test
	public void isAttackMoveValidTest() {
		attacking.setNoOfArmies(3);
		assertTrue(human.isAttackMoveValid(attacking, defending));
	}

}
