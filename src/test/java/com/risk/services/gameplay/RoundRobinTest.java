package com.risk.services.gameplay;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import com.risk.model.Player;

/**
 * Test class for Round Robin.
 * 
 * @author Farhan Shaheen
 *
 */

public class RoundRobinTest {

	/** Object for RoundRobin class */
	private RoundRobin roundRobin;

	/** Object for Player class */
	Player player;
	
	/** ArrayList to hold list of players in the game */
	private ArrayList<Player> listOfPlayers;
	
	/**
	 * Set up the initial objects for Round Robin Phase
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {

		listOfPlayers = new ArrayList<Player>();
		
		player = new Player();
		player.setName("ONE");
		listOfPlayers.add(player);
		
		player = new Player();
		player.setName("TWO");
		listOfPlayers.add(player);
		
		player = new Player();
		player.setName("THREE");
		listOfPlayers.add(player);
		
		player = new Player();
		player.setName("FOUR");
		listOfPlayers.add(player);
		
		roundRobin = new RoundRobin(listOfPlayers);
	}

	/**
	* Test to return the next player in round robin fashion.
	* @throws Exception
	*/
	@Test
	public void testnext() throws Exception{

		assertEquals("ONE", roundRobin.next().getName());
		assertEquals("TWO", roundRobin.next().getName());
		assertEquals("THREE", roundRobin.next().getName());
		assertEquals("FOUR", roundRobin.next().getName());
	}
}
