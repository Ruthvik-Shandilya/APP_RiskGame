package com.risk.services.gameplay;

import java.util.ArrayList;
import java.util.Iterator;

import com.risk.model.Player;

public class RoundRobin {
	private Iterator<Player> iterator;
	private ArrayList<Player> listOfPlayers;
	
	public RoundRobin(ArrayList<Player> listOfPlayers) {
		this.listOfPlayers = listOfPlayers;
		this.iterator = listOfPlayers.iterator();
	}
	
	public Player next() {
		if(!this.iterator.hasNext()) {
			this.iterator = this.listOfPlayers.iterator();
		}
		return this.iterator.next();
	}
}
