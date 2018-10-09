package com.risk.services;

import com.risk.model.Continent;
import com.risk.model.Country;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class MapIO {

	private HashMap<String, Continent> continents;

	private HashMap<Country, ArrayList<Country>> adjacentCountries;

	private HashMap<Continent, HashSet<Country>> countriesInContinent;

	private String fileName;

	private ArrayList<String> mapTagData;

	private String newFileName;

	private boolean isNewFile = false;

	private static final String COMMA_DELIMITER = ",";

	private HashMap<String, Country> countrySet;

	private MapGraph mapGraph;


	public MapIO(MapValidate map) {
		this.continents = map.getContinentSetOfTerritories();
		this.adjacentCountries = map.getAdjacentCountries();
		this.countriesInContinent = map.getCountriesInContinent();
		this.fileName = map.getFileName();
		this.mapTagData = map.getMapTagData();
		this.countrySet = map.getCountrySet();
		this.mapGraph = new MapGraph(this);
	}

	public MapIO(MapGraph mapGraph, String fileName,
			ArrayList<String> mapTagData, String newFileName, boolean isNewFile) {
		this.mapGraph = mapGraph;
		this.continents = mapGraph.getContinents();
		this.adjacentCountries = mapGraph.getAdjacentCountries();
		this.fileName = fileName;
		this.mapTagData = mapTagData;
		this.newFileName = newFileName;
		this.isNewFile = isNewFile;
	}

	public HashMap<Continent, HashSet<Country>> getCountriesInContinent() {
		return countriesInContinent;
	}

	public void setCountriesInContinent(HashMap<Continent, HashSet<Country>> countriesInContinent) {
		this.countriesInContinent = countriesInContinent;
	}

	public MapGraph readFile() {
		return this.mapGraph;
	}


	public MapIO writeToFile(boolean isNewFile) {
		File file;
		StringBuilder stringBuilder = new StringBuilder();
		if (this.isNewFile) {
			file = new File(new File("").getAbsolutePath() + "\\src\\main\\maps\\" + this.fileName + ".map");
		} else {
			file = new File(new File("").getAbsolutePath() + "\\src\\main\\maps\\" + this.newFileName + ".map");
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()))) {

			stringBuilder.append("[Map]");
			for (String line : mapTagData) {
				stringBuilder.append(line);
			}
			stringBuilder.append("\n");


			stringBuilder.append("[Continents]");
			for (Map.Entry<String, Continent> continentEntry : continents.entrySet()) {
				stringBuilder.append(continentEntry.getValue() + "=" + continentEntry.getValue().getControlValue());
			}
			stringBuilder.append("\n");

			stringBuilder.append("[Territories]");
			for (Map.Entry<Country,ArrayList<Country>> adjacentCountriesEntry : adjacentCountries.entrySet()) {
				Country country = adjacentCountriesEntry.getKey();
				ArrayList<Country> neighbourList = adjacentCountriesEntry.getValue();
				String line = country.getName() + COMMA_DELIMITER + country.getxValue() + COMMA_DELIMITER + country.getyValue() +
						country.getContinent();
				for(Country adjacentCountry: neighbourList) {
					line += COMMA_DELIMITER + adjacentCountry.getName();
				}
				stringBuilder.append(line);			
			}

			writer.write(stringBuilder.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public HashMap<Country, ArrayList<Country>> getAdjacentCountries() {
		return adjacentCountries;
	}

	public void setAdjacentCountries(HashMap<Country, ArrayList<Country>> adjacentCountries) {
		this.adjacentCountries = adjacentCountries;
	}

	public HashMap<String, Country> getCountrySet() {
		return countrySet;
	}

	public void setCountrySet(HashMap<String, Country> countrySet) {
		this.countrySet = countrySet;
	}

	public HashMap<String, Continent> getContinents() {
		return continents;
	}

	public void setContinents(HashMap<String, Continent> continents) {
		this.continents = continents;
	}

}