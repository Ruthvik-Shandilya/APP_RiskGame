package com.risk.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.risk.model.Country;
import com.risk.model.Continent;

/**
 * 
 * Map Validation class contains methods for the verification of a already map
 * file, that the user will load for starting a game.
 * 
 * 
 * @author Karandeep Singh
 * @author Ruthvik Shandilya
 * @author Palash Jain
 */
public class MapValidate {

	/** Containing set of all the continents */
	private HashMap<String, Continent> continentSetOfContinents;

	/** Continent set generated from the territories data */
	private HashMap<String, Continent> continentSetOfTerritories;

	/** Containing set of all the countries */
	private HashMap<String, Country> countrySet;

	/** Containing set of all the adjacent countries to a particular country */
	private HashMap<Country, ArrayList<Country>> adjacentCountries;

	/**
	 * HashMap containing continent as key and countries belonging to continent
	 * as value
	 */
	private HashMap<Continent, HashSet<Country>> countriesInContinent;

	/** Name of the file to be validated */
	private String fileName;

	/** List containing all the mapTag data, like name of author, scroll etc. */
	private ArrayList<String> mapTagData;

	/**
	 * A no argument constructor for initializing all the data fields of the
	 * MapValidate class.
	 * 
	 */
	public MapValidate() {
		this.continentSetOfContinents = new HashMap<>();
		this.continentSetOfTerritories = new HashMap<>();
		this.adjacentCountries = new HashMap<>();
		this.countriesInContinent = new HashMap<>();
		this.mapTagData = new ArrayList<>();
		this.countrySet = new HashMap<>();
	}

	/**
	 * Method to get continent set generated from territories data.
	 * 
	 * @return HashMap<String, Continent> which is continentSetOfTerritories
	 */
	public HashMap<String, Continent> getContinentSetOfTerritories() {
		return continentSetOfTerritories;
	}

	/**
	 * Method to set continent set generated from territories data.
	 * 
	 * @param continentSetOfTerritories
	 *            which is continent set generated from territories data.
	 */
	public void setContinentSetOfTerritories(HashMap<String, Continent> continentSetOfTerritories) {
		this.continentSetOfTerritories = continentSetOfTerritories;
	}

	/**
	 * Method to get Containing set of continent.
	 * 
	 * @return HashMap<String, Continent> which is continentSetOfTerritories
	 */
	public HashMap<String, Continent> getContinentSetOfContinents() {
		return continentSetOfContinents;
	}

	/**
	 * Method to get adjacent countries to a country.
	 * 
	 * @return HashMap<Country, ArrayList<Country>> which contains adjacent
	 *         countries corresponding each country.
	 */
	public HashMap<Country, ArrayList<Country>> getAdjacentCountries() {
		return adjacentCountries;
	}

	/**
	 * Method to get countries in a continent.
	 * 
	 * @return HashMap<Continent, HashSet<Country>> which contains countries in
	 *         a continent.
	 */
	public HashMap<Continent, HashSet<Country>> getCountriesInContinent() {
		return countriesInContinent;
	}

	/**
	 * Method to get file name.
	 * 
	 * @return String fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Method to get mapTagData.
	 * 
	 * @return ArrayList<String> Contents of map details
	 */
	public ArrayList<String> getMapTagData() {
		return mapTagData;
	}

	/**
	 * Method for validation of a map file. At first it check for validity of
	 * map tag data. Then it checks for continent and control value. After
	 * receiving the the continent and control value it moves to check
	 * countries.
	 * 
	 * In territories it reads all the countries one by one, along with its x, y
	 * coordinates and continent. Then it reads the list of adjacent countries
	 * and checks for the validation, that whether the two countries are present
	 * in each others list of adjacent countries or not
	 * 
	 * 
	 * @param mapFile
	 *            String that contains name of the file to be validated.
	 * 
	 * @return true if map is validated; otherwise false
	 */
	public boolean validateMapFile(String mapFile) {
		this.fileName = mapFile;

		if (mapFile != null) {
			try (BufferedReader read = new BufferedReader(new FileReader(mapFile))) {
				String inputText = new String(Files.readAllBytes(Paths.get(mapFile)), StandardCharsets.UTF_8);
				if (!checkAllTags(inputText)) {
					System.out.println("Missing tags or wrong tags.");
					return false;
				}

				for (String line; (line = read.readLine()) != null;) {
					if (line.trim().equals("[Map]")) {
						while (!((line = read.readLine()).equals("[Continents]"))) {
							if (!line.trim().isEmpty() && !(line.contains("="))) {
								System.out.print("Invalid map configuration");
								return false;
							}
							mapTagData.add(line);
						}
					}
					if (line.equals("[Continents]")) {
						while (!((line = read.readLine()).equals("[Territories]"))) {
							Pattern pattern = Pattern.compile("[a-z, A-Z]+=[0-9]+");
							if (!line.trim().isEmpty()) {
								Matcher match = pattern.matcher(line.trim());
								if (!match.matches()) {
									if (line.trim().equals("[Territories]")) {
										break;
									}
									System.out.print("Invalid continent configuration");
									return false;
								}

								if (continentSetOfContinents.containsKey(line.split("=")[0])) {
									System.out.println("Continent is already defined.");
									return false;
								}
								Continent continent = new Continent(line.split("=")[0],
										Integer.parseInt(line.split("=")[1]));
								continentSetOfContinents.put(continent.getName(), continent);
							}
						}
					}
					if (line.equals("[Territories]")) {
						while ((line = read.readLine()) != null) {
							if (!line.trim().isEmpty()) {
								String input[] = line.split(",");
								if (continentSetOfTerritories.get(input[3].trim()) == null) {
									Continent continent = new Continent(input[3].trim(), 0);
									continent.setControlValue(
											continentSetOfContinents.get(input[3].trim()).getControlValue());
									continentSetOfTerritories.put(continent.getName(), continent);
								}
								Country country = null;
								if (!countriesInContinent.keySet().contains(continentSetOfTerritories.get(input[3]))) {
									HashSet<Country> countries = new HashSet<>();
									if (!countrySet.containsKey(input[0].trim())) {
										country = new Country(input[0].trim());
										country.setContinent(continentSetOfTerritories.get(input[3]).getName());
										country.setxValue(input[1]);
										country.setyValue(input[2]);
										countrySet.put(input[0].trim(), country);
									} else {
										country = countrySet.get(input[0].trim());
										country.setContinent(continentSetOfTerritories.get(input[3]).getName());
										country.setxValue(input[1]);
										country.setyValue(input[2]);
									}
									country.setPartOfContinent(continentSetOfTerritories.get(input[3]));
									countries.add(country);
									if (!continentSetOfTerritories.get(input[3]).getListOfCountries()
											.contains(country)) {
										continentSetOfTerritories.get(input[3]).getListOfCountries().add(country);
									}
									countriesInContinent.put(continentSetOfTerritories.get(input[3]), countries);
								} else {
									HashSet<Country> countries = getCountriesFromContinent(input[3].trim(),
											countriesInContinent);
									if (!countrySet.containsKey(input[0].trim())) {
										country = new Country(input[0].trim());
										country.setxValue(input[1]);
										country.setyValue(input[2]);
										country.setContinent(continentSetOfTerritories.get(input[3]).getName());
										countrySet.put(input[0].trim(), country);
									} else {
										country = countrySet.get(input[0].trim());
										country.setContinent(continentSetOfTerritories.get(input[3]).getName());
										country.setxValue(input[1]);
										country.setyValue(input[2]);
									}
									country.setPartOfContinent(continentSetOfTerritories.get(input[3]));
									countries.add(country);
									if (!continentSetOfTerritories.get(input[3]).getListOfCountries()
											.contains(country)) {
										continentSetOfTerritories.get(input[3]).getListOfCountries().add(country);
									}
									countriesInContinent.put(continentSetOfTerritories.get(input[3]), countries);
								}
								ArrayList<Country> countries = new ArrayList<>();
								for (int i = 4; i < input.length; ++i) {
									Country adjacentCountry;
									if (!countrySet.containsKey(input[i].trim())) {
										adjacentCountry = new Country(input[i].trim());
										countrySet.put(input[i].trim(), adjacentCountry);
									} else {
										adjacentCountry = countrySet.get(input[i].trim());
									}
									countries.add(adjacentCountry);
								}
								country.setAdjacentCountries(countries);
								adjacentCountries.put(country, countries);
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			for (Map.Entry<Continent, HashSet<Country>> countries : countriesInContinent.entrySet()) {
				if (countries.getValue().size() < 2) {
					System.out.println(countries);
					System.out.println("Number of countries in a continent is less");
					return false;
				}
			}

			for (Map.Entry<Country, ArrayList<Country>> countries : adjacentCountries.entrySet()) {
				Country checkCountry = countries.getKey();
				ArrayList<Country> neighbours = countries.getValue();
				for (Country adjacent : neighbours) {
					if (!adjacentCountries.get(adjacent).contains(checkCountry)) {
						System.out.println("Countries are not adjacent");
						return false;
					}
				}
			}
		}

		if (continentSetOfContinents.size() != continentSetOfTerritories.size()) {
			System.out.println("Number of continents defined in continents tag does not match "
					+ " with the continents defined in the territories tag");
			return false;
		}

		ConnectedGraph connected = new ConnectedGraph(new HashSet<Country>(countrySet.values()));

		if (!connected.isConnected()) {
			return false;
		}

		return true;
	}

	/**
	 * Method for getting countries corresponding to a country.
	 * 
	 * @param continent
	 *            String that is the name of the continent.
	 * @param countriesInAContinent
	 *            HashMap<Continent, HashSet<Country>> Hashmap consisting of
	 *            continents and their countries.
	 * 
	 * @return HashSet<Country> countries inside a continent.
	 */
	public HashSet<Country> getCountriesFromContinent(String continent,
			HashMap<Continent, HashSet<Country>> countriesInAContinent) {
		for (Map.Entry<Continent, HashSet<Country>> pair : countriesInAContinent.entrySet()) {
			if (pair.getKey().getName().equals(continent)) {
				return pair.getValue();
			}
		}
		return null;
	}

	/**
	 * Method for checking whether all the attributes are present in a map file
	 * or not, these attributes are mapTag data, continents and territories.
	 * 
	 * @param fileData
	 *            String that has the particular tags.
	 * 
	 * @return true if mapTagData is present; otherwise false.
	 */
	public boolean checkAllTags(String fileData) {
		if (!fileData.contains("[Map]") || countOccurrences(fileData, "[Map]") != 1) {
			return false;
		} else if (!fileData.contains("[Continents]") || countOccurrences(fileData, "[Continents]") != 1) {
			return false;
		} else if (!fileData.contains("[Territories]") || countOccurrences(fileData, "[Territories]") != 1) {
			return false;
		}

		return true;
	}

	/**
	 * Method for checking that all tags occur only once or not.
	 * 
	 * 
	 * @param input
	 *            String .
	 * @param search
	 *            String
	 * 
	 * @return int is not number of occurrences.
	 */
	public int countOccurrences(String input, String search) {
		int count = 0, startIndex = 0;
		Pattern pattern = Pattern.compile(search, Pattern.LITERAL);
		Matcher match = pattern.matcher(input);

		while (match.find(startIndex)) {
			count++;
			startIndex = match.start() + 1;
		}
		return count;
	}

	/**
	 * Method for getting all the countries in the country set.
	 * 
	 * @return HashMap<String, Country> countrySet.
	 */
	public HashMap<String, Country> getCountrySet() {
		return countrySet;
	}

	/**
	 * Method for setting continents in the containing set of continents.
	 * 
	 * @param continentSetOfContinents
	 *            HashMap containing, continents of the map.
	 */
	public void setContinentSetOfContinents(HashMap<String, Continent> continentSetOfContinents) {
		this.continentSetOfContinents = continentSetOfContinents;
	}

}
