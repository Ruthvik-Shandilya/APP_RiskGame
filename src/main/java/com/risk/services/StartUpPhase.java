package com.risk.services;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import com.risk.model.Country;
import com.risk.model.Player;


/**
 * 
 * @author Karandeep Singh
 * @author Neha Pal 
 */

public class StartUpPhase {

	private ArrayList<Player> listOfPlayers;

	private MapIO mapIO;

	private int playerCount = 0;

	private static final int MINIMUM_PLAYER_COUNT = 2;

	private static final int MAXIMUM_PLAYER_COUNT = 6;

	public StartUpPhase(MapIO mapIO) {
		this.mapIO = mapIO;
		this.listOfPlayers = new ArrayList<Player>();
		Scanner scan = new Scanner(System.in);
		try {
			do {
				System.out.println("Enter number of players:");
				this.playerCount = Integer.parseInt(scan.nextLine());
				if(this.playerCount<2 || this.playerCount>6) {
					System.out.println("Number of players must be between 2 and 6.");
				}
			}while(this.playerCount<2 || this.playerCount>6);
		}
		catch(NumberFormatException e) {
			System.out.println("Invalid number format.");
		}

		for(int i=0; i<this.playerCount; ++i) {
			Player player = new Player();
			String name = null;
			if((name = scan.nextLine())!=null) {
				player.setName(name);
			}
			this.listOfPlayers.add(player);
		}

		scan.close();
	}

	public ArrayList<Player> getListOfPlayers() {
		return listOfPlayers;
	}

	public void setListOfPlayers(ArrayList<Player> listOfPlayers) {
		this.listOfPlayers = listOfPlayers;
	}

	public MapIO getMapIO() {
		return mapIO;
	}

	public void setMapIO(MapIO mapIO) {
		this.mapIO = mapIO;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	public static int getMinimumPlayerCount() {
		return MINIMUM_PLAYER_COUNT;
	}

	public static int getMaximumPlayerCount() {
		return MAXIMUM_PLAYER_COUNT;
	}

	public void countryAllocation() {
		ArrayList<Country> countries = new ArrayList<>(this.mapIO.getMapGraph().getCountrySet().values());
		while(countries.size()>0) {
			for(int i=0; i<this.listOfPlayers.size(); ++i) {
				if(countries.size()>1) {
					int assignCountryIndex = new Random().nextInt(countries.size());
					this.listOfPlayers.get(i).addCountry(countries.get(assignCountryIndex));
					countries.remove(assignCountryIndex);
				}
				else if (countries.size()==1){
					this.listOfPlayers.get(i).addCountry(countries.get(0));
					countries.remove(0);
					break;
				}
				else {
					break;
				}
			}	
		}
	}


	public void armyAllocationToPlayers() {
		for(Player player : this.listOfPlayers) {
			if(this.playerCount == 2) {
				player.setArmyCount(40);
			}
			else if (this.playerCount == 3){
				player.setArmyCount(35);
			}
			else if (this.playerCount == 4){
				player.setArmyCount(30);
			}
			else if (this.playerCount == 5){
				player.setArmyCount(25);
			}
			else if (this.playerCount == 6){
				player.setArmyCount(20);
			}
		}
	}

	public void initialArmyAllocationToCountries() {
		for(Country country : mapIO.getMapGraph().getCountrySet().values()) {
			country.setNoOfArmies(1);
		}
		for(Player player: this.listOfPlayers) {
			player.setArmyCount(player.getArmyCount() - player.getMyCountries().size());
		}
	}

	public void balanceArmyAllocationToCountries() {
		Scanner scan = new Scanner(System.in);
		for(Player player: this.listOfPlayers) {
			System.out.println("*****Player: " + player.getName() + " *****");
			for(Country country: player.getMyCountries()) {
				if(player.getArmyCount()>0) {
					System.out.println("Number of armies currently assigned to country " + country.getName() + " is: " + country.getNoOfArmies());
					System.out.println("Your available number of armies: " + player.getArmyCount());
					System.out.println("Enter number of armies you want to assign to country " + country.getName());
					int count = Integer.parseInt(scan.nextLine());
					player.addArmiesToCountry(country, count);
				}
				else {
					break;
				}
			}
		}
		scan.close();
	}
}
