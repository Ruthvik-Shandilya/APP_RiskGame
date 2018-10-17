package com.risk.services;

import com.risk.model.Continent;
import com.risk.model.Country;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
/**
 * Map Input Output to read the contents from an existing map 
 * or to create a new Map for the game.
 * 
 * @author Karandeep Singh
 * @author Palash Jain 
 */
public class MapIO {
	
	/** FileName of the existing map */
	private String fileName;

	/** List to hold the contents under the Map tag from the map */
	private ArrayList<String> mapTagData;

	/** FileName for the new map */
	private String newFileName;

	/** COMMA Delimiter */
	private static final String COMMA_DELIMITER = ",";

	/** Object of MapGraph */
	private MapGraph mapGraph;
	
	/**
	 * Constructor to load the contents of a New Map.
	 */
	public MapIO() {
		this.mapGraph = new MapGraph();
		this.mapTagData = new ArrayList<>();
	}
	
	/**
	 * Constructor to load the contents of an Existing Map.
	 * 
	 * @param map Object of MapValidate
	 */
	public MapIO(MapValidate map) {
		this.mapGraph = new MapGraph();
		this.mapGraph.setContinents(map.getContinentSetOfTerritories());
		this.mapGraph.setAdjacentCountries(map.getAdjacentCountries());
		this.mapGraph.setCountriesInContinent(map.getCountriesInContinent());
		this.fileName = map.getFileName();
		this.mapTagData = map.getMapTagData();
		this.mapGraph.setCountrySet(map.getCountrySet());
		
	}
	
	/**
	 * Method to read the data of existing Map file.
	 * 
	 * @return contents of the Map file
	 */
	public MapIO readFile() {
		return this;
	}
	
	/**
	 * Method to set the FileName
	 * 
	 * @param fileName String
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * Method to set the new FileName
	 * 
	 * @param newFileName String
	 */
	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}
	
	/**
	 * MapIO write contents to .map file
	 * 
	 * @param isNewFile boolean
	 * 
	 * @return MapIO file
	 */
	public MapIO writeToFile(boolean isNewFile) {
		File file;
		StringBuilder stringBuilder = new StringBuilder();
		if (isNewFile) {
			file = new File(new File("").getAbsolutePath() + "\\src\\main\\maps\\" + this.fileName + ".map");
		} else {
			file = new File(new File("").getAbsolutePath() + "\\src\\main\\maps\\" + this.newFileName + ".map");
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()))) {

			stringBuilder.append("[Map]\n");
			for (String line : mapTagData) {
				stringBuilder.append(line);
				stringBuilder.append("\n");
			}
			stringBuilder.append("\n");


			stringBuilder.append("[Continents]\n");
			for (Map.Entry<String, Continent> continentEntry : mapGraph.getContinents().entrySet()) {
				stringBuilder.append(continentEntry.getValue().getName() + "=" + continentEntry.getValue().getControlValue());
				stringBuilder.append("\n");
			}
			stringBuilder.append("\n");

			stringBuilder.append("[Territories]\n");
			for (Map.Entry<Country,ArrayList<Country>> adjacentCountriesEntry : mapGraph.getAdjacentCountries().entrySet()) {
				Country country = adjacentCountriesEntry.getKey();
				ArrayList<Country> neighbourList = adjacentCountriesEntry.getValue();
				String line = country.getName() + COMMA_DELIMITER + country.getxValue() + COMMA_DELIMITER + country.getyValue() + COMMA_DELIMITER +
						country.getContinent();
				for(Country adjacentCountry: neighbourList) {
					line += COMMA_DELIMITER + adjacentCountry.getName();
				}
				stringBuilder.append(line);
				
				stringBuilder.append("\n");
			}

			writer.write(stringBuilder.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	/**
	 * Object to get the MapGraph data
	 * 
	 * @return data of the MapGraph
	 */
	public MapGraph getMapGraph() {
		return mapGraph;
	}
	
	/**
	 * Method to get the mapTagData contents
	 * 
	 * @return mapTagData contents
	 */
	public ArrayList<String> getMapTagData() {
		return mapTagData;
	}
	
}