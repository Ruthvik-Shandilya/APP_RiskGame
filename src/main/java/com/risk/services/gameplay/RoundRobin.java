package com.risk.services.gameplay;

import java.util.ArrayList;
import java.util.Iterator;

import com.risk.model.Player;

/**
 * RoundRobin approach to iterate the Player turns.
 * 
 * @author Karandeep Singh
 *
 */
public class RoundRobin {
	
	/** Player Iterator */
	private Iterator<Player> iterator;
	
	/** List of Players */
	private ArrayList<Player> listOfPlayers;
	
	/**
	 * RoundRobin Constructor
	 * 
	 * @param listOfPlayers
	 */
	public RoundRobin(ArrayList<Player> listOfPlayers) {
		this.listOfPlayers = listOfPlayers;
		this.iterator = listOfPlayers.iterator();
	}
	
	/**
	 * Method to pass on the turn to next player.
	 * 
	 * @return
	 * 		Player turn
	 */
	public Player next() {
		if(!this.iterator.hasNext()) {
			this.iterator = this.listOfPlayers.iterator();
		}
		return this.iterator.next();
	}
}
