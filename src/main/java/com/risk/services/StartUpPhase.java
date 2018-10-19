package com.risk.services;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import com.risk.model.Country;
import com.risk.model.Player;

/**
 * 
 * Class for governing the startup phase of the game. It sets up the game by
 * getting the player data, and allocating countries and armies according to the
 * rules.
 * 
 * @author Karandeep Singh
 * @author Neha Pal
 * 
 */
public class StartUpPhase {

	/** List containing name of players */
	private ArrayList<Player> listOfPlayers;

	/** Variable for MapIO object */
	private MapIO mapIO;

	/** Number of players */
	private int playerCount = 0;

	/** Minimum number of players */
	private static final int MINIMUM_PLAYER_COUNT = 2;

	/** Maximum number of players */
	private static final int MAXIMUM_PLAYER_COUNT = 6;

	/**
	 * Constructor for the StartUpPhase class. This constructors sets up the
	 * game like, getting number of payers and initializing them
	 * 
	 * @param mapIO
	 *            Object of MapIO with map contents
	 */
	public StartUpPhase(MapIO mapIO) {
		this.mapIO = mapIO;
		this.listOfPlayers = new ArrayList<Player>();
		Scanner scan = new Scanner(System.in);
		try {
			do {
				System.out.println("Enter number of players:");
				this.playerCount = Integer.parseInt(scan.nextLine());
				if (this.playerCount < 2 || this.playerCount > 6) {
					System.out.println("Number of players must be between 2 and 6.");
				}
			} while (this.playerCount < 2 || this.playerCount > 6);
		} catch (NumberFormatException e) {
			System.out.println("Invalid number format.");
		}

		System.out.println("Please enter the name of player(s).");
		for (int i = 0; i < this.playerCount; ++i) {
			Player player = new Player();
			String name = null;
			if ((name = scan.nextLine()) != null) {
				player.setName(name);
			}
			this.listOfPlayers.add(player);
		}
	}

	/**
	 * Method for getting the list of players
	 * 
	 * @return ArrayList<Player> which has the list of players.
	 */
	public ArrayList<Player> getListOfPlayers() {
		return listOfPlayers;
	}

	/**
	 * Method for setting the list of players.
	 * 
	 * @param listOfPlayers
	 *            which is the list of players needs to be set.
	 */
	public void setListOfPlayers(ArrayList<Player> listOfPlayers) {
		this.listOfPlayers = listOfPlayers;
	}

	/**
	 * Method for getting MapIO object.
	 * 
	 * @return MapIO
	 */
	public MapIO getMapIO() {
		return mapIO;
	}

	/**
	 * Method for setting MapIO reference.
	 * 
	 * @param mapIO
	 *            MapIO object.
	 */
	public void setMapIO(MapIO mapIO) {
		this.mapIO = mapIO;
	}

	/**
	 * Method for getting player count.
	 * 
	 * @return int playerCount.
	 */
	public int getPlayerCount() {
		return playerCount;
	}

	/**
	 * Method for setting player count.
	 * 
	 * @param playerCount
	 */
	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	/**
	 * Method for getting minimum number of players.
	 * 
	 * @return int MINIMUM_PLAYER_COUNT
	 */
	public static int getMinimumPlayerCount() {
		return MINIMUM_PLAYER_COUNT;
	}

	/**
	 * Method for getting maximum number of players.
	 * 
	 * @return int MAXIMUM_PLAYER_COUNT
	 */
	public static int getMaximumPlayerCount() {
		return MAXIMUM_PLAYER_COUNT;
	}

	/**
	 * Method for allocating countries to the players. Here countries are
	 * randomly assigned countries to the players
	 */
	public void countryAllocation() {
		ArrayList<Country> countries = new ArrayList<>(this.mapIO.getMapGraph().getCountrySet().values());
		while (countries.size() > 0) {
			for (int i = 0; i < this.listOfPlayers.size(); ++i) {
				if (countries.size() > 1) {
					int assignCountryIndex = new Random().nextInt(countries.size());
					this.listOfPlayers.get(i).addCountry(countries.get(assignCountryIndex));
					countries.remove(assignCountryIndex);
				} else if (countries.size() == 1) {
					this.listOfPlayers.get(i).addCountry(countries.get(0));
					countries.remove(0);
					break;
				} else {
					break;
				}
			}
		}
	}

	/**
	 * Method for allocating number of armies to the players which varies
	 * according the number of players.
	 * 
	 */
	public void armyAllocationToPlayers() {
		for (Player player : this.listOfPlayers) {
			if (this.playerCount == 2) {
				player.setArmyCount(40);
			} else if (this.playerCount == 3) {
				player.setArmyCount(35);
			} else if (this.playerCount == 4) {
				player.setArmyCount(30);
			} else if (this.playerCount == 5) {
				player.setArmyCount(25);
			} else if (this.playerCount == 6) {
				player.setArmyCount(20);
			}
		}
	}

	/**
	 * Method for allocating armies to the countries such that each country gets
	 * at least one country.
	 * 
	 */
	public void initialArmyAllocationToCountries() {
		for (Country country : mapIO.getMapGraph().getCountrySet().values()) {
			country.setNoOfArmies(1);
		}
		for (Player player : this.listOfPlayers) {
			player.setArmyCount(player.getArmyCount() - player.getMyCountries().size());
		}
	}

	/**
	 * Method for allocating armies to the countries such that number of armies
	 * on the countries remain balanced.
	 * 
	 */
	public void balanceArmyAllocationToCountries() {
		Scanner scan = new Scanner(System.in);
		for (Player player : this.listOfPlayers) {
			System.out.println("*****Player: " + player.getName() + " *****");
			for (Country country : player.getMyCountries()) {
				if (player.getArmyCount() > 0) {
					System.out.println("Number of armies currently assigned to country " + country.getName() + " is: "
							+ country.getNoOfArmies());
					System.out.println("Your available number of armies: " + player.getArmyCount());
					System.out.println(
							"Enter number of armies you want to assign to country " + country.getName() + " :");
					int count = 0;
					try {
						count = Integer.parseInt(scan.nextLine());
						player.addArmiesToCountry(country, count);
					} catch (NumberFormatException e) {
						System.out.println("Please enter a valid number.");
					}
				} else {
					break;
				}
			}
		}
	}
}
