package com.risk.services.gameplay;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import com.risk.model.Player;

/**
 * RoundRobin approach to iterate the Player turns.
 *
 * @author Karandeep Singh
 * @author Palash Jain
 *
 */
public class RoundRobin implements Serializable {

	/** Player Iterator */
	private Iterator<Player> iterator;

	/** List of Players */
	private ArrayList<Player> listOfPlayers;

	/**
	 * RoundRobin Constructor
	 *
	 * @param listOfPlayers list of Players
	 */
	public RoundRobin(ArrayList<Player> listOfPlayers) {
		this.listOfPlayers = listOfPlayers;
		this.iterator = listOfPlayers.iterator();
	}

	/**
	 * Method to pass on the turn to next player.
	 *
	 * @return Player turn
	 */
	public Player next() {
		if (!this.iterator.hasNext()) {
			this.iterator = this.listOfPlayers.iterator();
		}
		return this.iterator.next();
	}

	/**
	 * Method to update after player loses turn.
	 * 
	 * @param player	player object
	 */
	public void updateAfterPlayerLost(Player player){
        this.listOfPlayers.remove(player);
        this.iterator = this.listOfPlayers.iterator();
    }
}
